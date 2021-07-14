package com.dm.app_galeria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewPhotoActivity extends AppCompatActivity {
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);

        String name = getIntent().getExtras().getString("IMAGE_NAME");

        TextView itemName = findViewById(R.id.view_photo_name);
        itemName.setText(name);

        ImageView itemImage = findViewById(R.id.view_photo_image);

        Context context = this;

        storageRef = FirebaseStorage.getInstance().getReference().child("images/" + name);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .into(itemImage);
            }
        });


    }
}