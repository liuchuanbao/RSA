package com.example.rsa;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

public class REStestActivity extends Activity implements View.OnClickListener {
    private Button btn1, btn2;// 加密，解密
    private EditText et1, et2, et3;// 需加密的内容，加密后的内容，解密后的内容

    private static String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXq6N83mz+K5Jrvp6va7n5BvD466Sxy1kweUbe0O/Nh0wmfTXX68Sz4dUTCIjIiruIan6y5NJZtgwSetamRG5Oc2X2n2oaIVir2m7ciqGw0FGwipp1iH4TX7l3N4gQmgsNl9j76fvhq049zf8e4+s3anXsAvgY6nvFOFRRINHY+wIDAQAB";
    private static String PRIVATEKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJero3zebP4rkmu+nq9rufkG8PjrpLHLWTB5Rt7Q782HTCZ9NdfrxLPh1RMIiMiKu4hqfrLk0lm2DBJ61qZEbk5zZfafahohWKvabtyKobDQUbCKmnWIfhNfuXc3iBCaCw2X2Pvp++GrTj3N/x7j6zdqdewC+Bjqe8U4VFEg0dj7AgMBAAECgYAzhAT/wLzciAgvuItFoh2EzCrFIaTLDvq4UDkWLXmGIdJnsFe9g0NIpggtctSi6RxRdXqbYMVh20e2byrBRrUAP3KJd+rUJplDk/EHroLVGcIYBX0GDqYuQ/nx2vs7/XuICMKGuT6Mo9QwBOS+Km4+sOX0W0sNtRtW1I8HtgIYQQJBAPVgaU2hBdRn+fmlohKdouEwSjK6qeToPqnj4UJ+Deq55nR7sjqPmB64OcokGoKloYZ+fKmCHr6wLRiOffrHTgMCQQCePKbSfr1XOnTpbWnDkWBcztoidD+dARi43jaJ7zQJWRgADTRjmO7xvAPRkRpg2mNvtmuEV1/hE4qxWNfrvHOpAkEAz5OE4Z/zf5GKPa/p4JesH5YrXqjcaoIx6KSXfhmHCmfDVg0CZFnvVSWB9cf/CUC22UENkpQ6EBSXwathVZHfIwJACXjR95m0lcsfAnYVNaq3HPcY4aUZxbkyFKbgluMlt0WJBT/FGg0miHvbsqi/7npEJ4TA7NwaFiwISlNqIWdXeQJAJAirBot0hMWkxHoaK4Xiw5pQN/5gNDiYUwqYr4pRSno2LIAN6WvIuYwIL94kstK9wx4UMU0xkPxu8rO2p8VpFw==";

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXq6N83mz+K5Jrvp6va7n5BvD466Sxy1kweUbe0O/Nh0wmfTXX68Sz4dUTCIjIiruIan6y5NJZtgwSetamRG5Oc2X2n2oaIVir2m7ciqGw0FGwipp1iH4TX7l3N4gQmgsNl9j76fvhq049zf8e4+s3anXsAvgY6nvFOFRRINHY+wIDAQAB";
    private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJero3zebP4rkmu+nq9rufkG8PjrpLHLWTB5Rt7Q782HTCZ9NdfrxLPh1RMIiMiKu4hqfrLk0lm2DBJ61qZEbk5zZfafahohWKvabtyKobDQUbCKmnWIfhNfuXc3iBCaCw2X2Pvp++GrTj3N/x7j6zdqdewC+Bjqe8U4VFEg0dj7AgMBAAECgYAzhAT/wLzciAgvuItFoh2EzCrFIaTLDvq4UDkWLXmGIdJnsFe9g0NIpggtctSi6RxRdXqbYMVh20e2byrBRrUAP3KJd+rUJplDk/EHroLVGcIYBX0GDqYuQ/nx2vs7/XuICMKGuT6Mo9QwBOS+Km4+sOX0W0sNtRtW1I8HtgIYQQJBAPVgaU2hBdRn+fmlohKdouEwSjK6qeToPqnj4UJ+Deq55nR7sjqPmB64OcokGoKloYZ+fKmCHr6wLRiOffrHTgMCQQCePKbSfr1XOnTpbWnDkWBcztoidD+dARi43jaJ7zQJWRgADTRjmO7xvAPRkRpg2mNvtmuEV1/hE4qxWNfrvHOpAkEAz5OE4Z/zf5GKPa/p4JesH5YrXqjcaoIx6KSXfhmHCmfDVg0CZFnvVSWB9cf/CUC22UENkpQ6EBSXwathVZHfIwJACXjR95m0lcsfAnYVNaq3HPcY4aUZxbkyFKbgluMlt0WJBT/FGg0miHvbsqi/7npEJ4TA7NwaFiwISlNqIWdXeQJAJAirBot0hMWkxHoaK4Xiw5pQN/5gNDiYUwqYr4pRSno2LIAN6WvIuYwIL94kstK9wx4UMU0xkPxu8rO2p8VpFw==";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView()
    {
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            // 加密
            case R.id.btn1:

                break;
            // 解密
            case R.id.btn2:
                testfile();
                break;
            default:
                break;
        }
    }
    public  void test() {
        try {
            System.err.println("公钥加密——私钥解密");
            String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
            System.out.println("\r加密前文字：\r\n" + source);
            byte[] data = source.getBytes();
            byte[] encodedData = RSAUtilss.encryptByPublicKey(data, publicKey);
            System.out.println("加密后文字：\r\n" + new String(encodedData));
            byte[] decodedData = RSAUtilss.decryptByPrivateKey(encodedData, privateKey);
            String target = new String(decodedData);
            System.out.println("解密后文字: \r\n" + target);
            Log.e("System", "test: 解密成功   =" +target);
        }catch (Exception e){
            Log.e("System", "test: 解密失败" );
            e.printStackTrace();
        }
    }
    public void testfile() {
    	/*System.err.println("公钥加密——私钥解密");
    	String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
    	System.out.println("\r加密前文字：\r\n" + source);
    	byte[] data = source.getBytes();


    	File file = new File("C:/Users/y/Desktop/armeabi/libSDC.head");// 文件
//    	File file = new File("C:/Users/y/Desktop/armeabi/libIAC.head");// 文件
		FileInputStream in = new FileInputStream(file);// 文件输入流
		ByteArrayOutputStream bout = new ByteArrayOutputStream();// 字节输出流
		byte[] tmpbuf = new byte[1024];// 缓存大小
		// 读取文件
		int count = 0;
		while ((count = in.read(tmpbuf)) != -1) {
			bout.write(tmpbuf, 0, count);
			tmpbuf = new byte[1024];
		}
		in.close();
		// 把输出流内的文件转为字节数组
		byte[] data = bout.toByteArray();
		*/
          try {
              long startTime = System.currentTimeMillis();   //获取开始时间
//		byte[] data = Base64Utilss.fileToByte("C:/Users/y/Desktop/armeabi/1.txt");
////              byte[] data = Base64Utilss.fileToByte("C:/Users/y/Desktop/armeabi/libsdcbarcodereader.tail");
//
//              System.out.println("原长度: \r\n" + data.length);
//              System.out.println(byte2hex(data));
//              byte[] encodedData = RSAUtilss.encryptByPrivateKey(data, privateKey);
//              System.out.println("加密后长度: \r\n" + encodedData.length);
////    	System.out.println("加密后文字：\r\n" + new String(encodedData));
//              long endTime = System.currentTimeMillis(); //获取结束时间
//
//              File file = new File("C:/Users/y/Desktop/armeabi/temp.dat");
//              OutputStream out = new FileOutputStream(file);
//              out.write(encodedData);
//              out.close();


//              byte[] decodedData = RSAUtilss.decryptByPublicKey(encodedData, publicKey);
              //获取资源文件里的数据
              byte[] b1 = getBytes();
              byte[] decodedData = RSAUtilss.decryptByPublicKey(b1, publicKey);
              long endTime = System.currentTimeMillis(); //获取结束时间
              System.out.println("解密后长度: \r\n" + decodedData.length);
    	String target = new String(decodedData);
    	System.out.println("解密后文字: \r\n" + target);
              Log.e("System", "testfile: 解密成功  ="+ target);

              System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
          }catch (Exception e){
              Log.e("System", "testfile: 解密失败" );
            e.printStackTrace();
        }
    }
    private static String byte2hex(byte [] buffer){
        String h = "";

        for(int i = 0; i < buffer.length; i++){
            String temp = Integer.toHexString(buffer[i] & 0xFF);
            if(temp.length() == 1){
                temp = "0" + temp;
            }
            h = h + " "+ temp;
        }

        return h;

    }
    static void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtilss.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtilss.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = RSAUtilss.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSAUtilss.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
    }
    /**
     * 基于位移的int转化成byte[]
     *
     * @param  number
     * @return byte[]
     */

    public static byte[] intToByte(int number) {
        byte[] abyte = new byte[4];
        // "&" 与（AND），对两个整型操作数中对应位执行布尔代数，两个位都为1时输出1，否则0。
        abyte[0] = (byte) (0xff & number);
        // ">>"右移位，若为正数则高位补0，若为负数则高位补1
        abyte[1] = (byte) ((0xff00 & number) >> 8);
        abyte[2] = (byte) ((0xff0000 & number) >> 16);
        abyte[3] = (byte) ((0xff000000 & number) >> 24);
        return abyte;
    }

    /**
     * 基于位移的 byte[]转化成int
     *
     * @param  bytes
     * @return int number
     */

    public static int bytesToInt(byte[] bytes) {
        int number = bytes[0] & 0xFF;
        // "|="按位或赋值。
        number |= ((bytes[1] << 8) & 0xFF00);
        number |= ((bytes[2] << 16) & 0xFF0000);
        number |= ((bytes[3] << 24) & 0xFF000000);
        return number;
    }

    /**
     * 基于arraycopy合并两个byte[] 数组
     *
     * @param  bytes1
     * @param  bytes2
     * @return byte[] bytes3
     */
    public static byte[] combineTowBytes(byte[] bytes1, byte[] bytes2) {
        byte[] bytes3 = new byte[bytes1.length + bytes2.length];
        System.arraycopy(bytes1, 0, bytes3, 0, bytes1.length);
        System.arraycopy(bytes2, 0, bytes3, bytes1.length, bytes2.length);
        return bytes3;
    }

    public static byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
        return buffer;
    }

    /**
     * 获得指定文件的byte数组
     * String filePath
     */
    private byte[] getBytes(){
        byte[] buffer = null;
        try {
//			File file = new File(filePath);
//			FileInputStream fis = new FileInputStream(file);
            InputStream inPublic = getResources().getAssets().open("temp1.dat");
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = inPublic.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            inPublic.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static void getFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath+"\\"+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
