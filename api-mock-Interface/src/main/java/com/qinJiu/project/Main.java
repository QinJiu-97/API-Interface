package com.qinJiu.project;

import com.qinJiu.ClientSDK.client.ApiClient;
import com.qinJiu.ClientSDK.model.User;

import javax.annotation.Resource;

/**
 * @author QinJiu
 * @Date 2022/11/26
 */
public class Main {
    @Resource
    static
    ApiClient apiClient;

    public static void main(String[] args) {

        String res1 = apiClient.getNameByGet("琴酒");
        String res2 = apiClient.getNameByPost("琴酒");
        User user = new User();
        user.setName("qinjiujiu");
        String res3 = apiClient.getUserNameByPost(user);
        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }
}