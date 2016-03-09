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
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;


public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private Button newUser;
    private TextView clickLink;
    String uname;
    String pass;
    Firebase mref, userRef;
    Resources res;
    UserSessionManager session;
    Query qref;
    String uExpertiseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        mref = new Firebase("https://scorching-inferno-7039.firebaseio.com");
        userRef = new Firebase("https://scorching-inferno-7039.firebaseio.com/users");
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

        userRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    Toast.makeText(getApplicationContext(), "authentication is working", Toast.LENGTH_SHORT).show();
                    Intent toMainActivity = new Intent(Login.this, StudentsListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("uExpertiseList", "Dance");
                    toMainActivity.putExtras(bundle);
                    startActivity(toMainActivity);
                } else {
                    Toast.makeText(getApplicationContext(), "authentication failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                            Toast.makeText(getApplicationContext(), "The email ID is - " + uname, Toast.LENGTH_SHORT).show();
                            uExpertiseList = getUserInterest(uname);
                            Toast.makeText(getApplicationContext(), " " + uExpertiseList, Toast.LENGTH_SHORT).show();
                            //Intent mainPage = new Intent(Login.this, ListAndOptionPage.class);
                            //startActivity(mainPage);
                            //Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                            Intent mainPage = new Intent(Login.this, StudentsListActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("uExpertiseList", uExpertiseList);
                            mainPage.putExtras(bundle);
                            startActivity(mainPage);
                            session.createUserLoginSession(uname, pass);
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

    public String getUserInterest(String uname) {
        qref = userRef.orderByChild("emailID").equalTo(uname);
        qref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), "INSIDE return string", Toast.LENGTH_SHORT).show();
                Users userData = dataSnapshot.getValue(Users.class);
                uExpertiseList = userData.getInterests();
                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
        return uExpertiseList;
    }
}
