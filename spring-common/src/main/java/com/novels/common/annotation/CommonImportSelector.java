package com.novels.common.annotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 查询报下要启动的类
 * @author 22343
 * @version 1.0
 */
public class CommonImportSelector  implements ImportSelector {

	@Override
	public String[] selectImports (AnnotationMetadata annotationMetadata) {
		String [] arr = {"com.wx.common.config.Knife4jSwaggerConfig"};
		// String [] arr = {};
		return arr;
	}

}
