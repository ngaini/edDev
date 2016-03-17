package edu.scu.levelup;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends Activity implements LocationListener {

    private TextView latituteField;
    private TextView longitudeField;
    private LocationManager locationManager;
    private String provider;
    List<Address> addresses;
    private String constructedAddress;

    private static double latValue;
    private static double longValue;

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        latituteField = (TextView) findViewById(R.id.location_displayLati_textView);
        longitudeField = (TextView) findViewById(R.id.location_displayLongi_textView);

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Log.e("###SEE THIS", " before on location chenged");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
//        Log.e("###SEE THIS", location.toString());
        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");

            onLocationChanged(location);
        } else {
            latituteField.setText("Location not available");
            longitudeField.setText("Location not available");
        }

        // for getting lat long from zipcode
        Geocoder coder = new Geocoder(this);
        List<Address> addresses1;
        double convLat;
        double convLong;
        final String zip = "95050";
        try {
            String streetAddr = "1050 Benton Street";
            addresses1 = coder.getFromLocationName(zip, 1);
            if (addresses1 != null && !addresses1.isEmpty()) {
                Address address = addresses1.get(0);
                // Use the address as needed
                convLat = address.getLatitude();
                convLong = address.getLongitude();
                String message = String.format("Latitude: %f, Longitude: %f",
                        address.getLatitude(), address.getLongitude());
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();

                //convert back to address
                Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
                try {
                    List<Address> addrConv = geocoder.getFromLocation(convLat, convLong, 1);


                    if (addrConv != null) {
                        Address returnedAddress = addrConv.get(0);
//                        StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
//                        for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
//                            strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
//                        }
//                        longitudeField.setText(strReturnedAddress.toString());

                        String city = addrConv.get(0).getLocality();
                        String state = addrConv.get(0).getAdminArea();
                        String country = addrConv.get(0).getCountryName();
//                        constructedAddress = streetAddr + " " + city + " " + state + " " + " "+zip+" " + country;
                        constructedAddress = "1405 Civic Centre Drive" + " " + "Santa Clara" + " " + "California" + " " +"USA"+ "95050";
                        ((TextView) findViewById(R.id.location_givenAddr_textView)).setText("the given address is:" + constructedAddress);

                        //getting the same address from the constructed address

                        String returnedAddr =convertToLatLong(constructedAddress);
                        ((TextView) findViewById(R.id.location_constructedAddr_textView)).setText("Trying to get the same address: \n"+returnedAddr);



                    } else {
                        longitudeField.setText("No Address returned!");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
//            e.printStackTrace();
                    longitudeField.setText("Canont get Address!");
                }


                ((TextView) findViewById(R.id.location_displayLati_textView)).setText(message);
            } else {
                // Display appropriate message when Geocoder services are not available
                Toast.makeText(this, "Unable to geocode zipcode", Toast.LENGTH_LONG).show();
            }




        } catch (IOException e) {
            // handle exception
        }
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = (location.getLatitude());
        double lng = (location.getLongitude());
        latituteField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(lng));
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);


            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                longitudeField.setText(strReturnedAddress.toString());
            } else {
                longitudeField.setText("No Address returned!");
            }

        } catch (IOException e) {
            e.printStackTrace();
//            e.printStackTrace();
            longitudeField.setText("Canont get Address!");
        }
//        Geocoder geocoder;
//        List<Address> addresses;
//        geocoder = new Geocoder(this, Locale.getDefault());
//
//        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//
//        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//        String city = addresses.get(0).getLocality();
//        String state = addresses.get(0).getAdminArea();
//        String country = addresses.get(0).getCountryName();
//        String postalCode = addresses.get(0).getPostalCode();
//        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    public void doThis(View v) {
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            latituteField.setText("Location not available");
            longitudeField.setText("Location not available");
        }
    }

    public static void getLatLong(String zipcode, Context context) {
//        Geocoder coder = new Geocoder(context);
//        List<Address> addresses;
//        final String zip = "95050";
//        try
//        {
//            addresses = coder.getFromLocationName(zip,1);
//            if (addresses != null && !addresses.isEmpty()) {
//                Address address = addresses.get(0);
//                // Use the address as needed
//                String message = String.format("Latitude: %f, Longitude: %f",
//                        address.getLatitude(), address.getLongitude());
//                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//
//                ((TextView)this.findViewById(R.id.location_displayLati_textView)).setText(message);
//            } else {
//                // Display appropriate message when Geocoder services are not available
//                Toast.makeText(context, "Unable to geocode zipcode", Toast.LENGTH_LONG).show();
//            }
//        } catch (IOException e) {
//            // handle exception
//        }

    }


    //convert address into lat long
    public String convertToLatLong(String completeAddr) {
        Geocoder coder = new Geocoder(this);
        addresses = null;
        latValue = longValue =0;
        String addr = null;
        try {
            Log.e("LocationDETAILS", completeAddr);
            addresses = coder.getFromLocationName(completeAddr, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                // Use the address as needed
                latValue = address.getLatitude();
                longValue = address.getLongitude();
                String message = String.format("Latitude: %f, Longitude: %f",
                        address.getLatitude(), address.getLongitude());
                Log.e("LocationDETAILS", " lat long value : "+ message);
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();

                 addr =convToAddr(latValue, longValue);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return addr;
    }

    public String convToAddr(double lat, double lng)
    {
        //convert from lat long to address
        String constructedAddressForMethod=null;
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        try {
            List<Address> addrConv = geocoder.getFromLocation(lat, lng, 1);


            if (addrConv != null) {
                Address returnedAddress = addrConv.get(0);
                        StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                        for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                            strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                        }
                        longitudeField.setText(strReturnedAddress.toString());

//                String city = addrConv.get(0).getLocality();
//                String state = addrConv.get(0).getAdminArea();
//                String country = addrConv.get(0).getCountryName();
                constructedAddressForMethod = strReturnedAddress.toString();
                ((TextView) findViewById(R.id.location_constructedAddr_textView)).setText(constructedAddress);
            } else {
                longitudeField.setText("No Address returned!");
//                constructedAddressForMethod = "";
            }

        } catch (IOException e) {
            e.printStackTrace();
//            e.printStackTrace();
            longitudeField.setText("Canont get Address!");
        }

        return constructedAddressForMethod;
    }

}