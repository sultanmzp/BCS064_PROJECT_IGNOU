package com.saffat.examconductor;

public class QuestionSaveAdpt {

    String examID,examPassword,className,subjectName,subjectCode,fullMarks,questionsSet,adminEmaill,timeResponse;

    public QuestionSaveAdpt(String examID, String examPassword, String className, String subjectName, String subjectCode, String fullMarks, String questionsSet, String adminEmaill, String timeResponse) {
        this.examID = examID;
        this.examPassword = examPassword;
        this.className = className;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.fullMarks = fullMarks;
        this.questionsSet = questionsSet;
        this.adminEmaill = adminEmaill;
        this.timeResponse = timeResponse;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getExamPassword() {
        return examPassword;
    }

    public void setExamPassword(String examPassword) {
        this.examPassword = examPassword;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getFullMarks() {
        return fullMarks;
    }

    public void setFullMarks(String fullMarks) {
        this.fullMarks = fullMarks;
    }

    public String getQuestionsSet() {
        return questionsSet;
    }

    public void setQuestionsSet(String questionsSet) {
        this.questionsSet = questionsSet;
    }

    public String getAdminEmaill() {
        return adminEmaill;
    }

    public void setAdminEmaill(String adminEmaill) {
        this.adminEmaill = adminEmaill;
    }

    public String getTimeResponse() {
        return timeResponse;
    }

    public void setTimeResponse(String timeResponse) {
        this.timeResponse = timeResponse;
    }
}
