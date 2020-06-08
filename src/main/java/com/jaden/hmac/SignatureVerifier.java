package com.jaden.hmac;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class SignatureVerifier {
    private static final String SIGNATURE_ALGORITHM = "HmacSHA256";

    @Value("${service.secret}")
    private String secret;

    public boolean verifySignature(String signature, String requestBody) {
        if (StringUtils.isBlank(signature) || StringUtils.isBlank(requestBody))
            return false;

        String madeSignature = getHmacSignature(requestBody.getBytes());
        log.info("Sender-sent signature: {}, made signature :{}", signature, madeSignature);

        return signature.trim().equals(madeSignature);
    }

    public String getHmacSignature(byte[] requestBody) {
        byte[] key = secret.getBytes();
        final SecretKeySpec secretKey = new SecretKeySpec(key, SIGNATURE_ALGORITHM);

        try {
            Mac mac = Mac.getInstance(SIGNATURE_ALGORITHM);
            mac.init(secretKey);
            return Base64.encodeBase64String(mac.doFinal(requestBody));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.warn("Error occurred processing Mac init - Exception : {}, secretKey: {}", e, secretKey);
            return "";
        }
    }
}
