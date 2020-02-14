package com.jennyfer.jenna;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.jennyfer.jenna.Alumno.ForoDiscusion;
import com.jennyfer.jenna.BuzonDeCorreo.ChatAlumno;
import com.jennyfer.jenna.Alumno.InicioAlumno;
import com.jennyfer.jenna.Alumno.ManualAlumno;
import com.jennyfer.jenna.CentroDeActividades.ActividadProfesor;
import com.jennyfer.jenna.CentroDeActividades.ActividadesAlumno;
import com.jennyfer.jenna.PerfilUsuario.PerfilAlumno;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.Quizz.Activities.ActivityResultados;
import com.jennyfer.jenna.Quizz.Activities.ActivityWelcome;
import com.jennyfer.jenna.Services.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

public class Inicio extends AppCompatActivity {

    private DrawerLayout drawer;
    private static final int INTERVALO = 3000; //2 segundos para salir
    private long tiempoPrimerClick;
    private Snackbar snackbar;
    private TextView tvNombre, tvCorreo;
    private ImageView fotoperfil;

    private String estudiante = "Estudiante";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hideItem();

        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
        fotoperfil = header.findViewById(R.id.image_menu);
        tvNombre = header.findViewById(R.id.nombre);
        tvCorreo = header.findViewById(R.id.correo);

        @SuppressLint("CutPasteId") NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
        }
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setupNavigationDrawerContent(navigationView);
        setFragment(0);



    }

    @Override
    public void onBackPressed() {
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()) {
            drawer.closeDrawers();
        } else {
            snackbar = Snackbar.make(findViewById(R.id.drawer_layout), "Presione dos veces para salir de la aplicación", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.pantone7421));
            snackbar.show();
        }
        tiempoPrimerClick = System.currentTimeMillis();


    }

    private void hideItem() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        String tipoUsuarioEstudiante = Preferences.obtenerPreferencesString(Inicio.this, Preferences.PREFERENCES_TIPO_USUARIO);
        if (estudiante.equals(tipoUsuarioEstudiante)) {
            nav_Menu.findItem(R.id.quizz).setVisible(true);
            nav_Menu.findItem(R.id.resultad).setVisible(false);
        } else {
            nav_Menu.findItem(R.id.quizz).setVisible(false);
            nav_Menu.findItem(R.id.resultad).setVisible(true);
        }
    }


    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected( MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.inicio:
                                menuItem.setChecked(true);
                                setFragment(0);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.perfil:
                                setFragment(1);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.chat:
                                menuItem.setChecked(true);
                                setFragment(2);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.manual:
                                menuItem.setChecked(true);
                                setFragment(3);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.centrodeactividades:
                                menuItem.setChecked(true);
                                setFragment(4);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.asesorias:
                                menuItem.setChecked(true);
                                setFragment(5);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.foro:
                                menuItem.setChecked(true);
                                setFragment(6);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.quizz:
                                menuItem.setChecked(true);
                                setFragment(7);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.resultad:
                                menuItem.setChecked(true);
                                setFragment(8);
                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                        }

                        return true;
                    }
                });
    }

    private void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                InicioAlumno inicioAlumno = new InicioAlumno();
                fragmentTransaction.replace(R.id.main, inicioAlumno);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                PerfilAlumno perfilAlumno = new PerfilAlumno();
                fragmentTransaction.replace(R.id.main, perfilAlumno);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ChatAlumno chatAlumno = new ChatAlumno();
                fragmentTransaction.replace(R.id.main, chatAlumno);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ManualAlumno manualAlumno = new ManualAlumno();
                fragmentTransaction.replace(R.id.main, manualAlumno);
                fragmentTransaction.commit();
                break;
            case 4:
                String tipousuario = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCES_TIPO_USUARIO);
                if (estudiante.equals(tipousuario)) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    ActividadesAlumno actividades = new ActividadesAlumno();
                    fragmentTransaction.replace(R.id.main, actividades);
                    fragmentTransaction.commit();
                } else {
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    ActividadProfesor actividad = new ActividadProfesor();
                    fragmentTransaction.replace(R.id.main, actividad);
                    fragmentTransaction.commit();
                }
                
                break;
            case 5:
                /*fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                AsesoriasAlumno asesoriasAlumno = new AsesoriasAlumno();
                fragmentTransaction.replace(R.id.main, asesoriasAlumno);
                fragmentTransaction.commit();*/
                Toast.makeText(this, "Espera...aún esta en desarrollo", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ForoDiscusion foro = new ForoDiscusion();
                fragmentTransaction.replace(R.id.main, foro);
                fragmentTransaction.commit();
                break;
                case 7:
                    Intent i = new Intent(Inicio.this, ActivityWelcome.class);
                    startActivity(i);
                break;
            case 8:
                Intent m = new Intent(Inicio.this, ActivityResultados.class);
                startActivity(m);
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        actualizarInformacion();
    }

    public void actualizarInformacion() {
        String usuario = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCES_USUARIO_LOGIN);
        SolicitudesJSON jsonS = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String respuesta = j.getString("resultado");
                    if (respuesta.equals("CC")) {
                        JSONObject json = new JSONObject(j.getString("datos"));

                        json.getString("id");
                        json.getString("usuario");
                        tvNombre.setText(json.getString("nombre") + " " + json.getString("paterno") + " " + json.getString("materno"));
                        json.getString("division");
                        json.getString("fecha_nacimiento");
                        json.getString("semestre");
                        tvCorreo.setText(json.getString("correo"));
                        json.getString("tipousuario");
                        json.getString("fotoPerfil");
                        final String foto = json.getString("fotoPerfil");
                        Glide.with(getApplicationContext())
                                .load(foto)
                                .error(R.drawable.student)
                                .into(fotoperfil);

                    }//else{}
                } catch (JSONException e) {
                    //Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void solicitudErronea() {
                //Toast.makeText(getApplicationContext(), "NO SE PUDO", Toast.LENGTH_SHORT).show();
            }
        };
        jsonS.JSONGET(getApplicationContext(), SolicitudesJSON.URL_USUARIOID + usuario);
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater ();
        inflater.inflate (R.menu.menu_profesor, menu);
        MenuItem item = menu.findItem(R.id.grupo);

        String tipoUser = Preferences.obtenerPreferencesString(Inicio.this, Preferences.PREFERENCES_TIPO_USUARIO);
        if (estudiante.equals(tipoUser)) { item.setVisible(false);
        } else { item.setVisible(true); }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.grupo:
                    Intent intent = new Intent(this, AddGroup.class);
                    startActivity(intent);
                    return true;
            case R.id.soporte:
                Toast.makeText(getApplicationContext(), "Soporte", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.sesion:
                AlertDialog.Builder dialog = new AlertDialog.Builder(Inicio.this);
                dialog.setCancelable(false);
                dialog.setTitle("Confirmar");
                dialog.setMessage("¿Esta seguro de cerrar sesión?" );
                dialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Preferences.savePreferencesBoolean(Inicio.this, false, Preferences.PREFERENCES_ESTADO);
                        Intent i = new Intent(Inicio.this, Login.class);
                        startActivity(i);
                        finish();
                    }
                })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Action for "Cancel".
                                dialog.cancel();
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
