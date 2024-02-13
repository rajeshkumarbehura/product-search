package com.sellde.inte;

import com.sellde.reward.service.model.LuckyDrawContestModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LuckyDrawContestResourceTest extends IntegrationTest {

    @Test
    public void testCreate() {
        System.out.println("Running test case.");

        var request = new LuckyDrawContestModel();
        request.setPhoneNo("+84909990919");


        System.out.println("Request -> "+ printAsJson(request));

        var responseModel = webTestClient
                .post()
                .uri("/v1/open/lucky-draw-contest")
                .body(Mono.just(request), LuckyDrawContestModel.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .expectBody(LuckyDrawContestModel.class)
                .returnResult().getResponseBody();

        System.out.println("Response -> " + printAsJson(responseModel));
    }


}