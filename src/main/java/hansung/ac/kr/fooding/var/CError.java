package hansung.ac.kr.fooding.var;

import lombok.Getter;

@Getter
public enum CError {
    REST_NOT_FOUND("Fooding-Restaurant Not Found"),
    RESERV_NOT_FOUND("Fooding-Reservation Not Found"),
    TABLE_NOT_FOUND("Fooding-Table Not Found"),

    USER_NOT_LOGIN("Fooding-User Not Login"),
    USER_NOT_ADMIN_ACOUNT("Fooding-User account type isn't Admin account"),
    USER_NOT_MEMBER_ACCOUNT("Fooding-User account type is not Member account"),
    USER_NOT_ADMIN_OF_REST("Fooding-Account Not Admin of Restaurant"),
    USER_NOT_OWNER_OF_RESERV("Fooding-Member is not Owner of Reservation");

    private String message;

    CError(String message){
        this.message = message;
    }
}
