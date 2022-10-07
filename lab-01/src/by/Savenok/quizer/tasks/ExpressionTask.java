package by.Savenok.quizer.tasks;

import by.Savenok.quizer.Result;

import java.util.Objects;

public class ExpressionTask implements Task {
    public ExpressionTask(String statement, String answer) {
        statement_ = statement;
        answer_ = answer;
    }
    public String getText(){
        return statement_;
    }
    public Result validate(String answer){
        if (Objects.equals(answer, answer_)) {
            return Result.OK;
        } else {
            return Result.WRONG;
        }
    }
    String statement_;
    String answer_;
}
