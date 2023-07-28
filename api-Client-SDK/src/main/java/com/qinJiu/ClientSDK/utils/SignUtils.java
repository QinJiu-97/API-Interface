package com.qinJiu.ClientSDK.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author QinJiu
 * @Date 2022/11/27
 * <p>
 * 签名工具
 */
public class SignUtils {

    public static String genSign(String accessKey,String body, String secretKey) {
        Map<String, String> params = new HashMap<>();
        params.put("accessKey", accessKey);
        params.put("body", body);
        return calculateSignature(params,secretKey);
    }

    /**
     * 计算API签名
     *
     * @param params 请求参数
     * @param secretKey 密钥
     * @return 签名字符串
     */
    public static String calculateSignature(Map<String, String> params, String secretKey) {
        // 对参数按照ASCII码从小到大排序
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        // 拼接排序后的参数
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String value = params.get(key);
            sb.append(key).append("=").append(value).append("&");
        }
        sb.append("secretKey=").append(secretKey);

        // 对拼接后的字符串进行MD5加密
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        byte[] digest = md5.digest(sb.toString().getBytes(StandardCharsets.UTF_8));
        return byte2hex(digest);
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param b 字节数组
     * @return 十六进制字符串
     */
    public static String byte2hex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (byte value : b) {
            tmp = Integer.toHexString(value & 0xFF);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString();
    }

}















