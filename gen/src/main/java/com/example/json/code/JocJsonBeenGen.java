package com.example.json.code;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Created by wudongchuan on 2015/3/12.
 */
public class JocJsonBeenGen {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            args = new String[2];
            args[0] = "D:\\android_project\\svn_project\\gen\\src\\main\\java\\com\\example\\";
            args[1] = "joc";
        }
        System.out.println(args[0]);

        List<AgrMode> agrModes = JocReader.read(args[0] + args[1]);

        for (AgrMode agrMode : agrModes) {
            if (!agrMode.dataList.isEmpty()) {
                genJsonData(agrMode);
            }
        }
    }

    private static final String PACKAGE_DIR = "dw.sample.";

    public static void genJsonData(AgrMode agrMode) throws Exception {
        JCodeModel cm = new JCodeModel();

        JDefinedClass dcData = genData(agrMode, cm);
        genBeen(agrMode, cm, dcData);

        File destDir = new File(".");
        cm.build(destDir);
    }

    private static void genSay() {
        //        // 生成 Singleton 类的成员方法 sayHelloMethod
//        JMethod sayHelloMethod = dcData.method(JMod.PUBLIC, cm.parseType("void"),
//                "sayHello");
//        // 生成方法级的 javadoc
//        sayHelloMethod.javadoc().add("This method will say Hello to the name.");
//        JBlock sayHelloBody = sayHelloMethod.body();
//        sayHelloMethod.param(cm.parseType("String"), "name");
//        JClass sys = cm.ref("java.lang.System");
//        JFieldRef ot = sys.staticRef("out");
//        JExpression sentance1 = JExpr.lit("Hello ").invoke("concat").arg(
//                JExpr.ref("name"));
//        JExpression sentance2 = sentance1.invoke("concat").arg("!");
//        sayHelloBody.invoke(ot, "println").arg(sentance2);
    }

    private static void genBeen(AgrMode agrMode, JCodeModel cm, JDefinedClass dcData) throws JClassAlreadyExistsException, ClassNotFoundException {
        JDefinedClass dcBeen = cm._class(PACKAGE_DIR + initialToUppercase(agrMode.titleEn.trim()) + "Been");
        dcBeen._extends(cm.ref(PACKAGE_DIR + "BestBeen"));

        // 生成类级的 javadoc
        JDocComment jdocBeen = dcBeen.javadoc();
        jdocBeen.add(agrMode.title);
        jdocBeen.addXdoclet("subCmd (0x" + agrMode.subCmd + ")");
        // 定义成员变量
        String fieldName = "data";
        JType type = cm.parseType("List<" + dcData.name() + ">");
        dcBeen.field(JMod.PRIVATE, type, fieldName);

        getjMethod(dcBeen, fieldName, type);
        SetMethod(dcBeen, fieldName, type);
    }

    private static JDefinedClass genData(AgrMode agrMode, JCodeModel cm) throws JClassAlreadyExistsException, ClassNotFoundException {
        JDefinedClass dcData = cm._class(PACKAGE_DIR + initialToUppercase(agrMode.titleEn.trim()) + "Data");

        dcData._implements(Serializable.class);

        // 生成类级的 javadoc
        JDocComment jdoc = dcData.javadoc();
        jdoc.add(agrMode.title);
        jdoc.addXdoclet("subCmd (0x" + agrMode.subCmd + ")");

        for (DataBody dataBody : agrMode.dataList) {
            //  JocTest.DataBody dataBody = agrMode.dataList.get(0);
            // 定义成员变量
            String fieldName = dataBody.name;
//            JType type = cm.parseType("String");
            JType type = cm.parseType("".equals(dataBody.type) || "string".equals(dataBody.type) ? "String" : dataBody.type);
            dcData.field(JMod.PRIVATE, type, fieldName).javadoc().add(dataBody.remark);

            // 生成  类的成员方法 getMethod
            JMethod getMethod = getjMethod(dcData, fieldName, type);
            getMethod.javadoc().add(dataBody.remark);

            // 生成  类的成员方法 setMethod
            SetMethod(dcData, fieldName, type);
        }
        return dcData;
    }

    // 生成  类的成员方法 getMethod
    private static void SetMethod(JDefinedClass dc, String fieldName, JType type) {
        JMethod setMethod = dc.method(JMod.PUBLIC, type,
                "set" + initialToUppercase(fieldName));
        setMethod.param(type, fieldName);
        JBlock setBody = setMethod.body();
        setBody.assign(JExpr._this().ref(fieldName), JExpr.ref(fieldName));
    }

    // 生成  类的成员方法 setMethod
    private static JMethod getjMethod(JDefinedClass dc, String fieldName, JType type) {
        JMethod getMethod = dc.method(JMod.PUBLIC, type,
                "get" + initialToUppercase(fieldName));
        JBlock getInstanceBody = getMethod.body();
        JFieldRef fieldRef = JExpr.ref(fieldName);
        getInstanceBody._return(fieldRef);
        return getMethod;
    }

    private static String initialToUppercase(String string) {
        return string.replaceFirst(string.substring(0, 1), string.substring(0, 1).toUpperCase());
    }
}
