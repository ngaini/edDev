package edu.scu.levelup;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import org.w3c.dom.Text;

import java.util.Map;

import static edu.scu.levelup.R.id.toolbar;


/**
 * This activity populates the list with tutors
 * This activity appears only for the student user
 * clicking on the list will give details of the specific tutor
 */
public class StudentsListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ActionBarDrawerToggle drawerListner;
    private CustomAdapter myCustomAdapter;
    private String[] navOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar myToolbar = (Toolbar) findViewById(toolbar);
        setSupportActionBar(myToolbar);
        Firebase.setAndroidContext(this);

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

//      for the tutors list
        final ListView tutorList_id = (ListView)findViewById(R.id.studentActivity_tutorList_listView);
        Firebase ref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users");
        Query queryRef = ref.orderByChild("pincode").equalTo(95050);
        FirebaseListAdapter<Users> adapter = new FirebaseListAdapter<Users>(this, Users.class,android.R.layout.two_line_list_item, queryRef) {


            @Override
            protected void populateView(View view, Users user, int i) {

               TextView text1_id =(TextView) view.findViewById(android.R.id.text1);
               TextView text2_id =(TextView) view.findViewById(android.R.id.text2);
                text1_id.setPaddingRelative(30,5,10,5);
                text2_id.setPaddingRelative(30,5,10,20);

                text1_id.setTextAppearance(view.getContext(), android.R.style.TextAppearance_Large);


                   text1_id.setText(user.getFullName());
                   text2_id.setText(user.getInterests());



            }
        };
        //Bind the list adapter to  listView
        tutorList_id.setAdapter(adapter);


        // item click action
        tutorList_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//     String Name = ((TextView) view.findViewById(android.R.id.text1)).getText()
                String name = ((TextView)view.findViewById(android.R.id.text1)).getText().toString();
                String interest = ((TextView)view.findViewById(android.R.id.text2)).getText().toString();

                Log.e("TESTING", " name "+name+" is interested in "+interest);
                Toast.makeText(StudentsListActivity.this," name "+name+" is interested in "+interest, Toast.LENGTH_SHORT).show();

                Intent tutorDetailIntent = new Intent(StudentsListActivity.this, TutorDetailActivity.class);
                // creating bundle
                Bundle extra = new Bundle();
                extra.putString("name",name);
                extra.putString("interest",interest);
                tutorDetailIntent.putExtras(extra);
                startActivity(tutorDetailIntent);


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

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
//        Toast.makeText(DrawerTestActivity.this, navOptions[position], Toast.LENGTH_SHORT).show();
        selectTitle(navOptions[position]);
    }

    private void selectTitle(String navOption) {

        //to change the nave bar name
        getSupportActionBar().setTitle(navOption);
    }
}
