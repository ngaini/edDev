package edu.scu.levelup;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class StudentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        // Get a reference to our posts
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://scorching-inferno-7039.firebaseio.com");
        TextView display_val = (TextView)findViewById(R.id.display_values);
        // Attach an listener to read the data at our posts reference

        // goes till the node Srinivas
        Firebase userRef = ref.child("users").child("Srinivas");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
//                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                TextView display_val = (TextView)findViewById(R.id.display_values);
//                Map<String, String> map = snapshot.getValue(Map.class);
//                String name = map.get("interests");
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
//                    Users post = postSnapshot.getValue(Users.class);
//                    System.out.println(post.getFullName() + " - " + post.getPhoneNumber());
                    String name = postSnapshot.getValue(String.class);
                    display_val.setText(name);
                    Log.e("LOG VALUE"," interests"+name);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

}
