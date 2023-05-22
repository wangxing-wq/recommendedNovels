package com.novels.common.bean;

import com.novels.common.enums.SystemConstantEnum;
import com.wx.common.collection.Maps;
import com.wx.common.core.bean.IResultCode;
import com.wx.common.exception.BizException;
import com.wx.common.util.StrUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.novels.common.enums.ResultConstantEnum.*;

/**
 * {
 * "code": 0,
 * "message": "成功",
 * "detailMessage": "成功",
 * "data": true,
 * "traceId": "8bca519e60b35b",
 * "spanId": "1540451004024094720",
 * "env": "dev"
 * }
 * 响应通用返回体
 * env: 当前服务部署的环境（比如dev、sit、uat、prod）
 * code: 返回码值 查看XxxEnum类,此类含有通用类型码值 | 详细的码值查看对应调用的服务文档,枚举值参考阿里巴巴文档
 * message: 返回信息
 * detailMessage: 内部开发人员看的消息（详细的错误信息，主要方便问题定位）
 * traceId: 分布式链路跟踪-traceId（通常与日志平台结合使用）
 * spanId: 分布式链路跨度-spanId，spanId组合在一起就是整个链路的trace
 * data: 通常是json数据
 * @author 王兴
 * @date 2023/5/10 20:55
 * 1、定义一个通用的统一返回码接口：IRespResultCode.java，接口里面规范子类对象要实现的方法，
 * 例如：getConstant(),getDesc(),getDetailMessage()。
 * 2、定义一个通用的统一返回码对象：RespResultCode.java实现IRespResultCode接口 ，主要放通用的返回结果信息，
 * 例如：请求成功、系统异常、网络异常、参数不合法等信息。
 * 3、定义一个处理返回结果的工具类RespResult.java，定义一些通用的返回结果的方法，例如返回成功结果的success方法、返回失败结果的error方法。
 * 4、封装业务异常类BusinessException.java，继承运行时异常，业务层出现一些逻辑的错误可以抛出BusinessException异常。
 * 5、定义通过全局异常拦截器GlobalExceptionHandler拦截各类异常，对返回结果的code和message做二次加工。
 * 5.1 Springboot的全局异常查是通过两个注解@RestControllerAdvice和@ExceptionHandler来实现的。
 * 只有代码出错或者throw出来的异常才会被捕捉处理，如果被catch的异常，就不会被捕捉，除非catch之后再throw异常。
 * @RestControllerAdvice：增强型控制器，对于控制器的全局配置放在同一个位置，全局异常的注解，放在类上。
 * @RestControllerAdvice默认只会处理controller层抛出的异常，如果需要处理service层的异常， 需要定义一个自定义的MyException来继承RuntimeException类，然后@ExceptionHandler（MyException.class）即可。
 * 5.2 @ExceptionHandler：指明需要处理的异常类型以及子类。注解放在方法上面。
 * 5.3 一般对业务方法，比如controller层返回的code通常有两种做法：
 * 1）返回http code 200，对所有异常进行捕获，返回的message二次加工展示给调用方。
 * 2）返回不同的http code，通过ResponseStatus对象控制不同的异常展示不同的status code。不好的地方，status code比较有限，使用起来不是很方便。
 * GlobalExceptionHandler处理异常的规则是：
 * . @ExceptionHandler(RuntimeException.class)： 会先查看异常是否属于RuntimeException异常以及其子类，如果是的话，就用这个方法进行处理。
 * . 一个方法处理多个异常类的异常： @ExceptionHandler(value = {Exception.class, Throwable.class})
 * . 有多个@ExceptionHandler注解的方法时，会根据抛出异常类去寻找处理方法， 如果没有，就往上找父类，直到找到为止。
 */
public final class Result<R> extends HashMap<String, Object> implements Serializable {


    /**
     * 对反序列化版本是否兼容进行控制,如果不写这个属性,表示不兼容
     */
    private static final long serialVersionUID = 1L;
    /**
     * 返回空包data数据
     */
    private static final Object[] DATA_EMPTY = {};


    /**
     * 默认构造方法,便于某些框架进行反序列化
     */
    public Result() {
        this(SystemConstantEnum.SUCCESS.constant(), SystemConstantEnum.SUCCESS.desc(), "", DATA_EMPTY);
    }

