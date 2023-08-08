package dev.umc.healody.today.note.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor
public class NoteResponseDtoList {

    List<NoteResponseDto> responseDtoList = new ArrayList<>();

    public void addResponseDto(NoteResponseDto responseDto) {
        responseDtoList.add(responseDto);
    }
}
