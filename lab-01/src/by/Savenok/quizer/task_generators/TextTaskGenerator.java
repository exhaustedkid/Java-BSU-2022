package by.Savenok.quizer.task_generators;

import by.Savenok.quizer.tasks.TextTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TextTaskGenerator implements TaskGenerator {
    public TextTaskGenerator(DifficultyLevel level) {

        difficulty_level_ = level;
        if (difficulty_level_ == DifficultyLevel.HARD) {
//            File file = new File("D:\\IntelliJ Projects\\Java-BSU-2022\\lab-01\\src\\by\\Savenok\\quizer\\input.txt");
//            br_ = new BufferedReader(new FileReader(file));
        }
        if (difficulty_level_ == DifficultyLevel.IMPOSSIBLE) {
            scanner_ = new Scanner("TextTaskStatementsImpossible.txt");
        }
        if (difficulty_level_ == DifficultyLevel.NIGHTMARE) {
            scanner_ = new Scanner("TextTaskStatementsNightmare.txt");
        }

    }

    public TextTask generate() {
//        String st = scanner_.nextLine();
//        String s = scanner_.nextLine();
//        System.out.print(st);
//        System.out.print(s);
        File file = new File("D:\\IntelliJ Projects\\Java-BSU-2022\\lab-01\\src\\by\\Savenok\\quizer\\input.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line = br.readLine();
            return new TextTask(line, br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TextTask("", "");
    }

    public enum DifficultyLevel {
        HARD,
        IMPOSSIBLE,
        NIGHTMARE
    }

    DifficultyLevel difficulty_level_;
    Scanner scanner_;
    BufferedReader bf_;
}
