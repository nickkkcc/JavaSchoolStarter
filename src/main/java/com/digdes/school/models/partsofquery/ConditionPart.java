package com.digdes.school.models.partsofquery;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class ConditionPart {
    private final ArrayList<Expression> expressions = new ArrayList<>();
    private final ArrayList<String> leftOperator = new ArrayList<>();
    private final ArrayList<String> rightOperator = new ArrayList<>();

    /*
    * Добавление выражения (выражение вида 'age' = 5464)
    * */
    public void addExpression(String exp, String operator) throws Exception {
        expressions.add(new Expression(exp, false));
        if (leftOperator.isEmpty()) {
            leftOperator.add(null);
        } else {
            leftOperator.add(rightOperator.get(rightOperator.size() - 1));
        }
        if (!Objects.equals(operator, null)) {
            rightOperator.add(operator.toLowerCase());
        } else {
            rightOperator.add(null);
        }

    }
    /*
    * Функция для првоерки подходит ли строка под условную часть
    */
    public boolean isSatisfiesWhereCondition(Map<String, Object> row) {
        ArrayList<Boolean> colConditionAnswer = new ArrayList<>();
        expressions.forEach(expression -> {
            Object obj = row.get(expression.getCol());
            colConditionAnswer.add(expression.makeComparison(obj));
        });
        return getComparisonResult(colConditionAnswer);
    }

    /*
    * Функция подсчета условий в условной части
    */
    private boolean getComparisonResult(ArrayList<Boolean> colConditionAnswer) {
        ArrayList<Boolean> colAnswerOR = new ArrayList<>();
        boolean temp = true;
        for (int i = 0; i < colConditionAnswer.size(); ++i) {
            if (Objects.equals(rightOperator.get(i), "and") || Objects.equals(leftOperator.get(i), "and")) {
                temp = temp && colConditionAnswer.get(i);
                if (Objects.equals(rightOperator.get(i), "or") || Objects.equals(rightOperator.get(i), null)) {
                    colAnswerOR.add(temp);
                    temp = true;
                }
            } else if (Objects.equals(rightOperator.get(i), null) && Objects.equals(leftOperator.get(i), "or") ||
                    Objects.equals(rightOperator.get(i), "or") && Objects.equals(leftOperator.get(i), "or") ||
                    leftOperator.size() == 1 || Objects.equals(rightOperator.get(i), "or")) {
                colAnswerOR.add(colConditionAnswer.get(i));
            }
        }
        return colAnswerOR.stream().reduce((first, second) -> first || second).orElse(false);
    }
}
