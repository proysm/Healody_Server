package dev.umc.healody.today.note;

import dev.umc.healody.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Inheritance(strategy = InheritanceType.JOINED)  // JOIN TABLE 전략
@DiscriminatorColumn
@SuperBuilder @NoArgsConstructor
@Entity @Getter
public class Note {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "note_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;
    private String title;
    private String memo;

    private String noteType;  // 노트 타입 구분

    public Note(User user, Date date, String title, String memo, String noteType) {
        this.user = user;
        this.date = date;
        this.title = title;
        this.memo = memo;
        this.noteType = noteType;
    }

    public void updateDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            this.date = format.parse(date);
        } catch (ParseException e) {
            System.out.println("updateDate 예외 처리");
        }
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }
}
