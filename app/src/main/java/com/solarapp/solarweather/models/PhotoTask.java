package com.solarapp.solarweather.models;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;
import com.solarapp.solarweather.App;

abstract public class PhotoTask extends AsyncTask<String, Void, PhotoTask.AttributedPhoto> {

    private int mHeight;

    private int mWidth;

    public PhotoTask(int width, int height) {
        mHeight = height;
        mWidth = width;
    }

    /**
     * Loads the first photo for a place id from the Geo Data API.
     * The place id must be the first (and only) parameter.
     */
    @Override
    protected AttributedPhoto doInBackground(String... params) {

        if (params.length != 1) {
            return null;
        }

        final String placeId = params[0];
        AttributedPhoto attributedPhoto = null;

        PlacePhotoMetadataResult result = Places.GeoDataApi
                .getPlacePhotos(App.getGoogleApiClient(), placeId).await();
        if (result.getStatus().isSuccess()) {
            PlacePhotoMetadataBuffer photoMetadataBuffer = result.getPhotoMetadata();
            if (photoMetadataBuffer.getCount() > 0 && !isCancelled()) {
                // Get the first bitmap and its attributions.
                System.out.println("get " + photoMetadataBuffer.getCount() + " photos");
                Bitmap[] image = new Bitmap[photoMetadataBuffer.getCount()];
                CharSequence[] attribution = new CharSequence[photoMetadataBuffer.getCount()];
                for (int i = 0; i < photoMetadataBuffer.getCount(); i++) {
                    PlacePhotoMetadata photo = photoMetadataBuffer.get(i);
                    attribution[i] = photo.getAttributions();
                    image[i] = photo.getScaledPhoto(App.getGoogleApiClient(), mWidth, mHeight).await()
                            .getBitmap();
                }
//                PlacePhotoMetadata photo = photoMetadataBuffer.get(0);
//                CharSequence attribution = photo.getAttributions();
                // Load a scaled bitmap for this photo.


                attributedPhoto = new AttributedPhoto(attribution, image);
            }
            // Release the PlacePhotoMetadataBuffer.
            photoMetadataBuffer.release();
        }
        return attributedPhoto;
    }

    /**
     * Holder for an image and its attribution.
     */
    public class AttributedPhoto {

        public final CharSequence[] attribution;

        public final Bitmap[] bitmap;

        public AttributedPhoto(CharSequence[] attribution, Bitmap[] bitmap) {
            this.attribution = attribution;
            this.bitmap = bitmap;
        }
    }
}
