package com.example.icareer.Model;

public class JobVacancy {
    private String jobTitle ;
    private String jobDescription ;
    private String jobSalary ;
    private String jobDate ;
    private String numberOfVacancy ;
    private String id ;
    private String companyName ;


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JobVacancy() {
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }

    public String getJobDate() {
        return jobDate;
    }

    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }

    public String getNumberOfVacancy() {
        return numberOfVacancy;
    }

    public void setNumberOfVacancy(String numberOfVacancy) {
        this.numberOfVacancy = numberOfVacancy;
    }
}
