package com.novels.common.api;

import java.util.function.Function;

/**
 * 生成
 * @author 王兴
 * @date 2023/7/1 18:09
 */
public interface IdGenerate {

    /**
     * 生成标准的id
     * @return
     */
    long idGenerate();

    /**
     * 给生成ID加上指定前缀
     * @param prefix id前缀
     * @return 生成ID
     */
    String idGenerate(String prefix);

    /**
     * 自定义处理生成ID
     * @param id 生成ID
     * @param apply 对ID的处理
     * @return 最终ID
     */
    String apply(Function<Long,String> apply);

}
