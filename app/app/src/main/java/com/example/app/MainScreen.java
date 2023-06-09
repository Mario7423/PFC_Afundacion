package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);  // Instancia de la Toolbar personalizada
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);  // Instancia del Layout
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); // ClickListener para el NavigationView

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);  // Usamos la toolbar
        toggle.syncState();

        if(savedInstanceState == null){  // Cargado del fragment de noticias si es la primera vez

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {  // Según lo que se pulse en el menú del NavigationView, se iniciará un Fragment diferente
        switch (item.getItemId()){

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;

            case R.id.nav_players:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PlayersFragment()).commit();
                break;

            case R.id.nav_calendar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalendarFragment()).commit();
                break;

            case R.id.nav_game:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GuessPlayerFragment()).commit();
                break;

            case R.id.nav_logout:  // En este caso, se borra las SharedPreferences y se retorna a la pantalla de inicio para cerrar la sesión
                SharedPreferences.Editor editor = getSharedPreferences("SESSIONS_APP_PREFS", MODE_PRIVATE).edit();
                editor.clear().apply();
                finish();
                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, FirstScreen.class);
                startActivity(intent);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {  // Controla el evento de retroceso
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);

        }else{

            super.onBackPressed();

        }
    }
}