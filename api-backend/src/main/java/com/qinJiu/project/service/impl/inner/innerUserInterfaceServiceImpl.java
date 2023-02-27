package com.qinJiu.project.service.impl.inner;

import com.qinJiu.project.service.UserInterfaceService;
import com.qinJiu.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author QinJiu
 * @Date 2023/2/15
 */
@DubboService
public class innerUserInterfaceServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceService userInterfaceService;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceService.invokeCount(interfaceInfoId, String.valueOf(userId));
    }
}
