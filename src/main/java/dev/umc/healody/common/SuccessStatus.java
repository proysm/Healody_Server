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
    SUCCESS(200, "SUCCESS", "요청이 완료되었습니다.");


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
