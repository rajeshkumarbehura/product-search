package com.sellde.inte;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sellde.reward.SupplyRewardEngineApplication;
import com.sellde.reward.repository.CustomerBadgeRepository;
import com.sellde.reward.repository.SupplierSalesRepository;
import com.sellde.reward.repository.UserProfileCopyRepository;
import com.sellde.reward.util.Constant;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Base composite annotation for integration tests.
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SupplyRewardEngineApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(Constant.ENV_TEST)
@TestPropertySource("classpath:config/application-test.yml")
public abstract class IntegrationTest {

    @Autowired
    SupplierSalesRepository supplierSalesRepository;

    @Autowired
    UserProfileCopyRepository userProfileCopyRepository;

    @Autowired
    CustomerBadgeRepository customerBadgeRepository;

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected ObjectMapper objectMapper;

    protected void cleanDB() {
        customerBadgeRepository.deleteAll()
                .then(supplierSalesRepository.deleteAll())
                .then(customerBadgeRepository.deleteAll())
                .block();
    }

    protected String printAsJson(Object object) {
        try {
            System.out.println("data -> " + object);
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
