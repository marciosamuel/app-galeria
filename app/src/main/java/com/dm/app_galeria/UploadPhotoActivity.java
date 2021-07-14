package com.dm.app_galeria;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class UploadPhotoActivity extends AppCompatActivity {

    private ImageButton imagemSelect;
    private Button submitBTN;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private EditText titulo;
    private StorageReference storageRef;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        titulo = findViewById(R.id.upload_photo_name);

        imagemSelect = findViewById(R.id.upload_photo_image);
        submitBTN = findViewById(R.id.submitBTN);

        imagemSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }
        });
        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = titulo.getText().toString();
                if(name.equals("")){
                    Toast.makeText(getApplicationContext(),"Informe um nome!", Toast.LENGTH_LONG).show();
                }else{
                    storageRef = FirebaseStorage.getInstance().getReference(name);
                    storageRef.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(),"Salvo com sucesso!", Toast.LENGTH_LONG).show();
                            titulo.setText("");
                            imagemSelect.setImageResource(R.drawable.image);
                        }
                    });
                }
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    imagemSelect.setImageDrawable(new BitmapDrawable(bitmap));
                }catch (IOException e){
                }
            }
        }
    }


    public String getPath(Uri uri) {

        if( uri == null ) {
            // TODO realizar algum log ou feedback do utilizador
            return null;
        }


        // Tenta recuperar a imagem da media store primeiro
        // Isto só irá funcionar para as imagens selecionadas da galeria

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