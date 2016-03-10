package edu.scu.levelup;

import android.content.Intent;
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
    private String uEmailID, uFullName;
    private String uPassword;
    private String userEmailID;
    private String userOldPassword;
    private String userNewPassword;
    private String userConfirmPassword;
    Firebase mref,href;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword = (EditText) findViewById(R.id.change_oldPassword);
        newPassword = (EditText) findViewById(R.id.change_NewPassword);
        confirmPassword = (EditText) findViewById(R.id.change_ConfirmPassword);
        confirm = (Button) findViewById(R.id.change_Confirm);
        back = (Button) findViewById(R.id.change_Back);
        Bundle extras = getIntent().getExtras();
        uEmailID = extras.getString("uEmailID");
        uFullName = extras.getString("uFullName");
        Toast.makeText(changePassword.this, "The email id is - "+uEmailID, Toast.LENGTH_SHORT).show();
        mref = new Firebase("https://scorching-inferno-7039.firebaseio.com");
        href = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/"+uFullName);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmailID = uEmailID.toString().trim();
                userOldPassword =  oldPassword.getText().toString().trim();
                userConfirmPassword = confirmPassword.getText().toString().trim();
                userNewPassword = newPassword.getText().toString().trim();
                //Toast.makeText(changePassword.this, "The old password is - "+userOldPassword, Toast.LENGTH_SHORT).show();
                //Toast.makeText(changePassword.this, "The new password is - "+userConfirmPassword, Toast.LENGTH_SHORT).show();
                if(userNewPassword.equals(userConfirmPassword))
                {
                 mref.changePassword(userEmailID, userOldPassword, userConfirmPassword, new Firebase.ResultHandler()
                 {
                     @Override
                     public void onSuccess() {
                         Toast.makeText(changePassword.this, "Password Changed!", Toast.LENGTH_SHORT).show();
                         href.child("password").setValue(userConfirmPassword);
                         mref.unauth();
                         Intent login = new Intent(changePassword.this, Login.class);
                         startActivity(login);
                     }

                     @Override
                     public void onError(FirebaseError firebaseError) {
                         Toast.makeText(changePassword.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                     }
                 });
                }
                else{
                    newPassword.setError("Password doesn't match!");
                    confirmPassword.setError("Password doesn't match!");
                }

            }
        });
    }

}
