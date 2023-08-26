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

    /** 근수 **/
    HOME_CREATED(201, "CREATED", "집이 생성 되었습니다."),

    HOME_READ(200, "SUCCESS", "집 구성원이 조회되었습니다."),

    HOME_UPDATE_SUCCESS(200, "SUCCESS", "집 정보가 수정되었습니다."),
    HOME_UPDATE_FAILURE(400, "FAILURE", "수정 요청이 실패하였습니다."),

    HOME_DELETE_SUCCESS(200, "SUCCESS", "집이 삭제되었습니다."),

    NOT_HOME_ADMIN(403, "FORBIDDEN", "집 관리자 권한이 없습니다."),

    /** 하늘 **/
    EMAIL_SUCCESS(200, "SUCCESS", "사용 가능한 이메일입니다."),
    EMAIL_FAILURE(400, "FAILURE", "이미 존재하는 이메일입니다."),

    PHONE_SUCCESS(200, "SUCCESS", "사용 가능한 휴대폰 번호입니다."),
    PHONE_FAILURE(400, "FAILURE", "이미 존재하는 휴대폰 번호입니다."),

    NICKNAME_SUCCESS(200, "SUCCESS", "사용 가능한 닉네임입니다."),
    NICKNAME_FAILURE(400, "FAILURE", "이미 존재하는 닉네임입니다."),

    EMAIL_CONFIRM_SUCCESS(200, "SUCCESS", "인증코드가 발송되었습니다. 이메일을 확인해주세요."),
    EMAIL_CONFIRM_CHECK_SUCCESS(200, "SUCCESS", "이메일 인증이 완료되었습니다."),
    EMAIL_CONFIRM_CHECK_FAILURE(400, "FAILURE", "이메일 인증 코드가 일치하지 않습니다."),

    USER_CREATE(201, "SUCCESS", "회원가입이 완료되었습니다."),
    USER_GET(200, "SUCCESS", "회원 정보 조회가 완료되었습니다."),

    PROFILE_UPDATE(201, "SUCCESS", "프로필 수정이 완료되었습니다."),
    MESSAGE_UPDATE(201, "SUCCESS", "상태메시지 수정이 완료되었습니다."),
    INFO_UPDATE(201, "SUCCESS", "회원 정보 수정이 완료되었습니다."),

    PASSWORD_SUCCESS(200, "SUCCESS", "비밀번호가 일치합니다."),
    PASSWORD_FAILURE(400, "FAILURE", "비밀번호가 일치하지 않습니다."),


    /** 현우 **/
    CARE_USER_CREATE(201, "SUCCESS", "돌봄 가족 생성 되었습니다."),
    CARE_USER_UPDATE(201, "SUCCESS", "돌봄 가족 업데이트 완료되었습니다."),
    CARE_USER_DELETE(200, "SUCCESS", "돌봄 가족 삭제 완료되었습니다."),
    CARE_USER_OVER_FAILURE(403, "FAILURE", "돌봄 가족 수가 초과 되었습니다"),
    CARE_USER_DUPLICATE_FAILURE(403, "FAILURE", "이미 존재하는 돌봄 가족입니다"),
    CARE_USER_FAILURE(400, "FAILURE", "돌봄 가족 요청이 실패하였습니다."),

    FAMILY_GET(200, "SUCCESS", "가족 구성원 조회가 완료되었습니다."),
    FAMILY_CREATE(201, "SUCCESS", "가족 생성 되었습니다."),
    FAMILY_CHANGE(200, "SUCCESS", "가족이 다른 집으로 이동되었습니다."),
    FAMILY_DELETE(201, "SUCCESS", "가족을 내보냈습니다."),
    FAMILY_EXIT(200, "SUCCESS", "집에서 나갔습니다."),
    FAMILY_DELTET_FAILURE(401, "FAILURE", "가족 삭제 권한이 없습니다."),
    FAMILY_OVER_FAILURE(403, "FAILURE", "가족 수가 초가 되었습니다"),
    FAMILY_MEMBER_OVER_FAILURE(403, "FAILURE", "가족 구성원 수가 초과 되었습니다"),
    FAMILY_DUPLICATE_FAILURE(403, "FAILURE", "이미 존재하는 가족입니다"),
    FAMILY_FAILURE(400, "FAILURE", "가족 요청이 실패하였습니다."),

    /** 재영 **/
    NOTE_CREATE(201, "SUCCESS", "기록 작성이 완료되었습니다."),
    NOTE_GET(200, "SUCCESS", "기록 조회가 완료되었습니다."),
    NOTE_DELETE(200, "SUCCESS", "기록 삭제가 완료되었습니다."),
    NOTE_FAILURE(400, "FAILURE", "기록 요청이 실패하였습니다."),

    TODO_CREATE(201, "SUCCESS", "할일 작성이 완료되었습니다."),
    TODO_GET(200, "SUCCESS", "할일 조회가 완료되었습니다."),
    TODO_UPDATE(201, "SUCCESS", "할일 수정이 완료되었습니다."),
    TODO_DELETE(200, "SUCCESS", "할일 삭제가 완료되었습니다."),
    TODO_FAILURE(400, "FAILURE", "할일 요청이 실패하였습니다."),

    GOAL_CREATE(201, "SUCCESS", "목표 작성이 완료되었습니다."),
    GOAL_GET(200, "SUCCESS", "목표 조회가 완료되었습니다."),
    GOAL_UPDATE(201, "SUCCESS", "목표 수정이 완료되었습니다."),
    GOAL_DELETE(200, "SUCCESS", "목표 삭제가 완료되었습니다."),
    GOAL_FAILURE(400, "FAILURE", "목표 요청이 실패하였습니다."),

    /** 수민 **/
    KAKAO_USER_CREATE(201, "SUCCESS", "카카오 회원가입이 완료되었습니다."),



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
