package com.dtstack.ws;

import java.util.ArrayList;
import java.util.List;
/**
 * @author xiaohe
 * @date 2019/11/9
 */
public class SoapValidationException extends SoapException {
    private final List<AssertionError> errors;

    public SoapValidationException(List<AssertionError> errors) {
        super("Message validation failed with " + errors.size() + " error(s)\n" + errors);
        this.errors = errors;
    }

    public List<AssertionError> getErrors() {
        return new ArrayList<AssertionError>(errors);
    }

}
