package dev.umc.healody.today.goal;

import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.today.goal.dto.GoalRequestDto;
import dev.umc.healody.today.goal.dto.GoalResponseDto;
import dev.umc.healody.today.goal.dto.RecordsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class GoalController {

    private final GoalService goalService;

    @PostMapping("/goal")
    public SuccessResponse<String> createGoal(@RequestBody GoalRequestDto requestDto) {
        String goalMessage = goalService.createGoal(requestDto.getUserId(), requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, goalMessage);
    }

    @GetMapping("/goal/{goalId}")
    public SuccessResponse<GoalResponseDto> findGoal(@PathVariable Long goalId) {
        goalService.createRecord(goalId);
        return new SuccessResponse<>(SuccessStatus.SUCCESS);
    }

    @PatchMapping("/goal/{goalId}")
    public SuccessResponse<String> updateGoalVal(@PathVariable Long goalId, @RequestBody RecordsRequestDto requestDto) {
        String updateMessage = goalService.updateRecords(goalId, requestDto.getVal());
        return new SuccessResponse<>(SuccessStatus.SUCCESS, updateMessage);
    }

    @GetMapping("/goal/{goalId}/{date}")
    public SuccessResponse dateFindGoal(@PathVariable Long goalId, @PathVariable String date) {
        goalService.dateCreateRecord(goalId, date);
        return new SuccessResponse(SuccessStatus.SUCCESS);
    }

    @PatchMapping("/goal/{goalId}")
    public SuccessResponse<Long> updateGoal(@PathVariable Long goalId, @RequestBody GoalRequestDto requestDto) {
        Long findGoalId = goalService.updateGoal(goalId, requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, findGoalId);
    }

    @DeleteMapping("/goal/{goalId}")
    public SuccessResponse deleteGoal(@PathVariable Long goalId) {
        goalService.deleteGoal(goalId);
        return new SuccessResponse(SuccessStatus.SUCCESS);
    }
}
