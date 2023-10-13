package com.example.examenuni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<SetModelo>list;
    SetAdaptador adapter;
    RecyclerView recyclerView;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ImageView menu;
    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.recycleView);

        menu = findViewById(R.id.menu);
        navigationView = (NavigationView)findViewById(R.id.navmenu);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);


            list = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        list.add(new SetModelo("¿Cómo vives?"));
        list.add(new SetModelo("El Arte de Resolver Problemas"));
        list.add(new SetModelo("Libro -3"));
        list.add(new SetModelo("Libro -4"));
        list.add(new SetModelo("Libro -5"));
        list.add(new SetModelo("Libro -6"));
        list.add(new SetModelo("Libro -7"));
        list.add(new SetModelo("Libro -8"));
        list.add(new SetModelo("Libro -9"));
        list.add(new SetModelo("Libro -10"));

        adapter = new SetAdaptador(list,this);
        recyclerView.setAdapter(adapter);


        header = navigationView.getHeaderView(0);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

               drawerLayout.closeDrawer(GravityCompat.START);
               }else {
                   drawerLayout.openDrawer(GravityCompat.START);
               }

            }
        });

         navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                 int itemId = item.getItemId();
                 if (itemId == R.id.home) {
                    Toast.makeText(MainActivity.this,"Home",Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                     return true;
                 } else if (itemId == R.id.compartir) {
                     Intent i = new Intent(Intent.ACTION_SEND);
                     i.setType("text/plain");
                     i.putExtra(Intent.EXTRA_SUBJECT,"Checa esta App para lectura de Libros");
                     i.putExtra(Intent.EXTRA_TEXT,"El enlace de tu aplicación aquí");
                     startActivity(Intent.createChooser(i, "Share via"));

                     return MainActivity.super.onOptionsItemSelected(item);
                 }

                 return false;

             }
         });


    }
}