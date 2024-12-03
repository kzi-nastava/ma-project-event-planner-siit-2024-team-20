package com.example.eventplanner.fragments.service_product;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.eventplanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceProductDetailFragment extends Fragment {


    public ServiceProductDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceProductDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiceProductDetailFragment newInstance(String param1, String param2) {
        ServiceProductDetailFragment fragment = new ServiceProductDetailFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String role = "organizer"; // Pretpostavljamo da postoji polje "role"
        ImageView bookIcon = view.findViewById(R.id.shop_icon);
        if ("organizer".equals(role)) {
            bookIcon.setVisibility(View.VISIBLE);
            bookIcon.setOnClickListener(v -> {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new BookingServiceFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            });
        } else {
            bookIcon.setVisibility(View.GONE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service_product_detail, container, false);
    }
}