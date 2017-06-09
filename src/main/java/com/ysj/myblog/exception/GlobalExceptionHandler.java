package com.ysj.myblog.exception;

import com.ysj.myblog.entity.ArgumentInvalidResult;
import com.ysj.myblog.entity.ResultObject;
import com.ysj.myblog.entity.ResultStatusCode;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yusijia
 * @Description:
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // 处理验证参数失败的异常
    @ExceptionHandler(value=BindException.class)
    @ResponseBody
    public Object MethodArgumentNotValidHandler(HttpServletRequest request,
                                              HttpServletResponse response,
                                              BindingResult bindingResult) throws Exception
    {
        //按需重新封装需要返回的错误信息
        List<ArgumentInvalidResult> invalidArguments = new ArrayList<>();
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (ObjectError error : bindingResult.getAllErrors()) {
            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
            invalidArgument.setDefaultMessage(error.getDefaultMessage());
            invalidArgument.setField(((FieldError)error).getField());
            invalidArgument.setRejectedValue(((FieldError) error).getRejectedValue());
            invalidArguments.add(invalidArgument);
        }

        ResultObject resultObject = new ResultObject(ResultStatusCode.PARAMETER_ERROR.getCode(), ResultStatusCode.PARAMETER_ERROR.getMessage(), invalidArguments);
        return resultObject;
    }


}
