package com.example.chappar10.ui.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.chappar10.R;
import com.example.chappar10.data.DataRepository;
import com.example.chappar10.data.User;
import com.example.chappar10.ui.view_model.AccessViewModel;
import com.example.chappar10.ui.view_model.MainViewModel;
import com.example.chappar10.utils.ImageConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment {

    GoogleMap googleMap;

    private MainViewModel mainViewModel;
    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            MapsFragment.this.googleMap = googleMap;
        }
    };

    private void navigateToUserDetails(String uid) {
        DataRepository.getInstance().getUserLiveData(uid).observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.nav_user_details, bundle);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
            mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        }

        mainViewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            //clear map
            googleMap.clear();
            //add markers
            for (User user : users) {
                LatLng latLng = new LatLng(user.location.latitude, user.location.longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(user.getUid());
                Drawable circleDrawable = getResources().getDrawable(R.drawable.angelina);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(ImageConverter.getCircleBitmap(circleDrawable, 150)));
                googleMap.addMarker(markerOptions);
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        navigateToUserDetails(marker.getTitle());
                        return false;
                    }
                });
            }
        });
    }

}