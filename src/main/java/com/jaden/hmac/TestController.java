package com.jaden.hmac;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TestController {
    private static final String X_SIGNATURE = "x_signature";
    private final SignatureVerifier verifier;

    @PostMapping("/endpoint")
    public ResponseEntity receiveEvent(@RequestHeader(value = X_SIGNATURE, required = false) String signature,
                                       @RequestBody(required = false) String json) {
        if (!verifier.verifySignature(signature, json)) {
            log.warn("Failed To verify signature.");
            return ResponseEntity.status(BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }
}
