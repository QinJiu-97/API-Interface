package com.qinJiu.model.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author QinJiu
 * @Date 2022/11/25
 */
public enum ApiInterfaceReviewStatusEnum {

    OFFLINE(0, "下线"),
    ONLINE(1, "上线"),
    ERROR(2, "异常");

    int code;
    String value;

    ApiInterfaceReviewStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }


    public String getValue() {
        return value;
    }


    public static List<Integer> getValues() {
        List<Integer> codes = new ArrayList<>();
        ApiInterfaceReviewStatusEnum[] values = ApiInterfaceReviewStatusEnum.values();
        for (ApiInterfaceReviewStatusEnum apiInterfaceReviewStatusEnum : values) {
            codes.add(apiInterfaceReviewStatusEnum.getCode());
        }
        return codes;
    }
}
