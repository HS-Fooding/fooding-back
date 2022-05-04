package hansung.ac.kr.fooding.service;

import hansung.ac.kr.fooding.domain.Account;
import hansung.ac.kr.fooding.domain.Admin;
import hansung.ac.kr.fooding.domain.Restaurant;
import hansung.ac.kr.fooding.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityService {
    @Autowired
    AccountRepository accountRepository;

    public String getUserIdentifier() {
//        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Account getAccount(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByIdentifier(name);
//        if (account == null) throw new IllegalStateException("Fooding-Account Not Found");
        return account;
    }

    public boolean isLogined(){
        Account account = getAccount();
        if(account == null) return false;
        return true;
    }

    @Transactional
    public boolean isRestaurantAdmin(Restaurant restaurant){
        Account account = getAccount();
        if(!(account instanceof Admin)) throw new SecurityException("Fooding-Account is Not Admin Account");
        if(restaurant.getAdmin() == (Admin)account)
            return true;
        else
            return false;
    }
}
