package com.saffat.examconductor;

public class ResultDatabaseContainer {
    String examID,studentName,studentRoll,studentEmail,studentIDs,marksObtained;

    public ResultDatabaseContainer(String examID, String studentName, String studentRoll, String studentEmail, String studentIDs, String marksObtained) {
        this.examID = examID;
        this.studentName = studentName;
        this.studentRoll = studentRoll;
        this.studentEmail = studentEmail;
        this.studentIDs = studentIDs;
        this.marksObtained = marksObtained;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRoll() {
        return studentRoll;
    }

    public void setStudentRoll(String studentRoll) {
        this.studentRoll = studentRoll;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentIDs() {
        return studentIDs;
    }

    public void setStudentIDs(String studentIDs) {
        this.studentIDs = studentIDs;
    }

    public String getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(String marksObtained) {
        this.marksObtained = marksObtained;
    }
}
