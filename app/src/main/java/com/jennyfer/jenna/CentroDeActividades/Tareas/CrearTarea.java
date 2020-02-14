package com.jennyfer.jenna.CentroDeActividades.Tareas;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Services.Preferences;
import com.jennyfer.jenna.Tools.VolleyRP;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.Placeholders;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.jennyfer.jenna.CentroDeActividades.Tareas.FilePath.getPath;

public class CrearTarea extends AppCompatActivity {

    //FILE MANAGER
    private TextView file_path;
    private LinearLayout lvFile;
    private Uri uri;
    public int PDF_REQ_CODE = 1;
    private String pathFile;
    private String t, d, f, h, g, a, id, di;

    private EditText titulo, descripcion, fecha, hora, autor;
    private CircleImageView profile;
    private Spinner grupo, Sdivi;
    ArrayList<String> grupL = new ArrayList<>();
    ArrayList<String> diviL = new ArrayList<>();
    private int week, day, month, year;
    private int hour, minutos;

    private VolleyRP volleyRP;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_vista);

        listaGrupo();
        listaDivision();
        AllowRunTimePermission();

        volleyRP = VolleyRP.getInstance(this);
        requestQueue = volleyRP.getRequestQueue();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Nueva tarea");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvFile = findViewById(R.id.lvArchivo);

        titulo = findViewById(R.id.titulo);
        titulo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        descripcion = findViewById(R.id.descripcion);
        descripcion.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        fecha = findViewById(R.id.fecha);
        fecha.setInputType(InputType.TYPE_NULL);
        hora = findViewById(R.id.hora);
        Sdivi = findViewById(R.id.diviSpinner);
        hora.setInputType(InputType.TYPE_NULL);
        grupo = findViewById(R.id.grupo);

        autor = findViewById(R.id.autor);
        profile = findViewById(R.id.profile);

        //FILE MANAGER
        file_path = findViewById(R.id.archivo);

        //Para el calendario
        final Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(CrearTarea.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //month = monthOfYear + 1;
                        String diaFormateado = (dayOfMonth < 10) ? 0 + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                        //String mesFormateado = (monthOfYear < 10)? 0 + String.valueOf(monthOfYear):String.valueOf(monthOfYear);
                        calendar.set(year, monthOfYear, dayOfMonth);
                        week = calendar.get(Calendar.DAY_OF_WEEK);
                        String semana = "";
                        if (week == Calendar.SUNDAY) {
                            semana = "Domingo";
                        } else if (week == Calendar.MONDAY) {
                            semana = "Lunes";
                        } else if (week == Calendar.TUESDAY) {
                            semana = "Martes";
                        } else if (week == Calendar.WEDNESDAY) {
                            semana = "Miercoles";
                            //semana = "Mi\\u00e9rcoles";
                        } else if (week == Calendar.THURSDAY) {
                            semana = "Jueves";
                        } else if (week == Calendar.FRIDAY) {
                            semana = "Viernes";
                        } else if (week == Calendar.SATURDAY) {
                            //semana = "S\\u00e1bado";
                            semana = "Sabado";
                        }

                        String mesFormateado = "";
                        if (monthOfYear == 0) {
                            mesFormateado = "enero";
                        } else if (monthOfYear == 1) {
                            mesFormateado = "febrero";
                        } else if (monthOfYear == 2) {
                            mesFormateado = "marzo";
                        } else if (monthOfYear == 3) {
                            mesFormateado = "abril";
                        } else if (monthOfYear == 4) {
                            mesFormateado = "mayo";
                        } else if (monthOfYear == 5) {
                            mesFormateado = "junio";
                        } else if (monthOfYear == 6) {
                            mesFormateado = "julio";
                        } else if (monthOfYear == 7) {
                            mesFormateado = "agosto";
                        } else if (monthOfYear == 8) {
                            mesFormateado = "septiembre";
                        } else if (monthOfYear == 9) {
                            mesFormateado = "octubre";
                        } else if (monthOfYear == 10) {
                            mesFormateado = "noviembre";
                        } else if (monthOfYear == 11) {
                            mesFormateado = "diciembre";
                        }
                        fecha.setText(semana + ", " + diaFormateado + " de " + mesFormateado + " del " + year);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        //TimePickerDialog
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);

        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog time = new TimePickerDialog(CrearTarea.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String CERO = "0";
                        String DOS_PUNTOS = ":";
                        //Formateo el hora obtenido: antepone el 0 si son menores de 10
                        String horaFormateada = (hourOfDay < 10) ? (CERO + hourOfDay) : String.valueOf(hourOfDay);
                        //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                        String minutoFormateado = (minute < 10) ? (CERO + minute) : String.valueOf(minute);
                        //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                        String AM_PM;
                        if (hourOfDay < 12) {
                            AM_PM = "a.m.";
                        } else {
                            AM_PM = "p.m.";
                        }
                        //Muestro la hora con el formato deseado
                        hora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);

                    }
                }, hour, minutos, false);
                time.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_creartarea, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.file:
                /*Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selecciona un archivo"), PDF_REQ_CODE);*/

                String[] mimeTypes =
                        {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                                "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                                "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                                "text/plain",
                                "application/pdf",
                                "application/zip"};

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                    if (mimeTypes.length > 0) {
                        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                    }
                } else {
                    String mimeTypesStr = "";
                    for (String mimeType : mimeTypes) {
                        mimeTypesStr += mimeType + "|";
                    }
                    intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
                }
                startActivityForResult(Intent.createChooser(intent, "ChooseFile"), PDF_REQ_CODE);

                return true;
            case R.id.send:
                if(uri != null) {
                    FileUploadPath();
                }else{
                    String fileEmpty = "Sin archivo";
                    g = grupo.getSelectedItem().toString();
                    di =Sdivi.getSelectedItem().toString();
                    id = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCES_USUARIO_LOGIN);
                    NuevaActividad( getStringET(titulo),
                            getStringET(descripcion),
                            getStringET(fecha),
                            getStringET(hora), g, di,
                            getStringET(autor), id,
                            fileEmpty);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void FileUploadPath() {
        if (!validarVacio()) return;

        t = getStringET(titulo);
        d = getStringET(descripcion);
        f = getStringET(fecha);
        h = getStringET(hora);
        g = grupo.getSelectedItem().toString();
        di = Sdivi.getSelectedItem().toString();
        a = getStringET(autor);
        id = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCES_USUARIO_LOGIN);

        pathFile = getPath(this, uri);
        if (pathFile == null) {
            Toast.makeText(this, "Mueva su archivo a la memoria interna del dispositivo", Toast.LENGTH_SHORT).show();
        } else{
            try {
                new MultipartUploadRequest(this, SolicitudesJSON.URL_INSERTTAREADF)
                        .addFileToUpload(pathFile, "file")
                        .addParameter("titulo", t)
                        .addParameter("descripcion", d)
                        .addParameter("fecha", f)
                        .addParameter("hora", h)
                        .addParameter("grupo", g)
                        .addParameter("division", di)
                        .addParameter("autor", a)
                        .addParameter("idUsuario", id)
                        .setNotificationConfig(new UploadNotificationConfig()
                                .setIcon(R.drawable.ic_ghost)
                                .setTitle("Tarea nueva")
                                .setCompletedMessage("Has subido una tarea nueva")
                                .setInProgressMessage("Subiendo a " + Placeholders.UPLOAD_RATE + " (" + Placeholders.PROGRESS + ")")
                                .setErrorMessage("No se pudo enviar la tarea"))
                        .setMaxRetries(5)
                        .startUpload();
                finish();
                Toast.makeText(this, "respuesta", Toast.LENGTH_SHORT).show();
            } catch (Exception exception) {
                Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void AllowRunTimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(CrearTarea.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(CrearTarea.this, "READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(CrearTarea.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {
        switch (RC) {
            case 1:
                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(CrearTarea.this,"Permission Granted", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(CrearTarea.this,"Permission Canceled", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PDF_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            //file_path.setText(uri.getPath()); //document/943 o /external_storage/Download/Manual.pdf
            file_path.setText(uri.getLastPathSegment()); //943 o Manual.pdf
            lvFile.setVisibility(View.VISIBLE);
        }
    }



//    -------------------------------------------------------

    private void listaGrupo() {
        SolicitudesJSON json = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    JSONArray jsa = j.getJSONArray("resultado");

                    for (int i = 0; i < jsa.length(); i++) {
                        JSONObject jo = jsa.getJSONObject(i);
                        String g = jo.getString("grupoTutorado");
                        grupL.add(g);
                    }

                    grupo.setAdapter(new ArrayAdapter<String>(CrearTarea.this, android.R.layout.simple_spinner_dropdown_item, grupL));

                } catch (JSONException e) {
                    Toast.makeText(CrearTarea.this, "Ocurrio un error en grupo", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void solicitudErronea() { }
        };
        String usuario = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCES_USUARIO_LOGIN);
        json.JSONGET(this, SolicitudesJSON.URL_GETGRUPO + usuario);
    }

    private void listaDivision() {
        SolicitudesJSON json = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    JSONArray jsa = j.getJSONArray("res");

                    for (int i = 0; i < jsa.length(); i++) {
                        JSONObject jo = jsa.getJSONObject(i);
                        String d = jo.getString("division");
                        diviL.add(d);
                    }

                    Sdivi.setAdapter(new ArrayAdapter<String>(CrearTarea.this, android.R.layout.simple_spinner_dropdown_item, diviL));

                } catch (JSONException e) {
                    Toast.makeText(CrearTarea.this, "Ocurrio un error en division", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void solicitudErronea() { }
        };
        String usuario = Preferences.obtenerPreferencesString(this, Preferences.PREFERENCES_USUARIO_LOGIN);
        json.JSONGET(this, SolicitudesJSON.URL_GETDIVISION + usuario);
    }

    private boolean validarVacio() {
        boolean valid = true;

        String uTitulo = titulo.getText().toString().trim();
        String uDescripcion = descripcion.getText().toString().trim();
        String ufecha = fecha.getText().toString().trim();
        String uHora = hora.getText().toString().trim();

        if (uTitulo.trim().isEmpty()) {
            titulo.setError("Escriba un titulo para la nueva actividad");
        } else {
            titulo.setError(null);
        }
        if (uDescripcion.trim().isEmpty()) {
            descripcion.setError("Es necesario detallar la acitivdad file_path crear");
            valid = false;
        } else {
            descripcion.setError(null);
        }
        if (ufecha.trim().isEmpty()) {
            fecha.setError("Se necesita una fecha límite de entrega");
            valid = false;
        } else {
            fecha.setError(null);
        }
        if (uHora.trim().isEmpty()) {
            hora.setError("Se necesita una hora límite de entrega");
        } else {
            hora.setError(null);
        }

        return valid;
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
                        autor.setText(json.getString("nombre") + " " + json.getString("paterno") + " " + json.getString("materno"));
                        json.getString("division");
                        json.getString("fecha_nacimiento");
                        json.getString("semestre");
                        json.getString("correo");
                        json.getString("tipousuario");
                        final String foto = json.getString("fotoPerfil");
                        Glide.with(getApplicationContext())
                                .load(foto)
                                .error(R.drawable.profile)
                                .into(profile);

                    }//else{}
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void solicitudErronea() {
                Toast.makeText(getApplicationContext(), "NO SE PUDO", Toast.LENGTH_SHORT).show();
            }
        };
        jsonS.JSONGET(getApplicationContext(), SolicitudesJSON.URL_USUARIOID + usuario);
    }


    public void NuevaActividad(String Stitulo, String Sdescripcion, String Sfecha, String Shora, String Sgrupo, String Sdivision,
                               String Sautor, String SidUsuario, String file){

        if (!validarVacio()) return;

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("titulo", Stitulo);
        hashMap.put("descripcion", Sdescripcion);
        hashMap.put("fecha", Sfecha);
        hashMap.put("hora", Shora);
        hashMap.put("grupo", Sgrupo);
        hashMap.put("division", Sdivision);
        hashMap.put("autor", Sautor);
        hashMap.put("idUsuario", SidUsuario);
        hashMap.put("archivo", file);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, SolicitudesJSON.URL_INSERTTAREA, new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String respuesta = datos.getString("respuesta");
                    if (respuesta.equals("200")) {
                        Toast.makeText(CrearTarea.this, "Se acaba de crear una tarea nueva", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (respuesta.equals("-1")) {
                        Toast.makeText(CrearTarea.this, "No se pudo crear", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    Toast.makeText(CrearTarea.this, "Intentalo más tarde", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearTarea.this, "No se subio la tarea", Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud, requestQueue, this, volleyRP);
    }

    private String getStringET (EditText e){ return e.getText().toString().trim(); }


}
