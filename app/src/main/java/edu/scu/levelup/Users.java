package edu.scu.levelup;

import android.widget.EditText;

import java.util.Date;

/**
 * Created by avidekar on 2016-02-26.
 */
public class Users {
    private int role;
    private String fullName;
    private int age;
    private int phoneNumber;
    private String password;

    public Users(int role, String fullName, int age, int phoneNumber, String password ){
        this.role = role;
        this.fullName = fullName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
    public int getRole()
    {
        return this.role;
    }

    public String getFullName()
    {
        return this.fullName;
    }

    public int getAge()
    {
        return this.age;
    }

    public int getPhoneNumber()
    {
        return this.phoneNumber;
    }


    public String getPassword()
    {
        return this.password;
    }

//    public String toString() { return "User{handle='"+handle+“', name='"+name+"', stackId="+stackId+"\’}”; }


//    @Override
//    public String toString() { return "User{fullName='"+fullName+"', name='"+phoneNumber+"', stackId='"+age+"'}"; }
}
