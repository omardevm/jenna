package com.jennyfer.jenna.Imagen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Services.Preferences;

import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;

public class UploadPhoto extends AppCompatActivity  {

    private ImageView fp;
    private FotoManagerGotev fmg;
    private LinearLayout bottom, gal, cam;
    private BottomSheetBehavior bsb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_foto);

        bottom = findViewById(R.id.bottom);
        bsb    = BottomSheetBehavior.from(bottom);
        bsb.setState(BottomSheetBehavior.STATE_HIDDEN);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Foto de perfil");
        //toolbar.setTitleTextColor(R.color.colorTransparent);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        fp = findViewById(R.id.fotito);
        String foto = getIntent().getExtras().getString("fotoPerfil");
        Glide.with(this)
                .load(foto)
                .error(R.drawable.student)
                .into(fp);

        fmg = new FotoManagerGotev(this, SolicitudesJSON.URL_UPLOADIMAGE) {
            @Override
            public void onProgress(UploadInfo uploadInfo) {}

            @Override
            public void onError(UploadInfo uploadInfo, Exception exception) {}

            @Override
            public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {}

            @Override
            public void onCancelled(UploadInfo uploadInfo) { }
        };

        fmg.setParameterNamePhoto("file");
        fmg.setEliminarFoto(false);
        fmg.setIDUSER(Preferences.obtenerPreferencesString(this,Preferences.PREFERENCES_USUARIO_LOGIN));

        //Verificar los permisos
        FotoManagerGotev.verifyStoragePermissions(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri u = fmg.onActivityResult(requestCode, resultCode, data);
        if (u != null) {
            fp.setImageURI(u);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_upload, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit:
                if (bsb.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    //If state is in collapse mode expand it
                    bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                bsb.setState(BottomSheetBehavior.STATE_HIDDEN);}

                gal = findViewById(R.id.galeria);
                gal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fmg.subirFotoGaleria();
                        bsb.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                });

                cam = findViewById(R.id.camara);
                cam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fmg.subirFotoCamara();
                        bsb.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
