package dev.umc.healody.today.note.dto;

import dev.umc.healody.today.note.type.Medicine;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter @NoArgsConstructor
public class MedicineResponseDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String title;
    private String memo;
    private String medicine1;
    private String medicine2;
    private String medicine3;
    private String place;
    private String noteType;

    @Builder
    public MedicineResponseDto(Date date, String title, String memo, String medicine1, String medicine2, String medicine3, String place, String noteType) {
        this.date = date;
        this.title = title;
        this.memo = memo;
        this.medicine1 = medicine1;
        this.medicine2 = medicine2;
        this.medicine3 = medicine3;
        this.place = place;
        this.noteType = noteType;
    }

    public MedicineResponseDto toDto(Medicine medicine) {
        return MedicineResponseDto.builder()
                .date(medicine.getDate())
                .title(medicine.getTitle())
                .memo(medicine.getMemo())
                .medicine1(medicine.getMedicine1())
                .medicine2(medicine.getMedicine2())
                .medicine3(medicine.getMedicine3())
                .place(medicine.getPlace())
                .noteType(medicine.getNoteType())
                .build();
    }
}
