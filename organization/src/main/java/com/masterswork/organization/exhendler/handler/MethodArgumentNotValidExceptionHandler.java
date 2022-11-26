package com.masterswork.organization.exhendler.handler;

import com.masterswork.organization.api.dto.error.ApiError;
import com.masterswork.organization.exhendler.exception.MessageSourcePropertyNotFoundException;
import com.masterswork.organization.exhendler.interpolator.SpelMessageInterpolator;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class MethodArgumentNotValidExceptionHandler extends MessageSourceExceptionHandler<MethodArgumentNotValidException> {

    public MethodArgumentNotValidExceptionHandler(MessageSource messageSource, SpelMessageInterpolator interpolator) {
        super(messageSource, interpolator);
    }

    @Override
    protected ApiError createErrorBody(MethodArgumentNotValidException ex) throws MessageSourcePropertyNotFoundException {
        ApiError apiError = super.createErrorBody(ex);
        int excCount = 0;
        StringBuilder detail = new StringBuilder(apiError.getError());
        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            detail.append(String.format(" %d. field '%s' %s", ++excCount, err.getField(), err.getDefaultMessage()));
        }
        return apiError.setError(detail.toString());
    }
}