    /**
     * 构造对象的核心方法
     * env: 当前服务部署的环境（比如dev、sit、uat、prod）
     * @param code:          返回码值 查看XxxEnum类,此类含有通用类型码值 | 详细的码值查看对应调用的服务文档,枚举值参考阿里巴巴文档
     * @param message:       返回信息
     * @param detailMessage: 内部开发人员看的消息（详细的错误信息，主要方便问题定位）
     *                       traceId: 分布式链路跟踪-traceId（通常与日志平台结合使用）
     *                       spanId: 分布式链路跨度-spanId，spanId组合在一起就是整个链路的trace
     *                       data: 通常是json数据
     * @param data           响应数据
     */
    private Result(String code, String message, String detailMessage, Object ... data) {
        // 比输出字段
        String env = System.getProperty(ENV.constant());
        env = env == null ? "dev" : env;
        this.put(CODE.constant(), code);
        this.put(MESSAGE.constant(), message);
        this.put(ENV.constant(), env);
        if (Objects.nonNull(data) && data.length == 1) {
            this.put(DATA.constant(),data[0]);
        }
        if (Objects.nonNull(data) && data.length > 1) {
            // 传入一个数据默认按照data,存入,传入多个按照map存入
            Map<String, Object> map = Maps.ofMap(data);
            this.put(DATA.constant(),map);
        }
//        TODO traceId 看情况实现
//        TODO spanId 看情况实现
        if (StrUtils.isNotBlank(detailMessage)) {
            this.put(DETAIL_MESSAGE.constant(), detailMessage);
        }
    }


    /**
     * 自定义响应状态码和响应数据,不传入stateCode,默认成功
     * @param IResultCode     码值
     * @param data          相应数据
     */
    public static <R> Result<R> define(IResultCode IResultCode, Object ... data) {
        // 状态码和data校验,不传入默认为ResponseCodeEnum.FAIL,和空的响应数据
        if (Objects.isNull(IResultCode)) {
            IResultCode = new BizException(SystemConstantEnum.SUCCESS);
        }
        if (data == null) {
            data = DATA_EMPTY;
        }
        return new Result<>(IResultCode.getCode(), IResultCode.getMessage(), IResultCode.getDetailMessage(), data);
    }


    /**
     * 静态方法,返回默认OK的数据
     * @param data 响应数据
     */
    public static <R> Result<R> success(Object... data) {
        return new Result<>(SystemConstantEnum.SUCCESS.constant(), SystemConstantEnum.SUCCESS.desc(), "", data);
    }
    /**
     * 默认使用ResponseCodeEnum的失败状态码
     * @param data 响应数据
     */
    public static <R> Result<R> fail(Object ... data) {
        return new Result<>(SystemConstantEnum.FAIL.constant(), SystemConstantEnum.FAIL.desc(), "", data);
    }

    /**
     * 自定义响应状态码信息
     * @param IResultCode 异常码
     * @param data   相应数据
     */
    public static <R> Result<R> fail(IResultCode IResultCode, Object ... data) {
        return define(IResultCode,data);
    }

    public boolean isSuccess() {
        return Objects.equals(this.code(),SystemConstantEnum.SUCCESS.constant());
    }

    public String code() {
        return (String) this.get(CODE);
    }

    public Result<R> code(String code) {
        this.put(CODE.constant(), code);
        return this;
    }

    public String message() {
        return (String) this.get(MESSAGE);
    }

    public Result<R> message(String message) {
        this.put(MESSAGE.constant(), message);
        return this;
    }

    public Result<R> data(R data) {
        this.put(DATA.constant(), data);
        return this;
    }

    /**
     * 存在序列化失败的问题
     */
    public R data() {
        return data(true);
    }

    /**
     * 获取数据Result存储数据
     * @param throwException 是否抛出异常
     * @return 存储数据
     */
    public R data(boolean throwException) {
        R r = null;
        try {
            r = (R) this.get(DATA);
        } catch (Exception e) {
            if (throwException) {
                throw e;
            }
        }
        return r;
    }

    public Result<R> add(String key, Object value) {
        this.put(key, value);
        return this;
    }


}
