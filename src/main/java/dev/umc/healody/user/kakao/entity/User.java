package dev.umc.healody.user.kakao.entity;

import dev.umc.healody.user.kakao.model.GenderType;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert // insert시에 null인 필드를 제외시켜준다.
@Entity // User 클래스가 MySQL에 테이블이 생성된다.
public class User {

  @Id //Primary key가 된다.
  @GeneratedValue(strategy = GenerationType.AUTO) // mysql-Auto
  private long user_id;

  @Column(nullable = false, length = 11)
  private String phone;

  @Column(nullable = false)
  private java.sql.Date birth;

  @Column(nullable = false, length = 40)
  private String email;

  @Enumerated(EnumType.STRING) // DB는 GenderType이라는게 없기 때문에 알려줘야 한다.
  private GenderType gender;

  @Column(nullable = false, length = 16)
  private String pw;

  @Column(nullable = true)
  private String image;

  @Column(nullable = true, length = 20)
  private String nickname;

  @Column(nullable = true, length = 40)
  private String message;

  @Column(nullable = false)
  private long familyCnt;


  public long getUserId() {return user_id;}

  public void setUserId(long user_id) {this.user_id = user_id;}


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public java.sql.Date getBirth() {return birth;}

  public void setBirth(java.sql.Date birth) {this.birth = birth;}


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public GenderType getGender() {
    return gender;
  }

  public void setGender(GenderType gender) {
    this.gender = gender;
  }


  public String getPw() {
    return pw;
  }

  public void setPw(String pw) {
    this.pw = pw;
  }


  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  public long getFamilyCnt() {
    return familyCnt;
  }

  public void setFamilyCnt(long familyCnt) {
    this.familyCnt = familyCnt;
  }


  public void buildUser(long userId, String phone, java.sql.Date birth, String email, GenderType gender,
                        String pw, String image, String nickname, String message, long familyCnt){

    setUserId(userId);
    setPhone(phone);
    setBirth(birth);
    setEmail(email);
    setGender(gender);
    setPw(pw);
    setImage(image);
    setNickname(nickname);
    setMessage(message);
    setFamilyCnt(familyCnt);

  }

}
