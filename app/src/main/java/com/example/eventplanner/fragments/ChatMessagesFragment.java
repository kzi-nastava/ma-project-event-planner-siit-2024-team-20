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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eventplanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatMessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatMessagesFragment extends DialogFragment {
    private ImageView backButton, closeButton, sendButton;
    private EditText inputMessage;
    private LinearLayout chatMessageList;
    private String personName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflacija layout-a za chat
        View rootView = inflater.inflate(R.layout.fragment_chat_messages, container, false);

        // Inicijalizacija UI elemenata
        backButton = rootView.findViewById(R.id.back_button);
        closeButton = rootView.findViewById(R.id.close_button);
        sendButton = rootView.findViewById(R.id.send_button);
        inputMessage = rootView.findViewById(R.id.input_message);
        chatMessageList = rootView.findViewById(R.id.chat_message_list);

        // Postavljanje imena osobe
        personName = getArguments().getString("person_name");
        TextView personNameText = rootView.findViewById(R.id.person_name);
        personNameText.setText(personName);

        // Postavljanje funkcionalnosti dugmadi
        backButton.setOnClickListener(v -> dismiss());  // Zatvoriti chat
        closeButton.setOnClickListener(v -> dismiss());  // Zatvoriti chat
        sendButton.setOnClickListener(v -> sendMessage());

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setGravity(Gravity.BOTTOM | Gravity.END);  // Postavljanje u donji desni ugao
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.x = 0;
                layoutParams.y = 0;
                window.setAttributes(layoutParams);
            }
        }
    }

    private void sendMessage() {
        String message = inputMessage.getText().toString();
        if (!message.isEmpty()) {
            addMessageToChat("Me", message);
            inputMessage.setText("");  // Resetovanje unosa
        }
    }

    private void addMessageToChat(String sender, String message) {
        TextView messageTextView = new TextView(getContext());
        messageTextView.setText(sender + ": " + message);
        chatMessageList.addView(messageTextView);
    }

    public static ChatMessagesFragment newInstance(String personName) {
        ChatMessagesFragment fragment = new ChatMessagesFragment();
        Bundle args = new Bundle();
        args.putString("person_name", personName);
        fragment.setArguments(args);
        return fragment;
    }
}
