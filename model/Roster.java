package com.example.project3cs213.model;

/**
 Contains array data structure that contains list of students in the
 Roster. Grows automatically in increments of 4 if the array is full
 and contains methods that interact with the list of students.
 @author Christian Osma, Liam Smith
 */
public class Roster {
    private static final int NOT_FOUND = -1;
    private static final int ROSTER_EMPTY = 0;
    private static final int EQUAL_TO = 0;
    private static final int MAX_FRESHMAN_CREDITS = 30;
    private static final int MIN_JUNIOR_CREDITS = 60;
    private static final int MAX_JUNIOR_CREDITS = 90;
    private static final int MIN_SENIOR_CREDITS = 90;
    private static final int DEFAULT_SIZE = 4;

    private Student [] roster;
    private int size;

    /**
     Initializes student roster to an array of size 4 and current
     size of 0.
     */
    public Roster(){
        this.roster = new Student[DEFAULT_SIZE];
        this.size = ROSTER_EMPTY;
    }

    /**
     * Getter method for getting the size of the roster.
     * @return integer representing the size of the roster
     */
    public int getSize(){
        return this.size;
    }

    /**
     * Getter method for returning the list of students in the roster.
     * @return Student array of the current roster.
     */
    public Student [] returnRoster(){
        return this.roster;
    }

    /**
     This method loops through the list of students and looks for the
     specific student in the params. Returns index or -1 if the
     student is not found.
     @param student : student to be found in the roster
     @return index of the student in the array of -1 if not found
     */
    private int find(Student student){
        for (int i = 0; i<this.size; i++){
            if (student.equals(roster[i])) return i;
        }
        return NOT_FOUND;
    }

    /**
     Creates a new array that contains 4 more elements than
     the current list and copies the students into the new array.
     */
    private void grow(){
        Student [] newRoster = new Student[roster.length+DEFAULT_SIZE];
        for (int i = 0; i<this.size; i++){
            newRoster[i] = roster[i];
        }
        this.roster = newRoster;
    }

    /**
     Takes the student from the params and adds that student
     to the roster.
     @param student : student to be added to the roster
     @return true if the student has been added, false if not
     */
    public boolean add (Student student){
        if (contains(student) == true) return false;
        if (this.size == roster.length) grow();
        this.roster[this.size] = student;
        this.size++;
        return true;
    }

    /**
     Removes the specific student from the roster and returns true
     if it has succeeded. Returns false if the student was not found.
     @param student : student to be removed from the roster
     @return true if the student has been removed
     */
    public boolean remove (Student student){
        int indexOfStudent = find(student);
        if (indexOfStudent == NOT_FOUND) return false;

        roster[indexOfStudent] = null;
        for (int i = indexOfStudent; i<this.size-1; i++){
            roster[i] = roster[i+1];
        }
        this.size--;
        return true;
    }

    /**
     Method returns true if the student was found in the roster
     and false otherwise. Calls the find() method to find the student.
     @param student : student to be found in the roster
     @return true if the student is found in the roster
     */
    public boolean contains (Student student){
        int index = find(student);
        if (index == NOT_FOUND) return false;
        return true;
    }

    /**
     Prints the roster in standard output sorted by the student's
     Profile (last name, first name, and date of birth).
     */
    public String print(){
        if (this.size == ROSTER_EMPTY)return "Student roster is empty!";
        for (int i = 0; i<this.size-1; i++){
            Student minProfile = this.roster[i];
            int minIndex = i;
            for (int j = i+1; j<this.size ; j++){
                if (this.roster[j].compareTo(minProfile) < EQUAL_TO){
                    minProfile = this.roster[j];
                    minIndex = j;
                }
            }
            Student temp = this.roster[minIndex];
            this.roster[minIndex] = this.roster[i];
            this.roster[i] = temp;
        }
        String returnString = "** Student roster sorted by last name, first name, DOB **\n";
        for (int i = 0; i<this.size; i++){
            returnString += this.roster[i].toString() + "\n";
        }
        returnString += "* end of roster *";
        return returnString;
    }

    /**
     Prints roster to standard output sorted by school, major.
     */
    public String printBySchoolMajor(){
        if (this.size == ROSTER_EMPTY) return "Student roster is empty!";
        
        int minIndex = 0;
        for (Major major: Major.values()){
            for (int i = minIndex; i<this.size; i++){
                Student currStudent = this.roster[i];
                Major studentMajor = currStudent.getMajor();
                if (studentMajor == major){
                    Student temp = this.roster[minIndex];
                    this.roster[minIndex] = currStudent;
                    this.roster[i] = temp;
                    minIndex++;
                }
            }
        }
        String returnString = "** Student roster sorted by school, major **\n";
        for (int i = 0; i<this.size; i++){
            returnString += roster[i].toString() + "\n";
        }
        returnString += "* end of roster *\n";
        return returnString;
    }

