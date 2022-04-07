package hansung.ac.kr.fooding.api;

import hansung.ac.kr.fooding.config.SwaggerConfig;
import hansung.ac.kr.fooding.config.filter.JwtFilter;
import hansung.ac.kr.fooding.domain.Admin;
import hansung.ac.kr.fooding.dto.login.JoinReqDTO;
import hansung.ac.kr.fooding.dto.login.LoginReqDTO;
import hansung.ac.kr.fooding.dto.login.LoginResDTO;
import hansung.ac.kr.fooding.dto.login.TokenResDTO;
import hansung.ac.kr.fooding.exception.X_IdAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NickNameAlreadyExistsException;
import hansung.ac.kr.fooding.exception.X_NotRegisteredRole;
import hansung.ac.kr.fooding.provider.JwtTokenProvider;
import hansung.ac.kr.fooding.service.AccountService;
import hansung.ac.kr.fooding.service.SecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = {SwaggerConfig.API_LOGIN})
@RestController
@RequiredArgsConstructor
public class LogInApiController {

    private final AccountService accountService;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityService securityService;

    @ApiOperation(value = "회원 가입")
    @PostMapping("/join")
    public ResponseEntity join(@RequestBody JoinReqDTO request) {
        try {
            accountService.join(request);
        } catch (X_IdAlreadyExistsException e) {
            return new ResponseEntity<>("Fooding-"+e.getClass(), HttpStatus.BAD_REQUEST);
        } catch (X_NickNameAlreadyExistsException e) {
            return new ResponseEntity<>("Fooding-"+e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (X_NotRegisteredRole e) {
            return new ResponseEntity<>("Fooding-"+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<LoginResDTO> login(@RequestBody LoginReqDTO request) {
        // userId와 userPassword로 authentication 토큰 생성

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword());

        // authenticate를 실행할 때, loadUserByUsername 메소드 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // authentication 객체를 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // jwt 토큰 생성
        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        System.out.println("###########################왜안되지");
        LoginResDTO loginResDTO = new LoginResDTO(new TokenResDTO(jwt), (Admin)securityService.getAccount());

        return new ResponseEntity<LoginResDTO>(loginResDTO, httpHeaders, HttpStatus.OK);
    }
}
