package com.lyx.sonar;

public class AnalyzeMainTest {

    public static void main(String[] args) throws Exception {
        // String argss[]={"-?"};
        String argss[] = {"-h", "192.168.9.195", "-p", "9988", "-s", "2019-07-01", "-e", "2019-10-18"};
        AnalyzeMain.main(argss);
    }
}
