package net.ftzcode.core;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    public static final int ERROR_DATA_FORMAT=100400;
    public static final int ERROR_VALIDATE_CODE=100401;
    public static final int ERROR_FORMATE_PWD=100402;
    public static final int ERROR_VALIDATE_PWD=100403;
    public static final int ERROR_UNKNOWN_RESULT=100404;
    public static final int ERROR_LOW_POWER=100405;
    public static final int ERROR_FORMAT_NAME=100406;
    
	
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }
    
    
    
    
	public Result setResult(int flag) {
        String err = "未知错误";
        switch (flag) {
            case ERROR_DATA_FORMAT:
                err = "数据格式不合法，请提交正确的数据";
                break;
            case ERROR_VALIDATE_CODE:
                err = "验证码为空或不正确";
                break;
            case ERROR_VALIDATE_PWD:
                err = "用户名或者密码错误";
                break;
            case ERROR_UNKNOWN_RESULT:
                err = "未查询到指定结果，请确认参数的正确性";
                break;
            case ERROR_LOW_POWER:
                err = "权限级别不够，无法访问";
                break;

        }
        return genFailResult(err);
    }
}
