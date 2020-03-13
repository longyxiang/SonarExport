package com.lyx.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author vvic
 * @date 2020/3/13
 * @description
 */
public class ReadUtil {

    /**
     * @author 戴尔电脑 * @date 2018-1-19 下午4:02:38 读取txt文件的内容 * @param file 想要读取的文件对象
     * 
     *         course.txt 1,数据库 2,数学 3,信息系统 4,操作系统 5,数据结构 6,数据处理
     * @return 返回文件内容
     */
    public static int txt2String(File file) {
        int index = 0;
        int totalTime = 0;
        int maxTime = 0;
        int minTime = 999999;
        try {
            // 构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;

            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                if (s.contains("times")) {
                    int times = s.lastIndexOf("times");
                    String substring = s.substring(times + 9);
                    int ms = substring.indexOf("ms");
                    String substring1 = substring.substring(0, ms);
                    try {
                        int totalTime1 = Integer.parseInt(substring1);
                        totalTime += totalTime1;
                        index++;
                        if (totalTime1 > maxTime) {
                            maxTime = totalTime1;
                        }
                        if (minTime > totalTime1) {
                            minTime = totalTime1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("总时间：" + totalTime);
        System.out.println("总次数：" + index);
        System.out.println("最大值：" + maxTime);
        System.out.println("最小值：" + minTime);
        return totalTime / index;
    }

    public static void main(String[] args) {
        File file = new File("D:/data_20200313_074410.txt");
        int x = txt2String(file);
        System.out.println(x);
    }

}
