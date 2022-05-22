package top.itifrd.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class PathToolTest {
    @Autowired
    PathTool tool;
    @Test
    public void getFileName() {
        String startTime = "2022-05-20-21-06-16.mp4";
        String endTime = "2022-05-20-19-55";
        System.out.println("测试的开始时间:" + startTime);
        System.out.println("测试的结束时间:" + endTime);
        String resFileName = startTime.substring(0, startTime.lastIndexOf("."));
        System.out.println(startTime.length());
        // try {
        //     tool.getFileName("501",startTime,endTime);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        // // tool.getFileName(startTime,endTime);
        // System.out.println(testTime.compareTo("2022-05-21-15-45"));
    }
}