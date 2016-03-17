package edu.scu.levelup;

/**
 * Created by avidekar on 2016-03-04.
 */

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

public class UserSessionManager {
    SharedPreferences pref;
    Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String preferName = "AndriodSession";
    private static final String isUserLogin = "isUserLogin";
    public static final String key_name = "name";
    public static final String key_email = "email";
//    public static final String key_lat = "lattitude";
//    public static final String key_lng = "longitude";
//    public static final String key_role = "userRole";
//    public static final String key_userID = "userID";

    public UserSessionManager(Context context)
    {
        this.context = context;
        pref = context.getSharedPreferences(preferName, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String name, String email)
    {
//        editor.putBoolean(isUserLogin, true);
        editor.putString(key_name, name);
<<<<<<< HEAD
        editor.putString(key_email, email);
//        editor.putInt(key_role, userRole);
//        editor.putString(key_userID, userID);
=======
        editor.putString(key_email, email );
>>>>>>> fb22bca39b0b41b93d2a9c20b0d72b1846c9af46
        editor.commit();
    }

    public boolean checkLogin()
    {
        if(!this.isUserLoggedIn())
        {
            return true;
        }
        else {
            return false;
        }
    }

    public HashMap<String, String> getUserDetails()
    {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(key_name, pref.getString(key_name, null));
        user.put(key_email, pref.getString(key_email, null));
<<<<<<< HEAD
//        user.put(key_role, pref.getString(key_role, null));
//        user.put(key_userID, pref.getString(key_userID, null));
=======
>>>>>>> fb22bca39b0b41b93d2a9c20b0d72b1846c9af46
        return user;
    }

    public void logout()
    {
        editor.clear();
        editor.commit();
    }

    public boolean isUserLoggedIn(){
        return pref.getBoolean(isUserLogin, false);
    }
}
