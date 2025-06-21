package com.example.eventplanner.fragments.service_product.create_product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.helpers.CategorySharedViewModel;
import com.example.eventplanner.model.productManage.CategoryResponse;
import com.example.eventplanner.services.IServiceProductService;
import com.example.eventplanner.services.spec.ApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExistingCategoriesFragment extends Fragment {

    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterItems;
    private List<String> categoryNames = new ArrayList<>();

    private CategorySharedViewModel sharedViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_existing_categories, container, false);

        autoCompleteTextView = rootView.findViewById(R.id.auto_complete_text);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(CategorySharedViewModel.class);

        fetchCategories();

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = adapterItems.getItem(position);
                if (selected != null) {
                    sharedViewModel.setSelectedCategory(selected);
                }
            }
        });

        return rootView;
    }

    private void fetchCategories() {
        IServiceProductService service = ApiService.getServiceProductService();
        Call<Set<CategoryResponse>> call = service.getActiveSPCategories();

        call.enqueue(new Callback<Set<CategoryResponse>>() {
            @Override
            public void onResponse(Call<Set<CategoryResponse>> call, Response<Set<CategoryResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryNames.clear();
                    for (CategoryResponse category : response.body()) {
                        categoryNames.add(category.getName());
                    }

                    adapterItems = new ArrayAdapter<>(requireContext(), R.layout.list_item, categoryNames);
                    autoCompleteTextView.setAdapter(adapterItems);
                } else {
                    Toast.makeText(requireContext(), "There is no available categories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Set<CategoryResponse>> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}