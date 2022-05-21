package top.itifrd.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.itifrd.pojo.Url;
import top.itifrd.r.R;
import top.itifrd.service.PathTool;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName
 * @Description 接收参数:视频的编号例如504，开始的时间，要包含开始的年-月-日-时-分，结束的年-月-日-时-分。返回文件地址，拼接服务器地址
 * @Author:chengyunlai
 * @Date
 * @Version 1.0
 **/
@RestController
@RequestMapping("/videoUrl")
@Slf4j
public class VideoUrlController {
    PathTool pathTool;
    @Autowired
    public void setPathTool(PathTool pathTool) {
        this.pathTool = pathTool;
    }

    @PostMapping
    public R getUrl(@RequestBody Url url){
        R res = new R();
        try {
            res = pathTool.getFileName(url.getId(), url.getStartTime(), url.getEndTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping
    public void testConnection(){
        log.info("测试连接");
    }
}
