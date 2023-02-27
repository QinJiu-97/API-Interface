package com.qinJiu.project.model.dto.apiInterface;

import lombok.Data;

/**
 * @author QinJiu
 * @Date 2022/11/25
 */
@Data
public class ApiInterfaceUpdateRequest {

    /**
     * id
     */
    private Long id;

    /**
     * 接口名字
     */
    private String apiName;

    /**
     * 创建人Id
     */
    private String userid;

    /**
     * 接口描述
     */
    private String apiDescription;

    /**
     * 接口地址
     */
    private String apiUrl;


    /**
     * 请求参数
     */
    private String apiRequestParams;
    /**
     * 请求类型
     */
    private String apiMethod;

    /**
     * 接口请求头
     */
    private String apiRequestHeader;

    /**
     * 接口响应头
     */
    private String apiResponseHeader;

    /**
     * 接口状态(0-关闭,1-开启)
     */
    private Integer apiStatus;


}
