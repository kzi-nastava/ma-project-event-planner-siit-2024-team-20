package com.example.eventplanner.fragments.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.helpers.GalleryAdapter;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GalleryFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private RecyclerView recyclerView;
    private GalleryAdapter adapter;
    private List<Uri> imageUris;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        imageUris = new ArrayList<>();
        adapter = new GalleryAdapter(imageUris);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3)); // Prikaz u mreži
        recyclerView.setAdapter(adapter);

        Button addButton = view.findViewById(R.id.btn_add_image);
        Button deleteButton = view.findViewById(R.id.btn_delete_selected);

        addButton.setOnClickListener(v -> openImagePicker());
        deleteButton.setOnClickListener(v -> deleteSelectedImages());

        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void deleteSelectedImages() {
        List<Uri> selectedUris = adapter.getSelectedUris();
        if (selectedUris.isEmpty()) {
            Toast.makeText(getContext(), "No images selected", Toast.LENGTH_SHORT).show();
        } else {
            imageUris.removeAll(selectedUris);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            imageUris.add(imageUri);
            adapter.notifyDataSetChanged();
        }
    }
    public Set<String> getAllImages() {
        Set<String> base64Images = new HashSet<>();

        for (Uri uri : imageUris) {
            try {
                InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    base64Images.add(base64Image);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return base64Images;
    }

}