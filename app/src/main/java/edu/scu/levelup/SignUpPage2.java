package edu.scu.levelup;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.KeyCharacterMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class SignUpPage2 extends AppCompatActivity {

    private RadioButton male;
    private RadioButton female;
    private RadioGroup genderGroup;
    private Spinner degreeList;
    private Spinner expertiseList;
    private EditText description;
    private EditText address;
    private EditText pincode;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        imageButton = (Button) findViewById(R.id.btn_UploadUserImage);
        mref = new Firebase("https://scorching-inferno-7039.firebaseio.com");
        image = (ImageView) findViewById(R.id.userImageUploaded);
        Bundle extras = getIntent().getExtras();
        userID = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());


        uRole = extras.getInt("userRole");
        uFullName = extras.getString("uFullName");
        uPhoneNumber = extras.getString("uPhoneNumber");
        uPassword = extras.getString("uPassword");
        uAge = extras.getString("uAge");
        uEmailID = extras.getString("uEmailID");

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
                Firebase newUserRef = mref.child("users").child(uFullName);
                Users newUser = new Users(userID, uRole, uFullName, uAge, uPhoneNumber, uPassword, uDegreeList, uDescription, uGender, uExpertiseList, uAddress, uPincode);
                newUserRef.setValue(newUser);
                mref.createUser(uEmailID, uPassword, new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {
                        Intent mainPage = new Intent(SignUpPage2.this, ListAndOptionPage.class);
                        startActivity(mainPage);
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {

                    }
                });

            }
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

}
