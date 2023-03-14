package com.example.project3cs213;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {

    //FOR THE PRINT TAB

    ObservableList<String> printByOptions = FXCollections.observableArrayList("Standing", "Major",
            "Roster", "Enrollment", "Tuition");

    @FXML
    private ChoiceBox printByBox;

    @FXML
    protected void initialize(){
        printByBox.setValue("...");
        printByBox.setItems(printByOptions);
    }
    @FXML
    private Tab printTab;

    @FXML
    private Button printButton;
    @FXML
    private Button cancelButton;

    @FXML
    private TextArea printTextArea;



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
    private RadioButton internationalNonStudyAbroad;

    @FXML
    private RadioButton internationalStudyAbroad;

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

}
