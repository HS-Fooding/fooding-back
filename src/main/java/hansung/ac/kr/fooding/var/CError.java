package hansung.ac.kr.fooding.var;

import lombok.Getter;

@Getter
public enum CError {
    REST_NOT_FOUND("Fooding-Restaurant Not Found"),
    NOT_ADMIN_OF_REST("Fooding-Account Not Admin of Restaurant"),
    USER_NOT_LOGIN("Fooding-User Not Login");
    private String errorMessage;

    CError(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
