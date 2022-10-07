public class ExpressionTaskGenerator implements TaskGenerator {
    ExpressionTaskGenerator(
            int minNumber,
            int maxNumber,
            boolean generateSum,
            boolean generateDifference,
            boolean generateMultiplication,
            boolean generateDivision
    ) {
        minNumber_ = minNumber;
        maxNumber_ = maxNumber;
        generateSum_ = generateSum;
        generateDifference_ = generateDifference;
        generateMultiplication_ = generateMultiplication;
        generateDivision_ = generateDivision;
    }

    private String ParseTask(int first, int second, char operator) {
        String ans = Integer.toString(first);
        ans += operator;
        ans += Integer.toString(second);
        return ans;
    }

    public ExpressionTask generate() {
        int first = Randomizer.GenerateNumber(minNumber_, maxNumber_);
        int second = Randomizer.GenerateNumber(minNumber_, maxNumber_);
        char[] enable_operators = new char[4];
        enable_operators[0] = '0';
        enable_operators[1] = '0';
        enable_operators[2] = '0';
        enable_operators[3] = '0';
        if (generateSum_) {
            enable_operators[0] = '+';
        }
        if (generateDifference_) {
            enable_operators[1] = '-';
        }
        if (generateMultiplication_) {
            enable_operators[2] = '*';
        }
        if (generateDivision_) {
            enable_operators[3] = '/';
        }
        char operator = '0';
        while (operator == '0') {
            int index = Randomizer.GenerateNumber(0, 4);
            operator = enable_operators[index];
        }
        return new ExpressionTask(ParseTask(first, second, operator), CalculateAnswer(first, second, operator));
    }

    String CalculateAnswer(int first, int second, char operator) {
        if (operator == '+') {
            return Integer.toString(first + second);
        }
        if (operator == '-') {
            return Integer.toString(first - second);
        }
        if (operator == '*') {
            return Integer.toString(first * second);
        }
        if (operator == '/') {
            if (second == 0) {
                second = 1;
            }
            return Integer.toString(first / second);
        }
        return "bad input"; // bad
    }

    int minNumber_;
    int maxNumber_;
    boolean generateSum_ = false;
    boolean generateDifference_ = false;
    boolean generateMultiplication_ = false;
    boolean generateDivision_ = false;
}
