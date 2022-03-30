package com.mgarciareimers.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class PlateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate);

        this.defineFields();
    }

    // Method that defines the fields.
    private void defineFields() {
        Button openButton = this.findViewById(R.id.openButton);
        ImageView imageView = this.findViewById(R.id.plateImageView);

        openButton.setOnClickListener(view -> {
            Intent intent = new Intent();

            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);

            byte[] bytes = stream.toByteArray();


            intent.putExtra("plate", "1234 BCD");
            intent.putExtra("image", Base64.encodeToString(bytes, 0));

            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }
}