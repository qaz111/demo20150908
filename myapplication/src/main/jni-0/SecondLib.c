#include "com_example_wudongchuan_myapplication_SecondLib.h"
#include <stdio.h>
#include "stdlib.h"
#include "string.h"

 const static int length=10;

 //int array
 JNIEXPORT jintArray JNICALL Java_com_example_wudongchuan_myapplication_SecondLib_intMethod
   (JNIEnv *env, jclass obj)
 {
    jintArray array;
    array=(*env)->NewIntArray(env,10);
    int i=1;
    for(;i<=10;++i)
    {
        (*env)->SetIntArrayRegion(env,array,i-1,1,&i);
    }

    //get array length
    int len=(*env)->GetArrayLength(env,array);

    //array content
    jint* elems=(*env)->GetIntArrayElements(env,array,0);

    return array;
 }

 //string array
 JNIEXPORT jobjectArray JNICALL Java_com_example_wudongchuan_myapplication_SecondLib_stringMethod
   (JNIEnv *env, jclass obj)
 {
    jclass class=(*env)->FindClass(env,"java/lang/String");
    jobjectArray string=(*env)->NewObjectArray(env,(jsize)length,
            class,0);
    jstring jstr;
    char* _char[]={"my ","name ","is ",
                    "linc!!","正在","学习",
                    "JNI","和","NDK","技术！"
                    };
    int i=0;
    for(;i<length;++i)
    {
        jstr=(*env)->NewStringUTF(env,_char[i]);
        (*env)->SetObjectArrayElement(env,string,i,jstr);
    }

    return string;
 }

 JNIEXPORT jint JNICALL Java_com_example_wudongchuan_myapplication_SecondLib_sumMethod
   (JNIEnv *env, jobject obj, jint a, jint b)
   {
  int rtn = (int)(a + b);
    return (jint)rtn;
   }