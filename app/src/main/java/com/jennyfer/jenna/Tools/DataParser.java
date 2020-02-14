package com.jennyfer.jenna.Tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.jennyfer.jenna.Adapter.AsesoriasAdapter;
import com.jennyfer.jenna.Atributos.AsesoriasAtributos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataParser extends AsyncTask<Void, Void, Integer> {
        private Context c;
        private String jsonData;
        private ListView listaAsesorias;
        private ProgressDialog pd;
        ArrayList<AsesoriasAtributos> asesorias =new ArrayList<>();


        public DataParser(Context c, String jsonData, ListView listaAsesorias) {
            this.c = c;
            this.jsonData = jsonData;
            this.listaAsesorias = listaAsesorias;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(c);
            pd.setTitle("Parse");
            pd.setMessage("Analizando ... Por favor espere");
            pd.show();
        }
        @Override
        protected Integer doInBackground(Void... params) {
            return this.parseData();
        }
        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            pd.dismiss();
            if(result==0)
            {
                Toast.makeText(c,"No se puede analizar", Toast.LENGTH_SHORT).show();
            }else {
                AsesoriasAdapter adapter = new AsesoriasAdapter(c, asesorias);
                listaAsesorias.setAdapter(adapter);
            }
        }
        private int parseData()
        {
            try
            {
                JSONArray ja = new JSONArray(jsonData);
                JSONObject jo = null;
                asesorias.clear();
                AsesoriasAtributos asesoriasAtributosA;
                for(int i=0;i<ja.length();i++)
                {
                    jo=ja.getJSONObject(i);
                    int id             = jo.getInt("id");
                    String titulo      = jo.getString("titulo");
                    String descripcion = jo.getString("descripcion");
                    String imagen      = jo.getString("imagen_default");
                    String foto        = jo.getString("imagen");
                    asesoriasAtributosA = new AsesoriasAtributos();
                    asesoriasAtributosA.setIdAsesoria(id);
                    asesoriasAtributosA.setTituloAses(titulo);
                    asesoriasAtributosA.setDescripcion(descripcion);
                    asesoriasAtributosA.setFoto(foto);
                    asesoriasAtributosA.setImagen(imagen);
                    asesorias.add(asesoriasAtributosA);
                }
                return 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return 0;
        }


}
