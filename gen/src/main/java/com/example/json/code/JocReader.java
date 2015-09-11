package com.example.json.code;

import com.example.util.Trans2PinYin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wudongchuan on 2015/6/23.
 */
public class JocReader {
    public static List<AgrMode> read(String pathname) throws IOException {
        List<String> allStr = readFileContent(pathname);
        String s;

        List<AgrMode> agrModes = new ArrayList<AgrMode>();
        for (int i = 0; i < allStr.size(); i++) {
            AgrMode agrMode = new AgrMode();

            s = allStr.get(i);
            if (s.matches("^\\d+、[\\S^\\s]*")) {
                agrMode.title = s.trim();

                String titlepy = s.replaceAll("\\d+", "");
                titlepy = titlepy.replaceAll("\\[[\\u4e00-\\u9fa5]*\\]", "");
                agrMode.titleEn = Trans2PinYin.trans2PinYin(titlepy).trim();

                if (++i >= allStr.size()) break;
                s = allStr.get(i);
                int beginIndex = s.lastIndexOf("0x") + 2;
                agrMode.subCmd = s.substring(beginIndex, beginIndex + 2).trim();

                agrMode.dataList = new ArrayList<DataBody>();
                boolean addFlag = false;
                while (true) {
                    if (++i >= allStr.size()) break;

                    s = allStr.get(i);
                    if (s.matches("[\\S^\\s]*数据体包含内容[\\S^\\s]*")) {
                        if (++i >= allStr.size()) break;

                        s = allStr.get(i);
                        addFlag = true;
                    }
                    if (s.matches("^\\d+、[\\S^\\s]*|^\\n\\s*")) {
                        i--;
                        break;
                    }
                    if (addFlag && s.matches("[\\S^\\s]*[a-zA-Z]+[\\S^\\s]*")) {
                        String[] data = s.split("\\s+");
                        String name = "";
                        String typeAndRemark = "";
                        for (int d = 0; d < data.length; d++) {
                            name = data[0];
                            if (d > 0)
                                typeAndRemark += data[d];
                        }

                        String tr = typeAndRemark.trim();
                        String remark = tr.replaceFirst("^[a-z]+", "");
                        String type = tr.substring(0, tr.indexOf(remark));
                        agrMode.dataList.add(new DataBody(name.trim(), type, remark));
                    }
                }
                agrModes.add(agrMode);
            }
        }


        System.out.println(allStr.size());
        System.out.println(allStr);
        int sum = 0;
        for (AgrMode a : agrModes) {
            if (!a.dataList.isEmpty()) sum++;
        }
        System.out.println(agrModes.size() + "-" + sum);
        System.out.println(agrModes);

        return agrModes;

    }

    public static List<String> readFileContent(String pathname) throws IOException {
        File file = new File(pathname);

        List<String> allStr = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String s = "";
        while ((s = br.readLine()) != null) {
            allStr.add(s);
        }
        br.close();
        return allStr;
    }
}
