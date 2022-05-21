package top.itifrd.r;

import java.util.Arrays;

/**
 * @ClassName
 * @Description
 * @Author:chengyunlai
 * @Date
 * @Version 1.0
 **/
public class R {
    // 返回所有文件名
    private String[] totalFilesName = null;
    // 返回此次查询是否成功，如果开始和结束时间都是空值则为null
    private Boolean flag = false;
    // 返回url地址
    private String data = null;

    public R(String[] totalFilesName, Boolean flag, String data) {
        this.totalFilesName = totalFilesName;
        this.flag = flag;
        this.data = data;
    }

    public R() {
    }

    public String[] getTotalFilesName() {
        return totalFilesName;
    }

    public void setTotalFilesName(String[] totalFilesName) {
        this.totalFilesName = totalFilesName;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "R{" +
                "totalFilesName=" + Arrays.toString(totalFilesName) +
                ", flag=" + flag +
                ", data='" + data + '\'' +
                '}';
    }
}
