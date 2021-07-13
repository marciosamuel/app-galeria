package com.dm.app_galeria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.dm.app_galeria.Adapters.PhotoAdapter;
import com.dm.app_galeria.Models.PhotoModel;

import java.util.ArrayList;

public class ListPhotosActivity extends AppCompatActivity {

    GridView photosGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photos);

        photosGridView = findViewById(R.id.list_photos_grid);

        ArrayList<PhotoModel> photoModelArrayList = new ArrayList<PhotoModel>();

        photoModelArrayList.add(new PhotoModel("Gatinho", R.drawable.gatinho));
        photoModelArrayList.add(new PhotoModel("Gatinho", R.drawable.gatinho));
        photoModelArrayList.add(new PhotoModel("Gatinho", R.drawable.gatinho));
        photoModelArrayList.add(new PhotoModel("Gatinho", R.drawable.gatinho));
        photoModelArrayList.add(new PhotoModel("Gatinho", R.drawable.gatinho));
        photoModelArrayList.add(new PhotoModel("Gatinho", R.drawable.gatinho));
        photoModelArrayList.add(new PhotoModel("Gatinho", R.drawable.gatinho));

        PhotoAdapter adapter = new PhotoAdapter(this, photoModelArrayList);
        photosGridView.setAdapter(adapter);

        Button addImage = findViewById(R.id.list_photos_btn_add);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPhotosActivity.this, UploadPhotoActivity.class);
                startActivity(intent);
            }
        });
    }
}