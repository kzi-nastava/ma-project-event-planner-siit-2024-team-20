package com.example.eventplanner.fragments.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.eventplanner.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

public class ProfileImageFragment extends Fragment {

    FloatingActionButton button;
    ImageView imageView;

    private Bitmap selectedBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_image, container, false);

        imageView = rootView.findViewById(R.id.imageView5);
        button = rootView.findViewById(R.id.floatingActionButton2);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ImagePicker.with(ProfileImageFragment.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });

        return rootView;
    }

    public void setImageBase64(String base64Image) {
        if (base64Image == null || base64Image.isEmpty()) return;

        if (base64Image.contains(",")) {
            base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
        }

        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        selectedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(selectedBitmap);
    }

    public String getImageBase64() {
        if (selectedBitmap == null) return null;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getData() != null) {
            Uri uri = data.getData();
            imageView.setImageURI(uri);

            try {
                selectedBitmap = BitmapFactory.decodeStream(requireActivity().getContentResolver().openInputStream(uri));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}