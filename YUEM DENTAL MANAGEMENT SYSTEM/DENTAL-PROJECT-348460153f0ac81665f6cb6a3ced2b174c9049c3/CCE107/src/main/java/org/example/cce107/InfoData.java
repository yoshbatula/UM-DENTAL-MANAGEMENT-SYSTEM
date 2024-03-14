package org.example.cce107;

import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;

// In this java class it's for storing and returning the data it collected.
public class InfoData {

   private String fullname;
   private Integer age;
   private String gender;
   private String mobileno;
   private String email;
   private Date date;
   private String time;
   private String services;

   private String address;

    public InfoData(String fullname,Integer age,String gender,String mobileno,String email,Date date,String time,String services, String address) {
        this.fullname = fullname;
        this.age = age;
        this.gender = gender;
        this.mobileno = mobileno;
        this.email = email;
        this.date = date;
        this.time = time;
        this.services = services;
        this.address = address;
    }

    public String getFullname() {
       return fullname;
   }
   public  Integer getAge() {
       return age;
   }
   public  String getGender() {
       return gender;
   }
   public String getMobileno() {
       return mobileno;
   }
   public String getEmail() {
       return email;
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

    public String getAddress() {
        return address;
    }
}
