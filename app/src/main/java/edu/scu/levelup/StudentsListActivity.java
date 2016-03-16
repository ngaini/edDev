package edu.scu.levelup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.batch.android.Batch;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Map;

import static edu.scu.levelup.R.id.toolbar;


/**
 * This activity populates the list with tutors
 * This activity appears only for the student user
 * clicking on the list will give details of the specific tutor
 *
 *
 * for google maps
 * API key = AIzaSyBU_2MPKbm5qSHTrQBlz7Fl-ci3TddSH6g
 *
 *
 * for GCM
 * API KEY: AIzaSyDYtLy2W-aF93cRlFo9efq1utGDfx24Ep4
 * SenderID : 175791489113
 *
 *
 * Batch API key DEV56E7BC3BA4A1F6DF11D0CAB6148
 *
 */
public class StudentsListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ActionBarDrawerToggle drawerListner;
    private CustomAdapter myCustomAdapter;
    private String[] navOptions;
    private String uExpertiseList, userID;
    private Button logout;
    private String uEmailID;
    private String uFullName;
    private String sessionUserName;
    private int uRole;
    private static int listRole;
    private double uLat;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private double uLong;
    private static final String preferName = "AndriodSession";
    public static final String key_userid = "name";
    public static final String key_email = "email";

    private static Firebase ref;
    private static Firebase ref1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar myToolbar = (Toolbar) findViewById(toolbar);
        setSupportActionBar(myToolbar);
        Firebase.setAndroidContext(this);

        pref = getApplicationContext().getSharedPreferences(preferName, 0);
        editor = pref.edit();
        sessionUserName = pref.getString(key_email, null);
<<<<<<< HEAD
//        Toast.makeText(StudentsListActivity.this, "Session user name is - " +sessionUserName, Toast.LENGTH_SHORT).show();
=======
        Toast.makeText(StudentsListActivity.this, "Session user name is - " +sessionUserName, Toast.LENGTH_SHORT).show();


        //for fetching the global variables
        MyApplication app =(MyApplication)getApplication();

//        uRole = 999 ;
//

        /**
         * commenting for test purposes
         *
         * REMEMBER to uncomment
         */

//        Bundle extras = getIntent().getExtras();
//        uExpertiseList = extras.getString("uExpertiseList");
//        uEmailID = extras.getString("uemailID");
//        uFullName = extras.getString("uFullName");
//        userID = extras.getString("userID");
//        uRole = extras.getInt("uRole");
//        uLat =extras.getDouble("lat");
//        uLong =extras.getDouble("long");

//          uRole =0; //tutor
          uRole =1; //student

//        Toast.makeText(StudentsListActivity.this,"user Expertise is - "+uExpertiseList, Toast.LENGTH_SHORT).show();
//        Toast.makeText(StudentsListActivity.this,"user email id is - "+uEmailID, Toast.LENGTH_SHORT).show();
//        Toast.makeText(StudentsListActivity.this,"user full name is - "+uFullName, Toast.LENGTH_SHORT).show();
//        Toast.makeText(StudentsListActivity.this,"user ID is - "+userID, Toast.LENGTH_SHORT).show();
        Toast.makeText(StudentsListActivity.this,"user role is - "+uRole, Toast.LENGTH_SHORT).show();
        Toast.makeText(StudentsListActivity.this,"user LatLong is - "+uLat+" "+uLong, Toast.LENGTH_SHORT).show();


//        Query queryRef = ref.orderByChild("interests").equalTo(uExpertiseList);

