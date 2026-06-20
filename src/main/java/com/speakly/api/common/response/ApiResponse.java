package com.speakly.api.common.response;


public record ApiResponse<T>(
        int code,
        T data,
        String msg
) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, data, "请求成功");
    }


    public static <T> ApiResponse<T> success(T data, String msg) {
        return new ApiResponse<>(200, data, msg);
    }

    public static <T> ApiResponse<T> error(String msg) {
        return new ApiResponse<>(500, null, msg);
    }

    public static <T> ApiResponse<T> unauthorized(String msg) {
        return new ApiResponse<>(401, null, msg);
    }
}
