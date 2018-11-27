package com.study.movieland.service.validator;

import com.study.movieland.data.RequestParams;
import com.study.movieland.data.SortDirection;
import com.study.movieland.service.exception.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class MovieRequestParamsValidator implements RequestParamsValidator {

    @Override
    public void validate(RequestParams requestParams) {
        if (requestParams.isSorted()) {
            if ("rating".equals(requestParams.getSortFieldName()) || "price".equals(requestParams.getSortFieldName())) {
                if ("rating".equals(requestParams.getSortFieldName()) && requestParams.getSortDirection() == SortDirection.ASC) {
                    throw new ValidationException("Prohibited sorting: " + requestParams.getSortDirection());
                }
            } else {
                throw new ValidationException("Is unknown field for sorting:  " + requestParams.getSortFieldName());
            }
        }
    }
}
