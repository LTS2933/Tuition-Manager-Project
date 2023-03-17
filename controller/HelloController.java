package com.example.project3cs213.controller;

import javafx.collections.FXCollections;
//import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.example.project3cs213.model.*;

/**
 * This class handles user commands with event handlers and helper methods
 * @author Christian Osma, Liam Smith
 */
public class HelloController {
    private static final boolean NOT_FROM_LOAD = false;
    private static final boolean FROM_LOAD = true;
    private static final int MIN_SCHOLARSHIP = 1;
    private static final int MAX_SCHOLARSHIP = 10000;
    private static final int ERROR_CREDITS = -404;
    private static final int MINIMUM_AGE = 16;
    private static final int POSITIVE_CREDITS = 0;
    private static final int MIN_GRADUATE_CREDITS = 120;
    private static final int ROSTER_EMPTY = 0;
    private static final int COMPARE_TO_EQUAL = 0;
    private static final int NO_INPUT = 0;

    ObservableList<String> printByOptions = FXCollections.observableArrayList("Standing", "Major",
            "Roster", "Enrollment", "Tuition", "Semester End");

    @FXML
    private ChoiceBox printByBox;

    @FXML
    protected void initialize(){
        printByBox.setValue("...");
        printByBox.setItems(printByOptions);
    }

    //FOR THE PRINT TAB
    @FXML
    private Button cancelButton;

    @FXML
    private Button printButton;

    @FXML
    private TextArea printTextArea;

    @FXML
    private Tab printTab;

    //FOR THE COMMANDS TAB

    @FXML
    private Button awardScholarshipButton;

    @FXML
    private Button dropButton;

    @FXML
    private Button enrollButton;

    @FXML
    private TextField scholarshipCentsBox;

    @FXML
    private TextField commandsCreditsText;

    @FXML
    private TextField scholarshipDollarsBox;

    @FXML
    private TextField commandsFirstNameText;

    @FXML
    private TextField commandsLastNameText;

    @FXML
    private DatePicker commandsDatePicker;

    @FXML
    private TextField commandsErrorTextField;

    //FOR THE ROSTER TAB
    @FXML
    private RadioButton resident;

    @FXML
    private RadioButton nonResident;

    @FXML
    private RadioButton triStateNY;

    @FXML
    private RadioButton triStateCT;

    @FXML
    private RadioButton international;

    @FXML
    private CheckBox studyAbroad;

    @FXML
    private boolean isResident;


    @FXML
    private TextField rosterFirstNameText;

    @FXML
    private TextField rosterLastNameText;

    @FXML
    private DatePicker rosterDatePicker;

    @FXML
    private RadioButton baitButton;

    @FXML
    private RadioButton csButton;

    @FXML
    private RadioButton eceButton;

    @FXML
    private RadioButton itiButton;

    @FXML
    private RadioButton mathButton;

    @FXML
    private TextField rosterCreditsText;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button changeMajorButton;

    @FXML
    private Button loadFromFileButton;

    @FXML
    private TextField rosterErrorText;

    @FXML
    private ToggleGroup MajorToggleGroup;

    @FXML
    private ToggleGroup nonResidentToggle;

    // For Modification Tab
    @FXML
    private TextField modificationsScholarship;

    @FXML
    private TextField modificationsErrorText;

    @FXML
    private TextField modificationsFirstName;

    @FXML
    private TextField modificationsLastName;

    @FXML
    private DatePicker modificationsDatePicker;

    @FXML
    private ToggleGroup modsMajorToggle;

    // FOR Tuition Manager instance variables
    private Roster roster = new Roster();
    private Enrollment enrollment = new Enrollment();
    private boolean loadedFile = false;

    @FXML
    protected void chooseResident(){
        nonResident.setDisable(true);
        triStateNY.setDisable(true);
        triStateCT.setDisable(true);
        international.setDisable(true);
        studyAbroad.setDisable(true);
        isResident = true;

    }
    @FXML
    protected void chooseNonResident(){
        resident.setDisable(true);
        isResident = false;
    }
    @FXML
    protected void chooseInternational(){
        resident.setDisable(true);
        triStateNY.setDisable(true);
        triStateCT.setDisable(true);
    }
    @FXML
    protected void chooseTriStateNY(){
        triStateCT.setDisable(true);
    }
    @FXML
    protected void chooseTriStateCT(){
        triStateNY.setDisable(true);
    }

