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


    public Users(int role, String fullName, int age, int phoneNumber, String password, String education, String description, String gender, String interests, String address, int pincode ){
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
<<<<<<< HEAD

//    public String toString() { return "User{handle='"+handle+“', name='"+name+"', stackId="+stackId+"\’}”; }


//    @Override
//    public String toString() { return "User{fullName='"+fullName+"', name='"+phoneNumber+"', stackId='"+age+"'}"; }
||||||| merged common ancestors
=======

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
>>>>>>> f8aedd4078ab0ffd63708d3ab9c045965d001b15
}
