package com.example.json.code;

import com.example.util.Trans2PinYin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by wudongchuan on 2015/3/3.
 */
public class JocEnumGen {
    //java -jar -Dfile.encoding=UTF-8 D:\android_project\svn_project\gen\build\libs\gen.jar
    //"D:\\android_project\\svn_project\\gen\\src\\main\\java\\com\\example\\joc"
    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            args = new String[2];
            args[0] = "D:\\android_project\\svn_project\\gen\\src\\main\\java\\com\\example\\";
            args[1] = "joc";
        }
        System.out.println(args[0]);

        List<AgrMode> agrModes = JocReader.read(args[0] + args[1]);

        FileWriter writer;
        try {
            writer = new FileWriter(args[0] + args[1] + "_enum.txt");//写入目标
            for (int i = 0; i < agrModes.size(); i++) {
                AgrMode agrMode = agrModes.get(i);
                String str = getEnumString(agrMode);
//                if (!agrMode.dataList.isEmpty())
                writer.write(str);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getEnumString(AgrMode agrMode) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < agrMode.dataList.size(); i++) {
            sb.append("\t\t");
            sb.append(agrMode.dataList.get(i).name);
            sb.append(",\t\t//  ");
            sb.append(agrMode.dataList.get(i).remark);
            sb.append(" (");
            sb.append(agrMode.dataList.get(i).type);
            sb.append(")");
            sb.append("\n");
        }

        String str = "   // %1$s \n" +
                "    @SubCmd(0x%2$s)\n" +
                "    enum %3$s {\n" +
                "%4$s" +
                "    }\n\n";

        str = String.format(str, agrMode.title, agrMode.subCmd, agrMode.titleEn, sb.toString());
        return str;
    }



    public void title() throws IOException {
        File file = new File("D:\\android_project\\svn_project\\gen\\src\\main\\java\\com\\example\\joc");

        StringBuilder sb = new StringBuilder();
        String s = "";
        BufferedReader br = new BufferedReader(new FileReader(file));
        int sum = 0;
        while ((s = br.readLine()) != null) {
            if (s.matches("^\\d+、[\\S^\\s]*")) {
                sb.append(s + "\n");
                sum++;
            }
        }

        br.close();
        String str = sb.toString();
        //str是你想要的东西

        System.out.println(sum);
        str = str.replaceAll("\\d+", "");
        str = str.replaceAll("\\[[\\u4e00-\\u9fa5]*\\]", "");
        String strpy = Trans2PinYin.trans2PinYin(str);
        System.out.print(strpy);
        System.out.print(str);
//        System.out.print("2q".matches("\\d+\\S*"));
    }


}
