package com.example.project3cs213.model;
/**
 * This class is an Enum Class which denotes the different types of Majors, the schools
 * to which they belong, and their code.
 *
 * @author Christian Osma, Liam Smith
 **/
public enum Major {
    BAIT("33:136", "RBS"),
    CS("01:198", "SAS"),
    MATH("01:640", "SAS"),
    ITI("04:547", "SC&I"),
    EE("14:332", "SOE");

    private final String majorCode;
    private final String schoolName;

    /**
     Constructor method. Does not instantiate anything new, but is used to assign a Major to different objects.
     @param majorCode code of the specific major
     @param schoolName school that the major belongs to
     */
    Major(String majorCode, String schoolName){
        this.majorCode = majorCode;
        this.schoolName = schoolName;
    }

    /**
     Getter method, returns the Major code corresponding to the Student's Major attribute
     @return String of the Major code of the current Student
     */
    public String getMajorCode(){
        return this.majorCode;
    }

    /**
     Getter method, returns the school name corresponding to the Student's Major attribute
     @return String of the school name of the current Student
     */
    public String getSchoolName(){
        return this.schoolName;
    }

    /**
     Returns string representation of the specific major
     @return string that represents the major
     */
    @Override
    public String toString(){
        String str = "(" + this.getMajorCode() + " " + this.name() + " " + this.getSchoolName() + ")";
        return str;
    }

}
