package com.study.movieland.service.validator;

import com.study.movieland.data.RequestParams;
import com.study.movieland.service.exception.ValidationException;
import org.junit.Test;

public class MovieRequestParamsValidatorTest {

    @Test
    public void testValidate() {

        MovieRequestParamsValidator validator = new MovieRequestParamsValidator();
        RequestParams requestParamBuilder = new RequestParams.Builder()
                .sortFieldName("rating")
                .sortDirection("desc")
                .build();

        validator.validate(requestParamBuilder);
    }

    @Test(expected = ValidationException.class)
    public void testValidateInvalidDirection() {
        MovieRequestParamsValidator validator = new MovieRequestParamsValidator();
        RequestParams requestParamBuilder = new RequestParams.Builder()
                .sortFieldName("rating")
                .sortDirection("asc")
                .build();

        validator.validate(requestParamBuilder);
    }


    @Test(expected = ValidationException.class)
    public void testValidateUnknownField() {
        MovieRequestParamsValidator validator = new MovieRequestParamsValidator();
        RequestParams requestParamBuilder = new RequestParams.Builder()
                .sortFieldName("some")
                .sortDirection("asc")
                .build();

        validator.validate(requestParamBuilder);
    }

}