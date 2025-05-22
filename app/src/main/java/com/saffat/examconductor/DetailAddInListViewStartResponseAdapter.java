package com.saffat.examconductor;

public class DetailAddInListViewStartResponseAdapter {

    String scode="";
    String sname="";
    int image;
    String ecode="",epass="",eanme="",fmark="",qsadpt="";

   public DetailAddInListViewStartResponseAdapter(String title1, String descp, int image1, String examCode, String empass, String exnamew, String fmarks, String qsa)
    {
        this.scode=title1;
        this.sname=descp;
        this.image=image1;
        this.ecode=examCode;
        this.epass=empass;
        this.eanme=exnamew;
        this.fmark=fmarks;
        this.qsadpt=qsa;
    }

}
