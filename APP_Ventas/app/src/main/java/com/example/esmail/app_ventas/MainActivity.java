package com.example.esmail.app_ventas;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.esmail.app_ventas.fragments.CustomersFragment;
import com.example.esmail.app_ventas.fragments.ExportedProductsFragment;
import com.example.esmail.app_ventas.fragments.InitialFragment;
import com.example.esmail.app_ventas.fragments.OrdersFragment;
import com.example.esmail.app_ventas.fragments.ProductsFragment;
import com.example.esmail.app_ventas.makesale.MakeSale;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

public class MainActivity extends AppCompatActivity {

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private Fragment fragment = null;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                                finish();
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
            case R.id.deleteCustomers:
                eliminarClientes();
                return true;
            case R.id.deleteProducts:
                eliminarProductos();
                return true;
            case R.id.viewExport:
                mostrarExportados();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void mostrarExportados() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        ExportedProductsFragment fragment = new ExportedProductsFragment();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    private void eliminarClientes() {
        DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
        operations.eliminarClientes();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void eliminarProductos() {
        DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
        operations.eliminarArticulos();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


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
}
