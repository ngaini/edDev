package edu.scu.levelup;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class SignUpPage2 extends AppCompatActivity implements LocationListener{

    private RadioButton male;
    private RadioButton female;
    private RadioGroup genderGroup;
    private Spinner degreeList;
    private Spinner expertiseList;
    private EditText description;
    private EditText address;
    private EditText pincode;
    private TextView areaOfInterest;
    private int uRole;
    private String uFullName;
    private String uAge;
    private String uPhoneNumber;
    private String uPassword;
    private String uEmailID;
    private Button imageButton;
    private Button back;
    private Button confirm;
    private String uGender;
    private String uDegreeList;
    private String uExpertiseList;
    private String uDescription;
    private String uAddress;
    private ImageView image;
    private String uPincode;
    private String userID;
    boolean flagCapButton = false;
    File imageFile;
    static String imagePath;
    String imageName;
    public static final int IMAGE_CAPTURE_IDENTIFIER = 1;
    Bitmap currentImage;
    ImageView imgView;
    Intent curPhotoIntent;
    File externalPictureDirectory;
    Uri imageUri;
    String imageCaption;
    ContentValues cv;
    Firebase mref;
    Bitmap userImage;
    String uImage;
    String getImageString;
    private TextView latituteField;
    private TextView longitudeField;
    private LocationManager locationManager;
    private String provider;


    List<Address> addresses;
    private String constructedAddress;

    private static double latValue;
    private static double longValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        male = (RadioButton) findViewById(R.id.rd_Male);
        female = (RadioButton) findViewById(R.id.rd_Female);
        genderGroup = (RadioGroup) findViewById(R.id.rg_gender);
        degreeList = (Spinner) findViewById(R.id.spinner_Education);
        expertiseList = (Spinner) findViewById(R.id.spinner_Interest);
        description = (EditText) findViewById(R.id.description);
        back = (Button) findViewById(R.id.back);
        confirm = (Button) findViewById(R.id.confirm);
        address = (EditText) findViewById(R.id.txt_Address);
        pincode = (EditText) findViewById(R.id.txt_PinCode);
        areaOfInterest = (TextView) findViewById(R.id.txt_Expertise);
        imageButton = (Button) findViewById(R.id.btn_UploadUserImage);
        mref = new Firebase("https://scorching-inferno-7039.firebaseio.com");
        image = (ImageView) findViewById(R.id.userImageUploaded);
        Bundle extras = getIntent().getExtras();
        uRole = extras.getInt("userRole");
        uFullName = extras.getString("uFullName");
        uPhoneNumber = extras.getString("uPhoneNumber");
        uPassword = extras.getString("uPassword");
        uAge = extras.getString("uAge");
        uEmailID = extras.getString("uEmailID");
        userID = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        Toast.makeText(SignUpPage2.this, "Value of uROle is - "+uRole, Toast.LENGTH_SHORT);
        if(uRole == 1)
        {
            areaOfInterest.setText("I need Tutor for ");
        }else{
            areaOfInterest.setText("Expertise ");
        }

        //for getting the location

        //Code for zipcode
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
            pincode.setText(uPincode);
            address.setText(uAddress);
//            Toast.makeText(SignUpPage2.this, " "+uPincode+", "+uAddress, Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(SignUpPage2.this, "Network Issues unable to fetch Loaction", Toast.LENGTH_SHORT);
//			latituteField.setText("Location not available");
//			longitudeField.setText("Location not available");
        }







        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagCapButton = true;
                curPhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(curPhotoIntent.resolveActivity(getPackageManager()) != null)
                {
                    externalPictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    Random randomGenerator = new Random();
                    int randomNumber = randomGenerator.nextInt(10000);
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    imageName = uPhoneNumber + randomNumber + timeStamp;
                    imageFile = new File(externalPictureDirectory, imageName + ".jpg");
                    imageUri = Uri.fromFile(imageFile);
                    imagePath = imageFile.getAbsolutePath();
                    curPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(curPhotoIntent, IMAGE_CAPTURE_IDENTIFIER);
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genderGroup.getCheckedRadioButtonId() == male.getId()) {
                    uGender = "male";
                } else {
                    uGender = "female";
                }
                uDegreeList = degreeList.getSelectedItem().toString();
                uExpertiseList = expertiseList.getSelectedItem().toString();
                uDescription = description.getText().toString();
                uAddress = address.getText().toString();
                uPincode = pincode.getText().toString();

                if (uAddress.trim().isEmpty()) {
                    address.setError("Invalid Input");
                } else if (uPincode.trim().isEmpty()) {
                    pincode.setError("Invalid Input");
                } else if (uDescription.trim().isEmpty()) {
                    description.setError("Invalid Input");
                }
                else
                {
                uPincode = pincode.getText().toString();

//                Firebase newUserRef = mref.child("users").child(uFullName);
//                Firebase newUserRef = mref.child("users").push();
//                Users newUser = new Users(uRole, uFullName, uAge, uPhoneNumber, uPassword, uDegreeList, uDescription, uGender, uExpertiseList, uAddress, uPincode);

//                Firebase newUserRef = mref.child("users").child(uFullName);
//                Users newUser = new Users(uRole, uFullName, uAge, uPhoneNumber, uPassword, uDegreeList, uDescription, uGender, uExpertiseList, uAddress, uPincode);

                Firebase newUserRef = mref.child("users").child(uFullName);
                Users newUser = new Users(userID, uRole, uFullName, uAge, uEmailID, uPhoneNumber, uPassword, uDegreeList, uDescription, uGender, uExpertiseList, uAddress, uPincode);
                newUserRef.setValue(newUser);
                    Intent mainPage = new Intent(SignUpPage2.this, StudentsListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("uExpertiseList", uExpertiseList);
                    bundle.putString("uemailID", uEmailID);
                    bundle.putString("uFullName", uFullName);
                    bundle.putInt("uRole", uRole);
                    mainPage.putExtras(bundle);
                    startActivity(mainPage);
                mref.createUser(uEmailID, uPassword, new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {

                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {

                    }
                });

            }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent firstPage = new Intent(SignUpPage2.this, MainActivity.class);
                startActivity(firstPage);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_CAPTURE_IDENTIFIER)
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            Bitmap b = BitmapFactory.decodeFile(imagePath, options);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            uImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
            mref.child("images").child(userID).setValue(uImage);

            mref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    getImageString = (String) dataSnapshot.child(userID).getValue();
                    byte[] imageAsBytes = Base64.decode(getImageString, Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                    image.setImageBitmap(null);
                    image.setImageBitmap(bmp);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });
        }
        if (requestCode == RESULT_CANCELED)
        {
            flagCapButton = false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = (location.getLatitude());
        double lng = (location.getLongitude());
//        latituteField.setText(String.valueOf(lat));
//        longitudeField.setText(String.valueOf(lng));
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);


            if (addresses != null) {
                Log.e(" ADDR VALUE"," inside location changed" );
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
//                longitudeField.setText(strReturnedAddress.toString());
                uPincode = addresses.get(0).getPostalCode();
                uAddress = addresses.get(0).getAddressLine(0).toString();
//                address.setText(uAddress);
//                pincode.setText(uPincode);
                Log.e(" ADDR VALUE"," "+uAddress+", "+uPincode );
            } else {
                longitudeField.setText("No Address returned!");
            }

        } catch (IOException e) {
            e.printStackTrace();
//            e.printStackTrace();
            longitudeField.setText("Canont get Address!");
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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

    //construct address from pin code
    public String constructAddress(String zip, String streetAddress)
    {
        Geocoder coder = new Geocoder(this);
        List<Address> addresses1;
        double convLat;
        double convLong;
        final String zippy = "95050";
        try {
            String streetAddr = "1050 Benton Street";
            addresses1 = coder.getFromLocationName(zippy, 1);
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
                        constructedAddress = streetAddr + " " + city + " " + state + " " +country +" "+zippy;
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

        return null;
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

//                addr =convToAddr(latValue, longValue);

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
