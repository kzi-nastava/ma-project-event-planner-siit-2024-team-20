package com.example.eventplanner.fragments.event;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.services.IEventService;
import com.example.eventplanner.services.spec.ApiService;
import com.example.eventplanner.services.spec.AuthService;

import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToFavouritesEventFragment extends Fragment {
    private ImageView favouriteIcon;
    private IEventService eventService;

    private Long userId;
    private Long eventId = -1L;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_to_favourites_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            eventId = getArguments().getLong("event_id", -1);
        }

        favouriteIcon = view.findViewById(R.id.imageView7);

        eventService = ApiService.getEventService();
        userId = (long) AuthService.getUserIdFromToken();

        if (userId == null || eventId == -1) {
            Toast.makeText(getContext(), "An error occured", Toast.LENGTH_SHORT).show();
            return;
        }

        checkIfFavourite();

        favouriteIcon.setOnClickListener(v -> toggleFavourite());
    }

    private void checkIfFavourite() {
        eventService.isInFavourites(userId, eventId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateFavouriteIcon(response.body());
                } else {
                    Toast.makeText(getContext(), "It is not possible to load favourites", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFavouriteIcon(boolean isFavourite) {
        if (isFavourite) {
            favouriteIcon.setImageResource(R.drawable.heart_favourite);
        } else {
            favouriteIcon.setImageResource(R.drawable.heart_not_favourite);
        }
        favouriteIcon.setTag(isFavourite);
    }

    private void toggleFavourite() {
        boolean currentlyFav = favouriteIcon.getTag() != null && (Boolean) favouriteIcon.getTag();

        if (currentlyFav) {
            eventService.addToFavourites(userId, eventId).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()) {
                        updateFavouriteIcon(false);
                        Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Failed to remove from favorites", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            eventService.addToFavourites(userId, eventId).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()) {
                        updateFavouriteIcon(true);
                        Toast.makeText(getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Railed to add to favourites", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}