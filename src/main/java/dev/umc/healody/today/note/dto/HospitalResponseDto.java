package dev.umc.healody.today.note.dto;

import dev.umc.healody.today.note.type.Hospital;
import dev.umc.healody.today.note.type.Purpose;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter @NoArgsConstructor
public class HospitalResponseDto {

    private String date;
    private String title;
    private String memo;
    private Purpose purpose;
    private String name;
    private String surgery;
    private String noteType;

    @Builder
    public HospitalResponseDto(String date, String title, String memo, Purpose purpose, String name, String surgery, String noteType) {
        this.date = date;
        this.title = title;
        this.memo = memo;
        this.purpose = purpose;
        this.name = name;
        this.surgery = surgery;
        this.noteType = noteType;
    }

    public HospitalResponseDto toDto(Hospital hospital) {

        Date realDate = hospital.getDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = dateFormat.format(realDate);

        return HospitalResponseDto.builder()
                .date(stringDate)
                .title(hospital.getTitle())
                .memo(hospital.getMemo())
                .purpose(hospital.getPurpose())
                .name(hospital.getName())
                .surgery(hospital.getSurgery())
                .noteType(hospital.getNoteType())
                .build();
    }
}
