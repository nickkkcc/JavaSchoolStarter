package com.digdes.school.models.partsofquery;

import com.digdes.school.models.enums.OperatorType;

import java.util.ArrayList;
import java.util.regex.Pattern;

/*
* Класс, реализующий работы с левой частью выражения, интерпретирующийся на колонки таблицы.
* */
public class ColumnValue {
    private final Class type;
    private final ArrayList<OperatorType> operators;
    private StringBuilder operatorsPattern;
    private final String name;
    private String operandsPattern;
    private final String acceptableValuesPattern;


    public ColumnValue(Class type, ArrayList<OperatorType> operators, String name, String acceptableValuesPattern) {
        this.type = type;
        this.operators = operators;
        this.name = name;
        this.acceptableValuesPattern = acceptableValuesPattern;
        getOperatorValues();
        getParseOperandPattern();
    }

    public ColumnValue(ColumnValue columnValue){
        this.type = columnValue.type;
        this.name = columnValue.name;
        this.acceptableValuesPattern = columnValue.acceptableValuesPattern;
        this.operators = columnValue.operators;
        this.operatorsPattern = columnValue.operatorsPattern;
        this.operandsPattern = columnValue.operandsPattern;
    }

    private void getParseOperandPattern() {
        if (type.equals(String.class)) {
            operandsPattern = "'(.+?)'";
        } else if (type.equals(Long.class)) {
            operandsPattern = "[0-9]+|-[0-9]+]";
        } else if (type.equals(Double.class)) {
            operandsPattern = "[0-9]+[.][0-9]+|-[0-9]+[.][0-9]+";
        } else if (type.equals(Boolean.class)) {
            operandsPattern = "(?i)true|false";
        }
    }

    public String getAcceptableValues() {
        return acceptableValuesPattern;
    }

    public Class getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    private void getOperatorValues() {
        operatorsPattern = new StringBuilder();
        operators.forEach(operatorType -> operatorsPattern.append(operatorType.toString()).append("|"));
        operatorsPattern.deleteCharAt(operatorsPattern.length() - 1);
    }

    public OperatorType operatorContains(String operator) {
        for (OperatorType operatorType : operators) {
            if (operatorType.toString().equalsIgnoreCase(operator)) {
                return operatorType;
            }
        }
        return null;
    }

    public String getOperatorPattern() {
        return operatorsPattern.toString();
    }

    public Class getOperandType(String operand) {
        if (Pattern.matches(operandsPattern, operand)) {
            return type;
        }
        return null;
    }

    public boolean isTrueName(String leftOperator){
        return name.equalsIgnoreCase(leftOperator.replace("'", ""));
    }
}
