package com.example.findviewbyid;

import com.example.StringHelper;
import com.example.json.code.JocReader;

import java.io.IOException;
import java.util.List;

public class Test {
    private static final String ACTIVITY_NAME = "AbcActivity";
    private static final String PRJ_DIR = "D:\\android_project\\svn_project\\myapplication\\src\\main\\";
    private static final String ACTIVITY_PAGE_PATH = "java\\com\\example\\wudongchuan\\myapplication\\";

//    private static final String ACTIVITY_NAME = "AbcActivity";
//    private static final String PRJ_DIR = "D:\\android_project\\svn_project\\Toyota\\";
//    private static final String ACTIVITY_PAGE_PATH = "src\\calinks\\toyota\\ui\\activity\\";

    private static final String[] ACTIVI_TYFILES = {PRJ_DIR + ACTIVITY_PAGE_PATH + ACTIVITY_NAME + ".java"};

    private static final String LAYOUT_DIR = PRJ_DIR + "res\\layout\\";
    private static final String REGEX_LAYOUT = "R.layout.activity_[\\w]+";
    private static final String REGEX_ITEM = "R.layout.item_[\\w]+";

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        List<String> content = JocReader.readFileContent(ACTIVI_TYFILES[0]);
        System.out.print(System.currentTimeMillis() - startTime);

        ViewCodeUtil viewCodeUtilLayout = getViewCodeUtil(content, REGEX_LAYOUT);
        ViewCodeUtil viewCodeUtilItem = getViewCodeUtil(content, REGEX_ITEM);

        for (String line : content) {
            line = line.replace("def_sbDefField", viewCodeUtilLayout.getSbDefField());
            line = line.replace("def_sbFindView", viewCodeUtilLayout.getSbFindView());
            line = line.replace("def_sbOnClkView", viewCodeUtilLayout.getSbOnClkView());
            line = line.replace("def_sbSetClkView", viewCodeUtilLayout.getSbSetClkView());
            line = line.replace("def_sbSetData", viewCodeUtilLayout.getSbSetData());

            line = line.replace("def_sbItemFindViewAndSetData", viewCodeUtilItem.getSbFindViewForItemAndSetData());

            System.out.println(line);
        }
        System.out.print(System.currentTimeMillis() - startTime);

    }

    private static ViewCodeUtil getViewCodeUtil(List<String> content, String regex) {
        ViewCodeUtil viewCodeUtil = null;
        for (String line : content) {
            String layoutName = StringHelper.matchPart(line, regex);
            if (!"".equals(layoutName)) {
//                System.out.println(layoutName);
                layoutName = layoutName.substring(9);
                String layoutFileName = LAYOUT_DIR + layoutName + ".xml";
                viewCodeUtil = new ViewCodeUtil(layoutFileName);
//                System.out.println(viewCodeUtil.getCode());
                break;
            }
        }
        return viewCodeUtil;
    }

}