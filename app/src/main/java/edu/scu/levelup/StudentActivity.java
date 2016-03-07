package edu.scu.levelup;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import org.w3c.dom.Text;

import java.util.Map;

public class StudentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        // Get a reference to our posts
                Firebase.setAndroidContext(this);
//        Firebase ref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users");
////        TextView display_val = (TextView)findViewById(R.id.display_values);
//        // Attach an listener to read the data at our posts reference
//
//        // goes till the node Srinivas
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
////                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
////                TextView display_val = (TextView)findViewById(R.id.display_values);
//
////                Map<String, String> map = snapshot.getValue(Map.class);
////                String name = map.get();
//
//                //working code
////                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
//////                    Users post = postSnapshot.getValue(Users.class);
//////                    System.out.println(post.getFullName() + " - " + post.getPhoneNumber());
////                    String name = postSnapshot.getValue(String.class);
////                    display_val.setText(name);
////                    Log.e("LOG VALUE"," interests"+name);
////                }
//
//                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
////                    Users post = postSnapshot.getValue(Users.class);
////                    System.out.println(post.getFullName() + " - " + post.getPhoneNumber());
////                    String name = "name: "+post.getFullName()+" address: "+post.getAddress()+" age:"+ String.valueOf(post.getAge())+" " ;
////                    Log.e("LOG VALUE","  start ");
////                    String idVal = userRef.getKey();
////                    Log.e("LOG VALUE","  id value "+idVal);
//
////                    Map<String, String> map = snapshot.getValue(Map.class);
////                    String name = map.get("fullName");
////                    String name= null;
//////                   name = " name : "+post.getFullName()+" addr: "+post.getAddress()+" age: "+String.valueOf(post.getAge())+" description: "+post.getDescription()+" edu: "+post.getEducation()+" gender: "+post.getGender()+" interests: "+post.getInterests()+ " pwd: "+post.getPassword()+" phone: "+post.getPhoneNumber()+" zipcode: "+post.getPincode()+" role:"+post.getRole();
////                    name = postSnapshot.child('idVal').ge
////                    display_val.setText(name);
//////
////                    String name = post.getFullName();
////                    System.out.println(" "+map.get("fullName"));
////                    Log.e("LOG VALUE","  name "+map.get("fullName"));
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                System.out.println("The read failed: " + firebaseError.getMessage());
//            }
//        });
        final ListView tutorList_id = (ListView)findViewById(R.id.studentActivity_tutorList_listView);
        Firebase ref = new Firebase("https://scorching-inferno-7039.firebaseio.com/users");
        FirebaseListAdapter<Users> adapter = new FirebaseListAdapter<Users>(this, Users.class,android.R.layout.two_line_list_item, ref) {


            @Override
            protected void populateView(View view, Users user, int i) {


                ((TextView) view.findViewById(android.R.id.text1)).setText(user.getFullName());
                ((TextView) view.findViewById(android.R.id.text2)).setText(user.getInterests());


            }
        };
        //Bind the list adapter to  listView
        tutorList_id.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
