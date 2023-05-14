package com.novels.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 王兴
 * @date 2023/5/14 22:51
 */
@Data
@Component
@ConfigurationProperties(prefix = "common")
public class CommonProperties {

    private LogHelperProperties logHelper;

    @Data
    public static class LogHelperProperties {

        private boolean enable = true;

        private String scanner = "com.wx";

    }

}
