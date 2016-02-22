package edu.scu.levelup;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView clickLink;
    TextView fireData;
    String returnedValue;
    String uname;
    String pass;
    static AppDatabase db;
    static SQLiteDatabase sqdb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        clickLink = (TextView) findViewById(R.id.login_SignUp);
        clickLink.setPaintFlags(clickLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        username = (EditText) findViewById(R.id.login_emailID);
        password = (EditText) findViewById(R.id.login_Password);
        login = (Button) findViewById(R.id.main_Login);

        clickLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iSignUp = new Intent(Login.this, MainActivity.class);
                startActivity(iSignUp);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = username.getText().toString();
                pass = password.getText().toString();
                Log.e("AJAY" ," VIDEKAR " + uname);
                Log.e("AJAY" ," VIDEKAR " + pass);
                if(uname.isEmpty() || pass.isEmpty())
                {
                    username.setError("Invalid Input");
                }
                else {
                    returnedValue = db.Login(uname);
                    Log.e("AJAY" ," VIDEKAR " + returnedValue);
                    if (returnedValue.equals(pass)) {

                        username.setError("SUCCESSFUL");
                    } else {
                        username.setError("FAILED");

                    }
                }
            }
        });
    }

}
