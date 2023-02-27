package com.qinJiu.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qinJiu.model.entity.User;
import com.qinJiu.model.entity.UserInterface;
import com.qinJiu.project.annotation.AuthCheck;
import com.qinJiu.project.common.BaseResponse;
import com.qinJiu.project.common.DeleteRequest;
import com.qinJiu.project.common.ErrorCode;
import com.qinJiu.project.common.ResultUtils;
import com.qinJiu.project.constant.CommonConstant;
import com.qinJiu.project.constant.UserConstant;
import com.qinJiu.project.exception.BusinessException;
import com.qinJiu.project.model.dto.userApiInterface.UserApiInterfaceAddRequest;
import com.qinJiu.project.model.dto.userApiInterface.UserApiInterfaceQueryRequest;
import com.qinJiu.project.model.dto.userApiInterface.UserApiInterfaceUpdateRequest;
import com.qinJiu.project.service.UserInterfaceService;
import com.qinJiu.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户接口关系 接口
 *
 * @author qinjiu
 */
@RestController
@RequestMapping("/userInterface")
@Slf4j
@AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
public class UserInterfaceController {

    @Resource
    private UserInterfaceService userInterfaceService;

    @Resource
    private UserService userService;


    // region 增删改查

    /**
     * 创建
     *
     * @param userInterfaceAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")

    public BaseResponse<Long> addUserInterface(@RequestBody UserApiInterfaceAddRequest userInterfaceAddRequest, HttpServletRequest request) {
        if (userInterfaceAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterface userInterface = new UserInterface();
        BeanUtils.copyProperties(userInterfaceAddRequest, userInterface);

        // 校验
        userInterfaceService.validUserInterface(userInterface, true);
        User loginUser = userService.getLoginUser(request);
        userInterface.setUserAccount(loginUser.getUserAccount());
        boolean result = userInterfaceService.save(userInterface);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newUserInterfaceId = userInterface.getId();
        return ResultUtils.success(newUserInterfaceId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUserInterface(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        UserInterface oldUserInterface = userInterfaceService.getById(id);
        if (oldUserInterface == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!userService.isAdmin(request) && !oldUserInterface.getUserAccount().equals(user.getUserAccount())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = userInterfaceService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param userInterfaceUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUserInterface(@RequestBody UserApiInterfaceUpdateRequest userInterfaceUpdateRequest,
                                                    HttpServletRequest request) {
        if (userInterfaceUpdateRequest == null || userInterfaceUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterface userInterface = new UserInterface();
        BeanUtils.copyProperties(userInterfaceUpdateRequest, userInterface);
        // 参数校验
        userInterfaceService.validUserInterface(userInterface, false);
        User user = userService.getLoginUser(request);
        long id = userInterfaceUpdateRequest.getId();
        // 判断是否存在
        UserInterface oldUserInterface = userInterfaceService.getById(id);
        if (oldUserInterface == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldUserInterface.getUserAccount().equals(user.getUserAccount()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = userInterfaceService.updateById(userInterface);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<UserInterface> getUserInterfaceById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterface userInterface = userInterfaceService.getById(id);
        return ResultUtils.success(userInterface);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param userInterfaceQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<UserInterface>> listUserInterface(UserApiInterfaceQueryRequest userInterfaceQueryRequest) {
        UserInterface userInterfaceQuery = new UserInterface();
        if (userInterfaceQueryRequest != null) {
            BeanUtils.copyProperties(userInterfaceQueryRequest, userInterfaceQuery);
        }
        QueryWrapper<UserInterface> queryWrapper = new QueryWrapper<>(userInterfaceQuery);
        List<UserInterface> userInterfaceList = userInterfaceService.list(queryWrapper);
        return ResultUtils.success(userInterfaceList);
    }

    /**
     * 分页获取列表
     *
     * @param userInterfaceQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<UserInterface>> listUserInterfaceByPage(UserApiInterfaceQueryRequest userInterfaceQueryRequest, HttpServletRequest request) {
        if (userInterfaceQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterface userInterfaceQuery = new UserInterface();
        BeanUtils.copyProperties(userInterfaceQueryRequest, userInterfaceQuery);
        long current = userInterfaceQueryRequest.getCurrent();
        long size = userInterfaceQueryRequest.getPageSize();
        String sortField = userInterfaceQueryRequest.getSortField();
        String sortOrder = userInterfaceQueryRequest.getSortOrder();
        // apiDescription 需支持模糊搜索
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserInterface> queryWrapper = new QueryWrapper<>(userInterfaceQuery);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<UserInterface> userInterfacePage = userInterfaceService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(userInterfacePage);
    }

    // endregion


}
