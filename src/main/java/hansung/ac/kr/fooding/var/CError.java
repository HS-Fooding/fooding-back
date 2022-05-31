package hansung.ac.kr.fooding.var;

import lombok.Getter;

@Getter
public enum CError {
    REST_NOT_FOUND("Fooding-Restaurant Not Found"),
    RESERV_NOT_FOUND("Fooding-Reservation Not Found"),
    TABLE_NOT_FOUND("Fooding-Table Not Found"),
    COMMENT_NOT_FOUND("Fooding-Comment Not Found"),
    MENU_NOT_FOUND("Fooding-Menu Not Found"),
    REGION_NOT_FOUND("Fooding-Region Not Found"),
    GEOCODE_NOT_FOUND("Fooding-Geocode Not Found"),

    USER_NOT_LOGIN("Fooding-User Not Login"),
    USER_NOT_ADMIN_ACOUNT("Fooding-User account type is not Admin account"),
    USER_NOT_MEMBER_ACCOUNT("Fooding-User account type is not Member account"),
    USER_NOT_ADMIN_OF_REST("Fooding-User is not Admin of Restaurant"),
    USER_NOT_BOOKER_OF_RESERV("Fooding-Member is not Owner of Reservation"),
    USER_NOT_AUTHOR_OF_COMMENT("Fooding-User is not Author of Comment"),
    USER_NOT_AUTHOR_OF_REVIEW("Fooding-User is not Author of Review");
    private String message;

    CError(String message){
        this.message = message;
    }
}
