package dev.umc.healody.today.goal;

import dev.umc.healody.common.SuccessResponse;
import dev.umc.healody.common.SuccessStatus;
import dev.umc.healody.today.goal.dto.GoalRequestDto;
import dev.umc.healody.today.goal.dto.GoalResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("/api")
public class GoalController {

    private final GoalService goalService;

    @PostMapping("/goal")
    public SuccessResponse<String> createGoal(@RequestBody GoalRequestDto requestDto) {
        String goalMessage = goalService.createGoal(requestDto.getUserId(), requestDto);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, goalMessage);
    }

    @GetMapping("/goal/{goalId}")
    public SuccessResponse<GoalResponseDto> findGoal(@PathVariable Long goalId) {
        GoalResponseDto responseDto = goalService.findGoal(goalId);
        return new SuccessResponse<>(SuccessStatus.SUCCESS, responseDto);
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
