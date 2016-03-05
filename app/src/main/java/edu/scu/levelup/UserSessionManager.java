package edu.scu.levelup;

/**
 * Created by avidekar on 2016-03-04.
 */

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserSessionManager {
    SharedPreferences pref;
    Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String preferName = "AndriodSession";
    private static final String isUserLogin = "isUserLogin";
    public static final String username = "username";
    public static final String password = "password";

    public UserSessionManager(Context context)
    {
        this.context = context;
        pref = context.getSharedPreferences(preferName, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String uName, String uPassword)
    {
        editor.putBoolean(isUserLogin, true);
        editor.putString(username, uName );
        editor.putString(password, uPassword);
        editor.commit();
    }

    public boolean checkLogin()
    {
        if(!this.isUserLoggedIn())
        {
            Intent i = new Intent(context, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }
        return false;
    }

    public HashMap<String, String> getUserDetails()
    {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(username, pref.getString(username, null));
        user.put(password, pref.getString(password, null));
        return user;
    }

    public void logout()
    {
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public boolean isUserLoggedIn(){
        return pref.getBoolean(isUserLogin, false);
    }
}
