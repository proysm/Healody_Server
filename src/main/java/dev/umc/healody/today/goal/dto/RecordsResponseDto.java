package dev.umc.healody.today.goal.dto;

import dev.umc.healody.today.goal.Records;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class RecordsResponseDto {

    private Long recordsId;
    private Long days;
    private String behavior;
    private String val;

    @Builder
    public RecordsResponseDto(Long recordsId, Long days, String behavior, String val) {
        this.recordsId = recordsId;
        this.days = days;
        this.behavior = behavior;
        this.val = val;
    }

    public RecordsResponseDto toDto(Records records) {
        return RecordsResponseDto.builder()
                .recordsId(records.getId())
                .days(records.getDays())
                .behavior(records.getBehavior())
                .val(records.getVal())
                .build();
    }
}
