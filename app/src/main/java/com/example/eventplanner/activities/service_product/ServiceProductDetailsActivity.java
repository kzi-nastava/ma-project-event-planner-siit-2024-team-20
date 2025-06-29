package com.example.eventplanner.activities.service_product;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.eventplanner.R;
import com.example.eventplanner.fragments.chat.ChatDialogFragment;
import com.example.eventplanner.fragments.profile.UserProfileFragment;
import com.example.eventplanner.fragments.service_product.ServiceProductDetailFragment;
import com.example.eventplanner.model.entities.Product;
import com.example.eventplanner.model.entities.Service;
import com.example.eventplanner.model.productDetails.ServiceDetailsResponse;
import com.example.eventplanner.services.spec.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceProductDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_service_product_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.service_product_details_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView returnBack = findViewById(R.id.return_back);
        returnBack.setOnClickListener(v -> onBackPressed());

        openChat(); // Ako koristiš chat ikonicu

        Long itemId = getIntent().getLongExtra("itemId", -1);
        String itemType = getIntent().getStringExtra("itemType");

        if (itemId == -1 || itemType == null) {
            finish(); // prekini ako nešto fali
            return;
        }

        // 💡 Popravljeno: prosleđujemo i itemId i itemType
        Fragment fragment = ServiceProductDetailFragment.newInstance(itemId, itemType);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void openChat(){
        ImageView chatBubble= findViewById(R.id.chat_icon);
        chatBubble.setOnClickListener(v -> {
            ChatDialogFragment chatDialog = ChatDialogFragment.newInstance();
            chatDialog.show(getSupportFragmentManager(), "ChatDialog");
        });
    }
    public void openUserProfileFragment(Long providerId) {
        UserProfileFragment userProfileFragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putLong("userId", providerId);
        userProfileFragment.setArguments(args);

        FrameLayout popupContainer = findViewById(R.id.fragment_popup_container);
        popupContainer.setVisibility(View.VISIBLE);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_popup_container, userProfileFragment, "profile_popup")
                .commit();
    }

}