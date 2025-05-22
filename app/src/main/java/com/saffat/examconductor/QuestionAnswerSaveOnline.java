package com.saffat.examconductor;

public class QuestionAnswerSaveOnline {
    String examIDs,studentName,studentRoll,studentEmailm,studentIDs,className,subjectName,subjectCode,fullMarks,questionAnswer;
    public QuestionAnswerSaveOnline(String eamniddd,String studentnm,String stdrol,String stdem,String stdid,
                                    String clasnfq,String sunnamoo,String subcoedd,String fmarkssss,String datas) {
        this.examIDs=eamniddd;
        this.studentName=studentnm;
        this.studentRoll=stdrol;
        this.studentEmailm=stdem;
        this.studentIDs=stdid;
        this.className=clasnfq;
        this.subjectName=sunnamoo;
        this.subjectCode=subcoedd;
        this.fullMarks=fmarkssss;
        this.questionAnswer=datas;
    }

    public String getExamIDs() {
        return examIDs;
    }

    public void setExamIDs(String examIDs) {
        this.examIDs = examIDs;
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

    public String getStudentEmailm() {
        return studentEmailm;
    }

    public void setStudentEmailm(String studentEmailm) {
        this.studentEmailm = studentEmailm;
    }

    public String getStudentIDs() {
        return studentIDs;
    }

    public void setStudentIDs(String studentIDs) {
        this.studentIDs = studentIDs;
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

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }
}
