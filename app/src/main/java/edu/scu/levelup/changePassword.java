package edu.scu.levelup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class changePassword extends Activity {

    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private Button confirm;
    private Button back;
    private String uEmailID, uFullName, userID;
    private String uPassword;
    private String userEmailID;
    private String userOldPassword, userStatement;
    private String userNewPassword;
    private String userConfirmPassword;
    private int uRole;
    Firebase mref;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Query qref;
    Firebase userRef, userRef1;
    private static final String preferName = "AndriodSession";
    private String sessionUserName, sessionUserID;
    public static final String key_userid = "name";
    public static final String key_email = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        pref = getApplicationContext().getSharedPreferences(preferName, 0);
        editor = pref.edit();
        sessionUserName = pref.getString(key_email, null);
        Toast.makeText(changePassword.this, "Session user name is - " +sessionUserName, Toast.LENGTH_SHORT).show();
        sessionUserID = pref.getString(key_userid, null);
        oldPassword = (EditText) findViewById(R.id.change_oldPassword);
        newPassword = (EditText) findViewById(R.id.change_NewPassword);
        confirmPassword = (EditText) findViewById(R.id.change_ConfirmPassword);
        confirm = (Button) findViewById(R.id.change_Confirm);
        back = (Button) findViewById(R.id.change_Back);

        //fetching the parent of emailID
        userRef = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student/");
        qref = userRef.orderByChild("emailID").equalTo(sessionUserName);
        qref.addListenerForSingleValueEvent(new ValueEventListener() {

                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.getChildrenCount() != 0) {
                                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                            Users userData = postSnapshot.getValue(Users.class);
                                                            uRole = userData.getRole();
                                                            userID = userData.getUserID();
                                                        }
                                                    } else {
                                                        userRef = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
                                                        qref = userRef.orderByChild("emailID").equalTo(sessionUserName);
                                                        qref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                                    Users userData = postSnapshot.getValue(Users.class);
                                                                    uRole = userData.getRole();
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(FirebaseError firebaseError) {

                                                            }
                                                        });

                                                    }


                                                }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
});
        if(uRole == 1)
        {
            userStatement = "Student";
        }else
        {
            userStatement = "Tutor";
        }

        mref = new Firebase("https://scorching-inferno-7039.firebaseio.com");
        userRef1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/"+userStatement+"/"+userID);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userOldPassword = oldPassword.getText().toString().trim();
                userConfirmPassword = confirmPassword.getText().toString().trim();
                userNewPassword = newPassword.getText().toString().trim();

                if (userNewPassword.equals(userConfirmPassword)) {
                    mref.changePassword(sessionUserName, userOldPassword, userConfirmPassword, new Firebase.ResultHandler() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(changePassword.this, "Password Changed!", Toast.LENGTH_SHORT).show();
                            userRef1.child("password").setValue(userConfirmPassword);
                            editor.clear();
                            editor.commit();
                            mref.unauth();
                            Intent login = new Intent(changePassword.this, Login.class);
                            startActivity(login);
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            Toast.makeText(changePassword.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    newPassword.setError("Password doesn't match!");
                    confirmPassword.setError("Password doesn't match!");
                }

            }
        });
    }

}
