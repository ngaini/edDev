package edu.scu.levelup;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import org.w3c.dom.Text;

import java.util.Map;

public class TutorDetailActivity extends Activity {

    private String FIREBASE_URL = "https://scorching-inferno-7039.firebaseio.com";
    private String TUTOR_TABLE_URL = "https://scorching-inferno-7039.firebaseio.com/users/Tutor";
    private String STUDENT_TABLE_URL = "https://scorching-inferno-7039.firebaseio.com/users/Student";
    private Firebase ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_detail);

        Bundle extra = getIntent().getExtras();
        final String name = extra.getString("name");
        final int role = extra.getInt("listRole");
        final TextView tutorName_id = (TextView)this.findViewById(R.id.TDA_tutorName_textView);
        final TextView tutorExpertize_id = (TextView)this.findViewById(R.id.TDA_tutorExpertise_textView);
        final TextView tutorAge_id = (TextView)this.findViewById(R.id.TDA_tutorAge_textView);
        final TextView tutorDescription_id = (TextView)this.findViewById(R.id.TDA_tutorDescription_textView);
        final TextView tutorGender_id = (TextView)this.findViewById(R.id.TDA_tutorGender_textView);
        final TextView tutorEducation_id = (TextView)this.findViewById(R.id.TDA_tutorEducation_textView);
        //Setting name to textView
//        ((TextView)this.findViewById(R.id.TDA_tutorName_textView)).setText(name);
//        tutorName_id.setText(name);

        if(!name.isEmpty())
        {
            if(role == 0)
            {
                ref = new Firebase(TUTOR_TABLE_URL);
            }
            else
            {
                ref= new Firebase(STUDENT_TABLE_URL);
            }
            
            Query queryRef = ref.orderByChild("fullName").equalTo(name);
            queryRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Users user = dataSnapshot.getValue(Users.class);
                    tutorName_id.setText(name);
                    tutorExpertize_id.setText("Gender: "+user.getInterests());
                    tutorAge_id.setText(user.getAge()+" yrs");
                    tutorEducation_id.setText("Highest Education:\n"+user.getEducation());
                    tutorGender_id.setText("Gender: "+user.getGender());
                    tutorDescription_id.setText("Description:\n"+user.getDescription());
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }

        //incase the tutor happens to delete his account
        else if(name.isEmpty())
        {
            Intent tutorUnAvailableIntent = new Intent(TutorDetailActivity.this, StudentsListActivity.class);
            startActivity(tutorUnAvailableIntent);
        }

    }

}
