package com.gearservice.model.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Class ReCaptchaCheckerResponse is model Entity, that not store in database
 * and consists answer of google's reCaptcha checker.
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

public class ReCaptchaCheckerResponse {
    @JsonProperty
    private Boolean success;

    @JsonProperty("error-codes")
    private List<String> errorCodes;

    public ReCaptchaCheckerResponse() {}

    public ReCaptchaCheckerResponse(Boolean success, List<String> errorCodes) {
        this.success = success;
        this.errorCodes = errorCodes;
    }

    public List<String> getErrorCodes() {return errorCodes;}
    public void setErrorCodes(List<String> errorCodes) {this.errorCodes = errorCodes;}
    public Boolean getSuccess() {return success;}
    public void setSuccess(Boolean success) {this.success = success;}
}
