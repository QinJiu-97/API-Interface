package com.qinJiu.project.model.dto.apiInterface;

import lombok.Data;

/**
 * @author QinJiu
 * @Date 2022/11/25
 */
@Data
public class ApiInterfaceAddRequest {
    /**
     * 接口名字
     */
    private String apiName;


    /**
     * 接口描述
     */
    private String apiDescription;

    /**
     * 接口地址
     */
    private String apiUrl;

    /**
     * 请求类型
     */
    private String apiMethod;

    /**
     * 请求参数
     */
    private String apiRequestParams;

    /**
     * 接口请求头
     */
    private String apiRequestHeader;

    /**
     * 接口响应头
     */
    private String apiResponseHeader;


}
