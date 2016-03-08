package edu.scu.levelup;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private Button newUser;
    private TextView clickLink;
    String uname;
    String pass;
    Firebase mref;
    Resources res;
    UserSessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        mref = new Firebase("https://scorching-inferno-7039.firebaseio.com");
        res = getResources();
        session = new UserSessionManager(getApplicationContext());
        username = (EditText) findViewById(R.id.login_emailID);
        password = (EditText) findViewById(R.id.login_Password);
        login = (Button) findViewById(R.id.main_Login);
        newUser = (Button) findViewById(R.id.btn_NewUser);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iSignUp = new Intent(Login.this, RoleChoice.class);
                startActivity(iSignUp);
            }
        });

//        mref.addAuthStateListener(new Firebase.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(AuthData authData) {
//                if (authData != null) {
//                    Toast.makeText(getApplicationContext(), "authentication is working", Toast.LENGTH_SHORT).show();
//                    Intent toMainActivity = new Intent(Login.this, StudentsListActivity.class);
//                    startActivity(toMainActivity);
//                } else {
//                    Toast.makeText(getApplicationContext(), "authentication failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = username.getText().toString();
                pass = password.getText().toString();
                if (uname.isEmpty() || pass.isEmpty()) {
                    username.setError("Invalid Input");
                } else {
                    mref.authWithPassword(uname, pass, new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData) {
                            //Intent mainPage = new Intent(Login.this, ListAndOptionPage.class);
                            //startActivity(mainPage);
                            Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                            session.createUserLoginSession(uname, pass);
                            Intent i = new Intent(getApplicationContext(), StudentsListActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();
                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

}
