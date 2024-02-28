package org.example.cce107;

public class dbMain {

    private static MAINPAGECONT MAINPAGECONT;

    // This block of code is to know that if the connectivity runs succesfully or failed.
    public static void main(String[] args) {

        MAINPAGECONT = new MAINPAGECONT();
        MAINPAGECONT.dentaldb("postgres", "postgres", "admin");

    }
}
