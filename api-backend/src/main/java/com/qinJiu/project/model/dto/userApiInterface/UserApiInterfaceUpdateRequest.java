package com.qinJiu.project.model.dto.userApiInterface;

import lombok.Data;

/**
 * @author QinJiu
 * @Date 2022/11/25
 */
@Data
public class UserApiInterfaceUpdateRequest {

    /**
     * 主键
     */

    private Long id;


    /**
     * 总调用次数
     */

    private Integer totalNum;

    /**
     * 剩余调用次数
     */

    private Integer remainNum;

    /**
     * 接口状态(0-关闭,1-开启)
     */

    private Integer status;




}
