package com.qinJiu.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qinJiu.model.entity.ApiInterface;
import com.qinJiu.project.common.ErrorCode;
import com.qinJiu.project.exception.BusinessException;
import com.qinJiu.project.mapper.ApiInterfaceMapper;

import com.qinJiu.project.service.ApiInterfaceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author QinJiu
 * @description 针对表【api_interface(接口信息表)】的数据库操作Service实现
 * @createDate 2022-11-25 15:51:16
 */
@Service
public class ApiInterfaceServiceImpl extends ServiceImpl<ApiInterfaceMapper, ApiInterface>
        implements ApiInterfaceService {

    @Override
    public void validApiInterface(ApiInterface apiInterface, boolean add) {

        if (apiInterface == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String apiName = apiInterface.getApiName();
        String userid = apiInterface.getUserid();
        String apiDescription = apiInterface.getApiDescription();
        String apiUrl = apiInterface.getApiUrl();
        String apiMethod = apiInterface.getApiMethod();
        String apiRequestHeader = apiInterface.getApiRequestHeader();
        String apiResponseHeader = apiInterface.getApiResponseHeader();


        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(apiName, userid, apiUrl, apiMethod, apiRequestHeader, apiResponseHeader)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(apiDescription) && apiDescription.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "描述内容过长");
        }
        if (StringUtils.isNotBlank(apiName) && apiName.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称内容过长");
        }


    }


}




