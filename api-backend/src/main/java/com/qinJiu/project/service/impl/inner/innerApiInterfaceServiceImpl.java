package com.qinJiu.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qinJiu.model.entity.ApiInterface;
import com.qinJiu.project.common.ErrorCode;
import com.qinJiu.project.exception.BusinessException;
import com.qinJiu.project.mapper.ApiInterfaceMapper;
import com.qinJiu.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author QinJiu
 * @Date 2023/2/15
 */
@DubboService
public class innerApiInterfaceServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private ApiInterfaceMapper apiInterfaceMapper;


    @Override
    public ApiInterface getInterfaceInfo(String url, String method) {
        if (StringUtils.isAnyBlank(url, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<ApiInterface> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("api_url", url);
        queryWrapper.eq("api_method", method);
        return apiInterfaceMapper.selectOne(queryWrapper);
    }
}
