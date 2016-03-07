package edu.scu.levelup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyCharacterMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

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
    private int uAge;
    private int uPhoneNumber;
    private String uPassword;
    private String uEmailID;
    private Button back;
    private Button confirm;
    private String uGender;
    private String uDegreeList;
    private String uExpertiseList;
    private String uDescription;
    private String uAddress;
    private int uPincode;
    Firebase mref;


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
        mref = new Firebase("https://scorching-inferno-7039.firebaseio.com");
        Bundle extras = getIntent().getExtras();

        uRole = extras.getInt("userRole");
        uFullName = extras.getString("uFullName");
        uPhoneNumber = extras.getInt("uPhoneNumber");
        uPassword = extras.getString("uPassword");
        uAge = extras.getInt("uAge");
        uEmailID = extras.getString("uEmailID");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(genderGroup.getCheckedRadioButtonId() == male.getId())
                {
                    uGender = "male";
                }
                else
                {
                    uGender = "female";
                }
                uDegreeList = degreeList.getSelectedItem().toString();
                uExpertiseList = expertiseList.getSelectedItem().toString();
                uDescription = description.getText().toString();
                uAddress = address.getText().toString();
                uPincode = Integer.parseInt(pincode.getText().toString());
//                Firebase newUserRef = mref.child("users").child(uFullName);
                Firebase newUserRef = mref.child("users").push();
                Users newUser = new Users(uRole, uFullName, uAge, uPhoneNumber, uPassword, uDegreeList, uDescription, uGender, uExpertiseList, uAddress, uPincode);
                newUserRef.setValue(newUser);
                mref.createUser(uEmailID, uPassword, new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {
                        Intent mainPage = new Intent(SignUpPage2.this, StudentList.class);
                        startActivity(mainPage);
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {

                    }
                });
            }
        });
    }

}
