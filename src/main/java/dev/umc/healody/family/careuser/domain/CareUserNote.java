package dev.umc.healody.family.careuser.domain;

import dev.umc.healody.today.note.type.Purpose;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Entity @Getter
public class CareUserNote {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "note_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "careuser_id")
    private CareUser careUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    private String title;

    private String memo;

    private String noteType;  // 노트 타입 구분

    // Hospital
    @Enumerated(value = EnumType.STRING)
    private Purpose purpose;
    private String hospitalName;
    private String surgery;

    // Medicine
    private String medicine1;
    private String medicine2;
    private String medicine3;
    private String place;

    // Symptom
    private String symptomName;

    @Builder
    public CareUserNote(CareUser careUser, Date date, String title, String memo, String noteType, Purpose purpose, String hospitalName, String surgery, String medicine1, String medicine2, String medicine3, String place, String symptomName) {
        this.careUser = careUser;
        this.date = date;
        this.title = title;
        this.memo = memo;
        this.noteType = noteType;
        this.purpose = purpose;
        this.hospitalName = hospitalName;
        this.surgery = surgery;
        this.medicine1 = medicine1;
        this.medicine2 = medicine2;
        this.medicine3 = medicine3;
        this.place = place;
        this.symptomName = symptomName;
    }
}
