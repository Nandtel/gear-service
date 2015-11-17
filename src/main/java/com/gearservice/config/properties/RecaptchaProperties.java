package com.gearservice.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "recaptcha")
public class ReCaptchaProperties {

    private boolean enabled = false;
    private String secretKey;
    private String siteVerify = "https://www.google.com/recaptcha/api/siteverify";

    public boolean isEnabled() {return enabled;}
    public void setEnabled(boolean enabled) {this.enabled = enabled;}
    public String getSecretKey() {return secretKey;}
    public void setSecretKey(String secretKey) {this.secretKey = secretKey;}
    public String getSiteVerify() {return siteVerify;}
    public void setSiteVerify(String siteVerify) {this.siteVerify = siteVerify;}
}
