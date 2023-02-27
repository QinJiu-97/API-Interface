package com.qinJiu.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qinJiu.model.entity.ApiInterface;

/**
* @author QinJiu
* @description 针对表【api_interface(接口信息表)】的数据库操作Service
* @createDate 2022-11-25 15:51:16
*/
public interface ApiInterfaceService extends IService<ApiInterface> {

    /**
     * 校验
     *
     * @param apiInterface
     * @param add 是否为创建校验
     */
    void validApiInterface(ApiInterface apiInterface, boolean add);

}
