package org.example.cce107;

public class dbMain {

    // This block of code is to know that if the connectivity runs succesfully or failed.
    public static void main(String[] args) {
        dentalbase db = new dentalbase();
        db.dentaldb("postgres", "postgres", "admin");


    }
}
