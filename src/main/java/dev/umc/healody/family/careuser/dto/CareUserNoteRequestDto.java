package dev.umc.healody.family.careuser.dto;

import dev.umc.healody.family.careuser.domain.CareUser;
import dev.umc.healody.family.careuser.domain.CareUserNote;
import dev.umc.healody.today.note.type.Purpose;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter @NoArgsConstructor
public class CareUserNoteRequestDto {

    private Long userId;
    private String date;
    private String title;
    private String memo;

    // Hospital
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
    public CareUserNoteRequestDto(Long userId, String date, String title, String memo, Purpose purpose, String hospitalName, String surgery, String medicine1, String medicine2, String medicine3, String place, String symptomName) {
        this.userId = userId;
        this.date = date;
        this.title = title;
        this.memo = memo;
        this.purpose = purpose;
        this.hospitalName = hospitalName;
        this.surgery = surgery;
        this.medicine1 = medicine1;
        this.medicine2 = medicine2;
        this.medicine3 = medicine3;
        this.place = place;
        this.symptomName = symptomName;
    }

    public CareUserNote toEntityHospital(CareUser user, Date date) {
        return CareUserNote.builder()
                .careUser(user)
                .date(date)
                .title(title)
                .memo(memo)
                .purpose(purpose)
                .hospitalName(hospitalName)
                .surgery(surgery)
                .noteType("H")
                .build();
    }

    public CareUserNote toEntityMedicine(CareUser user, Date date) {
        return CareUserNote.builder()
                .careUser(user)
                .date(date)
                .title(title)
                .memo(memo)
                .medicine1(medicine1)
                .medicine2(medicine2)
                .medicine3(medicine3)
                .place(place)
                .noteType("M")
                .build();
    }

    public CareUserNote toEntitySymptom(CareUser user, Date date) {
        return CareUserNote.builder()
                .careUser(user)
                .date(date)
                .title(title)
                .memo(memo)
                .symptomName(symptomName)
                .noteType("S")
                .build();
    }
}