    /**
     * Event handler that is called when the user wants to enroll a student.
     * Processes inputted information and calls helper method to enroll students and 
     * handle errors.
     */
    @FXML
    protected void onEnrollClick(){
        // for testing
        Profile profile = new Profile("Christian", "Osma", new Date("9/24/2002"));
        Student student = new Resident(profile, Major.CS, 106);
        roster.add(student);
        // for testing
        String firstName = commandsFirstNameText.getText();
        String lastName = commandsLastNameText.getText();
        if (commandsDatePicker.getValue() == null){
            commandsErrorTextField.setText("Missing data in command.");
            return;
        }
        String stringDOB = commandsDatePicker.getValue().toString();
        stringDOB = stringDOB.substring(5, 7) + "/" + stringDOB.substring(stringDOB.length()-2, stringDOB.length()) + "/" +  stringDOB.substring(0, 4);
        String stringCredits = commandsCreditsText.getText();
        handleEnrollStudent(firstName, lastName, stringDOB, stringCredits);
        cleanCommandCommands();
    }

    private void cleanCommandCommands(){
        commandsFirstNameText.setText("");
        commandsLastNameText.setText("");
        commandsDatePicker.setValue(null);
        commandsCreditsText.setText("");
    }

    /** 
     * This method handles enrolling new students into the enrollment list. This method
     * also detects errors such as invalid commands, invalid date of births, invalid
     * credits enrolled, and if the student is not in the roster.
     * @param firstName first name of student to be enrolled
     * @param lastName last name of student to be enrolled
     * @param stringDOB string representation of the student's date of birth
     * @param stringCredits string representation of the student's enrolled credits
     */
    private void handleEnrollStudent(String firstName, String lastName, String stringDOB, String stringCredits){
        if (firstName.length() == NO_INPUT || lastName.length() == NO_INPUT || stringDOB.length() == NO_INPUT || stringCredits.length() == NO_INPUT){
            commandsErrorTextField.setText("Missing data in command.");
            return;
        }
        Date dob = new Date(stringDOB);
        boolean validDate = validDate(dob, stringDOB, "enroll");
        if (validDate == false) return;
        int credits = 0;
        try {
            credits = Integer.parseInt(stringCredits);
        } catch (Exception e){ 
            commandsErrorTextField.setText(("Credits enrolled is not an integer."));
            return;
        }

        Student student = new Resident(new Profile(firstName, lastName, dob), Major.CS, credits);
        if (this.roster.contains(student) == false){
            commandsErrorTextField.setText(("Cannot enroll: " + student.getProfile().toString() + " is not in the roster."));
            return;
        }

        if (this.roster.validCredits(student, credits) == false){
            String typeOfStudent = this.roster.typeOfStudent(student);
            commandsErrorTextField.setText((typeOfStudent + " " + credits + ": invalid credit hours."));
            return;
        }
        EnrollStudent newStudent = new EnrollStudent(new Profile(firstName, lastName, dob), credits);
        if (this.enrollment.contains(newStudent)) this.enrollment.setCreditsEnrolled(newStudent, credits);
        else this.enrollment.add(newStudent);
        commandsErrorTextField.setText((newStudent.getProfile().toString() + " enrolled " + credits + " credits"));
    }

    /**
     * Event handler that is called when user wants to drop a specific student.
     * This method processes inputted information and calls a helper method that
     * drops a student and detects errors.
     */
    @FXML
    protected void onDropClick(){
        String firstName = commandsFirstNameText.getText();
        String lastName = commandsLastNameText.getText();
        if (commandsDatePicker.getValue() == null){
            commandsErrorTextField.setText("Missing data in command.");
            return;
        }
        String stringDOB = commandsDatePicker.getValue().toString();
        stringDOB = stringDOB.substring(5, 7) + "/" + stringDOB.substring(stringDOB.length()-2, stringDOB.length()) + "/" +  stringDOB.substring(0, 4);
        handleDropStudent(firstName, lastName, stringDOB);
        cleanCommandCommands();
    }

    /** 
     * Method that handles dropping a student from enrollment using the remainder
     * of the user commands. Detects and displays errors such as invalid commands 
     * and if the student is not in the enrollment list.
     * @param firstName first name of student to be dropped
     * @param lastName last name of student to be dropped
     * @param stringDOB string representation of the student's date of birth
     */
    private void handleDropStudent(String firstName, String lastName, String stringDOB){
        if (firstName.length() == NO_INPUT || lastName.length() == NO_INPUT || stringDOB.length() == NO_INPUT){
            commandsErrorTextField.setText(("Missing data in command."));
            return;
        }
        Date dob = new Date(stringDOB);

        EnrollStudent enrollStudent = new EnrollStudent(new Profile(firstName, lastName, dob), 0);
        if (this.enrollment.contains(enrollStudent) == false){
            commandsErrorTextField.setText((enrollStudent.getProfile().toString() + " is not enrolled."));
            return;
        }

        this.enrollment.remove(enrollStudent);
        commandsErrorTextField.setText((enrollStudent.getProfile().toString() + " dropped."));
    }

