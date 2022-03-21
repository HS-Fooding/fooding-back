package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.dto.JoinReqDTO;
import hansung.ac.kr.fooding.exception.X_IdAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NickNameAlreadyExistsException;
import hansung.ac.kr.fooding.repository.MemberRepository;
import hansung.ac.kr.fooding.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LogInApiController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody JoinReqDTO request) throws
            X_IdAlreadyExistsException,
            X_NickNameAlreadyExistsException {
        memberService.join(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
