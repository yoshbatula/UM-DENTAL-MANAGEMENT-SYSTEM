package org.example.cce107;

import java.sql.Date;

// In this java class it's for storing and returning the data it collected.
public class InfoData {

    private String fullname;
    private Integer age;
    private String Gender;
    private String mobileno;
    private String email;
    private String address;
    private Date date;
    private String time;
    private String services;

    public void AppointmentData(String fullname, Integer age, String Gender, String mobileno, String email, String address, Date date, String time, String services) {

        this.fullname = fullname;
        this.age = age;
        this.Gender = Gender;
        this.mobileno = mobileno;
        this.email = email;
        this.address = address;
        this.date = date;
        this.time = time;
        this.services = services;
    }

    public String getFullname() {
        return fullname;
    }
    public Integer getAge() {
        return age;
    }
    public String getGender() {
        return Gender;
    }
    public String getMobileno() {
        return mobileno;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }
    public Date getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public String getServices() {
        return services;
    }
}
