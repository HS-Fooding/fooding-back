package hansung.ac.kr.fooding.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public String getUserIdentifier() {
//        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
