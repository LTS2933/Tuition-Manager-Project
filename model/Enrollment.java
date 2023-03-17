package com.example.project3cs213.model;

/**
 * This class contains a list of EnrollStudents or students enrolled for the current semester.
 * The corresponding methods and interactions with the specific EnrollStudents are also included.
 * @author Christian Osma, Liam Smith
 */
public class Enrollment {
    private static final int NOT_FOUND = -1;
    private static final int STUDENTS_EMPTY = 0;
    private static final boolean IS_PARTTIME = true;
    private static final boolean NOT_PARTTIME = false;
    private static final int MIN_FULLTIME_CREDITS = 12;
    private static final int DEFAULT_SIZE = 4;

    private EnrollStudent[] students;
    private int size;

    /**
     * Initializes a new Enrollment class with a current size of 0 and list size of 4.
     */
    public Enrollment(){
        this.students = new EnrollStudent[DEFAULT_SIZE];
        this.size = STUDENTS_EMPTY;
    }


    /**
     * Getter method for the list of enrolled students.
     * @return EnrollStudent list of all the current students enrolled
     */
    public EnrollStudent[] returnEnrollStudent(){
        return this.students;
    }


    /**
     * Returns the number of students enrolled or the size of the enrollment list.
     * @return the current size of the enrollment list
     */
    public int getSize(){
        return this.size;
    }


    /**
     * This method takes an student and loops through the list to find the student.
     * Should return the index of the student or -1 if the student is not in the list.
     * @param student Student to be found in the list
     * @return Integer representing the index of the Student in the list or -1 if not found.
     */
    private int find(EnrollStudent student){
        for (int i = 0; i<this.size; i++){
            if (this.students[i].equals(student)) return i;
        }
        return NOT_FOUND;
    }


    /**
     * This method takes an student as a parameter and adds it to the end of the list.
     * @param enrollStudent The Student to be added to the enrollment list.
     */
    public void add (EnrollStudent enrollStudent){
        if (this.size == students.length) grow();
        this.students[this.size] = enrollStudent;
        this.size++;
    }

    /**
     * Method that creates a new list that has four more elements than the current array.
     * Then the method copies all students into the new list.
     */
    private void grow(){
        EnrollStudent [] newStudents = new EnrollStudent[this.students.length+DEFAULT_SIZE];
        for (int i = 0; i<this.students.length; i++){
            newStudents[i] = this.students[i];
        }
        this.students = newStudents;
    }


    /**
     * This method takes the student to be removed and swaps it with the student in the end of
     * the list. Then, the end of the list is removed. If the student is not found, the method
     * does nothing.
     * @param enrollStudent Student to be removed from the list.
     */
    public void remove(EnrollStudent enrollStudent){
        int indexOfStudent = find(enrollStudent);
        if (indexOfStudent == NOT_FOUND) return;

        EnrollStudent temp = this.students[indexOfStudent];
        this.students[indexOfStudent] = this.students[this.size-1];
        this.students[this.size-1] = temp;

        this.students[this.size-1] = null;
        this.size--;
    }


    /**
     * This method checks whether the passed in student is enrolled and returns
     * a boolean value.
     * @param enrollStudent The student to be found in the list.
     * @return true if the student is in the enrollment list and false if it is not.
     */
    public boolean contains(EnrollStudent enrollStudent){
        int index = find(enrollStudent);
        if (index == NOT_FOUND) return false;
        return true;
    }

    /**
     * This method loops through the enrollment list and returns a String containing every student
     * @return String representing the entire enrollment list
     */
    public String print(){
        if (this.size == STUDENTS_EMPTY) return "Enrollment is empty!";
            
        String returnString = "** Enrollment **\n";
        for (int i = 0; i<this.size; i++){
            returnString += this.students[i].toString() + "\n";
        }
        returnString += "* end of enrollment *\n";
        return returnString;
    }


    /**
     * This method finds the student in the enrollment list and changes their
     * enrolled credits to the inputted value.
     * @param student Student who needs their credits changed
     * @param credits Credits that the student is enrolled in
     */
    public void setCreditsEnrolled(EnrollStudent student, int credits){
        int indexOfStudent = find(student);
        this.students[indexOfStudent].setCreditsEnrolled(credits);
    }


    /**
     * This method checks the enrollment list to see if the inputted Student is
     * a part time student or not.
     * @param enrollStudent Student to be checked
     * @return true if the student is part time and false if they are not
     */
    public boolean isPartTime(EnrollStudent enrollStudent){
        int indexOfStudent = find(enrollStudent);
        if (indexOfStudent == NOT_FOUND) return NOT_PARTTIME;
        if (this.students[indexOfStudent].getCreditsEnrolled() < MIN_FULLTIME_CREDITS) return IS_PARTTIME;
        return NOT_PARTTIME;
    }

}
