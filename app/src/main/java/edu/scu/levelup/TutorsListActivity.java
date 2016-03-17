package edu.scu.levelup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.firebase.client.Firebase;

import static edu.scu.levelup.R.id.tutorToolbar;


/**
 * TutorsListActivty is the list view that the tutors sees when he is loggedin
 * he is able to view only the users that are interested to hire him
 *
 */
public class TutorsListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ActionBarDrawerToggle drawerListner;
    private CustomAdapter myCustomAdapter;
    private String[] navOptions;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static Firebase ref1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutors_list);
        Toolbar myToolbar = (Toolbar) findViewById(tutorToolbar);
        setSupportActionBar(myToolbar);

        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        ListView list = (ListView)findViewById(R.id.drawerList);
        myCustomAdapter = new CustomAdapter(this);
        list.setAdapter(myCustomAdapter);
        navOptions =getResources().getStringArray(R.array.navOptions);
        list.setOnItemClickListener(this);



        drawerListner = new ActionBarDrawerToggle(this,drawerLayout,myToolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//                getActionBar().setTitle(R.string.title_activity_drawer_test);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                getActionBar().setTitle("Menu");
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.setDrawerListener(drawerListner);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // this causes the drawer icon to appear
        drawerListner.syncState();
    }

    // change the navigation drawer when the configuration changes
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListner.onConfigurationChanged(newConfig);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        selectTitle(navOptions[position]);

        if(position == 1)
        {
            Intent editProfilePage = new Intent(TutorsListActivity.this, EditProfile.class);
            startActivity(editProfilePage);
        }

        if(position == 2)
        {
            Intent discoverySettingsPage = new Intent(TutorsListActivity.this, DiscoverySettingsPage.class);
            startActivity(discoverySettingsPage);
        }

        if(position == 5)
        {
            Intent changePasswordPage = new Intent(TutorsListActivity.this, changePassword.class);
            startActivity(changePasswordPage);
        }

        if(position == 6) {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which)
                    {
                        case DialogInterface.BUTTON_POSITIVE:
                            ref1.unauth();
                            editor.clear();
                            editor.commit();
                            Intent login = new Intent(TutorsListActivity.this, Login.class);
                            startActivity(login);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("CONFIRM");
            builder.setMessage("Are you sure ?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    private void selectTitle(String navOption) {
        //to change the nave bar name
        getSupportActionBar().setTitle(navOption);
    }
}
