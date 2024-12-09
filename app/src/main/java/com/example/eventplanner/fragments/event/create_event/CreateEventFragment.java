package com.example.eventplanner.fragments.event.create_event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.eventplanner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String[] item = {"option1", "option2"}; //all dodatno
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    private Switch switchPrivateEvent;
    private LinearLayout emailInputContainer;
    private EditText email1;
    private Button btnAddEmail;
    private int emailCount = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventFragment newInstance(String param1, String param2) {
        CreateEventFragment fragment = new CreateEventFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_event, container, false);
        autoCompleteTextView = rootView.findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(requireContext(), R.layout.list_item, item);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedType = parent.getItemAtPosition(position).toString();

            // Prosleđivanje odabranog tipa u SuggestedCategoriesFragment
            Bundle bundle = new Bundle();
            bundle.putString("event_type", selectedType);

            SuggestedCategoriesFragment fragment = new SuggestedCategoriesFragment();
            fragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView3, fragment) // zamjenjuje sadržaj FragmentContainerView-a
                    .commit();
        });
        // Dodaj listener za Switch
        switchPrivateEvent.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Ako je Switch uključen, prikazujemo polja za unos e-mail adresa
                emailInputContainer.setVisibility(View.VISIBLE);
                btnAddEmail.setVisibility(View.VISIBLE);
            } else {
                // Ako je Switch isključen, sakrivamo polja za unos
                emailInputContainer.setVisibility(View.GONE);
                btnAddEmail.setVisibility(View.GONE);
            }
        });

        // Dodavanje novih polja za e-mail adrese
        btnAddEmail.setOnClickListener(v -> {
            emailCount++;
            EditText newEmailInput = new EditText(getContext());
            newEmailInput.setHint("E-mail adress " + emailCount);
            newEmailInput.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            emailInputContainer.addView(newEmailInput);
        });

        return rootView;
    }

    // Funkcija za sakupljanje unetih e-mail adresa
    public List<String> collectEmailAddresses() {
        List<String> emailList = new ArrayList<>();
        for (int i = 0; i < emailInputContainer.getChildCount(); i++) {
            View child = emailInputContainer.getChildAt(i);
            if (child instanceof EditText) {
                String email = ((EditText) child).getText().toString().trim();
                if (!email.isEmpty()) {
                    emailList.add(email);
                }
            }
        }
        return emailList;
    }
}
