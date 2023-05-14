package com.novels.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novels.common.advice.LogHelperAspect;
import com.novels.common.properties.CommonProperties;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 王兴
 * @date 2023/5/2 20:37
 */
@Configuration
@ConditionalOnProperty(name = "common.log-helper.enable",havingValue = "true")
public class AdviceConfiguration {


    @Bean
    public AspectJExpressionPointcutAdvisor configurationAdvice(ObjectMapper objectMapper, CommonProperties commonProperties) {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        String pointcut = "@annotation(com.novels.common.annotation.LogHelper) || (@within(com.novels.common.annotation.LogHelper) && within(" + commonProperties.getLogHelper().getScanner() + "..*))";
        advisor.setExpression(pointcut);
        advisor.setAdvice(new LogHelperAspect(objectMapper));
        return advisor;
    }

}
