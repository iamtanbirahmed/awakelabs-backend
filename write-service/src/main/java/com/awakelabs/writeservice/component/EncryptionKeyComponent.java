package com.awakelabs.writeservice.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class EncryptionKeyComponent {
    @Value("${db.vitalsign.secret}")
    private String vitalSignSecret;

    @Value("${db.heartrate.secret}")
    private String heartRateSecret;

}
