package com.qinJiu.project.model.dto.apiInterface;

import lombok.Data;

import java.io.Serializable;

/**
 * @author QinJiu
 * @Date 2022/11/25
 */
@Data
public class ApiInterfaceInvokeRequest implements Serializable {
    /**
     * id
     *
     */
    private Long id;


    /**
     * 请求参数
     */
    private String userRequestParams;


}
