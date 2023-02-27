package com.qinJiu.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qinJiu.model.entity.UserInterface;

/**
* @author QinJiu
* @description 针对表【user_interface(用户接口关系表)】的数据库操作Service
* @createDate 2022-11-28 16:19:25
*/
public interface UserInterfaceService extends IService<UserInterface> {

    void validUserInterface(UserInterface userInterface, boolean add);

    boolean invokeCount(Long interfaceId, String userAccount);


}
