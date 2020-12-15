package com.baomidou.mybatisplus.samples.crud.config;

import com.cngc.boot.core.CngcResourceBundleMessageSource;
import com.cngc.boot.web.constant.WebMessageConstants;
import com.cngc.boot.web.exception.RequestBodyValidationException;
import com.cngc.boot.web.exception.ResourceNotFoundException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 处理内置异常.
 *
 * @author maxD
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ServletExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ServletExceptionHandler.class);

    @Autowired
    private CngcResourceBundleMessageSource cngcWebMessageSource;

    /**
     * 处理request param类型转换异常.
     * controller方法参数@RequestParam当发生类型转换异常时,不会触发@Validated校验,对其throws的异常MethodArgumentTypeMismatchException
     * 进行处理.
     * 与触发了@RequestParam参数校验的异常不同,方法参数类型匹配异常会在第一个异常发生时就会抛出.
     * 参数校验的异常会包括所有方法参数的校验失败信息.
     *
     * @param ex      异常对象
     * @param headers 请求头
     * @param status  响应状态
     * @param request 请求对象
     * @return 响应体
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException mismatchException = (MethodArgumentTypeMismatchException) ex;
            // 参数类型转换异常时,尝试找出参数的校验注解,并获取校验注解中的message值作为响应数据
            // 方法参数不存在校验注解时,使用异常的message作为响应数据
            String exMessage = mismatchException.getMessage();
            for (Annotation annotation : mismatchException.getParameter().getParameterAnnotations()) {
                // 校验注解中加入Constraint注解,故以注解对象的type进行Constraint是否存在判断
                if (annotation.annotationType().isAnnotationPresent(Constraint.class)) {
                    // 获取校验注解的message属性值,若不存在,则仍使用抛出异常MethodArgumentTypeMismatchException的message
                    exMessage = Optional.ofNullable(AnnotationUtils.getValue(annotation, "message"))
                            .map(Object::toString)
                            .orElse(exMessage);
                    break;
                }
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorMessage(exMessage));
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handle(new RequestBodyValidationException(ex.getBindingResult().getFieldErrors()));
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handle(new RequestBodyValidationException(ex.getBindingResult().getFieldErrors()));
    }

    /**
     * 处理操作资源不存在异常.
     *
     * @param e 异常对象
     * @return 响应体
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> notFoundHandle(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorMessage(StringUtils.isEmpty(e.getMessage()) ?
                        cngcWebMessageSource.getMessage(WebMessageConstants.ERROR_RESOURCE_NOT_FOUND) :
                        e.getMessage()));
    }

    /**
     * 违反数据约束异常处理.
     *
     * @param e 异常对象
     * @return 响应体
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationHandle(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorMessage(e.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(";")))
        );
    }

    /**
     * 处理请求参数校验异常.
     *
     * @param exception 校验异常对象
     * @return 响应体
     * TODO 处理error中的code与attribution
     */
    @ExceptionHandler(RequestBodyValidationException.class)
    public ResponseEntity<Object> handle(RequestBodyValidationException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .body(new RequestParameterErrorMessage(cngcWebMessageSource.getMessage(
                        WebMessageConstants.ERROR_DEFAULT_PARAMETER_VALIDATION), exception.getFieldErrors()));
    }

    /**
     * 未进行指定处理的,其他类型的异常,皆返回500内部错误,响应体为{@link ErrorMessage}对象.
     *
     * @return 响应体
     */
    @ExceptionHandler
    public ResponseEntity<Object> serverErrorHandle(Exception e) {
        LOG.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorMessage(cngcWebMessageSource.getMessage(WebMessageConstants.ERROR_INTERNAL_SERVER)));
    }

    /**
     * 出现请求参数异常时,返回的消息体对象.
     */
    @Data
    private static final class RequestParameterErrorMessage {
        private String message;
        private List<Error> errors = new ArrayList<>();

        private RequestParameterErrorMessage(String message, @NotNull List<FieldError> errors) {
            this.message = message;
            for (FieldError error : errors) {
                this.errors.add(new Error(error));
            }
        }

        @Data
        private static final class Error {
            private String code;
            private String attribute;
            private String message;
            private Object rejectedValue;

            private Error(FieldError error) {
                this.code = error.getCode();
                this.attribute = error.getField();
                this.message = error.getDefaultMessage();
                this.rejectedValue = error.getRejectedValue();
            }


        }
    }

    @Data
    private static final class ErrorMessage {
        private String message;

        private ErrorMessage(String message) {
            this.message = message;
        }
    }
}
