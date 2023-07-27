package dev.umc.healody.today.note;

import dev.umc.healody.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Inheritance(strategy = InheritanceType.JOINED)  // JOIN TABLE 전략
@DiscriminatorColumn(name = "NOTE_TYPE")
@SuperBuilder @NoArgsConstructor
@Entity @Getter
public class Note {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "note_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String title;
    private String memo;

    public Note(User user, Date date, String title, String memo) {
        this.user = user;
        this.date = date;
        this.title = title;
        this.memo = memo;
    }

    public void updateDate(Date date) {
        this.date = date;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }
}
