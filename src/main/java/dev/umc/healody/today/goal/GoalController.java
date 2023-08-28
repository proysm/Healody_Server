package dev.umc.healody.today.goal;

import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.today.goal.dto.GoalRequestDto;
import dev.umc.healody.today.goal.dto.RecordsRequestDto;
import dev.umc.healody.today.goal.dto.RecordsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static dev.umc.healody.common.FindUserInfo.getCurrentUserId;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class GoalController {

    private final GoalService goalService;

    @PostMapping("/goal")
    public SuccessResponse<String> createGoal(@RequestBody GoalRequestDto requestDto) {
        String goalMessage = goalService.createGoal(getCurrentUserId(), requestDto);
        return new SuccessResponse<>(SuccessStatus.GOAL_CREATE, goalMessage);
    }

    @GetMapping("/goal/{goalId}")
    public SuccessResponse<RecordsResponseDto> findGoal(@PathVariable Long goalId) {
        RecordsResponseDto responseDto = goalService.createRecord(goalId);
        return new SuccessResponse<>(SuccessStatus.GOAL_GET, responseDto);
    }

    @GetMapping("/goal/{goalId}/{date}")
    public SuccessResponse<RecordsResponseDto> dateFindGoal(@PathVariable Long goalId, @PathVariable String date) {
        RecordsResponseDto responseDto = goalService.dateCreateRecord(goalId, date);
        return new SuccessResponse(SuccessStatus.GOAL_GET, responseDto);
    }

    @PatchMapping("/goal/{goalId}")
    public SuccessResponse<String> updateGoalVal(@PathVariable Long goalId, @RequestBody RecordsRequestDto requestDto) {
        String updateMessage = goalService.updateRecords(goalId, requestDto.getVal());
        return new SuccessResponse<>(SuccessStatus.GOAL_UPDATE, updateMessage);
    }

    @DeleteMapping("/goal/{goalId}")
    public SuccessResponse deleteGoal(@PathVariable Long goalId) {
        goalService.deleteGoal(goalId);
        return new SuccessResponse(SuccessStatus.GOAL_DELETE);
    }
}
