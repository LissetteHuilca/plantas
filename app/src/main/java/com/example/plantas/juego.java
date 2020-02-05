package com.example.plantas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class juego extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;

    private ImageView tronco, raiz, hojas, flor, fruto;

    private ImageView troncoS, raizS, hojasS, florS, frutoS, siluetaF;

    float X;

    float Y;

    TextView nombre;

    EditText errores;

    EditText aciertos;

    int correct = 0;

    int fail = 0;

    public static final String TEXT = "Ganador";

    Button btnRepetir;

    Map<String,Map<String, String>> jugadores = new HashMap<String, Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

//if(sensor == null){
//            finish();

            sensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    if(sensorEvent.values[0]<sensor.getMaximumRange()){
                        //getWindow().getDecorView().setBackgroundColor(Color.RED);

                        Toast.makeText(juego.this, "Estás dentro del rango", Toast.LENGTH_SHORT).show();
                    }else {
                        //getWindow().getDecorView().setBackgroundColor(Color.GREEN);

                        Toast.makeText(juego.this, "Estás fuera del rango", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };
            start();

//    }

        nombre = findViewById(R.id.nom);

        //errores = findViewById(R.id.eErrores);

        aciertos = findViewById(R.id.eAcierto);

        //errores = findViewById(R.id.eErrores);

        Intent intent = getIntent();
        String nino = intent.getStringExtra(confirmar.MAIN_TEXT);

        nombre.setText(nino);

        //Images
        tronco = findViewById(R.id.tronco);
        raiz = findViewById(R.id.raiz);
        hojas = findViewById(R.id.hoja);
        flor = findViewById(R.id.flor);
        fruto = findViewById(R.id.fruto);

        //Siluetas
        troncoS = findViewById(R.id.troncoArbol);
        raizS = findViewById(R.id.raizArbol);
        hojasS = findViewById(R.id.hojasArbol);
        florS = findViewById(R.id.florArbol);
        frutoS = findViewById(R.id.frutoArbol);

        //


        // Sets the tag
        tronco.setTag("troncoS");
        raiz.setTag("raizS");
        hojas.setTag("hojaS");
        flor.setTag("florS");
        fruto.setTag("frutoS");


        // Sets the tag
        troncoS.setTag("troncoS");
        raizS.setTag("raizS");
        hojasS.setTag("hojaS");
        florS.setTag("florS");
        frutoS.setTag("frutoS");

        // set the listener Images
        tronco.setOnTouchListener(new MyTouchListener());
        raiz.setOnTouchListener(new MyTouchListener());
        hojas.setOnTouchListener(new MyTouchListener());
        flor.setOnTouchListener(new MyTouchListener());
        fruto.setOnTouchListener(new MyTouchListener());

        //Drag Siluetas
        //findViewById(R.id.topLinear).setOnDragListener(new MyDragListener());

        troncoS.setOnDragListener(new MyDragListener());
        raizS.setOnDragListener(new MyDragListener());
        hojasS.setOnDragListener(new MyDragListener());
        florS.setOnDragListener(new MyDragListener());
        frutoS.setOnDragListener(new MyDragListener());

        //leerUsuarios();
    }

    public void start(){
        sensorManager.registerListener(sensorEventListener, sensor, 2000*1000);

    }

    public void stop(){
        sensorManager.unregisterListener(sensorEventListener);

    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }

    private final class MyTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            // TODO Auto-generated method stub

            // create it from the object's tag
            ClipData.Item item = new ClipData.Item((CharSequence)view.getTag());

            String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };

            ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                view.startDrag( data, //data to be dragged
                        shadowBuilder, //drag shadow
                        view, //local data about the drag and drop operation
                        0   //no needed flags
                );
                //view.setVisibility(View.INVISIBLE);
                return true;

            } else {
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {
        private static final String LOGCAT = "pos";
        //Drawable normalShape = getDrawable(R.drawable.flor);


        @Override
        public boolean onDrag(View v, DragEvent event) {

            // Handles each of the expected events
            switch (event.getAction()) {

                case DragEvent.ACTION_DRAG_STARTED:

                    X = event.getX();
                    Y = event.getY();

                    Log.d(LOGCAT, "X " + (int) X + "Y " + (int) Y);
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    int x_cord = (int) event.getX();
                    int y_cord = (int) event.getY();

                    break;

                case DragEvent.ACTION_DRAG_LOCATION:


                    break;

                case DragEvent.ACTION_DRAG_EXITED:

                    x_cord = (int) event.getX();
                    y_cord = (int) event.getY();
                    break;

                //drag shadow has been released,the drag point is within the bounding box of the View
                case DragEvent.ACTION_DROP:{
                    String tronco = troncoS.getTag().toString();
                    String raiz = raizS.getTag().toString();
                    String hoja = hojasS.getTag().toString();
                    String flor = florS.getTag().toString();
                    String fruto = frutoS.getTag().toString();

                    // Gets the item containing the dragged data
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    // Gets the text data from the item.
                    //CharSequence data = item.getText();

                    //v.setVisibility(View.INVISIBLE);

                    System.out.println(item.getText().toString());


                    switch (item.getText().toString()){
                        case "troncoS":
                            if(tronco.equals(item.getText())){

                                troncoS.setImageResource(R.drawable.tronco);
                                correct ++;
                                //correct +=correct;
                                aciertos.setText(String.valueOf(correct));

                            }else{

                                fail ++;
                                errores.setText(String.valueOf(fail));
                        }
                        break;

                        case "raizS":
                            if(raiz.equals((item.getText().toString()))){
                            raizS.setImageResource(R.drawable.raiz);
                            correct ++;
                            //correct +=correct;
                            aciertos.setText(String.valueOf(correct));
                            }else{
                                fail ++;
                                errores.setText(String.valueOf(fail));
                            }
                        break;

                        case "hojaS":
                            if(hoja.equals((item.getText().toString()))){
                            hojasS.setImageResource(R.drawable.hojaarbol);
                            correct ++;
                            //correct +=correct;
                            aciertos.setText(String.valueOf(correct));
                            }else{
                                fail ++;
                                errores.setText(String.valueOf(fail));
                            }
                        break;

                        case "florS":
                            if(flor.equals((item.getText().toString()))){
                            florS.setImageResource(R.drawable.flor);
                            correct ++;
                            //correct +=correct;
                            aciertos.setText(String.valueOf(correct));
                            }else{
                                fail ++;
                                errores.setText(String.valueOf(fail));
                            }
                        break;

                        case "frutoS":
                            if(fruto.equals((item.getText().toString()))){
                            frutoS.setImageResource(R.drawable.ff);
                            correct ++;
                            //correct +=correct;
                            aciertos.setText(String.valueOf(correct));
                            }else{
                                fail ++;
                                errores.setText(String.valueOf(fail));
                            }
                        break;



                        default:

                            break;


                    }

                    if(correct == 5){
                        Ganador();
                    }

                }

                //the drag and drop operation has concluded.
                case DragEvent.ACTION_DRAG_ENDED:{
                    //return(true);
                }

                default:
                    break;
            }
            return true;
        }

    }

    public void Ganador(){

            String nom = nombre.getText().toString();
            int error = 0;

                    //Integer.parseInt(errores.getText().toString());

            int acierto = Integer.parseInt(aciertos.getText().toString());

            actualizarUsuario(nom, error, acierto);

            DialogFragment dialogFragment = new dialogo();
            dialogFragment.show(getSupportFragmentManager(), "Felicidades");

    }

    private void leerUsuarios() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic
                // should be here

                // Create URL
                URL url = null;
                try {
                    url = new URL("https://www.ebossystems.com/game/users");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Create connection
                try {
                    HttpURLConnection con =
                            (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");
                    //con.setDoOutput(true);
                    int responseCode = con.getResponseCode();
                    System.out.println("\nSending 'Get' request to URL : " +    url+"--"+responseCode);

                    InputStreamReader responseBodyReader =
                            new InputStreamReader(con.getInputStream(), "UTF-8");

                    JsonReader jsonReader = new JsonReader(responseBodyReader);
                    jsonReader.beginObject(); // Start processing the JSON object

                    while (jsonReader.hasNext()) { // Loop through all keys

                        Map<String, String> valores = new HashMap<String, String>();

                        String key = jsonReader.nextName(); // Fetch the next key
                        System.out.println("key: " + key);


                        if(key.equals("undefined")){
                            jsonReader.skipValue();
                            break;
                        }

                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) { // Loop through all keys
                            key = jsonReader.nextName(); // Fetch the next key
                            System.out.println("key: " + key);
                            if(key.equals("pass")){
                                String value = jsonReader.nextString(); // Fetch the next key
                                System.out.println("value: " + value);
                                valores.put(key, value);
                            } else {
                                int value = jsonReader.nextInt(); // Fetch the next key
                                System.out.println("value: " + value);
                                valores.put(key, String.valueOf(value));
                            }

                        }
                        jsonReader.endObject();

                        jugadores.put(key, valores);
                    }

                    jsonReader.endObject();
                    jsonReader.close();

                    con.disconnect();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        });
    }

    private void actualizarUsuario(final String nombre, final int errores, final int aciertos) {

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

    public void Salir(View v) {

        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void RegresarAvatar(View v) {
        Intent regresar = new Intent(this, avatar.class);
        startActivity(regresar);
    }


}
