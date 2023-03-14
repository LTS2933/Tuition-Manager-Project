package com.example.project3cs213.model;

/**
 * This class implements the Comparable Interface and runs various methods on Student objects.
 * Students can be sorted by standing through this Class using the compareTo() method.
 *
 * @author Christian Osma, Liam Smith
 **/
public abstract class Student implements Comparable<Student>{
    private static final int MIN_CREDITS_ENROLLED = 3;
    private static final int MAX_CREDITS_ENROLLED = 24;
    private static final int MAX_FRESHMAN_CREDITS = 30;
    private static final int MAX_SOPHOMORE_CREDITS = 60;
    private static final int MAX_JUNIOR_CREDITS = 90;
    private static final int DEFAULT_CREDITS_COMPLETED = 0;

    private Profile profile;
    private Major major;
    private int creditsCompleted;

    /**
     Default constructor. Takes no arguments and instantiates a new Student object. Calls the Default
     Constructor of the Profile Class, initiates major as null, and creditsCompleted as 0.
     */
    public Student(){
        this.profile = new Profile();
        this.major = null;
        this.creditsCompleted = DEFAULT_CREDITS_COMPLETED;
    }

    /**
     Overloaded constructor. Takes 3 arguments and uses them to populate the current Student object.
     @param profile Profile of the student including last name, first name, and dob
     @param major Major of the current student
     @param creditsCompleted number of credits the current student has completed
     */
    public Student(Profile profile, Major major, int creditsCompleted){
        this.profile = profile;
        this.major = major;
        this.creditsCompleted = creditsCompleted;
    }

    /**
     Copy constructor. Copies the information of the argument and stores its attributes within
     the corresponding instance variables of the current Student object.
     @param st Student object which contains the information we wish to copy to the current Student object
     */
    public Student(Student st){
        this.profile = st.profile;
        this.major = st.major;
        this.creditsCompleted = st.creditsCompleted;
    }

    /**
     Getter method, returns the Profile of the current Studen.
     @return Profile of the current Student
     */
    public Profile getProfile(){
        return this.profile;
    }

    /**
     Getter method, returns the Major of the current Student as an enum type.
     @return Major of the current Student as an enum
     */
    public Major getMajor(){
        return this.major;
    }

    /**
     Setter method, sets Student's Major to that of user's choice
     @param major Major which will be used to set Student's Major attribute
     */
    public void setMajor(Major major){
        this.major = major;
    }

    /**
     * Setter method for changing the credits completed of the student to the
     * inputted value.
     * @param creditsCompleted Integer representing the number of credits completed by the student
     */
    public void setCreditCompleted(int creditsCompleted){
        this.creditsCompleted = creditsCompleted;
    }

    /**
     Getter method, returns the number of credits completed by the Student as an int.
     @return int, number of credits completed by the current Student object
     */
    public int getCredits(){
        return this.creditsCompleted;
    }

    /**
     Converts the current Student object to a String format
     @return String, includes various attributes of the current Student in a specific format
     */
    @Override
    public String toString(){
        String str = profile.toString();
        str =  str + " " + major.toString() + " ";
        str = str + "credits completed: " + creditsCompleted;
        String year = "Senior";
        if (this.creditsCompleted < MAX_FRESHMAN_CREDITS) year = "Freshman";
        else if (this.creditsCompleted < MAX_SOPHOMORE_CREDITS) year = "Sophomore";
        else if (this.creditsCompleted < MAX_JUNIOR_CREDITS) year = "Junior";
        str += " (" + year + ")";
        return str;
    }

    /**
     Says whether the current Student is equal to the one passed as an argument
     @param obj Object which should be an instance of Student or method will automatically return false.
     Otherwise, obj is a Student instance to be compared against the current Student object
     @return true if the two Student objects are the same Student, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        Student st = null;
        if (obj instanceof Student) {
            st = (Student) obj;
        }
        return (this.profile.equals(st.getProfile()));
    }

    /**
     Helps to sort Student objects based on last name, first name, and date of birth.
     @param st Student which will be used to compare against current Student object.
     @return -1 if the current Student should take priority over Student st, 0 if they are equal, and 1 if st
     should take priority in the sorting order.
     */
    @Override
    public int compareTo(Student st) {
        return this.profile.compareTo(st.profile);
    }

    /**
     * This method calculate whether the credits enrolled is valid or not.
     * @param creditEnrolled number of credits the student is enrolling in
     * @return true if the credits enrolled is valid, false if it's not
     */
    public boolean isValid(int creditEnrolled){
        return (creditEnrolled >= MIN_CREDITS_ENROLLED && creditEnrolled <= MAX_CREDITS_ENROLLED);
    }

    /**
     * Abstract method for calculating tuition due
     * @param creditsEnrolled number of credits enrolled for the semester
     * @return double value representing the tuition due
     */
    public abstract double tuitionDue(int creditsEnrolled);

    /**
     * Abstract method for calculating if the student is a resident or not
     * @return true if the student is a resident and false if not
     */
    public abstract boolean isResident();

}
