package hanco.planpie.common.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CommonService {

    /* 휴대폰 인증번호 생성 */
    public String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(999999));  // 6자리 난수 생성
    }
}
