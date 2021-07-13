package com.dm.app_galeria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);

        Bundle itensIntent = getIntent().getExtras();

        TextView itemName = findViewById(R.id.view_photo_name);
        itemName.setText(
         itensIntent.getString("IMAGE_NAME")
        );

        ImageView itemImage = findViewById(R.id.view_photo_image);
        itemImage.setImageResource(itensIntent.getInt("IMAGE_FILE"));
    }
}