>>>>>>> 48e288b3b30aba7d4150ee15b1183a3c7713b915
        //setting up for the drawer
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        ListView list = (ListView)findViewById(R.id.drawerList);
        myCustomAdapter = new CustomAdapter(this);
        list.setAdapter(myCustomAdapter);
        navOptions =getResources().getStringArray(R.array.navOptions);

        //setting item lister for nav drawer item click
        list.setOnItemClickListener(this);

        drawerListner = new ActionBarDrawerToggle(this,drawerLayout,myToolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
            }
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            }
        };
        drawerLayout.setDrawerListener(drawerListner);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//      for the tutors list
        final ListView tutorList_id = (ListView)findViewById(R.id.studentActivity_tutorList_listView);


        // to find distance between two lat longs
        // users lat long
        final Location locationA = new Location("point A");
        final Location locationB = new Location("point B");

        locationA.setLatitude(uLat);
        locationA.setLongitude(uLong);

        // 37.352804, -121.963429
//        locationA.setLatitude( 37.352804);
//        locationA.setLongitude(-121.963429);
        listRole=1000;
        if (uRole ==1)
        {
             ref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
            ref1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student");
            listRole=0;
        }
        else
        {
            ref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student");
            ref1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
            listRole=1;
        }
//        Query queryRef1 = ref.orderByChild("pincode").equalTo("95050");
        FirebaseListAdapter<Users> adapter = new FirebaseListAdapter<Users>(this, Users.class,android.R.layout.two_line_list_item, ref)
        {
            @Override
            protected void populateView(View view, Users user, int i)
            {
               TextView text1_id =(TextView) view.findViewById(android.R.id.text1);
               TextView text2_id =(TextView) view.findViewById(android.R.id.text2);
                text1_id.setPaddingRelative(30,5,10,5);
                text2_id.setPaddingRelative(30, 5, 10, 20);
                String dFname= user.getFullName();
                double dLat = user.getLat();
                double dLng = user.getLng();
                locationB.setLatitude(dLat);
                locationB.setLongitude(dLng);
                float distanceInMeters =0;
                 distanceInMeters = locationA.distanceTo(locationB);
                Log.e("TESTING", " dist :"+distanceInMeters);

                text1_id.setTextAppearance(view.getContext(), android.R.style.TextAppearance_Large);
//                if(distanceInMeters<10)
//                {
                    DecimalFormat df = new DecimalFormat("####0.0");

                    text1_id.setText(dFname);
                    text2_id.setText(user.getInterests()+"  ("+df.format(getMiles(distanceInMeters))+ " mi. )");
//                }
            }
        };
        //Bind the list adapter to  listView
        tutorList_id.setAdapter(adapter);

        // item click action
        tutorList_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = ((TextView)view.findViewById(android.R.id.text1)).getText().toString();
                String interest = ((TextView)view.findViewById(android.R.id.text2)).getText().toString();
                Log.e("TESTING", " name "+name+" is interested in "+interest);
                Toast.makeText(StudentsListActivity.this," name "+name+" is interested in "+interest, Toast.LENGTH_SHORT).show();

                Intent tutorDetailIntent = new Intent(StudentsListActivity.this, TutorDetailActivity.class);
                // creating bundle
                Bundle extra = new Bundle();
                extra.putString("name", name);
                extra.putInt("listRole",listRole);
                tutorDetailIntent.putExtras(extra);
                startActivity(tutorDetailIntent);
            }
        });


    }

    @Override
    protected void onStart()
    {
        super.onStart();

        Batch.onStart(this);
//
    }

    @Override
    protected void onStop()
    {
        Batch.onStop(this);

        super.onStop();
    }




    //Methods for drawer
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
            Intent editProfilePage = new Intent(StudentsListActivity.this, EditProfile.class);
            startActivity(editProfilePage);
        }

        if(position == 2)
        {
            Intent discoverySettingsPage = new Intent(StudentsListActivity.this, DiscoverySettingsPage.class);
            startActivity(discoverySettingsPage);
        }

        if(position == 5)
        {
            Intent changePasswordPage = new Intent(StudentsListActivity.this, changePassword.class);
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
                            Intent login = new Intent(StudentsListActivity.this, Login.class);
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


    private double getMiles(float distanceVal)
    {
        return distanceVal/1609.344;
    }
}
