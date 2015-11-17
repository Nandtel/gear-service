package com.gearservice.model.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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
