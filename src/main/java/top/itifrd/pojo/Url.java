package top.itifrd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName
 * @Description
 * @Author:chengyunlai
 * @Date
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Url {
    // 设备的Id ；比如501
    private String id;
    // 格式 2022-05-22-10-10
    private String startTime;
    // 格式 2022-05-22-10-11
    private String endTime;
}
