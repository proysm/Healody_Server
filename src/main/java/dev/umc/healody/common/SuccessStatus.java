package dev.umc.healody.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.*;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public enum SuccessStatus {

    /**
     * SUCCESS CODE
     * 200 :
     * 201 :
     * 204 :
     */


    /**
     * SUCCESS CODE
     * 200 OK
     */
    SUCCESS(200, "SUCCESS", "요청이 완료되었습니다."),
    CREATED(201,"CREATED","집이 생성 되었습니다."),
    FAILURE(400, "FAILURE", "요청이 실패하였습니다."),
    FORBIDDEN(403,"FORBIDDEN","집 관리자 권한이 없습니다.");


    /**
     * SUCCESS CODE
     * 201 CREATED
     */



    /**
     * SUCCESS CODE
     * 204 NO CONTENT
     */

    private final int code;
    private final String result;
    private final String message;

}
