package com.example.wudongchuan.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * MainActivity
 */
public class MainActivity extends Activity implements View.OnClickListener {


    private static final String TAG = "MainActivity";
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method STUB
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String src = "//system//media//a.zip";
        String target = "//system//media//b.zip";

//        chmod();
//        copyFile(new File(src), new File(target));

        onClick(index);
//        FileHelper.getDirectoryAllFile("//",true);

        findViewById(R.id.button).setOnClickListener(this);
    }

    //�����ļ�
    public void copyFile(File src, File target) {
        InputStream in = null;
        OutputStream out = null;

        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(target);
            bin = new BufferedInputStream(in);
            bout = new BufferedOutputStream(out);

            int length = bin.available();
            Log.i("MainActivity", "--" + length);
            byte[] b = new byte[length];
            int len = bin.read(b);
            while (len != -1) {
                bout.write(b, 0, len);
                len = bin.read(b);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bin != null) {
                    bin.close();
                }
                if (bout != null) {
                    bout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // �޸�Ȩ��
    public void chmod() {
        Process process = null;
        DataOutputStream os = null;
        try {
//            String cmd = "chmod 777 " + getPackageCodePath();
            String cmd = "mount -o remount,rw /system/media/";
            process = Runtime.getRuntime().exec("su"); // 切换到root帐号
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            // return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
    }

    public void onClick(int index) {
        String[] commands = {"mount -o remount,rw /system",
                "cp /system/media/2.zip /system/media/cp" + index + ".zip",
        };
        Process process = null;
        DataOutputStream dataOutputStream = null;

        try {
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());

            String cmd = "chmod 777 " + getPackageCodePath();
            dataOutputStream.writeBytes(cmd + "\n");

            for (int i = 0; i < commands.length; i++) {
                Log.e(TAG, "commands[" + i + "]:" + commands[i]);
                Toast.makeText(this, "commands[" + i + "]:" + commands[i], Toast.LENGTH_LONG).show();
                dataOutputStream.writeBytes(commands[i] + "\n");
            }

            dataOutputStream.writeBytes("exit\n");

            dataOutputStream.flush();
            process.waitFor();
        } catch (Exception e) {
            Log.e(TAG, "copy fail", e);
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }

    }

    public DataInputStream Terminal(String paramString)
            throws Exception {
        Process localProcess = Runtime.getRuntime().exec("su");
        DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
        DataInputStream localDataInputStream = new DataInputStream(localProcess.getInputStream());
        localDataOutputStream.writeBytes(paramString + "\n");
        localDataOutputStream.flush();
        localDataOutputStream.writeBytes("exit\n");
        localDataOutputStream.flush();
        localProcess.waitFor();
        return localDataInputStream;
    }

    /**
     * 静默安装
     *
     * @param file
     * @return
     */
    public boolean slientInstall(File file) {
        boolean result = false;
        Process process = null;
        OutputStream out = null;
        try {
            process = Runtime.getRuntime().exec("su");
            out = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            dataOutputStream.writeBytes("chmod 777 " + file.getPath() + "\n");
            dataOutputStream.writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " +
                    file.getPath());
            // 提交命令
            dataOutputStream.flush();
            // 关闭流操作
            dataOutputStream.close();
            out.close();
            int value = process.waitFor();

            // 代表成功
            if (value == 0) {
                result = true;
            } else if (value == 1) { // 失败
                result = false;
            } else { // 未知情况
                result = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        File file = new File("//storage//sdcard1/BaoDownload//myapplication-debug.apk");
        if (file.exists()){
            Toast.makeText(this, "+++", Toast.LENGTH_LONG).show();
            Toast.makeText(this, slientInstall(file) + "--", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "===", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onClick(View view) {
//        onClick(++index);
        File file = new File("//storage//sdcard1/BaoDownload//myapplication-debug.apk");
        if (file.exists()){
            Toast.makeText(this, "+++", Toast.LENGTH_LONG).show();
            Toast.makeText(this, slientInstall(file) + "--", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "===", Toast.LENGTH_LONG).show();

        }
    }

    public static final String string = "#!//bin//sh\n";
}
//adb shell am broadcast -a android.intent.action.BOOT_COMPLETED
//adb shell am broadcast -a calinks.toyota.location.LocationServiceReceiver