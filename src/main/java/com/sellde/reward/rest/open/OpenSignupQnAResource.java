package com.sellde.reward.rest.open;

import com.sellde.reward.service.SignupQnAService;
import com.sellde.reward.service.model.SignupQnAModel;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/open/signup_qna")
public class OpenSignupQnAResource {

    @Autowired
    private SignupQnAService signupQnAService;

    @GetMapping("/query")
    @ResponseStatus(HttpStatus.OK)
    public Flux<SignupQnAModel.GetQuery> getAllDefaultQuery() {
        return signupQnAService.getAllDefaultQuery();
    }

    @PostMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Map<String, Object>> createAndValidate(@RequestBody List<SignupQnAModel.RequestResponse> requestList) {
        return signupQnAService.isValidAnswerByCustomer(requestList)
                .map(JSONObject::toMap);
    }

    @GetMapping("/is-done")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Map<String, Object>> checkIfAnswered(@RequestParam long phoneNo) {
        return signupQnAService.isAnsweredByPhoneNo(phoneNo)
                .map(isAnswered -> new JSONObject().put("isAnswered", isAnswered).toMap());
    }

}
