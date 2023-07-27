package dev.umc.healody.today.note.dto;

import dev.umc.healody.today.note.type.Hospital;
import dev.umc.healody.today.note.type.Purpose;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter @NoArgsConstructor
public class HospitalResponseDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String title;
    private String memo;
    private Purpose purpose;
    private String name;
    private String surgery;

    @Builder
    public HospitalResponseDto(Date date, String title, String memo, Purpose purpose, String name, String surgery) {
        this.date = date;
        this.title = title;
        this.memo = memo;
        this.purpose = purpose;
        this.name = name;
        this.surgery = surgery;
    }

    public HospitalResponseDto toDto(Hospital hospital) {
        return HospitalResponseDto.builder()
                .date(hospital.getDate())
                .title(hospital.getTitle())
                .memo(hospital.getMemo())
                .purpose(hospital.getPurpose())
                .name(hospital.getName())
                .surgery(hospital.getSurgery())
                .build();
    }
}
