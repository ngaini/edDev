package edu.scu.levelup;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class EditProfile extends Activity {

    private EditText streetAdrress;
    private EditText pinCode;
    private EditText phoneNumber;
    private EditText description;
    private Spinner education;
    private Spinner interest;
    private Spinner role;
    private Button confirm;
    private Button back;
    Firebase mref,href,xref;
    private String uEmailID1, uFullName;
    Query queryRef;
    private String userStreetAddress, userpinCode, userphoneNumber, userDescription, userRole, userID, userFullName, userAge, userEmailID, userPassword, userGender;
    private String uAddress, uPincode, uPhoneNumber, uEducation, uInterests, uDescription, uID, uFUllName, uAge, uEmailID, uPassword, uGender;
    private int uRole, userRoleInt;
    private String userStatement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Firebase.setAndroidContext(this);

        streetAdrress = (EditText) findViewById(R.id.update_StreetAdress);
        pinCode = (EditText) findViewById(R.id.update_PinCode);
        phoneNumber = (EditText) findViewById(R.id.update_PhoneNumber);
        description = (EditText) findViewById(R.id.update_Description);
        education = (Spinner) findViewById(R.id.update_Education);
        interest = (Spinner) findViewById(R.id.update_Interest);
        role = (Spinner) findViewById(R.id.update_UserRole);
        confirm = (Button) findViewById(R.id.update_Confirm);
        back = (Button) findViewById(R.id.update_Back);
        Bundle extras = getIntent().getExtras();
        uEmailID1 = extras.getString("uEmailID");
        userID = extras.getString("userID");
        uFullName = extras.getString("uFullName");
        uRole = extras.getInt("uRole");
        //Toast.makeText(getApplicationContext(), " "+uFullName, Toast.LENGTH_SHORT).show();
        if(uRole == 1) {
            userStatement = "Student";
            mref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/"+userStatement);
        }else {
            userStatement = "Tutor";
            mref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/"+userStatement);
        }

        queryRef = mref.orderByChild("userID").equalTo(userID);

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Users userData = postSnapshot.getValue(Users.class);
//                    userStreetAddress = userData.getAddress();
//                    userpinCode = userData.getPincode();
                    userphoneNumber = userData.getPhoneNumber();
                    userDescription = userData.getDescription();
                    userID = userData.getUserID();
                    userFullName = userData.getFullName();
                    userAge = userData.getAge();
                    userEmailID = userData.getEmailID();
                    userPassword = userData.getPassword();
                    userGender = userData.getGender();
                    streetAdrress.setText(userStreetAddress);
                    pinCode.setText(userpinCode);
                    phoneNumber.setText(userphoneNumber);
                    description.setText(userDescription);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uAddress = streetAdrress.getText().toString().trim();
                uPincode = pinCode.getText().toString().trim();
                uPhoneNumber = phoneNumber.getText().toString().trim();
                uDescription = description.getText().toString().trim();
                uID = userID.toString().trim();
                uFullName = userFullName.toString().trim();
                uAge = userAge.toString().trim();
                uEmailID = userEmailID.toString().trim();
                uPassword = userPassword.toString().trim();
                uGender = userGender.toString().trim();
                uEducation = education.getSelectedItem().toString();
                uInterests = interest.getSelectedItem().toString();
                userRole = role.getSelectedItem().toString();
                if(userRole.equals("Student"))
                {
                    userRoleInt = 1;
                }
                else{
                    userRoleInt = 0;
                }

                if(uAddress.equals(""))
                {
                    streetAdrress.setError("Field cannot be Empty!");
                }
                else if(uPincode.equals(""))
                {
                    pinCode.setError("Field cannot be Empty!");
                }
                else if(uPhoneNumber.equals(""))
                {
                    phoneNumber.setError("Field cannot be Empty!");
                }
                else if(uDescription.equals(""))
                {
                    description.setError("Field cannot be Empty!");
                }
                else{
                    if(uRole == userRoleInt) {
                        mref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/" + userRole);
                        Firebase newUserRef = mref.child(userID);
//                        Users newUser = new Users(uID, uRole, uFullName, uAge, uEmailID, uPhoneNumber, uPassword, uEducation, uDescription, uGender, uInterests, lat, lng);
//                        newUserRef.setValue(newUser);
                    }
//                    }else
//                    {
//                        mref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/"+userStatement);
//                        Firebase newUserRef = mref.child(userID);
//                        newUserRef.removeValue();
//                        xref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/"+userRole);
//                        Firebase newUserRef1 = mref.child(userID);
//                        Users newUser = new Users(uID, uRole, uFullName, uAge, uEmailID, uPhoneNumber, uPassword, uEducation, uDescription, uGender, uInterests, uAddress, uPincode);
//                        newUserRef1.setValue(newUser);
//                    }
                    Intent mainPage = new Intent(EditProfile.this, StudentsListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("uExpertiseList", uInterests);
                    bundle.putString("uemailID", uEmailID);
                    bundle.putString("uFullName", uFullName);
                    bundle.putInt("uRole", uRole);
                    mainPage.putExtras(bundle);
                    startActivity(mainPage);
                    Toast.makeText(getApplicationContext(), "CHECK IF UPDATED", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
