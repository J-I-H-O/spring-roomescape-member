package roomescape.controller.rest;

import java.util.Map;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import roomescape.repository.dynamic.Condition;
import roomescape.repository.dynamic.ReservationFilterConditions;

public class FilterArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().getName()
                .equals(ReservationFilterConditions.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Map<String, String[]> parameterMap = webRequest.getParameterMap();

        entry -> Condition.findConditionByName(entry.getKey())

        parameterMap.entrySet()
                .stream()
                .map()


        return new ReservationFilterConditions()
    }
}
