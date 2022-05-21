package top.itifrd.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import top.itifrd.r.R;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @ClassName
 * @Description
 * @Author:chengyunlai
 * @Date
 * @Version 1.0
 **/
@Service
@Slf4j
public class PathTool {
    // 定义要读取的路径
    @Value("${video.path}")
    private String path;
    int index = 0;
    // 开始的索引
    int startIndex = 0;
    // 结束的索引
    int endIndex = 0;
    // 开始的文件名
    String startTime = null;
    // 结束的文件名
    String endTime = null;
    // 返回值
    R r = new R();
    public R getFileName(String id,String st, String et) throws IOException {
        log.info("读到父类路径是:" + path);
        // 拼接具体摄像头的路径
        String filePath = path;
        filePath = filePath + id;
        log.info("具体要访问的路径是:" + filePath);

        // 拿到第一个值 所有的文件名列表
        String[] fileNames = this.ToolgetFileName(filePath);

        // 第一种开始时间和结束时间 都存在，拿到开始和结束时间的点的文件名
        // 第二种开始时间和结束时间 有一个为空值
        //    开始值不存在，结束值存在：返回当天小于结束点的所有时间段，文件0~结束索引
        //    开始值存在，结束值不存在：返回当天大于开始点的所有时间段，开始索引~文件长度-1
        // 第三种开始时间和结束时间 都不存在，返回false

        // 判断
        for (String fileName : fileNames) {

            // 处理只需要后缀名前面的时间内容
            String resFileName = fileName.substring(0, fileName.lastIndexOf("."));
            log.info("文件内容:" + resFileName);

            // 找一个小于结束时间，大于开始时间的节点
            if (resFileName.compareTo(st) > 0){
                if(startTime == null){
                    startIndex = index;
                    startTime = resFileName;
                }
            }
            if (resFileName.compareTo(et) < 0){
                    endIndex = index;
                    endTime = resFileName;
            }
            index++;
        }
        System.out.println("--");
        log.info("startTime" + startTime);
        log.info("endTime" + endTime);
        log.info("文件开始索引" + startIndex);
        log.info("文件结束索引" + endIndex);

        log.info("requirements.txt输出路径:" + path);
        // Java自定义的换行符
        String newLine = System.getProperty("line.separator");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + "requirements.txt"));
        for (int i = startIndex; i <= endIndex; i++) {
            log.info("正在写入=====>>" + fileNames[i]);
            bos.write(fileNames[i].getBytes());
            bos.write(newLine.getBytes());
        }
        log.info("写入完成");
        bos.close();
        // dos命令执行
        try {
            Runtime.getRuntime().exec("ffmpeg -f concat -safe 0 -i "+ path + "requirements.txt"+" -c copy "+ path + "result.mp4");
            String data = "http://172.18.45.106";
            r.setData(data);
            r.setFlag(true);
            r.setTotalFilesName(fileNames);
        }catch (Exception e){
            e.printStackTrace();
        }

        return r;
    }

    /**
     * @Description: 获取所有文件的名的字符串列表
     * @Param: [path]
     * @return: java.lang.String[]
     * @Author: chengyunlai
     * @Date: 2022/5/21
     */
    private String[] ToolgetFileName(String path){
        File file = new File(path);
        File[] files = file.listFiles();
        String[] sb = new String[files.length];
        int index = 0;
        for (File res : files) {
            if(!res.isDirectory()){
                sb[index++]= res.getName();
            }
        }
        return sb;
    }
}