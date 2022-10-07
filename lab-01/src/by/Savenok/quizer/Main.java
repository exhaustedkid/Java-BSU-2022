package by.Savenok.quizer;

import by.Savenok.quizer.task_generators.TaskGenerator;
import by.Savenok.quizer.task_generators.math_task_generators.EquationTaskGenerator;
import by.Savenok.quizer.task_generators.math_task_generators.ExpressionTaskGenerator;
import by.Savenok.quizer.task_generators.TextTaskGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;


public class Main {
    static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> generators = new HashMap<>();
        generators.put("Expression mode (5 tasks)", new Quiz(new ExpressionTaskGenerator(0, 100, true, true, true, true), 5));
        generators.put("Expression mode (10 tasks)", new Quiz(new ExpressionTaskGenerator(0, 100, true, true, true, true), 10));
        generators.put("Expression mode (15 tasks)", new Quiz(new ExpressionTaskGenerator(0, 100, true, true, true, true), 15));
        generators.put("Equation mode (10 tasks)", new Quiz(new EquationTaskGenerator(0, 100, true, true, true, true), 10));
        generators.put("Text mode (3 tasks)", new Quiz(new TextTaskGenerator(TextTaskGenerator.DifficultyLevel.HARD), 3));
        return generators;
    }
    public static void main(String[] args) {
        System.out.println("Choose task mode:");
        Map<String, Quiz> generators = getQuizMap();
        for (Map.Entry<String, Quiz> it : generators.entrySet())
        {
            System.out.println(it.getKey());
        }
        Scanner in = new Scanner(System.in);
        String mode = in.nextLine();
        System.out.println("Choose tasks count:");
        String count = in.nextLine();
        generators.get(mode + " mode " + "(" + count + " tasks)").StartQuiz();

        in.close();
    }
}