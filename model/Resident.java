package com.example.project3cs213.model;

/**
 * This class, Resident, extends Student as a subclass. Contains methods for
 * checking what tuition should be due for the Student depending on credits enrolled
 * and the toString() method.
 * @author Christian Osma, Liam Smith
 */
public class Resident extends Student {
    private static final int TUITION_RESIDENT = 15804;
    private static final int FULL_CREDITS = 16;
    private static final int MIN_CREDITS = 12;
    private static final int PART_TIME_RES_RATE = 404;
    private static final double UNIV_FEE_PART_TIME = 2614.4;

    private int scholarship;

    /**
     * Default constructor that instantiates a new Resident object by calling the
     * Student default constructor
     */
    public Resident(){
        super();
    }

    /**
     * Overloaded constructor that instantiates a new Resident object by calling the
     * Student default constructor and taking 1 parameter to complete instantiation
     * @param scholarship The integer representation of the scholarship to use as an instance variable for the object
     */
    public Resident(int scholarship){
        super();
        this.scholarship = scholarship;
    }


    /**
     * Overloaded constructor that instantiates a new NonResident object by calling the
     * Student constructor and taking 4 parameters to do so
     * @param scholarship scholarship amount for the resident student
     * @param profile The Profile object to use as an instance variable for the object
     * @param major The Major enum to use as an instance variable for the object
     * @param creditsCompleted the number of credits completed as an integer to use as an instance variable for the object
     */
    public Resident(int scholarship, Profile profile, Major major, int creditsCompleted){
        super(profile, major , creditsCompleted);
        this.scholarship = scholarship;
    }

    /**
     * Overloaded constructor that instantiates a new NonResident object by calling the
     * Student constructor and taking 3 parameters to do so
     * @param profile The Profile object to use as an instance variable for the object
     * @param major The Major enum to use as an instance variable for the object
     * @param creditsCompleted the number of credits completed as an integer to use as an instance variable for the object
     */
    public Resident(Profile profile, Major major, int creditsCompleted){
        super(profile, major , creditsCompleted);
        this.scholarship = 0;
    }

    /**
     * Getter method for getting the resident student's scholarship amount
     * @return the scholarship amount as an integer
     */
    public int getScholarship(){
        return this.scholarship;
    }

    /**
     * Setter method for changing the resident student's scholarship amount
     * to the passed in value.
     * @param scholarship New amount for the student's scholarship
     */
    public void setScholarship(int scholarship){
        this.scholarship = scholarship;
    }

    /**
     * Calculates what tuition will be due in USD for a Resident Student depending on their credits enrolled
     * @param creditsEnrolled number of credits currently enrolled as an integer
     * @return double value of tuition due for the Student in USD
     */
    @Override
    public double tuitionDue(int creditsEnrolled) {
        if (creditsEnrolled < MIN_CREDITS) return (PART_TIME_RES_RATE * creditsEnrolled) + UNIV_FEE_PART_TIME;
        if (creditsEnrolled > FULL_CREDITS) return (PART_TIME_RES_RATE * (creditsEnrolled - FULL_CREDITS)) + TUITION_RESIDENT - scholarship;
        return TUITION_RESIDENT - scholarship;
    }

    /**
     * Returns whether the Student is a resident
     * @return true since we are in the Resident class
     */
    @Override
    public boolean isResident(){
        return true;
    }

    /**
     * Converts the Resident Student to a String format
     * @return String representation of the Resident Student
     */
    @Override
    public String toString(){
        return super.toString() + "(resident)";
    }
}
