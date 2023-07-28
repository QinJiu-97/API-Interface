package com.qinJiu.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.qinJiu.ClientSDK.ClientSDKConfig;
import com.qinJiu.ClientSDK.client.ApiClient;
import com.qinJiu.model.entity.ApiInterface;
import com.qinJiu.model.entity.User;
import com.qinJiu.model.enums.ApiInterfaceReviewStatusEnum;
import com.qinJiu.project.annotation.AuthCheck;
import com.qinJiu.project.common.*;
import com.qinJiu.project.constant.CommonConstant;
import com.qinJiu.project.exception.BusinessException;
import com.qinJiu.project.model.dto.apiInterface.ApiInterfaceAddRequest;
import com.qinJiu.project.model.dto.apiInterface.ApiInterfaceInvokeRequest;
import com.qinJiu.project.model.dto.apiInterface.ApiInterfaceQueryRequest;
import com.qinJiu.project.model.dto.apiInterface.ApiInterfaceUpdateRequest;
import com.qinJiu.project.service.ApiInterfaceService;
import com.qinJiu.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * api接口信息 接口
 *
 * @author qinjiu
 */
@RestController
@RequestMapping("/apiInterface")
@Slf4j
public class InterfaceController {

    @Resource
    private ApiInterfaceService apiInterfaceService;

    @Resource
    private UserService userService;
    @Resource
    private ApiClient apiClient;

    // region 增删改查

