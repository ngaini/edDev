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
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.HashMap;
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
//    private String uExpertiseList;
    private String userID;
    private Button logout;
//    private String uEmailID;
    private String uFullName;
//    private String uDescription;
    private String sessionUserName;
    private static int uRole;
    private static int listRole;
    private double uLat;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private double uLong;
    private static final String preferName = "AndriodSession";
    public static final String key_userid = "name";
    public static final String key_email = "email";
    private final String TUTOR_TABLE_URL = "https://scorching-inferno-7039.firebaseio.com/users/Tutor";
    private final String STUDENT_TABLE_URL = "https://scorching-inferno-7039.firebaseio.com/users/Student";
    private static Firebase ref;
    private static Firebase ref1;
    private static Firebase userRef1;
    private static Query userQueryRef;
    public static final String key_role = "userRole";
    public static final String key_userID = "userID";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar myToolbar = (Toolbar) findViewById(toolbar);
        setSupportActionBar(myToolbar);
        Firebase.setAndroidContext(this);
        sessionUserName ="t@t.com";
//        pref = getApplicationContext().getSharedPreferences(preferName, 0);
//        editor = pref.edit();
//        sessionUserName = pref.getString(key_email, null);
//        uRole = Integer.parseInt(pref.getString(key_role, null));
//        userID = pref.getString(key_userID,null);

//        Toast.makeText(StudentsListActivity.this, "Session user name is - " +sessionUserName, Toast.LENGTH_SHORT).show();


        //for fetching the global variables
        MyApplication app =(MyApplication)getApplication();


//        Toast.makeText(StudentsListActivity.this,"email- "+sessionUserName, Toast.LENGTH_SHORT).show();


        //get the loggein user details

//

        userRef1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student/");
        userQueryRef = userRef1.orderByChild("emailID").equalTo(sessionUserName);
        userQueryRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            HashMap<String, String> hmap=new HashMap<String, String>();
//            UserSessionManager session;

            String appUserName, appUserID;
            int appUserRole;
            double appUserLat,appUserLng;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(getApplicationContext(), "INSIDE MAIN", Toast.LENGTH_SHORT).show();
                if (dataSnapshot.getChildrenCount() != 0) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Users userData = postSnapshot.getValue(Users.class);
                        Toast.makeText(getApplicationContext(), "INSIDE", Toast.LENGTH_SHORT).show();

                        appUserName = userData.getFullName();
                        appUserRole = userData.getRole();
                        appUserID= userData.getUserID();
                        appUserLat =userData.getLat();
                        appUserLng=userData.getLng();
                        processDataForLoggedInUser(appUserID,appUserName,appUserRole,appUserLat,appUserLng);
                        Log.e("LOGVAL STUDENT", "ello::" + appUserName + "::" + appUserRole + "::" + appUserID + "::" + appUserLat);
//

//                        uRole = userData.getRole();
//                        userID = userData.getUserID();
//                        hmap = new HashMap<String, String>();
//                        hmap.put("role", String.valueOf(userData.getRole()));
//                        hmap.put("userID", userData.getUserID());
//                        hmap.put("lat", String.valueOf(userData.getLat()));
//                        hmap.put("lng", String.valueOf(userData.getLng()));
//                        session.createUserLoginSession(userData.getFullName(), sessionUserName, userData.getRole(), userData.getUserID());


