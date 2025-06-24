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
import android.widget.TextView;

import com.example.eventplanner.R;
import com.example.eventplanner.model.entities.Product;
import com.example.eventplanner.model.entities.Service;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceProductDetailFragment extends Fragment {

    private Product baseItem;

    public ServiceProductDetailFragment() {
        // Required empty public constructor
    }

    public static ServiceProductDetailFragment newInstance(Product baseItem) {
        ServiceProductDetailFragment fragment = new ServiceProductDetailFragment();
        Bundle args = new Bundle();
        //args.putParcelable("baseItem", baseItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            baseItem = getArguments().getParcelable("baseItem"); // Dohvatimo podatke
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String role = "organizer"; // Pretpostavljamo da postoji polje "role"
        ImageView bookIcon = view.findViewById(R.id.shop_icon);
        if ("organizer".equals(role)) {
            bookIcon.setVisibility(View.VISIBLE);
            if(baseItem instanceof Service){
                bookIcon.setOnClickListener(v -> {
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new BookingServiceFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                });
            }
        } else {
            bookIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_service_product_detail, container, false);

        Bundle arguments = getArguments();
        baseItem = arguments.getParcelable("baseItem");

        if (baseItem != null) {
            // Koristi podatke za ažuriranje UI-a
            TextView nameTextView = rootView.findViewById(R.id.title);
            nameTextView.setText(baseItem.getName());

            TextView descriptionTextView = rootView.findViewById(R.id.description);

            // Dynamically generate description text based on the object type
            String description = baseItem.getDescription(); // Zajedničko za oba tipa

            // Proveri tip objekta
            if (baseItem instanceof Service) {
                Service service = (Service) baseItem;
                description += "\nSpecificity: " + service.getSpecificity();
                description += "\nCategory: " + service.getCategory();
                if (service.getDuration() > 0) {
                    description += "\nDuration: " + service.getDuration() + " hours";
                } else {
                    description += "\nMinimum Engagement: " + service.getMinDuration() + " hours";
                    description += "\nMaximum Engagement: " + service.getMaxDuration() + " hours";
                }
                description += "\nProvider: " + service.getProvider();
            }
            else if (baseItem instanceof Product) {
                Product product = (Product) baseItem;
                description += "\nStatus: " + product.getStatus();
                description += "\nCategory: " + product.getCategory();
                description += "\nProvider: " + product.getProvider();
                // Možeš dodati još polja
            }

                descriptionTextView.setText(description);

            }
            return rootView;

        }

}