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
    private long phoneNumber;
    private String password;
    private String education;
    private String description;
    private String gender;
    private String interests;
    private String address;
    private int pincode;
    private String userID;


    public Users(String userID, int role, String fullName, int age, int phoneNumber, String password, String education, String description, String gender, String interests, String address, int pincode ){
        this.userID = userID;
        this.role = role;
        this.fullName = fullName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.education = education;
        this.description = description;
        this.gender = gender;
        this.interests = interests;
        this.address = address;
        this.pincode = pincode;
    }


    // constructor without parameters essential for firebase to work
    public Users() {
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

    public long getPhoneNumber()
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


    public String getEducation()
    {
        return this.education;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getGender()
    {
        return this.gender;
    }

    public String getInterests()
    {
        return this.interests;
    }

    public String getAddress(){
        return this.address;
    }

    public int getPincode()
    {
        return this.pincode;
    }


    public String getUserID()
    {
        return this.userID;
    }

}