    /**
     * Event handler that is called when the user wants to award a scholarship.
     * This method processes the inputted information and calls a helper method
     * that handles awarding the scholarship and detecting errors.
     */
    @FXML
    protected void onScholarshipClick(){
        String firstName = commandsFirstNameText.getText();
        String lastName = commandsLastNameText.getText();
        if (commandsDatePicker.getValue() == null){
            commandsErrorTextField.setText("Missing data in command.");
            return;
        }
        String stringDOB = commandsDatePicker.getValue().toString();
        stringDOB = stringDOB.substring(5, 7) + "/" + stringDOB.substring(stringDOB.length()-2, stringDOB.length()) + "/" +  stringDOB.substring(0, 4);
        String stringScholarship = scholarshipDollarsBox.getText();
        handleAwardScholarship(firstName, lastName, stringDOB, stringScholarship);
        cleanCommandCommands();
    }

    /** 
     * Awards a scholarship to a specific student by updating their scholarship amount. 
     * This method also detects and displays errors such as invalid scholarships, non-resident
     * students, part time students, and if there is not enough information.
     * @param firstName the student's first name to be awared the scholarship
     * @param lastName the student's last name to be awared the scholarship
     * @param stringDOB string representation of the student's date of birth
     * @param stringScholarship string representation of the amount of student's scholarship
     */
    private void handleAwardScholarship(String firstName, String lastName, String stringDOB, String stringScholarship){
        if (firstName.length() == NO_INPUT || lastName.length() == NO_INPUT || stringDOB.length() == NO_INPUT){
            commandsErrorTextField.setText(("Missing data in command."));
            return;
        }
        Date dob = new Date(stringDOB);
        Student student = new Resident(new Profile(firstName, lastName, dob), Major.CS, 0);
        if (this.roster.contains(student) == false){
            commandsErrorTextField.setText((student.getProfile().toString() + " is not in the roster."));
            return;
        }
        if (this.roster.isResident(student) == false){
            String typeOfStudent = this.roster.typeOfStudent(student);
            commandsErrorTextField.setText((student.getProfile().toString() + " " + typeOfStudent + " is not eligible for the scholarship."));
            return;
        }

        int scholarship = 0;
        try {
            scholarship = Integer.parseInt(stringScholarship);
        } catch (Exception e){
            commandsErrorTextField.setText(("Amount is not an integer."));
            return;
        }
        if (scholarship < MIN_SCHOLARSHIP || scholarship > MAX_SCHOLARSHIP){
            commandsErrorTextField.setText((scholarship + ": invalid amount."));
            return;
        }
        EnrollStudent enrollStudent = new EnrollStudent(student.getProfile(), 0);
        if (this.enrollment.isPartTime(enrollStudent) == true){
            commandsErrorTextField.setText((enrollStudent.getProfile().toString() + " part time student is not eligible for the scholarship."));
            return;
        }
        this.roster.updateScholarship(student, scholarship);
        commandsErrorTextField.setText((student.getProfile().toString() + ": scholarship amount updated."));
    }

    /**
     * Event handler that is called when the user wants to print roster or enrollment.
     * This method processes the selected print method and calls helper method that
     * is able to execute those specific print commands.
     */
    @FXML
    protected void onPrintClick(){
        String command = printByBox.getValue().toString();
        // "Standing", "Major","Roster", "Enrollment", "Tuition", "Semester End"
        if (command.equals("...")) printTextArea.setText("Error: please enter a command!");
        else if (command.equals("Standing")) printTextArea.setText(this.roster.printByStanding());
        else if (command.equals("Major")) printTextArea.setText(this.roster.printBySchoolMajor());
        else if (command.equals("Roster")) printTextArea.setText(this.roster.print());
        else if (command.equals("Enrollment")) printTextArea.setText(this.enrollment.print());
        else if (command.equals("Tuition")) handlePrintTuition();
        else if (command.equals("Semester End")) handleSemesterEnd();
    }

    /**
     * Event handler that is called when the user wants to cancel their print request.
     * This method clears the output area and sets the inputted value to the default value.
     */
    @FXML
    protected void onCancelClick(){
        printTextArea.setText("");
        String defaultString = "...";
        printByBox.setValue(defaultString);
    }

