package com.example.project3cs213.model;
/**
 * This class implements the Comparable Interface and runs various methods on Profile objects.
 * Profiles need to be compared against each other in order to be sorted.
 * Attributes of a profile include first name, last name, and DOB.
 *
 * @author Christian Osma, Liam Smith
 **/
public class Profile implements Comparable<Profile>{
    private String lname;
    private String fname;
    private Date dob;

    private static final int COMPARE_TO_LESS_THAN = -1;
    private static final int COMPARE_TO_GREATER_THAN = 1;
    private static final int COMPARE_TO_EQUAL = 0;


    /**
     Default constructor. Instantiates a new Profile object with fname = "Jane", lname = "Doe" and a new Date object
     that corresponds to today's date.
     */
    public Profile(){
        fname = "Jane";
        lname = "Doe";
        dob = new Date();
    }

    /**
     Overloaded constructor. Takes 3 arguments and uses them to populate the current Profile object.
     @param fname - populates the first name of the object
     @param lname - populates the last name of the object
     @param dob - Date object that serves as the date of birth for the current Profile
     */
    public Profile(String fname, String lname, Date dob){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     Copy constructor. Copies the information of the argument and stores its variables within
     the corresponding instance variables of the current Profile object.
     @param pr : Profile which contains the information we wish to copy to the current Profile object
     */
    public Profile(Profile pr){
        this.fname = pr.fname;
        this.lname = pr.lname;
        this.dob = pr.dob;
    }

    /**
     Converts the current Profile object to a String.
     @return String which corresponds to a worded version of the Profile, such as "John Doe 3/20/2003"
     */
    @Override
    public String toString(){
        String str = fname + " " + lname + " " + dob.toString();
        return str;
    }

    /**
     Check if a Profile is equal to another Profile object.
     @param obj - Object which must be an instance of a Profile object to be compared against current Profile object
     @return true if the 2 Profiles are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        Profile compare = null;
        if (obj instanceof Profile){
            compare = (Profile) obj;
        }
        boolean firstNameEquals = this.fname.toLowerCase().equals(compare.fname.toLowerCase());
        boolean lastNameEquals = this.lname.toLowerCase().equals(compare.lname.toLowerCase());
        boolean dateEquals = this.dob.equals(compare.dob);

        return (firstNameEquals & lastNameEquals & dateEquals);
    }

    /**
     Helps to sort Profiles based on last name, first name, and date of birth.
     @param pr Profile which will be used to compare against current Profile object.
     @return -1 if the current Profile should take priority over Profile pr, 0 if they are equal, and 1 if pr
     should take priority in the sorting order.
     */
    @Override
    public int compareTo(Profile pr) {
        int lastNameCompare = this.lname.compareTo(pr.lname);
        if (lastNameCompare < COMPARE_TO_EQUAL) return COMPARE_TO_LESS_THAN;

        int firstNameCompare = this.fname.compareTo(pr.fname);
        if (lastNameCompare == COMPARE_TO_EQUAL && firstNameCompare < COMPARE_TO_EQUAL ) return COMPARE_TO_LESS_THAN;

        int dateCompare = this.dob.compareTo(pr.dob);
        if (lastNameCompare == COMPARE_TO_EQUAL && firstNameCompare == COMPARE_TO_EQUAL && dateCompare < COMPARE_TO_EQUAL) return COMPARE_TO_LESS_THAN;

        if (lastNameCompare + firstNameCompare + dateCompare == COMPARE_TO_EQUAL) return COMPARE_TO_EQUAL;

        return COMPARE_TO_GREATER_THAN;
    }

}
