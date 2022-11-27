package com.example.chappar10.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Objects;

public class PicassoMarker implements Target {
    private Marker mMarker;

    public PicassoMarker(Marker marker) {
        mMarker = marker;
    }


    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
        Bitmap circleBitmap = ImageConverter.getCircleBitmap(bitmap, 150);
        mMarker.setIcon(BitmapDescriptorFactory.fromBitmap(circleBitmap));
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PicassoMarker)) return false;
        PicassoMarker that = (PicassoMarker) o;
        return Objects.equals(mMarker, that.mMarker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mMarker);
    }
}

