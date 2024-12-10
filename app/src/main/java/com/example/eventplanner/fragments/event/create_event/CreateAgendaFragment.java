package com.example.eventplanner.fragments.event.create_event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eventplanner.R;

import java.util.ArrayList;


public class CreateAgendaFragment extends Fragment {

    private RecyclerView recyclerView;
    private AgendaAdapter agendaAdapter;
    private ArrayList<ActivityItem> activitiesList;
    private Button buttonAddActivity, buttonSaveAgenda;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_agenda, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerViewAgenda);
        buttonAddActivity = rootView.findViewById(R.id.buttonAddActivity);
        buttonSaveAgenda = rootView.findViewById(R.id.buttonSaveAgenda);

        // Inicijalizacija liste aktivnosti i adaptera
        activitiesList = new ArrayList<>();
        agendaAdapter = new AgendaAdapter(activitiesList);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(agendaAdapter);

        // Dodajemo novu aktivnost
        buttonAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewActivity();
            }
        });

        // Spremamo agendu
        buttonSaveAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAgenda();
            }
        });

        return rootView;
    }

    private void addNewActivity() {
        // Dodaj novu praznu aktivnost u listu
        activitiesList.add(new ActivityItem("", "", "", "", ""));
        agendaAdapter.notifyItemInserted(activitiesList.size() - 1);
    }

    private void saveAgenda() {
        // Pretpostavka: validacija i spremanje u bazu
        // Možeš ovdje proći kroz listu activitiesList i poslati podatke u bazu
        for (ActivityItem activity : activitiesList) {
            // Provjeri je li svaki unos popunjen
            if (activity.getName().isEmpty() || activity.getStartTime().isEmpty() || activity.getEndTime().isEmpty()) {
                // Prikazi poruku korisniku
                // Toast.makeText(getContext(), "Molimo popunite sve aktivnosti.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Ako je sve OK, spremi u bazu
        // Pretpostavka: Logika za spremanje u bazu podataka
    }
}