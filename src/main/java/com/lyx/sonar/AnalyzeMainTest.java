package com.lyx.sonar;

public class AnalyzeMainTest {

    public static void main(String[] args) throws Exception {
        // 不要跨月统计
        // String argss[] = {"-h", "192.168.9.195", "-p", "9988", "-s", "2019-07-01", "-e", "2019-10-18"};
        // String argss[] = {"-h", "192.168.9.195", "-p", "9988", "-s", "2019-10-08"};
        // AnalyzeMain.main(argss);

        String argss[] = {"-h", "192.168.9.195", "-p", "9988", "-s", "2019-10-08"};
        AnalyzeCuMain.main(argss);
    }
}
