package com.example.icareer.Model;

import android.net.Uri;

public class JobSeekerClass {
    private String Uid ;
    private String nameJobSeeker ;
    private String emailJobSeeker ;
    private String passwordJobSeeker ;
    private String phoneNumberJobSeeker ;
    private String addressJobSeeker ;
    private String titleJobSeeker ;
    private String education ;
    private  String skill ;
    private String experience ;

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
    public String getTitleJobSeeker() {
        return titleJobSeeker;
    }

    public void setTitleJobSeeker(String titleJobSeeker) {
        this.titleJobSeeker = titleJobSeeker;
    }

    private Uri imageUri ;

    public JobSeekerClass() {
    }

    public Uri getImageUri()
    {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {

        this.imageUri = imageUri;
    }

    public String getPhoneNumberJobSeeker() {
        return phoneNumberJobSeeker;
    }

    public void setPhoneNumberJobSeeker(String phoneNumberJobSeeker) {
        this.phoneNumberJobSeeker = phoneNumberJobSeeker;
    }

    public String getAddressJobSeeker() {
        return addressJobSeeker;
    }

    public void setAddressJobSeeker(String addressJobSeeker) {
        this.addressJobSeeker = addressJobSeeker;
    }



    public String getNameJobSeeker() {
        return nameJobSeeker;
    }

    public void setNameJobSeeker(String nameJobSeeker) {
        this.nameJobSeeker = nameJobSeeker;
    }

    public String getEmailJobSeeker() {
        return emailJobSeeker;
    }

    public void setEmailJobSeeker(String emailJobSeeker) {
        this.emailJobSeeker = emailJobSeeker;
    }

    public String getPasswordJobSeeker() {
        return passwordJobSeeker;
    }

    public void setPasswordJobSeeker(String passwordJobSeeker) {
        this.passwordJobSeeker = passwordJobSeeker;
    }

}
