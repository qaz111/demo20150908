package com.example.findviewbyid;


import com.example.StringHelper;

import java.util.List;

public class ViewCodeUtil {

    private StringBuilder sbDefField;
    private StringBuilder sbFindViewForItemAndSetData;
    private StringBuilder sbFindView;
    private StringBuilder sbSetClkView;
    private StringBuilder sbSetData;
    private StringBuilder sbOnClkView;

    public ViewCodeUtil(String resFileName) {
        initCode(resFileName);
    }

    public StringBuilder getSbDefField() {
        return sbDefField;
    }

    public StringBuilder getSbFindViewForItemAndSetData() {
        return sbFindViewForItemAndSetData;
    }

    public StringBuilder getSbFindView() {
        return sbFindView;
    }

    public StringBuilder getSbSetClkView() {
        return sbSetClkView;
    }

    public StringBuilder getSbSetData() {
        return sbSetData;
    }

    public StringBuilder getSbOnClkView() {
        return sbOnClkView;
    }

    public String getCode() {
        return sbDefField.toString() + sbFindView.toString() + sbSetClkView.toString() + sbSetData.toString() + sbOnClkView.toString();
    }

    public void initCode(String resFileName) {
        AndroidView res = new SaxHander().parse(resFileName);
        List<AndroidView> views = res.getAllChildViews();
        sbDefField = new StringBuilder();
        sbFindView = new StringBuilder();
        sbFindViewForItemAndSetData = new StringBuilder();
        sbSetClkView = new StringBuilder();
        sbSetData = new StringBuilder();
        sbOnClkView = new StringBuilder();

       /* sbDefField.append("//---------- 开始定义域--------------\n");
        sbFindView.append("//----------开始initView方法------------------\n");
        sbSetData.append("//----------开始initData方法------------------\n");
        sbOnClkView.append("//----------开始onClick方法------------------\n");*/

        String elseStr = "        ";
        for (AndroidView view : views) {
            if (!pickView(view)) continue;

            String name = createFindViewName(view);
            String type = view.getClassSimpleName();
            sbDefField.append("private "+type + " " + name + ";");
            sbDefField.append("\n");

            String findViewStatement = createFindViewStatement(view);
            sbFindView.append(name + " = " + findViewStatement);
            sbFindView.append("\n");

            String nameForItem = createFindViewNameForItem(view);
            String findViewStatementForItem = createFindViewStatementForItem(view);
            sbFindViewForItemAndSetData.append(nameForItem + " = " + findViewStatementForItem);
            sbFindViewForItemAndSetData.append("\n");

            if ("TextView".equals(type)) {
                sbSetData.append(name + ".setText(mData.);");
                sbSetData.append("\n");

                sbFindViewForItemAndSetData.append(nameForItem + ".setText(getItem(position).)");
                sbFindViewForItemAndSetData.append("\n");
            }
            sbFindViewForItemAndSetData.append("\n");

//            if (!pickClkView(view)) continue;

            sbSetClkView.append(name + ".setOnClickListener(this);");
            sbSetClkView.append("\n");

            sbOnClkView.append(elseStr + "if ( v == " + name + " ) {\n");
            elseStr = " else ";
            sbOnClkView.append("\n");
            sbOnClkView.append("        }");
        }
    }

    private static boolean pickView(AndroidView view) {
        return view.getIdValue().indexOf("_") > 0;
    }

    private static boolean pickClkView(AndroidView view) {
        return view.getIdValue().indexOf("clk") > 0;
    }

    private static String createFindViewStatement(AndroidView view) {
        return ("view".equalsIgnoreCase(view.getClassSimpleName()) ? "" : "(" + view.getClassSimpleName() + ") ")
                + "findViewById(R.id." + view.getIdValue() + ");";
    }

    private static String createFindViewStatementForItem(AndroidView view) {
        return  "viewHolder.$(R.id." + view.getIdValue() + ");";
    }

    private static String createFindViewName(AndroidView view) {
        String idValue = view.getIdValue();
        String[] strings = idValue.split("_");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(StringHelper.toFristUpperCase(strings[i]));
        }
        return "m" + sb.toString();
    }

    private static String createFindViewNameForItem(AndroidView view) {
        String idValue = view.getIdValue();
        String[] strings = idValue.split("_");

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < strings.length; i++) {
            sb.append(StringHelper.toFristUpperCase(strings[i]));
        }
        return strings[0] + sb.toString();
    }

}