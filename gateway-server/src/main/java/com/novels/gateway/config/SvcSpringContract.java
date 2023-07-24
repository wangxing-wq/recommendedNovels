package com.novels.gateway.config;

import feign.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.CollectionFormat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.annotation.*;
import org.springframework.cloud.openfeign.encoding.HttpEncoding;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;

import static feign.Util.checkState;
import static feign.Util.emptyToNull;
import static java.util.Optional.ofNullable;
import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

/**
 * 重写契约
 *
 * @author qiyu
 * @version 1.0.0
 */
@ConditionalOnBean
public class SvcSpringContract extends Contract.BaseContract implements ResourceLoaderAware {
    private static final Log LOG = LogFactory.getLog(SvcSpringContract.class);

    private static final String ACCEPT = "Accept";

    private static final String CONTENT_TYPE = "Content-Type";

    private static final TypeDescriptor STRING_TYPE_DESCRIPTOR = TypeDescriptor.valueOf(String.class);

    private static final TypeDescriptor ITERABLE_TYPE_DESCRIPTOR = TypeDescriptor.valueOf(Iterable.class);

    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    private final Map<Class<? extends Annotation>, AnnotatedParameterProcessor> annotatedArgumentProcessors;

    private final Map<String, Method> processedMethods = new HashMap<>();

    private final ConversionService conversionService;

    private final ConvertingExpanderFactory convertingExpanderFactory;

    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    private boolean decodeSlash;

    public SvcSpringContract() {
        this(Collections.emptyList());
    }

