package com.study.movieland.web.argumentresolver;

import com.study.movieland.data.RequestParams;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

public class RequestParamsArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == RequestParams.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        RequestParams.Builder requestParamBuilder = new RequestParams.Builder();
        for (Map.Entry<String, String[]> param : webRequest.getParameterMap().entrySet()) {
            switch (param.getKey()) {
                case "rating":
                case "price":
                    requestParamBuilder = requestParamBuilder
                            .sortFieldName(param.getKey())
                            .sortDirection(param.getValue()[0]);
                    break;
            }
        }
        return requestParamBuilder.build();
    }
}