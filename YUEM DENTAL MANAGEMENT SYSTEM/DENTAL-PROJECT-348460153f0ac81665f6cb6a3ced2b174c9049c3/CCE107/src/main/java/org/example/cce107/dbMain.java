package org.example.cce107;

public class dbMain {

    private static MAINPAGECONT MAINPAGECONT;

    public static void main(String[] args) {

        MAINPAGECONT = new MAINPAGECONT();
        MAINPAGECONT.dentaldb("postgres", "postgres", "admin");

    }
}