    public SvcSpringContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors) {
        this(annotatedParameterProcessors, new DefaultConversionService());
    }

    public SvcSpringContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors,
                             ConversionService conversionService) {
        this(annotatedParameterProcessors, conversionService, true);
    }

    public SvcSpringContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors,
                             ConversionService conversionService, boolean decodeSlash) {
        Assert.notNull(annotatedParameterProcessors, "Parameter processors can not be null.");
        Assert.notNull(conversionService, "ConversionService can not be null.");

        List<AnnotatedParameterProcessor> processors = getDefaultAnnotatedArgumentsProcessors();
        processors.addAll(annotatedParameterProcessors);

        annotatedArgumentProcessors = toAnnotatedArgumentProcessorMap(processors);
        this.conversionService = conversionService;
        convertingExpanderFactory = new ConvertingExpanderFactory(conversionService);
        this.decodeSlash = decodeSlash;
    }


    @Override
    public void processAnnotationOnClass(MethodMetadata data, Class<?> clz) {
        if (data != null && clz.getInterfaces().length == 0) {
            RequestMapping classAnnotation = AnnotatedElementUtils.findMergedAnnotation(clz, RequestMapping.class);
            if (classAnnotation != null && classAnnotation.value().length > 0) {
                String pathValue = Util.emptyToNull(classAnnotation.value()[0]);
                pathValue = this.resolve(pathValue);
                if (!pathValue.startsWith("/")) {
                    pathValue = "/" + pathValue;
                }
                data.template().uri(pathValue);
            }
        }
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public MethodMetadata parseAndValidateMetadata(Class<?> targetType, Method method) {
        processedMethods.put(Feign.configKey(targetType, method), method);
        return super.parseAndValidateMetadata(targetType, method);
    }

    @Override
    public void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {
        if (CollectionFormat.class.isInstance(methodAnnotation)) {
            CollectionFormat collectionFormat = findMergedAnnotation(method, CollectionFormat.class);
            data.template().collectionFormat(collectionFormat.value());
        }

        if (!RequestMapping.class.isInstance(methodAnnotation)
                && !methodAnnotation.annotationType().isAnnotationPresent(RequestMapping.class)) {
            return;
        }

        RequestMapping methodMapping = findMergedAnnotation(method, RequestMapping.class);
        // HTTP Method
        RequestMethod[] methods = methodMapping.method();
        if (methods.length == 0) {
            methods = new RequestMethod[]{RequestMethod.GET};
        }
        checkOne(method, methods, "method");
        data.template().method(Request.HttpMethod.valueOf(methods[0].name()));

        // path
        checkAtMostOne(method, methodMapping.value(), "value");
        if (methodMapping.value().length > 0) {
            String pathValue = emptyToNull(methodMapping.value()[0]);
            if (pathValue != null) {
                pathValue = resolve(pathValue);
                // Append path from @RequestMapping if value is present on method
                if (!pathValue.startsWith("/") && !data.template().path().endsWith("/")) {
                    pathValue = "/" + pathValue;
                }
                data.template().uri(pathValue, true);
                if (data.template().decodeSlash() != decodeSlash) {
                    data.template().decodeSlash(decodeSlash);
                }
            }
        }

        // produces
        parseProduces(data, method, methodMapping);

        // consumes
        parseConsumes(data, method, methodMapping);

        // headers
        parseHeaders(data, method, methodMapping);

        data.indexToExpander(new LinkedHashMap<>());
    }

    @Override
    public boolean processAnnotationsOnParameter(MethodMetadata data, Annotation[] annotations, int paramIndex) {
        boolean isHttpAnnotation = false;

        AnnotatedParameterProcessor.AnnotatedParameterContext context = new SimpleAnnotatedParameterContext(data,
                paramIndex);
        Method method = processedMethods.get(data.configKey());
        for (Annotation parameterAnnotation : annotations) {
            AnnotatedParameterProcessor processor = annotatedArgumentProcessors
                    .get(parameterAnnotation.annotationType());
            if (processor != null) {
                Annotation processParameterAnnotation;
                // synthesize, handling @AliasFor, while falling back to parameter name on
                // missing String #value():
                processParameterAnnotation = synthesizeWithMethodParameterNameAsFallbackValue(parameterAnnotation,
                        method, paramIndex);
                isHttpAnnotation |= processor.processArgument(context, processParameterAnnotation, method);
            }
        }

        if (!isMultipartFormData(data) && isHttpAnnotation && data.indexToExpander().get(paramIndex) == null) {
            TypeDescriptor typeDescriptor = createTypeDescriptor(method, paramIndex);
            if (conversionService.canConvert(typeDescriptor, STRING_TYPE_DESCRIPTOR)) {
                Param.Expander expander = convertingExpanderFactory.getExpander(typeDescriptor);
                if (expander != null) {
                    data.indexToExpander().put(paramIndex, expander);
                }
            }
        }
        return isHttpAnnotation;
    }

    private String resolve(String value) {
        if (StringUtils.hasText(value) && resourceLoader instanceof ConfigurableApplicationContext) {
            return ((ConfigurableApplicationContext) resourceLoader).getEnvironment().resolvePlaceholders(value);
        }
        return value;
    }

    private void checkOne(Method method, Object[] values, String fieldName) {
        checkState(values != null && values.length == 1, "Method %s can only contain 1 %s field. Found: %s",
                method.getName(), fieldName, values == null ? null : Arrays.asList(values));
    }

    private void checkAtMostOne(Method method, Object[] values, String fieldName) {
        checkState(values != null && (values.length == 0 || values.length == 1),
                "Method %s can only contain at most 1 %s field. Found: %s", method.getName(), fieldName,
                values == null ? null : Arrays.asList(values));
    }

    private void parseProduces(MethodMetadata md, Method method, RequestMapping annotation) {
        String[] serverProduces = annotation.produces();
        String clientAccepts = serverProduces.length == 0 ? null : emptyToNull(serverProduces[0]);
        if (clientAccepts != null) {
            md.template().header(ACCEPT, clientAccepts);
        }
    }

    private void parseConsumes(MethodMetadata md, Method method, RequestMapping annotation) {
        String[] serverConsumes = annotation.consumes();
        String clientProduces = serverConsumes.length == 0 ? null : emptyToNull(serverConsumes[0]);
        if (clientProduces != null) {
            md.template().header(CONTENT_TYPE, clientProduces);
        }
    }

    private void parseHeaders(MethodMetadata md, Method method, RequestMapping annotation) {
        if (annotation.headers() != null && annotation.headers().length > 0) {
            for (String header : annotation.headers()) {
                int index = header.indexOf('=');
                if (!header.contains("!=") && index >= 0) {
                    md.template().header(resolve(header.substring(0, index)),
                            resolve(header.substring(index + 1).trim()));
                }
            }
        }
    }

    private Annotation synthesizeWithMethodParameterNameAsFallbackValue(Annotation parameterAnnotation, Method method,
                                                                        int parameterIndex) {
        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(parameterAnnotation);
        Object defaultValue = AnnotationUtils.getDefaultValue(parameterAnnotation);
        if (defaultValue instanceof String && defaultValue.equals(annotationAttributes.get(AnnotationUtils.VALUE))) {
            Type[] parameterTypes = method.getGenericParameterTypes();
            String[] parameterNames = PARAMETER_NAME_DISCOVERER.getParameterNames(method);
            if (shouldAddParameterName(parameterIndex, parameterTypes, parameterNames)) {
                annotationAttributes.put(AnnotationUtils.VALUE, parameterNames[parameterIndex]);
            }
        }
        return AnnotationUtils.synthesizeAnnotation(annotationAttributes, parameterAnnotation.annotationType(), null);
    }

    private boolean shouldAddParameterName(int parameterIndex, Type[] parameterTypes, String[] parameterNames) {
        // has a parameter name
        return parameterNames != null && parameterNames.length > parameterIndex
                // has a type
                && parameterTypes != null && parameterTypes.length > parameterIndex;
    }

    private boolean isMultipartFormData(MethodMetadata data) {
        Collection<String> contentTypes = data.template().headers().get(HttpEncoding.CONTENT_TYPE);

        if (contentTypes != null && !contentTypes.isEmpty()) {
            String type = contentTypes.iterator().next();
            try {
                return Objects.equals(MediaType.valueOf(type), MediaType.MULTIPART_FORM_DATA);
            } catch (InvalidMediaTypeException ignored) {
                return false;
            }
        }

        return false;
    }

    private List<AnnotatedParameterProcessor> getDefaultAnnotatedArgumentsProcessors() {

        List<AnnotatedParameterProcessor> annotatedArgumentResolvers = new ArrayList<>();

        annotatedArgumentResolvers.add(new MatrixVariableParameterProcessor());
        annotatedArgumentResolvers.add(new PathVariableParameterProcessor());
        annotatedArgumentResolvers.add(new RequestParamParameterProcessor());
        annotatedArgumentResolvers.add(new RequestHeaderParameterProcessor());
        annotatedArgumentResolvers.add(new QueryMapParameterProcessor());
        annotatedArgumentResolvers.add(new RequestPartParameterProcessor());

        return annotatedArgumentResolvers;
    }

    private Map<Class<? extends Annotation>, AnnotatedParameterProcessor> toAnnotatedArgumentProcessorMap(
            List<AnnotatedParameterProcessor> processors) {
        Map<Class<? extends Annotation>, AnnotatedParameterProcessor> result = new HashMap<>();
        for (AnnotatedParameterProcessor processor : processors) {
            result.put(processor.getAnnotationType(), processor);
        }
        return result;
    }

    private static TypeDescriptor createTypeDescriptor(Method method, int paramIndex) {
        Parameter parameter = method.getParameters()[paramIndex];
        MethodParameter methodParameter = MethodParameter.forParameter(parameter);
        TypeDescriptor typeDescriptor = new TypeDescriptor(methodParameter);

        // Feign applies the Param.Expander to each element of an Iterable, so in those
        // cases we need to provide a TypeDescriptor of the element.
        if (typeDescriptor.isAssignableTo(ITERABLE_TYPE_DESCRIPTOR)) {
            TypeDescriptor elementTypeDescriptor = getElementTypeDescriptor(typeDescriptor);

            checkState(elementTypeDescriptor != null,
                    "Could not resolve element type of Iterable type %s. Not declared?", typeDescriptor);

            typeDescriptor = elementTypeDescriptor;
        }
        return typeDescriptor;
    }

    private static TypeDescriptor getElementTypeDescriptor(TypeDescriptor typeDescriptor) {
        TypeDescriptor elementTypeDescriptor = typeDescriptor.getElementTypeDescriptor();
        // that means it's not a collection but it is iterable, gh-135
        if (elementTypeDescriptor == null && Iterable.class.isAssignableFrom(typeDescriptor.getType())) {
            ResolvableType type = typeDescriptor.getResolvableType().as(Iterable.class).getGeneric(0);
            if (type.resolve() == null) {
                return null;
            }
            return new TypeDescriptor(type, null, typeDescriptor.getAnnotations());
        }
        return elementTypeDescriptor;
    }


    private static class ConvertingExpanderFactory {

        private final ConversionService conversionService;

        ConvertingExpanderFactory(ConversionService conversionService) {
            this.conversionService = conversionService;
        }

        Param.Expander getExpander(TypeDescriptor typeDescriptor) {
            return value -> {
                Object converted = conversionService.convert(value, typeDescriptor, STRING_TYPE_DESCRIPTOR);
                return (String) converted;
            };
        }

    }

    private class SimpleAnnotatedParameterContext implements AnnotatedParameterProcessor.AnnotatedParameterContext {

        private final MethodMetadata methodMetadata;

        private final int parameterIndex;

        SimpleAnnotatedParameterContext(MethodMetadata methodMetadata, int parameterIndex) {
            this.methodMetadata = methodMetadata;
            this.parameterIndex = parameterIndex;
        }

        @Override
        public MethodMetadata getMethodMetadata() {
            return methodMetadata;
        }

        @Override
        public int getParameterIndex() {
            return parameterIndex;
        }

        @Override
        public void setParameterName(String name) {
            nameParam(methodMetadata, name, parameterIndex);
        }

        @Override
        public Collection<String> setTemplateParameter(String name, Collection<String> rest) {
            return addTemplateParameter(rest, name);
        }

        Collection<String> addTemplateParameter(Collection<String> possiblyNull, String paramName) {
            Collection<String> params = ofNullable(possiblyNull).map(ArrayList::new).orElse(new ArrayList<>());
            params.add(String.format("{%s}", paramName));
            return params;
        }
    }
}
