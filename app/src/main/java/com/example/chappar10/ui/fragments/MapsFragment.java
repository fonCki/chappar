package com.example.chappar10.ui.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chappar10.R;
import com.example.chappar10.data.UsersDataRepository;
import com.example.chappar10.model.User;
import com.example.chappar10.ui.view_model.MainViewModel;
import com.example.chappar10.utils.ImageConverter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
        UsersDataRepository.getInstance().getUserLiveData(uid).observe(getViewLifecycleOwner(), user -> {
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
            if (googleMap != null) {
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
            }
        });
    }

}