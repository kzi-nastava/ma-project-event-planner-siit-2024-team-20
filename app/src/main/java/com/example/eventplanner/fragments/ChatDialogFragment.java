package com.example.eventplanner.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.eventplanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatDialogFragment extends DialogFragment {
    private LinearLayout chatParticipantList;

    public static ChatDialogFragment newInstance() {
        return new ChatDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflatovanje layouta za dijalog
        View view = inflater.inflate(R.layout.dialog_chats, container, false);

        // Zatvaranje dijaloga kada se klikne na Exit dugme
        ImageView closeButton = view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(v -> dismiss());

        // Inicijalizacija chat liste
        chatParticipantList = view.findViewById(R.id.chat_participant_list);

        // Dodavanje chat itema u listu (ovde možeš dodati stvarne podatke)
        for (int i = 0; i < 5; i++) { // Primer sa 5 chat itema
            View chatItem = inflater.inflate(R.layout.chat_item, container, false);
            // Dodaj marginu između chat itema
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 3, 0, 3); // 16dp margina sa vrha i sa dna
            chatItem.setLayoutParams(params);

            chatParticipantList.addView(chatItem);


            // Klik listener za svaki chat item
            final String personName = "Person " + (i + 1);  // Primer imena
            chatItem.setOnClickListener(v -> openChat(personName));  // Otvori chat sa tom osobom
        }

        return view;
    }

    // Otvori chat fragment sa tom osobom
    private void openChat(String personName) {
        ChatMessagesFragment chatMessagesFragment = ChatMessagesFragment.newInstance(personName);
        chatMessagesFragment.show(getParentFragmentManager(), "chatMessagesFragment");
    }

    @Override
    public void onStart() {
        super.onStart();
        // Postavljanje dijaloga u donji desni ugao
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setGravity(Gravity.BOTTOM | Gravity.END);  // Donji desni ugao
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.x = 0;  // Horizontalna pozicija (ako je potrebno)
                layoutParams.y = 0;  // Verticalna pozicija (ako je potrebno)
                window.setAttributes(layoutParams);
            }
        }
    }
}

