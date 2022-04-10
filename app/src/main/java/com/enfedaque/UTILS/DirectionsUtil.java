package com.enfedaque.UTILS;

import com.google.maps.model.LatLng;

import java.util.List;

public class DirectionsUtil {

    public static LatLng fromMapsToDirectionsApi(com.google.android.gms.maps.model.LatLng position) {
        return new LatLng(position.latitude, position.longitude);
    }

    public static com.google.android.gms.maps.model.LatLng fromDirectionsApiToMaps(LatLng position) {
        return new com.google.android.gms.maps.model.LatLng(position.lat, position.lng);
    }

    public static com.google.android.gms.maps.model.LatLng[] fromMapsToDirections(List<LatLng> positions) {
        com.google.android.gms.maps.model.LatLng[] convertedPositions = new com.google.android.gms.maps.model.LatLng[positions.size()];

        for (int i = 0; i < positions.size(); i++) {
            convertedPositions[i] = DirectionsUtil.fromDirectionsApiToMaps(positions.get(i));
        }

        return convertedPositions;
    }
}
