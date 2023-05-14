package com.novels.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.RequestHandler;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author 22343
 */
@ConfigurationProperties(
		prefix = "spring.web.knife4j"
)
public class KnifeConfiguration {

	@Value("${spring.application.name}")
	private String title;
	@Value("${spring.application.name}")
	private String description;
	private String serviceUrl;
	private String delimiter = ",";
	private String version = "1.0";
	private String basePackages = "";
	private String pathType;
	private boolean enable;

	public String getTitle () {
		return title;
	}

	public void setTitle (String title) {
		this.title = title;
	}

	public String getDescription () {
		return description;
	}

	public void setDescription (String description) {
		this.description = description;
	}

	public String getServiceUrl () {
		return serviceUrl;
	}

	public void setServiceUrl (String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getDelimiter () {
		return delimiter;
	}

	public void setDelimiter (String delimiter) {
		this.delimiter = delimiter;
	}

	public String getVersion () {
		return version;
	}

	public void setVersion (String version) {
		this.version = version;
	}

	public String getBasePackages () {
		return basePackages;
	}

	public void setBasePackages (String basePackages) {
		this.basePackages = basePackages;
	}

	public String getPathType () {
		return pathType;
	}

	public void setPathType (String pathType) {
		this.pathType = pathType;
	}

	public boolean getEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	private Function<Class<?>,Boolean> handlerPackage (final String basePackage) {
		return input -> {
			// 循环判断匹配
			for (String strPackage : basePackage.split(delimiter)) {
				boolean isMatch = input.getPackage().getName().startsWith(strPackage);
				if (isMatch) {
					return true;
				}
			}
			return false;
		};
	}

	public Predicate<RequestHandler> basePackage () {
		return input -> (Boolean) declaringClass(input).map(handlerPackage(basePackages)).orElse(true);
	}

	private Optional<? extends Class<?>> declaringClass (RequestHandler input) {
		return Optional.ofNullable(input.declaringClass());
	}

}
