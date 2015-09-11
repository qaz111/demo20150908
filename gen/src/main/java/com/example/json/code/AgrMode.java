package com.example.json.code;

import java.util.List;

/**
 * Created by wudongchuan on 2015/6/23.
 */
public class AgrMode {
    String title;
    String titleEn;
    String subCmd;
    List<DataBody> dataList;

    @Override
    public String toString() {
        return title + "\n" + subCmd + "\n" + dataList.size() + dataList + "\n";
    }
}
