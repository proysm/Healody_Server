package dev.umc.healody.today.goal;

import dev.umc.healody.today.goal.dto.GoalRequestDto;
import dev.umc.healody.today.goal.dto.GoalResponseDto;
import dev.umc.healody.today.goal.dto.RecordsResponseDto;
import dev.umc.healody.user.entity.User;
import dev.umc.healody.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    private final RecordsRepository recordsRepository;

    @Transactional
    public String createGoal(Long userId, GoalRequestDto requestDto) {
        // 사용자 아이디를 통해 Entity 조회
        Optional<User> byId = userRepository.findById(userId);
        User user = new User();
        if(byId.isPresent())
            user = byId.get();

        Goal goal = requestDto.toEntity(user);
        goalRepository.save(goal);
        return goal.getStartDate().getMonthValue() + "월 회원님의 목표는 " + goal.getBehavior().getDisplayValue() + "입니다";
    }

    @Transactional
    public RecordsResponseDto createRecord(Long goalId) {
        Goal goal = goalRepository.findById(goalId).get();

        int startCnt = goal.getStartDate().getDayOfMonth();
        int nowCnt = LocalDate.now().getDayOfMonth();

        RecordsResponseDto responseDto = new RecordsResponseDto();

        // 날짜가 지났다면
        if((nowCnt - startCnt) == goal.getCnt()) {
            Records records = Records.builder()
                    .goal(goal)
                    .today(LocalDate.now())
                    .val("0")
                    .behavior(goal.getBehavior().getDisplayValue())
                    .days(goal.getCnt() + 1)
                    .build();

            goal.plusCnt();
            recordsRepository.save(records);
        }

        Records records = recordsRepository.findByGoalIdAndDays(goalId, goal.getCnt());
        return responseDto.toDto(records);
    }

    @Transactional
    public RecordsResponseDto dateCreateRecord(Long goalId, String date) {
        Goal goal = goalRepository.findById(goalId).get();

        LocalDate localDate = LocalDate.parse(date);

        int startCnt = goal.getStartDate().getDayOfMonth();
        int nowCnt = localDate.getDayOfMonth();

        RecordsResponseDto responseDto = new RecordsResponseDto();

        // 날짜가 지났다면
        if((nowCnt - startCnt) == goal.getCnt()) {
            Records records = Records.builder()
                    .goal(goal)
                    .today(localDate)
                    .val("0")
                    .behavior(goal.getBehavior().getDisplayValue())
                    .days(goal.getCnt() + 1)
                    .build();
            recordsRepository.save(records);
            goal.plusCnt();
        }

        Records records = recordsRepository.findByGoalIdAndDays(goalId, goal.getCnt());
        return responseDto.toDto(records);
    }

    @Transactional
    public String updateRecords(Long recordsId, String val) {
        Records records = recordsRepository.findById(recordsId).get();
        records.updateVal(val);

        return val + " 값으로 업데이트 되었습니다.";
    }

    public GoalResponseDto findGoal(Long goalId) {
        Optional<Goal> byGoalId = goalRepository.findById(goalId);
        Goal goal = new Goal();
        if(byGoalId.isPresent())
            goal = byGoalId.get();

        GoalResponseDto responseDto = new GoalResponseDto();
        return responseDto.toDto(goal);
    }

    @Transactional
    public Long updateGoal(Long goalId, GoalRequestDto requestDto) {
        Optional<Goal> byGoalId = goalRepository.findById(goalId);
        Goal goal = new Goal();
        if(byGoalId.isPresent())
            goal = byGoalId.get();

        if(requestDto.getStartDate() != null)
            goal.updateStartDate(requestDto.getStartDate());

        if(requestDto.getEndDate() != null)
            goal.updateEndDate(requestDto.getEndDate());

        if(requestDto.getBehavior() != null)
            goal.updateBehavior(requestDto.getBehavior());

        if(requestDto.getTarget() != null)
            goal.updateTarget(requestDto.getTarget());

        return goal.getId();
    }

    @Transactional
    public void deleteGoal(Long goalId) {
        // 이거 따로 메소드로 만드는게 좋을듯
        Optional<Goal> byGoalId = goalRepository.findById(goalId);
        Goal goal = new Goal();
        if(byGoalId.isPresent())
            goal = byGoalId.get();

        goalRepository.delete(goal);
    }
}
