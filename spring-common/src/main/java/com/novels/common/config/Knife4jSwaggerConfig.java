package com.novels.common.config;

import com.github.xiaoymin.knife4j.spring.configuration.Knife4jAutoConfiguration;
import com.novels.common.properties.KnifeConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationWebMvcConfiguration;

/**
 * 存在swagger,knife4j依赖时记载这个类<br/>
 * 尽在test,和dev环境启用 <br/>
 * spring.web.knife4j.enable 配置可以启用和禁用这个类 <br/>
 * 对Knife4j优化封装方式 | 什么时候启用
 * @author 22343
 * @version 1.0
 */

@Configuration
@EnableSwagger2WebMvc
@Profile({"dev","test"})
@EnableConfigurationProperties({KnifeConfiguration.class})
@ConditionalOnProperty(name = "spring.web.knife4j.enable", havingValue = "true")
@ConditionalOnClass({Swagger2DocumentationWebMvcConfiguration.class, Knife4jAutoConfiguration.class})
public class Knife4jSwaggerConfig {

	/**
	 * 读取Knife4j配置信息
	 */
	final KnifeConfiguration knifeConfiguration;

	public Knife4jSwaggerConfig(KnifeConfiguration knifeConfiguration) {
		this.knifeConfiguration = knifeConfiguration;
	}


	@Bean(value="defaultApi2")
	@ConditionalOnMissingBean
	public Docket defaultApi2() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(new ApiInfoBuilder()
						.title(knifeConfiguration.getTitle())
						.description(knifeConfiguration.getDescription())
						.termsOfServiceUrl(knifeConfiguration.getServiceUrl())
						.version(knifeConfiguration.getVersion())
						.build())
				.groupName("2.X版本").select()
				.apis(knifeConfiguration.basePackage()).paths(PathSelectors.any()).build();
	}




}
