package com.example.eventplanner.fragments.admin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.home.HomeActivity;
import com.example.eventplanner.fragments.home.HomeFragment;
import com.example.eventplanner.model.entities.Category;
import com.example.eventplanner.model.entities.EventType;
import com.example.eventplanner.model.eventTypeCreation.CategoriesResponse;
import com.example.eventplanner.model.eventTypeCreation.EventTypeCreationRequest;
import com.example.eventplanner.model.eventTypeCreation.EventTypeEditRequest;
import com.example.eventplanner.services.IEventTypeService;
import com.example.eventplanner.services.spec.ApiService;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEventTypeFragment extends Fragment {
    private TableLayout tableLayout;
    private Button suggestionsButton;
    private List<String> suggestionsList = new ArrayList<>();
    private Set<String> selectedSuggestions = new HashSet<>();

    private Set<String> selectedSuggestionsEdit = new HashSet<>();

    private LinearLayout editSection;
    private TextView editTitle;
    private EditText editDescription;
    private Button editSuggestionsButton;
    private Button changeButton;

    private EventType selectedEventType;
    private EditText nameInput;
    private EditText descriptionInput;
    private Button createButton;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_event_type, container, false);

        tableLayout = rootView.findViewById(R.id.tableLayout);
        suggestionsButton = rootView.findViewById(R.id.button3);

        nameInput = rootView.findViewById(R.id.editTextText8);
        descriptionInput = rootView.findViewById(R.id.editTextText9);
        createButton = rootView.findViewById(R.id.button4);

        createButton.setOnClickListener(v -> handleCreateEventType());

        editSection = rootView.findViewById(R.id.editEventTypeSection);
        editDescription = rootView.findViewById(R.id.eventDescriptionEdited);
        editTitle = rootView.findViewById(R.id.eventNameEdited);
        editSuggestionsButton = rootView.findViewById(R.id.toggleCheckbox2Button);
        changeButton = rootView.findViewById(R.id.saveChangesButton);

        loadEventTypes();
        loadCategorySuggestions();

        return rootView;
    }

    private void loadEventTypes() {
        IEventTypeService eventTypeService = ApiService.getEventTypeService();
        eventTypeService.getAllEventTypes().enqueue(new Callback<List<EventType>>() {
            @Override
            public void onResponse(Call<List<EventType>> call, Response<List<EventType>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayEventTypes(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<EventType>> call, Throwable t) {
                Log.e("EventTypes", "Request failed", t);
            }
        });
    }

    private void displayEventTypes(List<EventType> eventTypes) {
        tableLayout.removeAllViews();

        TableRow headerRow = new TableRow(getContext());
        headerRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView headerName = new TextView(getContext());
        headerName.setText("Name");
        headerName.setTextColor(Color.BLACK);
        headerName.setPadding(16, 16, 16, 16);
        headerName.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        headerName.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView headerActive = new TextView(getContext());
        headerActive.setText("Activity");
        headerActive.setTextColor(Color.BLACK);
        headerActive.setPadding(16, 16, 16, 16);
        headerActive.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        headerActive.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView emptyHeader1 = new TextView(getContext());
        emptyHeader1.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        TextView emptyHeader2 = new TextView(getContext());
        emptyHeader2.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        headerRow.addView(headerName);
        headerRow.addView(headerActive);
        headerRow.addView(emptyHeader1);
        headerRow.addView(emptyHeader2);

        tableLayout.addView(headerRow);

        for (EventType eventType : eventTypes) {
            TableRow row = new TableRow(getContext());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView nameView = new TextView(getContext());
            nameView.setText(eventType.getName());
            nameView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            TextView activeView = new TextView(getContext());
            if(eventType.isActive()){
                activeView.setText("yes");
            }else{
                activeView.setText("no");
            }
            activeView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            Log.d("EventType", "Name: " + eventType.getName() + ", isActive: " + eventType.isActive());

            ImageButton editBtn = new ImageButton(getContext());
            editBtn.setImageResource(R.drawable.pen_solid);
            editBtn.setBackground(null);
            editBtn.setOnClickListener(v -> showEditSection(eventType));

            Button toggleButton = new Button(getContext());
            toggleButton.setText("Toggle Activity");
            toggleButton.setOnClickListener(v -> changeEventTypeActivity(eventType));

            row.addView(nameView);
            row.addView(activeView);
            row.addView(editBtn);
            row.addView(toggleButton);

            tableLayout.addView(row);
        }
    }


    private void changeEventTypeActivity(EventType eventType) {
        if (getContext() == null) return;

        new androidx.appcompat.app.AlertDialog.Builder(getContext())
                .setTitle("Confirm")
                .setMessage("Are you sure you want to change the activity status for this event type?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    IEventTypeService service = ApiService.getEventTypeService();
                    service.changeEventTypeActivity(eventType.getId()).enqueue(new Callback<EventType>() {
                        @Override
                        public void onResponse(Call<EventType> call, Response<EventType> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Snackbar.make(requireView(), "Event type activity changed successfully!", Snackbar.LENGTH_SHORT).show();
                                loadEventTypes();
                            } else {
                                Snackbar.make(requireView(), "Failed to change activity", Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<EventType> call, Throwable t) {
                            Snackbar.make(requireView(), "Error: " + t.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showEditSection(EventType eventType) {
        selectedEventType = eventType;

        editSection.setVisibility(View.VISIBLE);


        editTitle.setText(eventType.getName());
        editDescription.setText(eventType.getDescription());


        selectedSuggestionsEdit.clear();
        for (Category cat : eventType.getCategorySuggestions()) {
            selectedSuggestionsEdit.add(cat.getName());
        }


        setupMultiSelectPopup(editSuggestionsButton, selectedSuggestionsEdit);

        changeButton.setOnClickListener(v -> handleSaveChanges());
    }

    private void handleSaveChanges() {
        if (selectedEventType == null) return;

        Long id = selectedEventType.getId();
        String name = selectedEventType.getName();
        String description = editDescription.getText().toString().trim();

        if (description.isEmpty()) {
            Snackbar.make(requireView(), "Description cannot be empty", Snackbar.LENGTH_SHORT).show();
            return;
        }

        EventTypeEditRequest request = new EventTypeEditRequest(id, name, description, selectedSuggestionsEdit);

        IEventTypeService service = ApiService.getEventTypeService();
        service.editEventType(id, request).enqueue(new Callback<EventType>() {
            @Override
            public void onResponse(Call<EventType> call, Response<EventType> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Snackbar.make(requireView(), "Changes saved successfully!", Snackbar.LENGTH_SHORT).show();
                    editSection.setVisibility(View.GONE);
                    loadEventTypes();
                } else {
                    Snackbar.make(requireView(), "Failed to save changes", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventType> call, Throwable t) {
                Snackbar.make(requireView(), "Error: " + t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }


    private void loadCategorySuggestions() {
        ApiService.getEventTypeService().getActiveCategories().enqueue(new Callback<Set<CategoriesResponse>>() {
            @Override
            public void onResponse(Call<Set<CategoriesResponse>> call, Response<Set<CategoriesResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (CategoriesResponse category : response.body()) {
                        suggestionsList.add(category.getName());
                    }
                    setupMultiSelectPopup(suggestionsButton, selectedSuggestions);
                    //setupMultiSelectPopup(editSuggestionsButton, selectedSuggestions);
                }
            }

            @Override
            public void onFailure(Call<Set<CategoriesResponse>> call, Throwable t) {
                Log.e("SP-Categories", "Request failed", t);
            }
        });
    }

    private void setupMultiSelectPopup(Button button, Set<String> selected) {
        button.setOnClickListener(v -> {
            PopupWindow popupWindow = new PopupWindow(getContext());
            LinearLayout layout = new LinearLayout(getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(20, 20, 20, 20);

            for (String item : suggestionsList) {
                CheckBox checkBox = new CheckBox(getContext());
                checkBox.setText(item);
                checkBox.setChecked(selected.contains(item));
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) selected.add(item);
                    else selected.remove(item);
                });
                layout.addView(checkBox);
            }

            popupWindow.setContentView(layout);
            popupWindow.setWidth(600);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            popupWindow.showAsDropDown(button);
        });
    }
    private void handleCreateEventType() {
        String name = nameInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty()) {
            View root = rootView != null ? rootView : getView();
            if (root != null) {
                Snackbar.make(root, "Name and description are required", Snackbar.LENGTH_SHORT).show();
            } else if (getContext() != null) {
                Toast.makeText(getContext(), "Name and description are required", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        EventTypeCreationRequest request = new EventTypeCreationRequest(name, description, selectedSuggestions);

        IEventTypeService service = ApiService.getEventTypeService();
        service.createEventType(request).enqueue(new Callback<EventType>() {
            @Override
            public void onResponse(Call<EventType> call, Response<EventType> response) {
                View root = rootView != null ? rootView : getView();
                if (response.isSuccessful()) {
                    if (root != null) {
                        Snackbar.make(root, "Event type created successfully!", Snackbar.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    if (root != null) {
                        Snackbar.make(root, "Failed to create event type", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<EventType> call, Throwable t) {
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
