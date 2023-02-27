package com.qinJiu.project.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qinJiu.model.entity.UserInterface;
import com.qinJiu.project.common.ErrorCode;
import com.qinJiu.project.exception.BusinessException;
import com.qinJiu.project.mapper.UserInterfaceMapper;
import com.qinJiu.project.service.UserInterfaceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author QinJiu
* @description 针对表【user_interface(用户接口关系表)】的数据库操作Service实现
* @createDate 2022-11-28 16:19:25
*/
@Service
public class UserInterfaceServiceImpl extends ServiceImpl<UserInterfaceMapper, UserInterface>
    implements UserInterfaceService {

    @Override
    public void validUserInterface(UserInterface userInterface, boolean add) {


        if (userInterface == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long interfaceId = userInterface.getInterfaceId();
        String userAccount = userInterface.getUserAccount();
        Integer totalNum = userInterface.getTotalNum();
        Integer remainNum = userInterface.getRemainNum();




        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(userAccount) || interfaceId < 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户或接口不存在");
            }
        }
        if (remainNum < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"剩余次数不能小于零");

        }

    }

    @Override
    public synchronized boolean invokeCount(Long interfaceId, String userAccount) {
        //判断
        if (interfaceId < 0 || StringUtils.isBlank(userAccount)){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        UpdateWrapper<UserInterface> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceId",interfaceId)
                .eq("userAccount",userAccount)
                .gt("remainNum",0);
        updateWrapper.setSql("remainNum = remainNum - 1, totalNum = totalNum + 1");
        return this.update(updateWrapper);

    }
}




