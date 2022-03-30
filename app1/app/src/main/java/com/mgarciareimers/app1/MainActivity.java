package com.mgarciareimers.app1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView plateImageView;
    private TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.defineFields();
    }

    // Method that defines the fields.
    private void defineFields() {
        Button openButton = this.findViewById(R.id.openButton);

        this.plateImageView = this.findViewById(R.id.plateImageView);
        this.dataTextView = this.findViewById(R.id.dataTextView);

        openButton.setOnClickListener(view -> {
            Intent intent = new Intent();

            intent.setAction("com.mgarciareimers.app2.getPlate");
            intent.setType("text/plain");

            startActivityForResult.launch(intent);
        });
    }

    ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            // There are no request codes
            Intent data = result.getData();

            this.dataTextView.setText(data == null || data.getStringExtra("plate") == null ? this.getString(R.string.no_data) : data.getStringExtra("plate"));

            if (data == null || data.getStringExtra("image") == null) {
                this.plateImageView.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_baseline_image_not_supported_24));
            } else {
                byte[] decodedString = Base64.decode(data.getStringExtra("image"), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                this.plateImageView.setImageDrawable(new BitmapDrawable(this.getResources(), bitmap));
            }
        } else {
            this.dataTextView.setText(this.getString(R.string.no_data));
            this.plateImageView.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_baseline_image_not_supported_24));
        }
    });
}