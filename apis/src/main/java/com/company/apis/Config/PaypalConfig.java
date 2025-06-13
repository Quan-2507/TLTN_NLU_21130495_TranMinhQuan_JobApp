package com.company.apis.Config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

@Configuration
public class PaypalConfig {

    // @Value("$paypal.client.app")
    // private String clientId;
    // @Value("$paypal.client.secret")
    // private String clientSecret;
    // @Value("$paypal.mode")
    // private String mode;

    @Bean
    public Map<String, String> paypalSdkConfig() {
        Map<String, String> sdkConfig = new HashMap<>();
        sdkConfig.put("mode", "sandbox");
        return sdkConfig;
    }

    @Bean
    public OAuthTokenCredential authTokenCredential() {
        return new OAuthTokenCredential("AQ_IASgWyjVJsvXemEueQrq1E8PEunulUOX7OpfcqFaJpaukrZ5lQicdVST5bVYog18nziu7r89IgaTp",
         "EOyEQSITB_bi-VHw6tHJ7Dm3ZiYah357iM9K1Xsp9SboTevlpEzI5JQH4alGUBlOg_PyvwaBByb2Vp0J", paypalSdkConfig());
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext apiContext = new APIContext(authTokenCredential().getAccessToken());
        apiContext.setConfigurationMap(paypalSdkConfig());
        return apiContext;
    }

}
