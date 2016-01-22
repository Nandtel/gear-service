package com.gearservice.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Class ReCaptchaProperties is configuration
 * Gets parameters from application.properties for ReCaptcha configuration
 *
 * @version 1.1
 * @author Dmitry
 * @since 22.01.2016
 */

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