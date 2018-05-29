package com.example.esmail.app_ventas;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.esmail.app_ventas.fragments.CustomersFragment;
import com.example.esmail.app_ventas.fragments.ExportedProductsFragment;
import com.example.esmail.app_ventas.fragments.InitialFragment;
import com.example.esmail.app_ventas.fragments.OrdersFragment;
import com.example.esmail.app_ventas.fragments.ProductsFragment;
import com.example.esmail.app_ventas.makesale.MakeSale;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private Class<?> mClss;

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private Fragment fragment = null;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //lanza los permisos
        launchActivity(MainActivity.class);

        mFragmentManager = getFragmentManager();

        appbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.navview);
        //cabecera
        View hView = navView.getHeaderView(0);
        //barra navegacion
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;

                        switch (menuItem.getItemId()) {
                            case R.id.menu_start:
                                fragment = new InitialFragment();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_customers:
                                fragment = new CustomersFragment();
                                fragmentTransaction = true;
                                break;
                            case R.id.menus_products:
                                fragment = new ProductsFragment();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_make_sale:
                                startActivity(new Intent(getApplicationContext(), MakeSale.class));
                                break;
                            case R.id.menu_orders:
                                fragment = new OrdersFragment();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_salir:
                                System.exit(0);
                                break;
                        }
                        //iniciar fragments
                        if (fragmentTransaction) {
                            mFragmentManager.beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .addToBackStack(null)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    /**
     * Fecha atras
     */
    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() > 1)
            mFragmentManager.popBackStack();
        else {
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        InitialFragment fragment = new InitialFragment();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Menu opciones
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            //eliminar clientes
            case R.id.deleteCustomers:
                eliminarClientes();
                return true;
            //eliminar productos
            case R.id.deleteProducts:
                eliminarProductos();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void eliminarClientes() {
        DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
        operations.eliminarClientes();
        //recarga el activity
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void eliminarProductos() {
        DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
        operations.eliminarArticulos();
        //recarga el activity
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * Metodo para recargar fragments
     *
     * @param id
     */
    public void recargarFragment(String id) {
        switch (id) {
            case "clientes":
                fragment = new CustomersFragment();
                break;
            case "articulos":
                fragment = new ProductsFragment();
                break;
            case "pedidos":
                fragment = new OrdersFragment();
                break;
        }
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Revisa los permisos
     *
     * @param clss
     */
    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * permiso de escritura
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mClss != null) {
                    }
                } else {
                    Toast.makeText(this,"No se han aceptado los permisos de escritura", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}
