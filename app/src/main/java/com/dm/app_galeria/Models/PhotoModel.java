package com.dm.app_galeria.Models;

import android.net.Uri;

public class PhotoModel {
    private String photoName;
    private String imagePath;
    private Uri imageUri;


    public PhotoModel(String photoName, String imagePath, Uri imageUri) {
        this.photoName = photoName;
        this.imagePath = imagePath;
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getimagePath() {
        return imagePath;
    }

    public void setimagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
