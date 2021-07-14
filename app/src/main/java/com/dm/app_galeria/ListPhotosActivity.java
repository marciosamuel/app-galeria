package com.dm.app_galeria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.dm.app_galeria.Adapters.PhotoAdapter;
import com.dm.app_galeria.Models.PhotoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class ListPhotosActivity extends AppCompatActivity {

    GridView photosGridView;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photos);
        ArrayList<PhotoModel> photoModelArrayList = new ArrayList<PhotoModel>();

        storageRef = FirebaseStorage.getInstance().getReference().child("images");
        System.out.println("Foi#1");

        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                System.out.println("Foi#2");
                for (StorageReference item : listResult.getItems()) {
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String selectedImagePath = getPath(uri);
//                            photoModelArrayList.add(new PhotoModel(item.getName(), selectedImagePath, uri));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("Error#2");
                        }
                    });
                }
                PhotoAdapter adapter = new PhotoAdapter(getApplicationContext(), photoModelArrayList);
                photosGridView = findViewById(R.id.gridView);
                photosGridView.setAdapter(adapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Erro#1");
            }
        });

//        ArrayList<PhotoModel> photoModelArrayList = new ArrayList<PhotoModel>();
//
//        photoModelArrayList.add(new PhotoModel("Gatinho", R.drawable.gatinho));
//        photoModelArrayList.add(new PhotoModel("Gatinho", R.drawable.gatinho));
//        photoModelArrayList.add(new PhotoModel("Gatinho", R.drawable.gatinho));
//        photoModelArrayList.add(new PhotoModel("Gatinho", R.drawable.gatinho));
//        photoModelArrayList.add(new PhotoModel("Gatinho", R.drawable.gatinho));
//        photoModelArrayList.add(new PhotoModel("Gatinho", R.drawable.gatinho));
//        photoModelArrayList.add(new PhotoModel("Gatinho", R.drawable.gatinho));
//
//        PhotoAdapter adapter = new PhotoAdapter(getApplicationContext(), photoModelArrayList);
//        photosGridView = findViewById(R.id.gridView);
//        photosGridView.setAdapter(adapter);

        Button addImage = findViewById(R.id.list_photos_btn_add);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPhotosActivity.this, UploadPhotoActivity.class);
                startActivity(intent);
            }
        });
    }

    public String getPath(Uri uri) {

        if( uri == null ) {
            return null;
        }

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }

        return uri.getPath();
    }
}