    /**
     * Displays the students in the enrollment list that are eligible for graduation after the semester.
     */
    private void handleSemesterEnd(){
        String printString = "Credit completed has been updated.\n";
        printString += "** list of students eligible for graduation **\n";
        Student [] array = new Student[this.roster.getSize()];
        int index = 0;
        for (int i = 0; i < this.enrollment.getSize(); i++){ //iterates through enrollment
            for(int j = 0; j < this.roster.getSize(); j++){ //iterates through roster
                Profile enroll =  this.enrollment.returnEnrollStudent()[i].getProfile(); //finds the profile through enrollment
                Profile roster = this.roster.returnRoster()[j].getProfile(); //finds the profile through roster
                if(enroll.equals(roster)) {
                    int creditsEnrolled = this.enrollment.returnEnrollStudent()[i].getCreditsEnrolled();
                    int creditsCompleted = this.roster.returnRoster()[j].getCredits();
                    if (creditsCompleted + creditsEnrolled >= MIN_GRADUATE_CREDITS){
                        this.roster.returnRoster()[j].setCreditCompleted(creditsCompleted + creditsEnrolled);
                        array[index] = this.roster.returnRoster()[j];
                        index++;
                    }
                }
            }
        }
        for (int i = 0; i<index-1; i++){
            Student minimum = array[i];
            int minIndex = i;
            for (int j= i+1; j<index; j++){
                if (array[j].getCredits() > minimum.getCredits()){
                    minimum = array[j];
                    minIndex = j;
                }
            }
            printString += minimum.toString() + "\n";
            Student temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
        if (index > 0) printString += array[index-1].toString() + "\n";
        printTextArea.setText(printString);
    }

    /**
     * Iterates through the enrollment list and prints out the tuition due
     * by each student, which is handle by the tuitionDue() method in the Student class.
     */
    private void handlePrintTuition(){
        if (this.roster.getSize() == ROSTER_EMPTY){
            printTextArea.setText("Student roster is empty!");
            return;
        }
        String printString = "** Tuition due **\n";
        for (int i = 0; i < this.enrollment.getSize(); i++){ //iterates through enrollment
            for(int j = 0; j < this.roster.getSize(); j++){ //iterates through roster
                Profile enroll =  this.enrollment.returnEnrollStudent()[i].getProfile(); //finds the profile through enrollment
                Profile roster = this.roster.returnRoster()[j].getProfile(); //finds the profile through roster
                if(enroll.equals(roster)){
                    int creditsEnrolled = this.enrollment.returnEnrollStudent()[i].getCreditsEnrolled();
                    double tuition = this.roster.returnRoster()[j].tuitionDue(creditsEnrolled);
                    DecimalFormat df = new DecimalFormat("#,###.00");

                    EnrollStudent enrollStudent = this.enrollment.returnEnrollStudent()[i];
                    Student dummyStudent = new Resident(enrollStudent.getProfile(), Major.CS, 0);
                    printString += enrollStudent.getProfile().toString() + " " + this.roster.typeOfStudent(dummyStudent) + " enrolled " + enrollStudent.getCreditsEnrolled() + " credits: tuition due: $" + df.format(tuition) + "\n";
                }
            }
        }
        printString += "* end of tuition due *\n";
        printTextArea.setText(printString);
    }

    /**
     * Event handler that is called when the user wants to change the major of a specific student.
     * This method processes inputted information and calls a helper method that changes a student's major
     * and detects errors.
     */
    @FXML
    protected void onChangeMajor(){
        String firstName = modificationsFirstName.getText();
        String lastName = modificationsLastName.getText();
        if (modificationsDatePicker.getValue() == null){
            modificationsErrorText.setText("Missing data in command.");
            return;
        }
        if (modsMajorToggle.getSelectedToggle() == null){
            modificationsErrorText.setText("Missing data in command.");
            return;
        }
        String stringDOB = modificationsDatePicker.getValue().toString();
        stringDOB = stringDOB.substring(5, 7) + "/" + stringDOB.substring(stringDOB.length()-2, stringDOB.length()) + "/" +  stringDOB.substring(0, 4);
        String stringMajor = modsMajorToggle.getSelectedToggle().getUserData().toString();
        handleChangeMajor(firstName, lastName, stringDOB, stringMajor);
    }

    /**
     Changes student major given the rest of the command from the user.
     Prints out error if major is invalid or if the student is not in the roster
     and success message if major was successfully changed
     @param firstName first name of student 
     @param lastName last name of student
     @param stringDate string representation of the student's date of birth
     @param stringMajor string representation of the student's requested major
     */
    private void handleChangeMajor(String firstName, String lastName, String stringDate, String stringMajor){
        if (firstName.length() == NO_INPUT || lastName.length() == NO_INPUT || stringDate.length() == NO_INPUT || stringMajor.length() == NO_INPUT){
            modificationsErrorText.setText("Missing data in command.");
            return;
        }
        Date date = new Date(stringDate);
        Major major = getMajor(stringMajor);

        if (major == null){
            modificationsErrorText.setText(("Major code invalid: " + stringMajor));
            return;
        }

        Student student = new Resident(new Profile(firstName, lastName, date), Major.CS, 100);
        if (this.roster.contains(student) == false){
            modificationsErrorText.setText(firstName + " " + lastName + " " + stringDate + " is not in the roster.");
            return;
        }

        modificationsErrorText.setText((firstName + " " + lastName + " " + stringDate + " major changed to " + stringMajor));
        this.roster.changeMajor(student, major);
    }

    /**
     * Event handler that is called when user wants to add student to a roster.
     * This method processes inputted information and calls a specific helper method
     * that deals with adding students and detecting students depending on the type
     * of student requested.
     */
    @FXML
    protected void onAddClick(){
        String firstName = rosterFirstNameText.getText();
        String lastName = rosterLastNameText.getText();
        String stringCredits = rosterCreditsText.getText();
        if (rosterDatePicker.getValue() == null){
            rosterErrorText.setText("Missing data in command.");
            return;
        }
        String stringDOB = rosterDatePicker.getValue().toString();
        stringDOB = stringDOB.substring(5, 7) + "/" + stringDOB.substring(stringDOB.length()-2, stringDOB.length()) + "/" +  stringDOB.substring(0, 4);
        if (MajorToggleGroup.getSelectedToggle() == null){
            rosterErrorText.setText("Missing data in command.");
            return;
        }
        String stringMajor = MajorToggleGroup.getSelectedToggle().getUserData().toString();
        if (nonResidentToggle.getSelectedToggle() == null){
            rosterErrorText.setText("Missing data in command.");
        }
        String typeStudent = nonResidentToggle.getSelectedToggle().getUserData().toString();
        if (typeStudent.equals("Resident")) handleAddResident(firstName, lastName, stringDOB, stringMajor, stringCredits, NOT_FROM_LOAD);
        else if (typeStudent.equals("Non-Resident")) handleAddNonResident(firstName, lastName, stringDOB, stringMajor, stringCredits, NOT_FROM_LOAD);
        else if (typeStudent.equals("International Study Abroad")) handleAddInternationalStudent(firstName, lastName, stringDOB, stringMajor, stringCredits, true, NOT_FROM_LOAD);
        else if (typeStudent.equals("International Non-Study Abroad")) handleAddInternationalStudent(firstName, lastName, stringDOB, stringMajor, stringCredits, false, NOT_FROM_LOAD);
        else if (typeStudent.equals("Tri-State NY")) handleAddTristateStudent(firstName, lastName, stringDOB, stringMajor, stringCredits, "NY", NOT_FROM_LOAD);
        else if (typeStudent.equals("Tri-State CT")) handleAddTristateStudent(firstName, lastName, stringDOB, stringMajor, stringCredits, "CT", NOT_FROM_LOAD);
        cleanRosterCommands();
    }

    /** 
     * This method handles adding new tri-state students. Detects errors such as invalid 
     * states, invalid commands, invalid majors, and invalid credits. Displays success
     * message unless the method is called from handleLoadStudents().
     * @param firstName first name of tri-state student
     * @param lastName last name of tri-state student
     * @param stringDOB string representation of student's date of birth
     * @param stringMajor string representation of student's major
     * @param stringCredits string representaiton of student's credits
     * @param state either NY or CT to represent the state the student lives in 
     * @param isFromLoad true if the caller is handleLoadStudents and false otherwise
     */
    private void handleAddTristateStudent(String firstName, String lastName, String stringDOB, String stringMajor, String stringCredits, String state, boolean isFromLoad){
        if (firstName.length() == NO_INPUT || lastName.length() == NO_INPUT || stringDOB.length() == NO_INPUT || stringMajor.length() == NO_INPUT || stringCredits.length() == NO_INPUT){
            rosterErrorText.setText("Missing data in line command.");
            return;
        }
        Date dob = new Date(stringDOB);
        if (state.length() == NO_INPUT){
            rosterErrorText.setText("Missing the state code.");
            return;
        }
        boolean validDate = validDate(dob, stringDOB, "roster");
        if (validDate == false) return;

        Major major = getMajor(stringMajor.toUpperCase());
        if (major == null){
            rosterErrorText.setText("Major code invalid: " + stringMajor);
            return;
        }
        int credits = validCredits(stringCredits);
        if (credits == ERROR_CREDITS) return;
        if (state.toLowerCase().equals("ny") == false && state.toLowerCase().equals("ct") == false && state.toLowerCase().equals("nj") == false){
            rosterErrorText.setText(state + ": Invalid state code.");
            return;
        }
        Student student = new TriState(state, new Profile(firstName, lastName, dob), major, credits);
        boolean addStudent = this.roster.add(student);
        if (addStudent == false){
            rosterErrorText.setText(student.getProfile().toString() + " is already in the roster.");
            return;
        }
        if (isFromLoad == NOT_FROM_LOAD) rosterErrorText.setText(student.getProfile().toString() + " added to the roster.");
    }

    /** 
     * This method handles added new international students. Detects errors such as 
     * invalid commands, invalid dates, invalid majors, etc. Prints out a success 
     * message unless this method was called from handleLoadStudent() method.
     * @param firstName first name of international student
     * @param lastName last name of international student
     * @param stringDOB string representation of student's date of birth
     * @param stringMajor string representation of student's major
     * @param stringCredits string representaiton of student's credits
     * @param isStudyAbroad true if the student studies abroad and false otherwise
     * @param isFromLoad true if the caller is handleLoadStudents and false otherwise
     */
    private void handleAddInternationalStudent(String firstName, String lastName, String stringDOB, String stringMajor, String stringCredits, boolean isStudyAbroad, boolean isFromLoad){
        if (firstName.length() == NO_INPUT || lastName.length() == NO_INPUT || stringDOB.length() == NO_INPUT || stringMajor.length() == NO_INPUT || stringCredits.length() == NO_INPUT){
            rosterErrorText.setText("Missing data in line command.");
            return;
        }
        Date dob = new Date(stringDOB);

        boolean validDate = validDate(dob, stringDOB, "roster");
        if (validDate == false) return;

        Major major = getMajor(stringMajor.toUpperCase());
        if (major == null){
            rosterErrorText.setText("Major code invalid: " + stringMajor);
            return;
        }
        int credits = 0;
        try { 
            credits = Integer.parseInt(stringCredits);
        } catch (Exception e){ 
            rosterErrorText.setText("Credits completed invalid: not an integer!");
        }
        if (credits < POSITIVE_CREDITS){
            rosterErrorText.setText("Credits completed invalid: cannot be negative!");
            return;
        }
        Student student = new International(isStudyAbroad, new Profile(firstName, lastName, dob), major, credits);
        boolean addStudent = this.roster.add(student);
        if (addStudent == false){
            rosterErrorText.setText(student.getProfile().toString() + " is already in the roster.");
            return;
        }
        if (isFromLoad == NOT_FROM_LOAD) rosterErrorText.setText(student.getProfile().toString() + " added to the roster.");
    }

    /** 
     * This method handles adding new non-resident students. Detects errors such as
     * invalid commands, invalid major, invalid date of birth, and if the student is already
     * in the roster. Displays success message unless message caller is handleLoadStudents()
     * @param firstName first name of non resident student
     * @param lastName last name of non resident student
     * @param stringDOB string representation of student's date of birth
     * @param stringMajor string representation of student's major
     * @param stringCredits string representaiton of student's credits
     * @param isFromLoad true if the caller is handleLoadStudents and false otherwise
     */
    private void handleAddNonResident(String firstName, String lastName, String stringDOB, String stringMajor, String stringCredits, boolean isFromLoad){
        if (firstName.length() == NO_INPUT || lastName.length() == NO_INPUT || stringDOB.length() == NO_INPUT || stringMajor.length() == NO_INPUT || stringCredits.length() == NO_INPUT){
            rosterErrorText.setText("Missing data in line command.");
            return;
        }
        Date dob = new Date(stringDOB);
        boolean validDate = validDate(dob, stringDOB, "roster");
        if (validDate == false) return;

        Major major = getMajor(stringMajor.toUpperCase());
        if (major == null){
            rosterErrorText.setText("Major code invalid: " + stringMajor);
            return;
        }
        int credits = 0;
        try { 
            credits = Integer.parseInt(stringCredits);
        } catch (Exception e){ 
            rosterErrorText.setText("Credits completed invalid: not an integer!");
        }
        if (credits < POSITIVE_CREDITS){
            rosterErrorText.setText("Credits completed invalid: cannot be negative!");
            return;
        }
        Student student = new NonResident(new Profile(firstName, lastName, dob), major, credits);
        boolean addStudent = this.roster.add(student);
        if (addStudent == false){
            rosterErrorText.setText(student.getProfile().toString() + " is already in the roster.");
            return;
        }
        if (isFromLoad == NOT_FROM_LOAD) rosterErrorText.setText(student.getProfile().toString() + " added to the roster.");
    }

    /** 
     * This method handles adding new resident students. Detects errors such as invalid
     * commands and invalid inputted information about the resident student. Displays
     * success message unless the method caller is handleLoadStudents()
     * @param firstName first name of resident student
     * @param lastName last name of resident student
     * @param stringDOB string representation of student's date of birth
     * @param stringMajor string representation of student's major
     * @param stringCredits string representaiton of student's credits
     * @param isFromLoad true if the caller is handleLoadStudents and false otherwise
     */
    private void handleAddResident(String firstName, String lastName, String stringDOB, String stringMajor, String stringCredits, boolean isFromLoad){
        if (firstName.length() == NO_INPUT || lastName.length() == NO_INPUT || stringDOB.length() == NO_INPUT || stringMajor.length() == NO_INPUT || stringCredits.length() == NO_INPUT){
            rosterErrorText.setText("Missing data in line command.");
            return;
        }
        Date dob = new Date(stringDOB);
        Major major = getMajor(stringMajor.toUpperCase());
        if (major == null){
            rosterErrorText.setText("Major code invlaid: " + stringMajor);
            return;
        }
        boolean validDate = validDate(dob, stringDOB, "roster");
        if (validDate == false) return;

        int credits = 0;
        try  {
            credits = Integer.parseInt(stringCredits);
        } catch (Exception e){
            rosterErrorText.setText("Credits completed invalid: not an integer!");
            return;
        }
        if (credits < POSITIVE_CREDITS){
            rosterErrorText.setText("Credits completed invalid: cannot be negative!");
            return;
        }
        Student student = new Resident(new Profile(firstName, lastName, dob), major, credits);
        boolean addStudent = this.roster.add(student);
        if (addStudent == false){
            rosterErrorText.setText(student.getProfile().toString() + " is already in the roster.");
            return;
        }
        if (isFromLoad == NOT_FROM_LOAD) rosterErrorText.setText(student.getProfile().toString() + " added to the roster.");
    }

    /**
     * Sets user input to default value after command is initiated
     */
    private void cleanRosterCommands(){
        rosterFirstNameText.setText("");
        rosterLastNameText.setText("");
        rosterDatePicker.setValue(null);
        rosterCreditsText.setText("");
        MajorToggleGroup.selectToggle(null);
        nonResidentToggle.selectToggle(null);
    }

    /**
     * Event handler that is called when the user wants to remove a student from the roster.
     * This method processes inputted information and calls a helper method that removes
     * the student from the roster and detects errors.
     */
    @FXML
    protected void onRemoveClick(){
        String firstName = rosterFirstNameText.getText();
        String lastName = rosterLastNameText.getText();
        if (firstName.length() == NO_INPUT || lastName.length() == NO_INPUT || rosterDatePicker.getValue() == null){
            rosterErrorText.setText("Missing data in command.");
            return;
        }
        String stringDOB = rosterDatePicker.getValue().toString();
        stringDOB = stringDOB.substring(5, 7) + "/" + stringDOB.substring(stringDOB.length()-2, stringDOB.length()) + "/" +  stringDOB.substring(0, 4);
        handleRemoveStudent(firstName, lastName, stringDOB);
        cleanRosterCommands();
    }

    /**
     Removes student from the Roster unless they are not in the roster.
     Prints out error or success message.
     @param firstName first name of student to be removed
     @param lastName last name of student to be removed
     @param stringDate string representation of the student's date of birth
     */
    private void handleRemoveStudent(String firstName, String lastName, String stringDate){
        Date date = new Date(stringDate);

        Student student = new Resident(new Profile(firstName, lastName, date), Major.CS, 106);
        if (this.roster.contains(student) == false){
            rosterErrorText.setText(firstName + " " + lastName + " " + stringDate + " is not in the roster.");
            return;
        }

        this.roster.remove(student);
        rosterErrorText.setText(firstName + " " + lastName + " " + stringDate + " removed from the roster.");
    }

    /**
     * Event handler that is called when the user wants to load students from a file.
     * Calls a helper method that scans a .txt that contains student information and 
     * handles other errors as well.
     */
    @FXML
    protected void onLoadClick(){
        if (loadedFile == true) { 
            rosterErrorText.setText("Already loaded from file!");
            return;
        }
        loadedFile = true;
        try {
            handleLoadStudents();
        } catch (FileNotFoundException e){
            rosterErrorText.setText("File not found!");
        }
    }

    /** 
     * This method reads input from a file passed into it and adds students to the roster depending
     * on the contents of that file.
     * @param file String representation of the file to read containing new students
     * @throws FileNotFoundException Error if the file was not found
     */
    private void handleLoadStudents() throws FileNotFoundException{
        Scanner scanner = new Scanner(new File("src/main/java/com/example/project3cs213/studentList.txt"));
        while (scanner.hasNextLine()){
            StringTokenizer st = new StringTokenizer(scanner.nextLine(), ",");
            String firstOperating = st.nextToken();
            String firstName = st.nextToken();
            String lastName = st.nextToken();
            String stringDOB = st.nextToken();
            String stringMajor = st.nextToken();
            String stringCredits = st.nextToken();
            switch(firstOperating){
                case "R": 
                    handleAddResident(firstName, lastName, stringDOB, stringMajor, stringCredits, FROM_LOAD);
                    break;
                case "N":
                    handleAddNonResident(firstName, lastName, stringDOB, stringMajor, stringCredits, FROM_LOAD);
                    break;
                case "I":
                    boolean isStudyAbroad = false;
                    if (st.nextToken() == "true") isStudyAbroad = true;
                    handleAddInternationalStudent(firstName, lastName, stringDOB, stringMajor, stringCredits, isStudyAbroad, FROM_LOAD);
                    break;
                case "T":
                    handleAddTristateStudent(firstName, lastName, stringDOB, stringMajor, stringCredits, st.nextToken(), FROM_LOAD);
                    break;
                default: 
                    rosterErrorText.setText("Invalid command.");
                    return;
            }
        }
        rosterErrorText.setText("Students loaded to the roster.");
        scanner.close();
    }
    

    /**
     * Helper method that checks if the date is a valid of date of birth.
     * @param date Date object which represents the date to be checked
     * @param stringDate date inputted by the user, which is used to print error to standard output
     * @param from string representation of the section the caller is from
     * @return true if the date is a valid date of birth and false otherwise
     */
    private boolean validDate(Date date, String stringDate, String from){
        if (date.isValid() == false){
            if (from.equals("enroll")) commandsErrorTextField.setText("DOB invalid: " + stringDate + " not a valid calendar date!");
            if (from.equals("roster")) rosterErrorText.setText("DOB invalid: " + stringDate + " not a valid calendar date!");
            return false;
        }
        if (isBeforeCurrent(date) == false){
            if (from.equals("enroll")) commandsErrorTextField.setText("DOB invalid: " + stringDate + " is today or in the future!");
            if (from.equals("roster")) rosterErrorText.setText("DOB invalid: " + stringDate + " is today or in the future!");
            return false;
        }
        if (date.getAge() < MINIMUM_AGE){
            if (from.equals("enroll")) commandsErrorTextField.setText("DOB invalid: " + stringDate + " younger than 16 years old.");
            if (from.equals("roster")) rosterErrorText.setText("DOB invalid: " + stringDate + " younger than 16 years old.");
            return false;
        }
        return true;
    }


    /**
     Takes in a student's date of birth and returns true if the student
     was born before present and false otherwise
     @param dob : student's date of birth
     @return true if student was born before present day
     */
    private boolean isBeforeCurrent(Date dob){
        Date currentDate = new Date();
        int compare = dob.compareTo(currentDate);

        if (compare < COMPARE_TO_EQUAL) return true;
        return false;
    }

    /**
     Takes String input and returns its equivalent value in the
     enum Major class.
     @param major : String representation of the inputed major
     @return returns object from Major class depending on input
     */
    private Major getMajor(String major){
        switch(major){
            case "BAIT":
                return Major.BAIT;
            case "CS":
                return Major.CS;
            case "MATH":
                return Major.MATH;
            case "ITI":
                return Major.ITI;
            case "EE":
                return Major.EE;
            default:
                return null;
        }
    }

    /**
     * Helper method that calculates the integer value of the inputted String.
     * If the String is not an integer it prints out an error message and returns -1.
     * @param number String representation of a number
     * @return the integer value of the String or -1 if it is not valid
     */
    private int validCredits(String number){
        int credits = 0;

        try { 
            credits = Integer.parseInt(number);
        } catch (Exception e){
            rosterErrorText.setText("Credits completed invalid: not an integer!");
            return ERROR_CREDITS;
        }

        if (credits < POSITIVE_CREDITS){
            rosterErrorText.setText("Credits completed invalid: cannot be negative!");
            return ERROR_CREDITS;
        }

        return credits;
    }
}
