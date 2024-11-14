package com.example.eventplanner.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.eventplanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatDialogFragment extends DialogFragment {
        public static ChatDialogFragment newInstance() {
            return new ChatDialogFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflatovanje layouta za dijalog
            View view = inflater.inflate(R.layout.dialog_chats, container, false);

            // Zatvaranje dijaloga kada se klikne na Exit dugme
            Button closeButton = view.findViewById(R.id.close_chat_button);
            closeButton.setOnClickListener(v -> dismiss());

            return view;
        }

        @Override
        public void onStart() {
            super.onStart();
            // Postavljanje dijaloga u donji desni ugao
            Dialog dialog = getDialog();
            if (dialog != null) {
                Window window = dialog.getWindow();
                if (window != null) {
                    window.setGravity(Gravity.BOTTOM | Gravity.END); // Donji desni ugao
                    WindowManager.LayoutParams layoutParams = window.getAttributes();
                    layoutParams.x = 0;  // Horizontalna pozicija (ako je potrebno)
                    layoutParams.y = 0;  // Verticalna pozicija (ako je potrebno)
                    window.setAttributes(layoutParams);
                }
            }
        }
    }
