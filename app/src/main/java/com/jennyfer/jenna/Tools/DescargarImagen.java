package com.jennyfer.jenna.Tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class DescargarImagen extends AsyncTask<Void, Void, String> {
        private Context c;
        private String urlAddress;
        private ListView lv;
        private ProgressDialog pd;
        public DescargarImagen(Context c, String urlAddress, ListView lv) {
            this.c = c;
            this.urlAddress = urlAddress;
            this.lv = lv;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(c);
//            pd.setTitle("Actualizando");
            pd.setMessage("Actualizando ... Por favor espere");
            pd.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            return downloadData();
        }
        @Override
        protected void onPostExecute(String jsonData) {
            super.onPostExecute(jsonData);
            pd.dismiss();
            if(jsonData==null)
            {
                Toast.makeText(c,"Sin éxito, sin datos recuperados", Toast.LENGTH_SHORT).show();
            }else {
                //PARSE
                DataParser parser=new DataParser(c,jsonData,lv);
                parser.execute();
            }
        }
        private String downloadData()
        {
            HttpURLConnection con= Conector.connect(urlAddress);
            if(con==null)
            {
                return null;
            }
            try
            {
                InputStream is=new BufferedInputStream(con.getInputStream());
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer jsonData=new StringBuffer();
                while ((line=br.readLine()) != null)
                {
                    jsonData.append(line+"n");
                }
                br.close();
                is.close();
                return jsonData.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
}
