package com.example.eventplanner.fragments.service_product;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.service_product.ServiceProductDetailsActivity;
import com.example.eventplanner.fragments.profile.UserProfileFragment;
import com.example.eventplanner.model.entities.Product;
import com.example.eventplanner.model.entities.Service;
import com.example.eventplanner.model.productDetails.ProductDetailsResponse;
import com.example.eventplanner.model.productDetails.ServiceDetailsResponse;
import com.example.eventplanner.services.spec.ApiService;
import com.example.eventplanner.services.spec.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceProductDetailFragment extends Fragment {

    private Long itemId;
    private String itemType;

    private Product productDetails;
    private Service serviceDetails;

    private String role="";

    private ProductDetailsResponse productItem;

    private ServiceDetailsResponse serviceItem;

    public ServiceProductDetailFragment() {
        // Required empty public constructor
    }

    public static ServiceProductDetailFragment newInstance(Long itemId, String itemType) {
        ServiceProductDetailFragment fragment = new ServiceProductDetailFragment();
        Bundle args = new Bundle();
        args.putLong("itemId", itemId);
        args.putString("itemType", itemType); // "service" ili "product"
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemId = getArguments().getLong("itemId");
            itemType = getArguments().getString("itemType");
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        role = AuthService.getRoleFromToken();
        Log.d("ROLE_CHECK", role);

        if ("service".equalsIgnoreCase(itemType)) {
            fetchServiceDetails(view);
        } else {
            fetchProductDetails(view);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_service_product_detail, container, false);
        }
    private void fetchServiceDetails(View view) {
        ApiService.getServiceService().getServiceDetails(itemId).enqueue(new Callback<ServiceDetailsResponse>() {
            @Override
            public void onResponse(Call<ServiceDetailsResponse> call, Response<ServiceDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    serviceItem = response.body();
                    populateServiceUI(view);
                } else {
                    Toast.makeText(getContext(), "Greška prilikom učitavanja servisa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServiceDetailsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Greška: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fetchProductDetails(View view) {
        ApiService.getProductService().getProductDetails(itemId).enqueue(new Callback<ProductDetailsResponse>() {
            @Override
            public void onResponse(Call<ProductDetailsResponse> call, Response<ProductDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productItem = response.body();
                    populateProductUI(view);
                } else {
                    Toast.makeText(getContext(), "Greška prilikom učitavanja proizvoda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductDetailsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Greška: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateServiceUI(View view) {
        TextView nameText = view.findViewById(R.id.title);
        TextView descText = view.findViewById(R.id.description);
        ImageView bookIcon = view.findViewById(R.id.shop_icon);

        nameText.setText(serviceItem.getName());

        String description = serviceItem.getDescription();
        description += "\nSpecificity: " + serviceItem.getSpecificity();
        description += "\nCategory: " + serviceItem.getCategory();
        if (serviceItem.getDuration() > 0) {
            description += "\nDuration: " + serviceItem.getDuration() + " hours";
        } else {
            description += "\nMinimum Engagement: " + serviceItem.getMinDuration() + " minutes";
            description += "\nMaximum Engagement: " + serviceItem.getMaxDuration() + " minutes";
        }
        description += "\nProvider: " + serviceItem.getProvider().getName()+" "+serviceItem.getProvider().getLastName()+"\nEmail: "+serviceItem.getProvider().getEmail();

        descText.setText(description);
        TextView providerLink = view.findViewById(R.id.provider_name_link);

        String fullName = serviceItem.getProvider().getName() + " " + serviceItem.getProvider().getLastName();
        providerLink.setText("View Provider: " + fullName);
        providerLink.setPaintFlags(providerLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // podvlači

        providerLink.setOnClickListener(v -> {
            openUserProfile(serviceItem.getProvider().getId());
        });

        // Prikaz i funkcionalnost dugmeta
        if ("ROLE_EVENT_ORGANIZER".equalsIgnoreCase(role)) {
            bookIcon.setVisibility(View.VISIBLE);
            bookIcon.setImageResource(R.drawable.ic_book); // Knjiga
            bookIcon.setOnClickListener(v -> {
                BookingServiceFragment bookingFragment = BookingServiceFragment.newInstance(itemId); // itemId je serviceId
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, bookingFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            });

        } else {
            bookIcon.setVisibility(View.GONE);
        }
    }
    private void populateProductUI(View view) {
        TextView nameText = view.findViewById(R.id.title);
        TextView descText = view.findViewById(R.id.description);
        ImageView bookIcon = view.findViewById(R.id.shop_icon);

        nameText.setText(productItem.getName());

        String description = productItem.getDescription();
        description += "\nAvailable: " + productItem.isAvailable();
        description += "\nCategory: " + productItem.getCategory();
        description += "\nProvider: " + productItem.getProvider().getName() + " " + productItem.getProvider().getLastName() + "\nEmail:" + productItem.getProvider().getEmail();


        descText.setText(description);
        TextView providerLink = view.findViewById(R.id.provider_name_link);

        String fullName = productItem.getProvider().getName() + " " + productItem.getProvider().getLastName();
        providerLink.setText("View Provider: " + fullName);
        providerLink.setPaintFlags(providerLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        providerLink.setOnClickListener(v -> {
            openUserProfile(productItem.getProvider().getId());
        });

        // Ikonica šoping kolica, ali bez klika
        if ("ROLE_EVENT_ORGANIZER".equalsIgnoreCase(role)) {
            bookIcon.setVisibility(View.VISIBLE);
            bookIcon.setImageResource(R.drawable.ic_shop);
            bookIcon.setOnClickListener(null);
        } else {
            bookIcon.setVisibility(View.GONE);
        }
    }
    private void openUserProfile(Long providerId) {
        ((ServiceProductDetailsActivity) requireActivity()).openUserProfileFragment(providerId);
    }


}