package com.saffat.examconductor;

public class RegisterDataAdapterHolder {

    String AdminName,AdminEmail,AdminPhoneNo,AdminPassword;

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String adminName) {
        AdminName = adminName;
    }

    public String getAdminEmail() {
        return AdminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        AdminEmail = adminEmail;
    }

    public String getAdminPhoneNo() {
        return AdminPhoneNo;
    }

    public void setAdminPhoneNo(String adminPhoneNo) {
        AdminPhoneNo = adminPhoneNo;
    }

    public String getAdminPassword() {
        return AdminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        AdminPassword = adminPassword;
    }

    public RegisterDataAdapterHolder(String AdminName, String AdminEmail, String AdminPhoneNo, String AdminPassword)
    {
        this.AdminName=AdminName;
        this.AdminEmail=AdminEmail;
        this.AdminPhoneNo=AdminPhoneNo;
        this.AdminPassword=AdminPassword;
    }

}
