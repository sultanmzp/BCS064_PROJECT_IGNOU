package com.saffat.examconductor;

public class ListOfStudentsAnswerTT {
    String mid="",studentroll="",studentenail="",studentname="",studentid="",classname="",subjectname="",subjectcode="",fmarkks="",quans="";
    int image;


    public ListOfStudentsAnswerTT(String midd,String stunamed,String studentrold,String studentemaild,String studentidd,String classnamed,String subnamed,String subjectcoded,String fullmarksd,String quesansd,int imaged)
    {
        this.mid=midd;
        this.studentname=stunamed;
        this.studentroll=studentrold;
        this.studentenail=studentemaild;
        this.studentid=studentidd;
        this.classname=classnamed;
        this.subjectname=subnamed;
        this.subjectcode=subjectcoded;
        this.fmarkks=fullmarksd;
        this.quans=quesansd;
        this.image=imaged;
    }
}
