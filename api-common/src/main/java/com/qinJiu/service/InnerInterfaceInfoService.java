package com.qinJiu.service;

import com.qinJiu.model.entity.ApiInterface;

/**
 *
 */
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     */
    ApiInterface getInterfaceInfo(String path, String method);
}
