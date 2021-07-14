package com.dm.app_galeria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.dm.app_galeria.Adapters.PhotoAdapter;
import com.dm.app_galeria.Models.PhotoModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;

public class ListPhotosActivity extends AppCompatActivity {

    GridView photosGridView;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_photos);
        storageRef = FirebaseStorage.getInstance().getReference("app-galeria");
        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                ListView friendsListView = findViewById(R.id.listViewID);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    imagemSelect.setImageDrawable(new BitmapDrawable(bitmap));
                }catch (IOException e){
                }
                final ArrayList<ListResult> myFotos = new ArrayList<ListResult>(listResult);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, myFotos);
                friendsListView.setAdapter(listResult);
                friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getApplicationContext(), "Hello " + myFotos.get(i), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

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