//                        Log.e("LOGVAL STUDENT", "ello::" + userData.getRole() + "::" + userData.getUserID() + "::" + userData.getLng()+"::"+userData.getFullName());
////                        session.createUserLoginSession("session stored", sessionUserName, uRole, userID);
////                                            Toast.makeText(getApplicationContext(), "user email ID is - "+pref.getString(key_email, null), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(), "user role is - "+uRole, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(), "user ID is - "+userID, Toast.LENGTH_SHORT).show();
//                        Intent mainPage = new Intent(StudentsListActivity.this, StudentsListActivity.class);
//                        startActivity(mainPage);


                    }
                }else
                {
                    userRef1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
                    userQueryRef = userRef1.orderByChild("emailID").equalTo(sessionUserName);
                    userQueryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Users userData = postSnapshot.getValue(Users.class);

                                appUserName = userData.getFullName();
                                appUserRole = userData.getRole();
                                appUserID= userData.getUserID();
                                appUserLat =userData.getLat();
                                appUserLng=userData.getLng();
                                processDataForLoggedInUser(appUserID,appUserName,appUserRole,appUserLat,appUserLng);
                                Log.e("LOGVAL STUDENT", "ello::" + appUserName + "::" + appUserRole + "::" + appUserID + "::" + appUserLat);
//

//                                uRole = userData.getRole();
//                                userID = userData.getUserID();
//                                hmap = new HashMap<String, String>();
//                                hmap.put("role",String.valueOf(userData.getRole()));
//                                hmap.put("userID",userData.getUserID());
//                                hmap.put("lat",String.valueOf(userData.getLat()));
//                                hmap.put("lng",String.valueOf(userData.getLng()));
//                                session.createUserLoginSession(userData.getFullName(), sessionUserName, userData.getRole(), userData.getUserID());
//                                session.createUserLoginSession("session stored", uname, uRole, userID);
                                //Toast.makeText(getApplicationContext(), "user email ID is - "+pref.getString(key_email, null), Toast.LENGTH_SHORT).show();
//                                Log.e("LOGVAL STUDENT", "ello::" + userData.getRole() + "::" + userData.getUserID() + "::" + userData.getLng());
//                                Toast.makeText(getApplicationContext(), "user role is - "+uRole, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(), "user ID is - "+userID, Toast.LENGTH_SHORT).show();
////                                Intent mainPage = new Intent(Login.this, StudentsListActivity.class);
////                                startActivity(mainPage);
//                                Log.e("LOGVAL TUTOR", "ello::" +uRole+"::"+userID +"::" +sessionUserName);

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


















        // then qery in the tutor list

//        uRole = Integer.parseInt(hmap.get("role"));
//        userID=hmap.get("userID");
//        uLat = Double.parseDouble(hmap.get("lat"));
//        uLong = Double.parseDouble(hmap.get("lng"));
//        Log.e("CHECK THIS LOG OUT ",uFullName+":: "+userID+":: "+uRole+sessionUserName);
//        Query queryRef = ref.orderByChild("interests").equalTo(uExpertiseList);
//        Query queryRef = ref.orderByChild("interests").equalTo(uExpertiseList);
//        Toast.makeText(StudentsListActivity.this,"full name: "+uFullName, Toast.LENGTH_SHORT).show();



//        uLat = Double.parseDouble(hashMap.get("lat"));
//        uLong = Double.parseDouble(hashMap.get("lng"));

        Toast.makeText(StudentsListActivity.this,"role: "+uRole, Toast.LENGTH_SHORT).show();

        //setting up for the drawer
//        Log.e("LOGVAL", "::" +uRole+"::"+userID +"::" +sessionUserName);
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

////      for the tutors list
//        final ListView tutorList_id = (ListView)findViewById(R.id.studentActivity_tutorList_listView);
//
//
//        // to find distance between two lat longs
//        // users lat long
//        final Location locationA = new Location("point A");
//        final Location locationB = new Location("point B");
//
////        locationA.setLatitude(uLat);
////        locationA.setLongitude(uLong);
//
//        // 37.352804, -121.963429
//        locationA.setLatitude( 37.352804);
//        locationA.setLongitude(-121.963429);
//        listRole=1000;
//
//        // if the logged in user is a student show a the list of tutors
//        if (uRole ==1)
//        {
//             ref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
//            ref1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student");
//
//            // if it is a student List role should be 0 as we will be sing list of tutors
//            listRole=0;
//        }
//        // if he is a tutor show list of students
//        else if (uRole==0)
//        {
//            ref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student");
//            ref1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
//            // if it is a tutor List role should be 1 as we will be sing list of students
//            listRole=1;
//        }
//
//        Query queryRef1 = ref.orderByChild("pincode").equalTo("95050");
//        FirebaseListAdapter<Users> adapter = new FirebaseListAdapter<Users>(this, Users.class,android.R.layout.two_line_list_item, ref)
//        {
//            @Override
//            protected void populateView(View view, Users user, int i)
//            {
//               TextView text1_id =(TextView) view.findViewById(android.R.id.text1);
//               TextView text2_id =(TextView) view.findViewById(android.R.id.text2);
//                text1_id.setPaddingRelative(30,5,10,5);
//                text2_id.setPaddingRelative(30, 5, 10, 20);
//                String dFname= user.getFullName();
//                double dLat = user.getLat();
//                double dLng = user.getLng();
//                locationB.setLatitude(dLat);
//                locationB.setLongitude(dLng);
//                float distanceInMeters =0;
//                 distanceInMeters = locationA.distanceTo(locationB);
//                Log.e("TESTING", " dist :"+distanceInMeters);
//
//                text1_id.setTextAppearance(view.getContext(), android.R.style.TextAppearance_Large);
////                if(distanceInMeters<10)
////                {
//                    DecimalFormat df = new DecimalFormat("####0.0");
//
//                    text1_id.setText(dFname);
//                    text2_id.setText(user.getInterests()+"  ("+df.format(getMiles(distanceInMeters))+ " mi. )");
////                }
//            }
//        };
//        //Bind the list adapter to  listView
//        tutorList_id.setAdapter(adapter);
//
//        // item click action
//        tutorList_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                String name = ((TextView)view.findViewById(android.R.id.text1)).getText().toString();
//                String interest = ((TextView)view.findViewById(android.R.id.text2)).getText().toString();
//                Log.e("TESTING", " name "+name+" is interested in "+interest);
//                Toast.makeText(StudentsListActivity.this," name "+name+" is interested in "+interest, Toast.LENGTH_SHORT).show();
//
//                Intent tutorDetailIntent = new Intent(StudentsListActivity.this, TutorDetailActivity.class);
//                // creating bundle
//                Bundle extra = new Bundle();
//                extra.putString("name", name);
//                extra.putInt("listRole",listRole);
//                tutorDetailIntent.putExtras(extra);
//                startActivity(tutorDetailIntent);
//            }
//        });


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


    public void getUserHashValues(final String lName) throws InterruptedException {

        userRef1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student/");
        userQueryRef = userRef1.orderByChild("emailID").equalTo(sessionUserName);
        userQueryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            HashMap<String, String> hmap=new HashMap<String, String>();
            UserSessionManager session;
            String appUserName, appUserID;
            int appUserRole;
            double appUserLat,appUserLng;


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(getApplicationContext(), "INSIDE MAIN", Toast.LENGTH_SHORT).show();
                if (dataSnapshot.getChildrenCount() != 0) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Users userData = postSnapshot.getValue(Users.class);
                        Toast.makeText(getApplicationContext(), "INSIDE", Toast.LENGTH_SHORT).show();



//                        uRole = userData.getRole();
//                        userID = userData.getUserID();
//                        hmap = new HashMap<String, String>();
//                        hmap.put("role", String.valueOf(userData.getRole()));
//                        hmap.put("userID", userData.getUserID());
//                        hmap.put("lat", String.valueOf(userData.getLat()));
//                        hmap.put("lng", String.valueOf(userData.getLng()));
//                        session.createUserLoginSession(userData.getFullName(), lName, userData.getRole(), userData.getUserID());
                        appUserName = userData.getFullName();
                        appUserRole = userData.getRole();
                        appUserID= userData.getUserID();
                        appUserLat =userData.getLat();
                        appUserLng=userData.getLng();
                        processDataForLoggedInUser(appUserID,appUserName,appUserRole,appUserLat,appUserLng);

                        Log.e("LOGVAL STUDENT", "ello::" + appUserName + "::" + appUserRole + "::" + appUserID+"::"+appUserLat);
//                        session.createUserLoginSession("session stored", sessionUserName, uRole, userID);
//                                            Toast.makeText(getApplicationContext(), "user email ID is - "+pref.getString(key_email, null), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(), "user role is - "+uRole, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(), "user ID is - "+userID, Toast.LENGTH_SHORT).show();
//                        Intent mainPage = new Intent(StudentsListActivity.this, StudentsListActivity.class);
//                        startActivity(mainPage);


                    }
                }else
                {
                    userRef1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
                    userQueryRef = userRef1.orderByChild("emailID").equalTo(sessionUserName);
                    userQueryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Users userData = postSnapshot.getValue(Users.class);

                                appUserName = userData.getFullName();
                                appUserRole = userData.getRole();
                                appUserID= userData.getUserID();
                                appUserLat =userData.getLat();
                                appUserLng=userData.getLng();
                                processDataForLoggedInUser(appUserID,appUserName,appUserRole,appUserLat,appUserLng);
                                Log.e("LOGVAL STUDENT", "ello::" + appUserName + "::" + appUserRole + "::" + appUserID + "::" + appUserLat);
//                                uRole = userData.getRole();
//                                userID = userData.getUserID();
//                                hmap = new HashMap<String, String>();
//                                hmap.put("role",String.valueOf(userData.getRole()));
//                                hmap.put("userID",userData.getUserID());
//                                hmap.put("lat",String.valueOf(userData.getLat()));
//                                hmap.put("lng",String.valueOf(userData.getLng()));
//                                session.createUserLoginSession(userData.getFullName(), lName, userData.getRole(), userData.getUserID());
//                                session.createUserLoginSession("session stored", uname, uRole, userID);
                                //Toast.makeText(getApplicationContext(), "user email ID is - "+pref.getString(key_email, null), Toast.LENGTH_SHORT).show();
//                                Log.e("LOGVAL STUDENT", "ello::" + userData.getRole() + "::" + userData.getUserID() + "::" + userData.getLng());
//                                Toast.makeText(getApplicationContext(), "user role is - "+uRole, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getApplicationContext(), "user ID is - "+userID, Toast.LENGTH_SHORT).show();
//                                Intent mainPage = new Intent(Login.this, StudentsListActivity.class);
//                                startActivity(mainPage);
//                                Log.e("LOGVAL TUTOR", "ello::" +uRole+"::"+userID +"::" +sessionUserName);

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
//            userQueryRef.wait(5000);
//        uRole = Integer.parseInt(hmap.get("role"));
//        userID=hmap.get("userID");
//        uLat = Double.parseDouble(hmap.get("lat"));
//        uLong = Double.parseDouble(hmap.get("lng"));
//        Log.e("!@# HERE WE ARE hmap", "ello::"+uRole+"::"+userID+"::"+uLat);


    }


    public void processDataForLoggedInUser(String clientId, String clientName, int clientRole, double clientLat, double clientLng)
    {

//        for the tutors list
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

        // if the logged in user is a student show a the list of tutors
        if (clientRole ==1)
        {
            ref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
            ref1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student");

            // if it is a student List role should be 0 as we will be sing list of tutors
            listRole=0;
        }
        // if he is a tutor show list of students
        else if (clientRole==0)
        {
            ref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Student");
            ref1 = new Firebase("https://scorching-inferno-7039.firebaseio.com/users/Tutor");
            // if it is a tutor List role should be 1 as we will be sing list of students
            listRole=1;
        }

        Query queryRef1 = ref.orderByChild("pincode").equalTo("95050");
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

}
