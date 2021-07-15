package com.dm.app_galeria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewPhotoActivity extends AppCompatActivity {
    private StorageReference storageRef;
    private Button btnDelet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);

        String name = getIntent().getExtras().getString("IMAGE_NAME");

        TextView itemName = findViewById(R.id.view_photo_name);
        itemName.setText(name);

        btnDelet = findViewById(R.id.view_photo_btn_delete);

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

        btnDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmIntent();
            }
        });
    }
    private void confirmIntent() {
        AlertDialog.Builder confirm = new AlertDialog.Builder(this);
        confirm.setTitle("Excluir");
        confirm.setIcon(R.drawable.excluir);
        confirm.setMessage("Deseja Excluir essa imagem?");
        confirm.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = getIntent().getExtras().getString("IMAGE_NAME");
                FirebaseStorage.getInstance().getReference().child("images/" + name).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Deletado com Sucesso!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), ListPhotosActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
        confirm.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        confirm.show();
    }
}