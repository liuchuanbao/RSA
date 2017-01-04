package com.example.rsa;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener
{
	private Button btn1, btn2,btn_click,btn_click2;// 加密，解密
	private EditText et1, et2, et3;// 需加密的内容，加密后的内容，解密后的内容

	/* 密钥内容 base64 code */
	private static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfRTdcPIH10gT9f31rQuIInLwe" +
			"7fl2dtEJ93gTmjE9c2H+kLVENWgECiJVQ5sonQNfwToMKdO0b3Olf4pgBKeLThra" +
			"z/L3nYJYlbqjHC3jTjUnZc0luumpXGsox62+PuSGBlfb8zJO6hix4GV/vhyQVCpG" +
			"9aYqgE7zyTRZYX9byQIDAQAB";
	private static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ9FN1w8gfXSBP1/" +
			"fWtC4gicvB7t+XZ20Qn3eBOaMT1zYf6QtUQ1aAQKIlVDmyidA1/BOgwp07Rvc6V/" +
			"imAEp4tOGtrP8vedgliVuqMcLeNONSdlzSW66alcayjHrb4+5IYGV9vzMk7qGLHg" +
			"ZX++HJBUKkb1piqATvPJNFlhf1vJAgMBAAECgYA736xhG0oL3EkN9yhx8zG/5RP/" +
			"WJzoQOByq7pTPCr4m/Ch30qVerJAmoKvpPumN+h1zdEBk5PHiAJkm96sG/PTndEf" +
			"kZrAJ2hwSBqptcABYk6ED70gRTQ1S53tyQXIOSjRBcugY/21qeswS3nMyq3xDEPK" +
			"XpdyKPeaTyuK86AEkQJBAM1M7p1lfzEKjNw17SDMLnca/8pBcA0EEcyvtaQpRvaL" +
			"n61eQQnnPdpvHamkRBcOvgCAkfwa1uboru0QdXii/gUCQQDGmkP+KJPX9JVCrbRt" +
			"7wKyIemyNM+J6y1ZBZ2bVCf9jacCQaSkIWnIR1S9UM+1CFE30So2CA0CfCDmQy+y" +
			"7A31AkB8cGFB7j+GTkrLP7SX6KtRboAU7E0q1oijdO24r3xf/Imw4Cy0AAIx4KAu" +
			"L29GOp1YWJYkJXCVTfyZnRxXHxSxAkEAvO0zkSv4uI8rDmtAIPQllF8+eRBT/deD" +
			"JBR7ga/k+wctwK/Bd4Fxp9xzeETP0l8/I+IOTagK+Dos8d8oGQUFoQJBAI4Nwpfo" +
			"MFaLJXGY9ok45wXrcqkJgM+SN6i8hQeujXESVHYatAIL/1DgLi+u46EFD69fw0w+" +
			"c7o0HLlMsYPAzJw=";
	private static String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXq6N83mz+K5Jrvp6va7n5BvD466Sxy1kweUbe0O/Nh0wmfTXX68Sz4dUTCIjIiruIan6y5NJZtgwSetamRG5Oc2X2n2oaIVir2m7ciqGw0FGwipp1iH4TX7l3N4gQmgsNl9j76fvhq049zf8e4+s3anXsAvgY6nvFOFRRINHY+wIDAQAB";
	private static String PRIVATEKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJero3zebP4rkmu+nq9rufkG8PjrpLHLWTB5Rt7Q782HTCZ9NdfrxLPh1RMIiMiKu4hqfrLk0lm2DBJ61qZEbk5zZfafahohWKvabtyKobDQUbCKmnWIfhNfuXc3iBCaCw2X2Pvp++GrTj3N/x7j6zdqdewC+Bjqe8U4VFEg0dj7AgMBAAECgYAzhAT/wLzciAgvuItFoh2EzCrFIaTLDvq4UDkWLXmGIdJnsFe9g0NIpggtctSi6RxRdXqbYMVh20e2byrBRrUAP3KJd+rUJplDk/EHroLVGcIYBX0GDqYuQ/nx2vs7/XuICMKGuT6Mo9QwBOS+Km4+sOX0W0sNtRtW1I8HtgIYQQJBAPVgaU2hBdRn+fmlohKdouEwSjK6qeToPqnj4UJ+Deq55nR7sjqPmB64OcokGoKloYZ+fKmCHr6wLRiOffrHTgMCQQCePKbSfr1XOnTpbWnDkWBcztoidD+dARi43jaJ7zQJWRgADTRjmO7xvAPRkRpg2mNvtmuEV1/hE4qxWNfrvHOpAkEAz5OE4Z/zf5GKPa/p4JesH5YrXqjcaoIx6KSXfhmHCmfDVg0CZFnvVSWB9cf/CUC22UENkpQ6EBSXwathVZHfIwJACXjR95m0lcsfAnYVNaq3HPcY4aUZxbkyFKbgluMlt0WJBT/FGg0miHvbsqi/7npEJ4TA7NwaFiwISlNqIWdXeQJAJAirBot0hMWkxHoaK4Xiw5pQN/5gNDiYUwqYr4pRSno2LIAN6WvIuYwIL94kstK9wx4UMU0xkPxu8rO2p8VpFw==";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
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
		btn_click = (Button) findViewById(R.id.btn_click);
		btn_click.setOnClickListener(this);
		btn_click2 = (Button) findViewById(R.id.btn_click2);
		btn_click2.setOnClickListener(this);
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
			String source = et1.getText().toString().trim();
			try
			{
				// 从字符串中得到公钥
				 PublicKey publicKey = RSAUtils.loadPublicKey(PRIVATEKEY);
				// 从文件中得到公钥
//				InputStream inPublic = getResources().getAssets().open("rsa_public_key.pem");
//				PublicKey publicKey = RSAUtils.loadPublicKey(inPublic);
				// 加密
				byte[] encryptByte = RSAUtils.encryptData(source.getBytes(), publicKey);
				// 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
				String afterencrypt = Base64Utils.encode(encryptByte);
				et2.setText(afterencrypt);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			break;
		// 解密
		case R.id.btn2:
			String encryptContent = et2.getText().toString().trim();
			try
			{
				// 从字符串中得到私钥
				 PrivateKey privateKey = RSAUtils.loadPrivateKey(PUBLICKEY);
				// 从文件中得到私钥
//				InputStream inPrivate = getResources().getAssets().open("pkcs8_rsa_private_key.pem");
//				PrivateKey privateKey = RSAUtils.loadPrivateKey(inPrivate);
				// 因为RSA加密后的内容经Base64再加密转换了一下，所以先Base64解密回来再给RSA解密

				byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(encryptContent), privateKey);
				//解密资源文件里的加密资源
//				byte[] b1 = getBytes();
//				byte[] decryptByte = RSAUtils.decryptData(b1, privateKey);

				String decryptStr = new String(decryptByte);
				et3.setText(decryptStr);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			break;
			case R.id.btn_click:
				startActivity(new Intent(MainActivity.this,REStestActivity.class));
				break;
			case R.id.btn_click2:
				startActivity(new Intent(MainActivity.this,GRpctestActivity.class));
				break;
		default:
			break;
		}
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

}
