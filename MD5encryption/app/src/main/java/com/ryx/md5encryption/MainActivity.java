package com.ryx.md5encryption;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.TextView;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class MainActivity extends AppCompatActivity {
    private String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCt9MS+bXUO6IftXgrjJJQ78jDFuyvFsDbNlnHX\n" +
            "QTfEigXpQkIxlL0y8xRCczZf9bQfs89ow4XVtDo3s3tjMRyhtCUWZMkLQwiEPkMPddO076Qa+Bea\n" +
            "PyBAhx7DHM8/v5+hozODvXqbVj3cx9pwGBH/2rAqj1YaipjPJ/bb+FISMwIDAQAB";

    String encryPsw ;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        try {
            encryPsw = encrypt("136861");
            tv.setText(encryPsw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
 * 函数说明：getPublicKey 取得公钥
 * @param key 公钥字符串
 * @return PublicKey 返回公钥
 * @throws Exception
 */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decode(key, Base64.DEFAULT);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public String encrypt(String source) throws Exception {
        Key publicKey;
        publicKey = getPublicKey(PUBLIC_KEY);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] b = source.getBytes();
        /** 执行加密操作 */
        byte[] b1 = cipher.doFinal(b);
        return Base64.encodeToString(b1, Base64.DEFAULT);
    }
}
