package com.example.eventplanner.fragments.service_product.create_product;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.helpers.RecycleAdapter;
import com.example.eventplanner.helpers.TempImageHolder;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ImagesPickerFragment extends Fragment implements  RecycleAdapter.CountOfImageWhenRemoved{

    RecyclerView recyclerView;
    TextView textView;
    Button pick;

    ArrayList<Uri> uri = new ArrayList<>();

    RecycleAdapter adapter;
    private static final int Read_Premission = 101;
    private static final int PICK_IMAGE = 1;
    Set<String> originalBase64Images = new HashSet<>();

    ArrayList<Bitmap> backendBitmaps = new ArrayList<>();

    private Map<Bitmap, String> backendBitmapMap = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_images_picker, container, false);

        textView = rootView.findViewById(R.id.totalPhotos);
        recyclerView = rootView.findViewById(R.id.recyclerView_Gallery_Images);
        pick = rootView.findViewById(R.id.pick);

        adapter = new RecycleAdapter(uri, backendBitmaps, requireContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 4));

        if (!TempImageHolder.backendImages.isEmpty()) {
            setBackendImages(TempImageHolder.backendImages);
        }


        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Read_Premission);

                    return;
                }

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        return  rootView;

    }
    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        for (int i = 0; i < count; i++) {
                            Uri imageUri = data.getClipData().getItemAt(i).getUri();
                            uri.add(imageUri);
                        }
                        adapter.notifyDataSetChanged();
                        textView.setText("Photos (" + uri.size() + ")");
                    } else if (data.getData() != null) {
                        Uri imageUri = data.getData();
                        uri.add(imageUri);
                        adapter.notifyDataSetChanged();
                        textView.setText("Photos (" + uri.size() + ")");
                    }
                }
            });

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            if (data.getClipData() != null) {
                int x = data.getClipData().getItemCount();
                for (int i = 0; i < x; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    uri.add(imageUri);
                }
                adapter.notifyDataSetChanged();
                textView.setText("Photos (" + uri.size() + ")");
            } else {
                Uri imageUri = data.getData();
                uri.add(imageUri);
            }
            adapter.notifyDataSetChanged();
            textView.setText("Photos (" + uri.size() + ")");
        } else {
            Toast.makeText(requireContext(), "You haven't pick any image", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void clicked(int getSize) {
        textView.setText("Photos (" + uri.size() + ")");

    }

    @Override
    public void onBackendImageRemoved(Bitmap bitmap) {
        removeBackendImage(bitmap);
    }

    @Override
    public void onUriImageRemoved(Uri uri) {
        this.uri.remove(uri);
        adapter.notifyDataSetChanged();
        textView.setText("Photos (" + (this.uri.size() + backendBitmaps.size()) + ")");
    }
    public void removeBackendImage(Bitmap bitmap) {
        String base64 = backendBitmapMap.get(bitmap);
        if (base64 != null) {
            originalBase64Images.remove(base64);
        }
        backendBitmapMap.remove(bitmap);
        backendBitmaps.remove(bitmap);
        adapter.notifyDataSetChanged();
        textView.setText("Photos (" + (uri.size() + backendBitmaps.size()) + ")");
    }


    public Set<String> getAllImages() {
        Set<String> base64Images = new HashSet<>(originalBase64Images);
        for (Uri imageUri : uri) {
            try {
                InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
                byte[] imageBytes = outputStream.toByteArray();

                String encodedImage = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
                base64Images.add(encodedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return base64Images;
    }

    public void setBackendImages(List<String> base64Images) {
        backendBitmapMap.clear();
        originalBase64Images.clear();
        for (String base64 : base64Images) {
            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            backendBitmapMap.put(bitmap, base64);
            originalBase64Images.add(base64);
        }
        backendBitmaps.clear();
        backendBitmaps.addAll(backendBitmapMap.keySet());
        adapter.notifyDataSetChanged();
    }



}