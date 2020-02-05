package com.example.plantas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class avatar extends AppCompatActivity {

    ImageView avatar1, avatar2, avatar3, avatar4;

    TextView nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        avatar1 = findViewById(R.id.avatar1);
        avatar2 = findViewById(R.id.avatar2);
        avatar3 = findViewById(R.id.avatar3);
        avatar4 = findViewById(R.id.avatar4);

    }


    public void Confirmar1(View v) {
            avatar1.buildDrawingCache();
            Bitmap bmap = avatar1.getDrawingCache();
            Intent confirmar = new Intent(this, confirmar.class);
            confirmar.putExtra("imagen", bmap);
            startActivity(confirmar);
    }

    public void Confirmar2(View v) {
        avatar2.buildDrawingCache();
        Bitmap bmap = avatar2.getDrawingCache();
        Intent confirmar = new Intent(this, confirmar.class);
        confirmar.putExtra("imagen", bmap);
        startActivity(confirmar);
    }

    public void Confirmar3(View v) {
        avatar3.buildDrawingCache();
        Bitmap bmap = avatar3.getDrawingCache();
        Intent confirmar = new Intent(this, confirmar.class);
        confirmar.putExtra("imagen", bmap);
        startActivity(confirmar);
    }

    public void Confirmar4(View v) {
        avatar4.buildDrawingCache();
        Bitmap bmap = avatar4.getDrawingCache();
        Intent confirmar = new Intent(this, confirmar.class);
        confirmar.putExtra("imagen", bmap);
        startActivity(confirmar);
    }

    public void RegresarInicio(View v) {
        Intent regresar = new Intent(this, MainActivity.class);
        startActivity(regresar);
    }

    public void Salir(View v) {

        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

}
