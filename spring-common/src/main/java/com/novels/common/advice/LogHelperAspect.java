package com.novels.common.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.novels.common.annotation.LogHelper;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.event.Level;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 全局日志处理
 * @author 王兴
 * @date 2023/5/2 16:39
 */
@Slf4j
public class LogHelperAspect implements MethodInterceptor {

    private final ObjectMapper objectMapper;

    public LogHelperAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        LogHelper logHelper = getLogHelper(methodInvocation);
        boolean enable = logHelper.enable();
        boolean pretty = logHelper.isPretty();
        Level level = logHelper.level();
        int maxLength = logHelper.maxLength();
        boolean methodSignature = logHelper.methodSignature();
        boolean parameter = logHelper.parameter();
        boolean returnValue = logHelper.returnValue();
        boolean exception = logHelper.exception();
        Map<String, Object> data = new HashMap<>();
        ObjectWriter objectWriter = objectMapper.writer();
        Object result = null;
        String json = null;
        try {
            result = methodInvocation.proceed();
            if (!enable) {
                data.put("enable", "未启用日志");
                return result;
            }
        } catch (Throwable e) {
            if (exception) {
                data.put("exception", e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }
        try {
            if (returnValue) {
                data.put("result", processLength(result, maxLength));
            }
            if (methodSignature) {
                data.put("methodSignature", processLength(methodInvocation.getMethod(), maxLength));
            }
            if (parameter) {
                data.put("parameter", processLength(Arrays.asList(methodInvocation.getArguments()), maxLength));
            }
            if (pretty) {
                objectWriter = objectWriter.withDefaultPrettyPrinter();
            }
            json = objectWriter.writeValueAsString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        printLog(level, json);
        return result;
    }

    /**
     * 获取要参照的LogHelper,如果方法上有就以方法为主,否则以类唯
     * @param methodInvocation 代理方法对象
     * @return LogHelper
     */
    private LogHelper getLogHelper(MethodInvocation methodInvocation) {
        // 获取类上面注解
        LogHelper classAnnotation = methodInvocation.getThis().getClass().getAnnotation(LogHelper.class);
        // 获取方法上注解
        LogHelper methodAnnotation = methodInvocation.getMethod().getAnnotation(LogHelper.class);
        // 如果类有,方法无,返回类
        if (Objects.isNull(classAnnotation) && Objects.isNull(methodAnnotation)) {
            throw new RuntimeException("logHelper not null" + methodInvocation.getMethod());
        }
        if (Objects.nonNull(methodAnnotation)) {
            return methodAnnotation;
        }
        return classAnnotation;
    }

    /**
     * 将长度超过最大值的字符串按照最大值处理
     * @param obj 要截取的参数
     * @return 处理后参数
     */
    private String processLength(Object obj, int maxLength) {
        if (Objects.isNull(obj)) {
            return Strings.EMPTY;
        }
        String str = obj.toString();
        return str.length() > maxLength ? str.substring(0, maxLength) : str;
    }

    /**
     * 根据日志等级打印对象
     * @param level 日志等级
     * @param obj   打印的对象
     */
    private void printLog(Level level, Object obj) {
        switch (level) {
            case TRACE:
                log.trace("\n{}", obj);
                break;
            case DEBUG:
                log.debug("\n{}", obj);
                break;
            case INFO:
                log.info("\n{}", obj);
                break;
            case WARN:
                log.warn("\n{}", obj);
                break;
            case ERROR:
                log.error("\n{}", obj);
                break;
        }
    }

}
