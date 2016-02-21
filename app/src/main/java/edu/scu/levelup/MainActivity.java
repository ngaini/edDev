package edu.scu.levelup;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText fullName;
    private EditText age;
    private EditText phoneNumber;
    private EditText emailID;
    private EditText password;
    private EditText rePassword;
    private Button confirm;
    private Button back;
    private boolean flag = true;
    private String uFullName;
    private String uRole;
    private String uAge;
    private String uPhoneNumber;
    private String uEmailID;
    private String uPassword;
    ContentValues contentvalues;
    static AppDatabase db;
    static SQLiteDatabase sqdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        db = new AppDatabase(this);
        sqdb = db.getWritableDatabase();

        fullName = (EditText) findViewById(R.id.main_FullName);
        age = (EditText) findViewById(R.id.main_Age);
        phoneNumber = (EditText) findViewById(R.id.main_PhoneNumber);
        emailID = (EditText) findViewById(R.id.main_EmailAddress);
        password = (EditText) findViewById(R.id.main_Password);
        rePassword = (EditText) findViewById(R.id.main_ConfirmPassword);
        confirm = (Button) findViewById(R.id.main_Confirm);
        back = (Button) findViewById(R.id.main_Back);



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uRole = "1";
                uFullName = fullName.getText().toString();
                uAge = age.getText().toString();
                uPhoneNumber = phoneNumber.getText().toString();
                uEmailID = emailID.getText().toString();
                uPassword = password.getText().toString();
                if (fullName.getText().toString().trim().isEmpty())
                {
                    fullName.setError("Invalid Input");
                }
                else if (age.getText().toString().trim().isEmpty())
                {
                    age.setError("Invalid Input");
                }
                else if (phoneNumber.getText().toString().trim().isEmpty())
                {
                    phoneNumber.setError("Invalid Input");
                }
                else if (emailID.getText().toString().trim().isEmpty())
                {
                    emailID.setError("Invalid Input");
                }
                else if (password.getText().toString().trim().isEmpty())
                {
                    password.setError("Invalid Input");
                }
                else if(rePassword.getText().toString().trim().isEmpty())
                {
                    rePassword.setError("Invalid Input");
                }
                else if (!password.getText().toString().trim().equals(rePassword.getText().toString().trim()))
                {
                    password.setError("Password does not match");
                    rePassword.setError("Password does not match");
                }
                else {
                    contentvalues = new ContentValues();
                    contentvalues.put(AppDatabase.COLUMN_ROLE, uRole);
                    contentvalues.put(AppDatabase.COLUMN_FULLNAME, uFullName);
                    contentvalues.put(AppDatabase.COLUMN_AGE, uAge);
                    contentvalues.put(AppDatabase.COLUMN_PHONE_NUMBER, uPhoneNumber);
                    contentvalues.put(AppDatabase.COLUMN_EMAILID, uEmailID);
                    contentvalues.put(AppDatabase.COLUMN_PASSWORD, uPassword);
                    long res = MainActivity.sqdb.insert(AppDatabase.TABLE_NAME, null, contentvalues);

                }

            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
