package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.dto.JoinReqDTO;
import hansung.ac.kr.fooding.exception.X_IdAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NickNameAlreadyExistsException;
import hansung.ac.kr.fooding.repository.MemberRepository;
import hansung.ac.kr.fooding.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "LogInApiController", description = "로그인 및 회원가입 API")
@RestController
@RequiredArgsConstructor
public class LogInApiController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @ApiOperation(value="회원 가입")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value="아이디", required=true, dataType = "String", paramType="path"),
            @ApiImplicitParam(name = "password", value="패스워드", required=true, dataType = "String", paramType="path"),
            @ApiImplicitParam(name = "name", value="이름", required=true, dataType = "String", paramType="path"),
            @ApiImplicitParam(name = "nickName", value="닉네임", required=true, dataType = "String", paramType="path"),
            @ApiImplicitParam(name = "sex", value="성별", required=true, dataType = "boolean", paramType="path"),
            @ApiImplicitParam(name = "birthDate", value="생일", required=true, dataType = "String", paramType="path"),
            @ApiImplicitParam(name = "favor", value="좋아하는 것", required=true, dataType = "List<Enum>", paramType="path"),
            @ApiImplicitParam(name = "role", value="권한", required=true, dataType = "List<String>", paramType="path"),
            @ApiImplicitParam(name = "job", value="직업", required=true, dataType = "Enum", paramType="path")
    })
    @PostMapping("/join")
    public ResponseEntity join(@RequestBody JoinReqDTO request) throws
            X_IdAlreadyExistsException,
            X_NickNameAlreadyExistsException {
        memberService.join(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
