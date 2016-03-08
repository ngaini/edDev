package edu.scu.levelup;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import org.w3c.dom.Text;

import java.util.Map;


/**
 * This activity populates the list with tutors
 * This activity appears only for the student user
 * clicking on the list will give details of the specific tutor
 */
public class StudentsListActivity extends Activity {

    private Button logout;
    Firebase ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users");
     //   logout = (Button) findViewById(R.id.btn_logout);

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ref.unauth();
//                Intent test = new Intent(StudentsListActivity.this, Login.class);
//                startActivity(test);
//            }
//        });
//
        final ListView tutorList_id = (ListView)findViewById(R.id.studentActivity_tutorList_listView);


        FirebaseListAdapter<Users> adapter = new FirebaseListAdapter<Users>(this, Users.class,android.R.layout.two_line_list_item, ref) {


            @Override
            protected void populateView(View view, Users user, int i) {


                ((TextView) view.findViewById(android.R.id.text1)).setText(user.getFullName());
                ((TextView) view.findViewById(android.R.id.text2)).setText(user.getInterests());


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
}