    /**
     Prints roster to standard output sorted by standing
     */
    public String printByStanding(){
        if (this.size == ROSTER_EMPTY)return "Student roster is empty!";
        
        int index = 0;
        for (int i = index+1; i<this.size; i++){
            if (this.roster[i].getCredits() < MAX_FRESHMAN_CREDITS){
                Student temp = this.roster[i];
                this.roster[i] = this.roster[index];
                this.roster[index] = temp;
                index++;
            }
        }
        for (int i = index; i<this.size; i++){
            if (this.roster[i].getCredits() >= MIN_JUNIOR_CREDITS && this.roster[i].getCredits() < MAX_JUNIOR_CREDITS){
                Student temp = this.roster[i];
                this.roster[i] = this.roster[index];
                this.roster[index] = temp;
                index++;
            }
        }
        for (int i = index; i<this.size; i++){
            if (this.roster[i].getCredits() >= MIN_SENIOR_CREDITS){
                Student temp = this.roster[i];
                this.roster[i] = this.roster[index];
                this.roster[index] = temp;
                index++;
            }
        }
        String returnString = "** Student roster sorted by standing **\n";
        for (int i = 0; i<this.size; i++) returnString += this.roster[i].toString() + "\n";
        returnString += "* end of roster *\n";
        return returnString;
    }

    /**
     Returns a list of students that attend the corresponding
     school passed into the method, sorted by Profile.
     @param string : String representation of the school
     @return Student array of students that attend that specific school
     */
    public Student [] getBySchool(String string){
        if (this.size == ROSTER_EMPTY) return null;

        int schoolSize = 0;
        String school = string.toUpperCase();
        for (int i = 0; i<this.size; i++){
            if (this.roster[i].getMajor().getSchoolName().equals(school)) schoolSize++;
        }
        Student [] array = new Student[schoolSize];
        int index = 0;
        for (int i = 0; i<this.size; i++){
            if (this.roster[i].getMajor().getSchoolName().equals(school)){
                array[index] = this.roster[i];
                index++;
            }
        }
        for (int i = 0; i<schoolSize-1; i++){
            Student minStudent = array[i];
            int minIndex = i;
            for (int j = i+1; j<schoolSize; j++){
                if (array[j].compareTo(minStudent) < EQUAL_TO){
                    minStudent = array[j];
                    minIndex = j;
                }
            }
            Student temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
        return array;
    }

    /**
     Changes the inputed Student's major to the Major that was
     also given to the method.
     @param student : the student that wants their major changed
     @param major : the major the student wants to change into
     */
    public void changeMajor(Student student, Major major){
        int indexOfStudent = find(student);
        this.roster[indexOfStudent].setMajor(major);
    }

    /**
     * This method finds the student who needs their scholarship awarded
     * and gives that specific student the scholarship passed to in the parameter.
     * @param student Student object whose scholarship is to be awarded
     * @param scholarship the amount that the student is awared
     */
    public void updateScholarship(Student student, int scholarship){
        int indexOfStudent = find(student);
        Resident residentStudent = null;
        if (this.roster[indexOfStudent] instanceof Resident){
            residentStudent = (Resident) this.roster[indexOfStudent];
        }
        residentStudent.setScholarship(scholarship);
    }

    /**
     * This method finds the corresponding student in the list and checks
     * whether they are a Resident or not
     * @param student Student that needs to be checked
     * @return true if the student is a Resident and false otherwise
     */
    public boolean isResident(Student student){
        int indexOfStudent = find(student);
        return this.roster[indexOfStudent].isResident();
    }

    /**
     * This method checks the type of the student passed into the parameter
     * and returns its type as a String format.
     * @param student Student whose type needs to checked
     * @return String representing the type of student
     */
    public String typeOfStudent(Student student){
        String type = "";
        int indexOfStudent = find(student);

        if (this.roster[indexOfStudent] instanceof Resident){
            return "(Resident)";
        } else if (this.roster[indexOfStudent] instanceof International){
            International internationalStudent = (International) this.roster[indexOfStudent];
            type = "(International student";
            if (internationalStudent.getIsStudyAbroad() == true) type += "study abroad)";
            else type += ")";
            return type;
        } else if (this.roster[indexOfStudent] instanceof TriState){
            TriState triStudent = (TriState) this.roster[indexOfStudent];
            return "(Tri-state " + triStudent.getState().toUpperCase() + ")";
        }
        type = "(Non-Resident)";
        return type;
    }

    /**
     * This method finds the student in the roster and returns true
     * if the inputted credits is valid and false if it is not.
     * @param student Student whose credits needs to be checked
     * @param creditEnrolled number of credits to be enrolled
     * @return true if the number of credits is valid and false otherwise
     */
    public boolean validCredits(Student student, int creditEnrolled){
        int indexOfStudent = find(student);
        return this.roster[indexOfStudent].isValid(creditEnrolled);
    }

}
