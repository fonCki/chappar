package com.example.chappar10.ui.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MapsFragment extends Fragment {

    GoogleMap googleMap;

    private MainViewModel mainViewModel;
    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            MapsFragment.this.googleMap = googleMap;
            putMarkers();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
            mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        }
        Log.i("MapsFragment", "second: ");
    }


    private void putMarkers() {
        mainViewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            if (googleMap != null) {
                //clear map
                googleMap.clear();
                //add markers
                for (User user : users) {
                    LatLng latLng = new LatLng(user.getLocation().getLatitude(), user.getLocation().getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(user.getUid());


                    Picasso.get().load(user.getProfileImageUrl()).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
                            Bitmap circleBitmap = ImageConverter.getCircleBitmap(bitmap, 150);
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(circleBitmap));
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    });
                    googleMap.addMarker(markerOptions);
                    googleMap.animateCamera(com.google.android.gms.maps.CameraUpdateFactory.zoomTo(5));
                    googleMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLng(latLng));
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