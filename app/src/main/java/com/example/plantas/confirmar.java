package com.example.plantas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class confirmar extends AppCompatActivity {

    ImageView avatar;

    EditText nombre;

    public static final String MAIN_TEXT = "Nombre";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar);

        avatar = findViewById(R.id.avatar);

        //
        nombre = findViewById(R.id.nombre);

        //Avatar
        Bitmap bmap = avatar.getDrawingCache();

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            bmap = extras.getParcelable("imagen");
            avatar.setImageBitmap(bmap);
        }

    }


    public void Datos(View v) {

        String nino = nombre.getText().toString();

        int errores = 0;

        int aciertos = 0;

        Intent juego = new Intent(this, juego.class);

        juego.putExtra(MAIN_TEXT, nino);

        CrearUsuario(nino, errores, aciertos);

        startActivity(juego);

    }

    private void CrearUsuario(final String nombre, final int errores, final int aciertos) {

        System.out.print("Iniciando...");

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic
                // should be here

                System.out.print("Iniciando...");

                // Create URL
                URL url = null;
                try {
                    url = new URL("https://www.ebossystems.com/game/users");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                // Create connection
                try {

                    String data = "{\"name\":  \"" + nombre + "\"," +
                            "\"errores\": " + errores + "," +
                            "\"aciertos\":  " + aciertos + "}";

                    byte[] postData       = data.getBytes( "utf-8" );
                    int    postDataLength = postData.length;

                    HttpURLConnection con =
                            (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    con.setUseCaches( false );

                    con.setRequestMethod("POST");

                    con.setRequestProperty("Content-Type", "application/json");

                    con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));

                    try(OutputStream os = con.getOutputStream()) {
                        os.write(postData);

                    }

                    try(BufferedReader br = new BufferedReader(
                            new InputStreamReader(con.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine = null;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        System.out.println(response.toString());
                    }
                    con.disconnect();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void RegresarAvatar(View v) {
        Intent regresar = new Intent(this, avatar.class);
        startActivity(regresar);
    }

}
