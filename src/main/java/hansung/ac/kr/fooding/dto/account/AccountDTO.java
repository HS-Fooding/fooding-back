package hansung.ac.kr.fooding.dto.account;

import hansung.ac.kr.fooding.domain.Account;
import lombok.Data;

@Data
public class AccountDTO {
    private Long id;
    private String name;
    private String nickName;

    private AccountDTO(Account account){
        this.id = account.getId();
        this.name = account.getName();
        this.nickName = account.getNickName();
    }

    public static AccountDTO from(Account account){
        return new AccountDTO(account);
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
