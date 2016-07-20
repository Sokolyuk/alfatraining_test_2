package de.sda.einkaufsliste;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.sda.einkaufsliste.model.GPSConsts;

/**
 * Created by Alfa on 19.07.2016.
 */
public class FragmentHome extends Fragment{

    private TextView latitude;
    private TextView altitude;
    private TextView longitude;
    private TextView status;

    private GPSLocationListener mLocationListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmant_home, container, false);

        latitude = (TextView) view.findViewById(R.id.f_h_Latitude);
        longitude = (TextView) view.findViewById(R.id.f_h_Longitude);
        altitude = (TextView) view.findViewById(R.id.f_h_Altitude);
        status = (TextView) view.findViewById(R.id.f_h_Status);

        mLocationListener = new GPSLocationListener();

        ((Button)view.findViewById(R.id.f_h_startGPS)).setOnClickListener(v -> {
            //Ueberpruefen ob die Berechtigung genehmigt wurd
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                //Erfragen der Berechtigung
                getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                status.setText("requestPermissions");
            } else {
                //Berechigung ist eingeräumt
                ((MainActivity)getActivity()).mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        GPSConsts.MIN_TIME_TO_REFRESH,
                        GPSConsts.MIN_DISTANCE_TO_REFRESH,
                        mLocationListener);
            }
        });


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        //Location Bestimmung abschalte
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            ((MainActivity)getActivity()).mLocationManager.removeUpdates(mLocationListener);
        }
    }

    public class GPSLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            latitude.setText(String.valueOf(location.getLatitude()));
            longitude.setText(String.valueOf(location.getLongitude()));
            altitude.setText(String.valueOf(location.getAltitude()));

            status.setText("onLocationChanged");
        }

        @Override
        public void onStatusChanged(String provider, int aStatus, Bundle extras) {
            status.setText(String.format("onStatusChanged: status='%d', provider='%s', extras='%s'", aStatus, provider, extras.toString()));
        }

        @Override
        public void onProviderEnabled(String provider) {
            status.setText(String.format("GPS is enabled. provider='%s'", provider));
        }

        @Override
        public void onProviderDisabled(String provider) {
            status.setText(String.format("GPS is disabled. provider='%s'", provider));
        }
    }

}
