package com.qinJiu.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口信息表
 *
 * @TableName api_interface
 */
@TableName(value = "api_interface")
@Data
public class ApiInterface implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接口名字
     */
    @TableField(value = "api_name")
    private String apiName;

    /**
     * 创建人Id
     */
    @TableField(value = "userId")
    private String userid;

    /**
     * 接口描述
     */
    @TableField(value = "api_description")
    private String apiDescription;

    /**
     * 接口地址
     */
    @TableField(value = "api_url")
    private String apiUrl;

    /**
     * 请求类型
     */
    @TableField(value = "api_method")
    private String apiMethod;

    /**
     * 请求参数
     */
    @TableField(value = "api_request_params")
    private String apiRequestParams;

    /**
     * 接口请求头
     */
    @TableField(value = "api_request_header")
    private String apiRequestHeader;

    /**
     * 接口响应头
     */
    @TableField(value = "api_response_header")
    private String apiResponseHeader;

    /**
     * 接口状态(0-关闭,1-开启)
     */
    @TableField(value = "api_status")
    private Integer apiStatus;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableLogic
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}