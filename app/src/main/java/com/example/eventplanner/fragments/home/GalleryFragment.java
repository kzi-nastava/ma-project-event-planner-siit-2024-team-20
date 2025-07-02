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

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GalleryFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private RecyclerView recyclerView;
    private GalleryAdapter adapter;

    private final Map<Uri, String> uriBase64Map = new LinkedHashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        adapter = new GalleryAdapter(null);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
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
            return;
        }

        // Obriši iz adaptera
        adapter.removeSelectedUris();

        // Obriši iz base64 mape
        for (Uri uri : selectedUris) {
            uriBase64Map.remove(uri);
        }

        Toast.makeText(getContext(), "Selected images deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            adapter.addUriImage(imageUri);

            try (InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri)) {
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

                    uriBase64Map.put(imageUri, base64Image);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Set<String> getAllImages() {
        return new HashSet<>(uriBase64Map.values());
    }

    public void setBase64Images(Set<String> base64Images) {
        uriBase64Map.clear();
        adapter.clearAllImages();

        for (String base64 : base64Images) {
            try {
                byte[] decodedBytes = Base64.decode(base64, Base64.NO_WRAP);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                if (bitmap != null) {
                    Uri uri = saveBitmapToTempUri(bitmap);
                    adapter.addUriImage(uri);
                    uriBase64Map.put(uri, base64);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Uri saveBitmapToTempUri(Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(requireContext().getContentResolver(), bitmap, "TempImage", null);
        return Uri.parse(path);
    }
}