package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    @Autowired
    AccountRepository accountRepository;

    public String getUserIdentifier() {
//        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Account getAccount() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        return accountRepository.findByIdentifier(name);
    }
}
