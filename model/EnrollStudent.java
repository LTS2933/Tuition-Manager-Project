package com.example.project3cs213.model;

/**
 * This class represents a student that is enrolled in the current semester. This class
 * also contains important methods such as getters and setters and comparing functions.
 * @author Christian Osma, Liam Smith
 */
public class EnrollStudent {
    private static final int DEFAULT_CREDITS = 0;

    private Profile profile;
    private int creditsEnrolled;

    /**
     * Creates new instance of EnrollStudent with default Profile and a
     * credits enrolled of zero.
     */
    public EnrollStudent(){
        this.profile = new Profile();
        this.creditsEnrolled = DEFAULT_CREDITS;
    }

    /**
     * This method takes in two parameters, the Profile and credits enrolled, and creates
     * a new instance of the class with these two parameters.
     * @param profile Profile of the student
     * @param creditsEnrolled number of credis the student is enrolled in
     */
    public EnrollStudent(Profile profile, int creditsEnrolled){
        this.profile = profile;
        this.creditsEnrolled = creditsEnrolled;
    }


    /**
     * Getter method for getting the profile of the current student
     * @return Profile of the student object
     */
    public Profile getProfile(){
        return this.profile;
    }


    /**
     * Setter method for changing the profile of the student.
     * @param profile New profile of the student.
     */
    public void setProfile(Profile profile){
        this.profile = profile;
    }


    /**
     * Getter method for returning the number of credits the student is enrolled in.
     * @return integer representing the number of credits enrolled by the student.
     */
    public int getCreditsEnrolled(){
        return this.creditsEnrolled;
    }


    /**
     * Setter method to change number of credits enrolled to the
     * inputted value.
     * @param creditsEnrolled New number of credits enrolled
     */
    public void setCreditsEnrolled(int creditsEnrolled){
        this.creditsEnrolled = creditsEnrolled;
    }


    /**
     * This method takes an EnrollStudent object and compares it to the current object.
     * Returns true if they are the same and false otherwise.
     * @param obj Object representing an EnrollStudent instance
     * @return true if the current student and passed in student are equivalent
     */
    @Override
    public boolean equals(Object obj){
        EnrollStudent compare = null;
        if (obj instanceof EnrollStudent){
            compare = (EnrollStudent) obj;
        }
        return compare.profile.equals(this.profile);
    }


    /**
     * This overriding method returns the string representation of the current
     * student.
     * @return String representation of the EnrollStudent.
     */
    @Override
    public String toString(){
        return this.profile.toString() + ": credits enrolled: " + this.creditsEnrolled;
    }

}