    /**
     * 创建
     *
     * @param apiInterfaceAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addApiInterface(@RequestBody ApiInterfaceAddRequest apiInterfaceAddRequest, HttpServletRequest request) {
        if (apiInterfaceAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApiInterface apiInterface = new ApiInterface();
        BeanUtils.copyProperties(apiInterfaceAddRequest, apiInterface);
        String userAccount = userService.getLoginUser(request).getUserAccount();
        apiInterface.setUserid(userAccount);
        // 校验
        apiInterfaceService.validApiInterface(apiInterface, true);
        User loginUser = userService.getLoginUser(request);
        apiInterface.setUserid(loginUser.getUserAccount());
        boolean result = apiInterfaceService.save(apiInterface);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newApiInterfaceId = apiInterface.getId();
        return ResultUtils.success(newApiInterfaceId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteApiInterface(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        ApiInterface oldApiInterface = apiInterfaceService.getById(id);
        if (oldApiInterface == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!userService.isAdmin(request) && !oldApiInterface.getUserid().equals(user.getUserAccount())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = apiInterfaceService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param apiInterfaceUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateApiInterface(@RequestBody ApiInterfaceUpdateRequest apiInterfaceUpdateRequest,
                                                    HttpServletRequest request) {
        if (apiInterfaceUpdateRequest == null || apiInterfaceUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApiInterface apiInterface = new ApiInterface();
        BeanUtils.copyProperties(apiInterfaceUpdateRequest, apiInterface);
        // 参数校验
        apiInterfaceService.validApiInterface(apiInterface, false);
        User user = userService.getLoginUser(request);
        long id = apiInterfaceUpdateRequest.getId();
        // 判断是否存在
        ApiInterface oldApiInterface = apiInterfaceService.getById(id);
        if (oldApiInterface == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldApiInterface.getUserid().equals(user.getUserAccount()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = apiInterfaceService.updateById(apiInterface);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<ApiInterface> getApiInterfaceById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApiInterface apiInterface = apiInterfaceService.getById(id);
        return ResultUtils.success(apiInterface);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param apiInterfaceQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<ApiInterface>> listApiInterface(ApiInterfaceQueryRequest apiInterfaceQueryRequest) {
        ApiInterface apiInterfaceQuery = new ApiInterface();
        if (apiInterfaceQueryRequest != null) {
            BeanUtils.copyProperties(apiInterfaceQueryRequest, apiInterfaceQuery);
        }
        QueryWrapper<ApiInterface> queryWrapper = new QueryWrapper<>(apiInterfaceQuery);
        List<ApiInterface> apiInterfaceList = apiInterfaceService.list(queryWrapper);
        return ResultUtils.success(apiInterfaceList);
    }

    /**
     * 分页获取列表
     *
     * @param apiInterfaceQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<ApiInterface>> listApiInterfaceByPage(ApiInterfaceQueryRequest apiInterfaceQueryRequest, HttpServletRequest request) {
        if (apiInterfaceQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApiInterface apiInterfaceQuery = new ApiInterface();
        BeanUtils.copyProperties(apiInterfaceQueryRequest, apiInterfaceQuery);
        long current = apiInterfaceQueryRequest.getCurrent();
        long size = apiInterfaceQueryRequest.getPageSize();
        String sortField = apiInterfaceQueryRequest.getSortField();
        String sortOrder = apiInterfaceQueryRequest.getSortOrder();
        String apiDescription = apiInterfaceQuery.getApiDescription();
        // apiDescription 需支持模糊搜索
        apiInterfaceQuery.setApiDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<ApiInterface> queryWrapper = new QueryWrapper<>(apiInterfaceQuery);
        queryWrapper.like(StringUtils.isNotBlank(apiDescription), "api_description", apiDescription);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<ApiInterface> apiInterfacePage = apiInterfaceService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(apiInterfacePage);
    }

    // endregion

    /**
     * 接口上线
     *
     * @param idRequest
     * @param request
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @PostMapping("/online")
    public BaseResponse<Boolean> onlineApiInterface(@RequestBody idRequest idRequest,
                                                    HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断是否存在
        Long requestId = idRequest.getId();
        // 判断是否存在
        ApiInterface oldApiInterface = apiInterfaceService.getById(requestId);
        if (oldApiInterface == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }


        //---------
        ApiInterface apiInterface = new ApiInterface();
        apiInterface.setId(requestId);
        apiInterface.setApiStatus(ApiInterfaceReviewStatusEnum.ONLINE.getCode());
        boolean result = apiInterfaceService.updateById(apiInterface);
        return ResultUtils.success(result);
    }

    /**
     * 接口下线
     *
     * @param idRequest
     * @param request
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @PostMapping("/offline")
    public BaseResponse<Boolean> offlineApiInterface(@RequestBody idRequest idRequest,
                                                     HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断是否存在
        Long requestId = idRequest.getId();
        // 判断是否存在
        ApiInterface oldApiInterface = apiInterfaceService.getById(requestId);
        if (oldApiInterface == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }


        ApiInterface apiInterface = new ApiInterface();
        apiInterface.setId(requestId);
        apiInterface.setApiStatus(ApiInterfaceReviewStatusEnum.OFFLINE.getCode());
        boolean result = apiInterfaceService.updateById(apiInterface);
        return ResultUtils.success(result);
    }

    /**
     * 调用接口
     *
     * @param apiInterfaceInvokeRequest
     * @param request
     * @return
     */
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeApiInterface(@RequestBody ApiInterfaceInvokeRequest apiInterfaceInvokeRequest,
                                                   HttpServletRequest request) {
        if (apiInterfaceInvokeRequest == null || apiInterfaceInvokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断是否存在
        Long requestId = apiInterfaceInvokeRequest.getId();
        String userRequestParams = apiInterfaceInvokeRequest.getUserRequestParams();
        ApiInterface oldApiInterface = apiInterfaceService.getById(requestId);
        if (oldApiInterface == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        if (oldApiInterface.getApiStatus() != ApiInterfaceReviewStatusEnum.ONLINE.getCode()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "接口异常");

        }


        Gson gson = new Gson();
        com.qinJiu.ClientSDK.model.User user = gson.fromJson(userRequestParams, com.qinJiu.ClientSDK.model.User.class);
        String userNameByPost = apiClient.getUserNameByPost(user);

        return ResultUtils.success(userNameByPost);
    }

}
