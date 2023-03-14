package com.example.project3cs213.model;

/**
 * This class, International, extends NonResident as a subclass. Contains methods for
 * checking what tuition should be due for the Student depending on credits enrolled
 * and the toString() method.
 * @author Christian Osma, Liam Smith
 */
public class International extends NonResident{
    private static final int HEALTH_INSURANCE_FEE = 2650;
    private static final int TUITION = 5918;
    private static final boolean CREDITS_VALID = true;
    private static final boolean CREDITS_NOT_VALID = false;
    private static final int MIN_FULLTIME_CREDITS = 12;
    private static final boolean IS_STUDY_ABROAD = true;
    private static final boolean NOT_STUDY_ABROAD = false;

    private boolean isStudyAbroad;

    /**
     * Default constructor that instantiates a new International object by calling the
     * NonResident default constructor
     */
    public International(){
        super();
    }

    /**
     * Overloaded constructor that instantiates a new International object by calling the
     * NonResident constructor and taking 3 parameters to do so
     * @param profile The Profile object to use as an instance variable for the object
     * @param major The Major enum to use as an instance variable for the object
     * @param creditsCompleted the number of credits completed as an integer to use as an instance variable for the object
     */
    public International(Profile profile, Major major, int creditsCompleted){
        super(profile, major, creditsCompleted);
        this.isStudyAbroad = false;
    }

    /**
     * Overloaded constructor that instantiates a new International object by calling the
     * NonResident default constructor and taking 1 parameter to complete instantiation
     * @param isStudyAbroad The boolean value of whether the object is a study abroad student to use as an instance variable for the object
     * @param profile The Profile object to use as an instance variable for the object
     * @param major The Major enum to use as an instance variable for the object
     * @param creditsCompleted the number of credits completed as an integer to use as an instance variable for the object
     */
    public International(boolean isStudyAbroad, Profile profile, Major major, int creditsCompleted){
        super(profile, major, creditsCompleted);
        this.isStudyAbroad = isStudyAbroad;
    }

    /**
     * Returns a boolean value that says whether the International Student is study abroad.
     * @return true if the Student is study abroad, false if not
     */
    public boolean getIsStudyAbroad(){
        return this.isStudyAbroad;
    }

    /**
     * Setter method that changes if the student is study abroad based on the
     * inputted value.
     * @param isStudyAbroad parameter that is true if the student is study abroad or not.
     */
    public void setIsStudyAbroad(boolean isStudyAbroad){
        this.isStudyAbroad = isStudyAbroad;
    }

    /**
     * Converts the International Student to a String format
     * @return String representation of the International Student
     */
    @Override
    public String toString(){
        String str = super.toString() + "(international";
        if (this.isStudyAbroad == IS_STUDY_ABROAD){
            str += ":study abroad)";
        } else {
            str += ")";
        }
        return str;
    }

    /**
     * This method checks whether or not the number of credits passed in
     * is valid for an International student.
     * @param creditEnrolled credits that the student is enrolled in
     */
    @Override
    public boolean isValid(int creditEnrolled){
        if (this.isStudyAbroad == IS_STUDY_ABROAD && creditEnrolled > MIN_FULLTIME_CREDITS) return CREDITS_NOT_VALID;
        if (this.isStudyAbroad == NOT_STUDY_ABROAD && creditEnrolled < MIN_FULLTIME_CREDITS) return CREDITS_NOT_VALID;

        return CREDITS_VALID;
    }

    /**
     * Calculates what tuition will be due in USD for an International Student depending on their credits enrolled
     * @param creditsEnrolled number of credits currently enrolled as an integer
     * @return double value of tuition due for the Student in USD
     */
    @Override
    public double tuitionDue(int creditsEnrolled){
        double tuition = 0;
        if (this.getIsStudyAbroad()) {
            tuition = TUITION;
        }
        else {
            tuition = super.tuitionDue(creditsEnrolled) + HEALTH_INSURANCE_FEE;
        }
        return tuition;
    }
}
