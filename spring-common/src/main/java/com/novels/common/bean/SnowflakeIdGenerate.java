package com.novels.common.bean;

import com.novels.common.api.IdGenerate;
import com.novels.common.properties.ClusterIdProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

/**
 * snowflake 生成部分
 * 正负(1) + 时间(41) + 12(序号) + 5 (服务号) + 机器号(5)
 * @author 王兴
 * @date 2023/7/2 17:27
 */
@Component
public class SnowflakeIdGenerate implements IdGenerate {

    private final ClusterIdProperties clusterIdProperties;

    private final int maxServerNoOffset;
    private final int maxSeqNoOffset;

    private final int timestampOffset = 41;

    /**
     * 开始时间截(服务启动时的时间戳)
     */
    private final long startTime = System.currentTimeMillis();

    private long lastTime = 0;

    private long seq = 0;

    public SnowflakeIdGenerate (ClusterIdProperties clusterIdProperties) {
        maxServerNoOffset = clusterIdProperties.getMaxMachineNoBit();
        maxSeqNoOffset = clusterIdProperties.getMaxMachineNoBit() + clusterIdProperties.getMaxServerNoBit();
        if (clusterIdProperties.getMaxSeqNoBit() + clusterIdProperties.getMaxServerNoBit() + clusterIdProperties.getMaxMachineNoBit() != 22) {
            throw new IllegalArgumentException("config bit ill not required");
        }
        if (clusterIdProperties.getServerId() > ~(-1L << clusterIdProperties.getMaxServerNoBit())) {
            throw new IllegalArgumentException("serverId can't be greater than " + (~(clusterIdProperties.getServerId() <<  clusterIdProperties.getMaxServerNoBit())));
        }
        if (clusterIdProperties.getMachine() > ~(-1L << clusterIdProperties.getMaxMachineNoBit())) {
            throw new IllegalArgumentException("serverId can't be greater than " + (~(clusterIdProperties.getMachine() << clusterIdProperties.getMaxMachineNoBit())));
        }

        this.clusterIdProperties = clusterIdProperties;
    }

    @Override
    public synchronized long idGenerate() {
        long current = System.currentTimeMillis();
        if (current < lastTime) {
            throw new RuntimeException("Clock");
        }
        if (current == lastTime) {
            seq = (seq + 1L) & ~(-1L << clusterIdProperties.getMaxSeqNoBit());
            if (seq == 0) {
                lastTime = waitNextSeconds(lastTime);
            }
        } else {
            seq = 0;
        }
        return (current - startTime) << timestampOffset | clusterIdProperties.getMaxSeqNoBit() << maxSeqNoOffset | (long) clusterIdProperties.getServerId() << maxServerNoOffset | clusterIdProperties.getMachine();
    }

    public long waitNextSeconds(long lastTime){
        while (lastTime >= System.currentTimeMillis());
        return System.currentTimeMillis();
    }

    @Override
    public String idGenerate(String prefix) {
        if (Objects.isNull(prefix) || prefix.isEmpty()) {
            return Long.toString(idGenerate());
        }
        return apply(id -> prefix + "-" + id);
    }

    @Override
    public String apply(Function<Long, String> apply) {
        long id = idGenerate();
        if (Objects.isNull(apply)) {
            return Long.toString(id);
        }
        return apply.apply(id);
    }
}
