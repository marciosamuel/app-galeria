package com.dm.app_galeria.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.dm.app_galeria.ListPhotosActivity;
import com.dm.app_galeria.Models.PhotoModel;
import com.dm.app_galeria.R;
import com.dm.app_galeria.ViewPhotoActivity;

import java.util.ArrayList;

public class PhotoAdapter extends ArrayAdapter<PhotoModel> {
    public PhotoAdapter(@NonNull Context context, ArrayList<PhotoModel> photoModelArrayList) {
        super(context, 0, photoModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.photo_card, parent, false);
        }
        PhotoModel photoModel = getItem(position);
        TextView photoName = listitemView.findViewById(R.id.photo_card_name);
        ImageView photoImage = listitemView.findViewById(R.id.photo_card_image);

        photoName.setText(photoModel.getPhotoName());
        photoImage.setImageResource(photoModel.getImageId());

        photoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewPhotoActivity.class);
                intent.putExtra("IMAGE_NAME", photoModel.getPhotoName());
                intent.putExtra("IMAGE_FILE", photoModel.getImageId());
                getContext().startActivity(intent);
            }
        });

        return listitemView;
    }
}
