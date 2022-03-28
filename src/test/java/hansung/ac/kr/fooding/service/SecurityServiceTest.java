package hansung.ac.kr.fooding.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityServiceTest {
    @Autowired SecurityService securityService;

    @Test
    @WithMockUser(username = "testAdmin", roles = "ADMIN")
    void getAccount() {
        System.out.println(securityService.getAccount());
    }
}