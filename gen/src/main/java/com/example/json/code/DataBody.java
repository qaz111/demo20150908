package com.example.json.code;

/**
 * Created by wudongchuan on 2015/6/23.
 */
public class DataBody {
    String name;
    String type;
    String remark;

    DataBody(String name, String type, String remark) {
        this.name = name;
        this.type = type;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return name + "," + remark;
    }
}
