package com.disciplineapp.logic.calculator.impl;

import com.disciplineapp.Constants;
import com.disciplineapp.entity.User;
import com.disciplineapp.api.service.UserService;
import com.disciplineapp.entity.Challenge;
import com.disciplineapp.entity.habit.Habit;
import com.disciplineapp.logic.calculator.UserScoreCalculator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OLTS on 11.09.2017.
 */
@Component
public class UserScoreCalculatorImpl implements UserScoreCalculator {

    @Resource
    private UserService userService;

    public void calculate(Habit habit) {
        User user = habit.getHabitUser();
        if (habit.isCompleted()) {
            int score;
            if (habit.isAchieved()) {
                score = user.getHabitScore() + habit.getDifficulty() - (habit.getCompletedCount() / Constants.HABIT_BORDER_COUNT);
            } else {
                score = user.getHabitScore() + habit.getDifficulty();
            }
            user.setHabitScore(score);
            habit.setCompletedCount(habit.getCompletedCount() + 1);
        } else {
            if (habit.isAchieved()) {
                return;
            }
            Double score = Math.ceil(habit.getNonCompletedCount() * ((float) habit.getDifficulty() / Constants.HABIT_BORDER_COUNT));
            user.setHabitScore(user.getHabitScore() - score.intValue());
            habit.setNonCompletedCount(habit.getNonCompletedCount() + 1);
        }
        userService.update(user);
    }

    @Override
    public void calculate(Challenge challenge) {
        if (!challenge.isCompleted()) {
            throw new UnsupportedOperationException("challenge is not completed! Calculate operation not supported");
        }
        User user = challenge.getCreatedBy();
        if (challenge.getCompletedCount() == user.getLevel()) {
            user.setAllowedChallenges(user.getAllowedChallenges() + 1);
        }
        userService.update(user);
    }
}
