package com.novels.common.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 王兴
 * @date 2023/7/3 23:22
 */
@Data
@Component
@ConfigurationProperties(prefix = "cluster.id")
public class ClusterIdProperties {

    /**
     * 项目的服务ID
     */
    private final int serverId;

    /**
     * 项目的机器ID
     */
    private final int machine;

    @Value("${spring.application.name}")
    private final String prefix;


    /**
     * 最大机器号,默认五位比特位
     */
    private final int maxMachineNoBit = 5;

    /**
     * 最大服务号,默认五位比特位
     */
    private final int maxServerNoBit = 5;

    /**
     * 最大序列号,默认12比特位
     */
    private final long maxSeqNoBit = 12;

    private final long startTime = 1676332800000L;


}
