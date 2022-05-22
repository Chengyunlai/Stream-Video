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
    String[] doneFileNames = null;
    public R getFileName(String id,String st, String et) throws IOException {
        int done_index = 0;
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
            log.info("文件名是:" + fileName);
            if (fileName.length() == 23){
                String resFileName = fileName.substring(0, fileName.lastIndexOf(".mp4"));
                log.info("时间日期是:" + resFileName);

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
        }
        log.info("startTime:" + startTime);
        log.info("endTime:" + endTime);
        log.info("文件开始索引:" + startIndex);
        log.info("文件结束索引" + endIndex);

        log.info("requirements.txt输出路径:" + path);
        // Java自定义的换行符
        String newLine = System.getProperty("line.separator");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + "requirements.txt"));
        doneFileNames = new String[endIndex-startIndex];
        for (int i = startIndex+1; i <= endIndex; i++) {
            doneFileNames[done_index++] = fileNames[i];
            String txt = "file '"+path+"501/"+fileNames[i]+"'";
            log.info("正在写入=====>>" + txt);
            bos.write(txt.getBytes());
            bos.write(newLine.getBytes());
        }
        log.info("requirements.txt,写入完成");
        bos.close();
        // dos命令执行
        try {
            log.info("开始执行命令行");
            String command = "ffmpeg -f concat -safe 0 -i "+ path + "requirements.txt"+" -c copy "+ path + "result/result.mp4";
            log.info("执行的命令是:" + command);
            Runtime.getRuntime().exec(command);
            String data = "http://172.18.45.106:8888/result/result.mp4";
            r.setData(data);
            r.setFlag(true);
            r.setTotalFilesName(doneFileNames);
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
