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
    private String userStatement;
    String uname;
    String pass;
    private int uRole;
    Firebase mref, userRef;
    Resources res;
    UserSessionManager session;
    Query qref;
    private static String uExpertiseList, uEmailID, uFullName;
    Users userData;


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

        userRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    userRef.unauth();
//                    Toast.makeText(getApplicationContext(), "authentication is working", Toast.LENGTH_SHORT).show();
//                    Intent toMainActivity = new Intent(Login.this, StudentsListActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("uExpertiseList", "Dance");
//                    bundle.putString("uemailID", "g@g.com");
//                    bundle.putString("uExpertiseList", "Dance");
//                    bundle.putString("uFullName", "g");
//                    bundle.putInt("uRole", 1);
//                    toMainActivity.putExtras(bundle);
//                    startActivity(toMainActivity);
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
                            userRef = new Firebase("https://scorching-inferno-7039.firebaseio.com/users");
                            qref = userRef.orderByChild("emailID").equalTo(uname);
                            qref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        Users userData = postSnapshot.getValue(Users.class);
                                        uExpertiseList = userData.getInterests();
                                        uEmailID = userData.getEmailID();
                                        uFullName = userData.getFullName();
                                        uRole = userData.getRole();
                                    }
                                    Intent mainPage = new Intent(Login.this, StudentsListActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("uExpertiseList", uExpertiseList);
                                    bundle.putString("uemailID", uname);
                                    bundle.putString("uFullName", uFullName);
                                    bundle.putInt("uRole", uRole);
                                    mainPage.putExtras(bundle);
                                    startActivity(mainPage);
                                    session.createUserLoginSession(uname, pass);

                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }

                            });

                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError) {
                            switch (firebaseError.getCode()){
                                case FirebaseError.USER_DOES_NOT_EXIST:
                                    username.setError("username does not exist");
                                    break;
                                case FirebaseError.INVALID_PASSWORD:
                                    password.setError("password is not correct");
                            }
                        }
                    });
                }
            }
        });
    }

}
