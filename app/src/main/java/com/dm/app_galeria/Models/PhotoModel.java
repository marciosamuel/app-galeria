package com.dm.app_galeria.Models;

public class PhotoModel {
    private String photoName;
    private int imageId;

    public PhotoModel(String photoName, int imageId) {
        this.photoName = photoName;
        this.imageId = imageId;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
