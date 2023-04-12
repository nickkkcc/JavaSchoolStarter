package com.digdes.school.models.partsofquery;

import com.digdes.school.models.enums.OperatorType;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    private final ArrayList<Object> leftRightExp = new ArrayList<>();
    private OperatorType operator;
    private final String expression;
    private final boolean isValuePart;
    private static final String CAPTURE_PATTERN_VALUE_PART = "([0-9]+[.][0-9]+)|(-[0-9]+[.][0-9]+)|([0-9]+)|(-[0-9]+)|('[\\wаА-яЯ ]+')|((?i)null|true|false|=)";
    private static final String CAPTURE_PATTERN_COND_PART = "([0-9]+[.][0-9]+)|(-[0-9]+[.][0-9]+)|([0-9]+)|(-[0-9]+)|('[\\wаА-яЯ% ]+')|(?i)true|false|=|!=|>=|<=|<|>|like|ilike";
    private static final String ALL_COND_PART_OPERAND = "([0-9]+|-[0-9]+|'.+'|[0-9]+[.][0-9]+|-[0-9]+[.][0-9]+|(?i)true|false)";

    private static final String LIKE_OPERATOR_PATTERN = "%[\\wаА-яЯ ]+%|[\\wаА-яЯ ]+%|%[\\wаА-яЯ ]+|[\\wаА-яЯ ]+";
    private static final HashSet<ColumnValue> colValues = new HashSet<>(
            Arrays.asList(
                    new ColumnValue(Long.class,
                            new ArrayList<>(Arrays.asList(
                                    OperatorType.EQ,
                                    OperatorType.NOT_EQ,
                                    OperatorType.GREAT_THAN_EQ,
                                    OperatorType.LESS_THAT_EQ,
                                    OperatorType.LESS,
                                    OperatorType.GREATER
                            )), "id", "([0-9]+|-[0-9]+"
                    ), new ColumnValue(Long.class,
                            new ArrayList<>(Arrays.asList(
                                    OperatorType.EQ,
                                    OperatorType.NOT_EQ,
                                    OperatorType.GREAT_THAN_EQ,
                                    OperatorType.LESS_THAT_EQ,
                                    OperatorType.LESS,
                                    OperatorType.GREATER
                            )), "age", "([0-9]+|-[0-9]+"
                    ), new ColumnValue(String.class,
                            new ArrayList<>(Arrays.asList(
                                    OperatorType.EQ,
                                    OperatorType.NOT_EQ,
                                    OperatorType.LIKE,
                                    OperatorType.ILIKE
                            )), "lastName", "(.+"
                    ), new ColumnValue(Double.class,
                            new ArrayList<>(Arrays.asList(
                                    OperatorType.EQ,
                                    OperatorType.NOT_EQ,
                                    OperatorType.GREAT_THAN_EQ,
                                    OperatorType.LESS_THAT_EQ,
                                    OperatorType.LESS,
                                    OperatorType.GREATER
                            )), "cost", "([0-9]+[.][0-9]+|-[0-9]+[.][0-9]+"
                    ), new ColumnValue(Boolean.class,
                            new ArrayList<>(Arrays.asList(
                                    OperatorType.EQ,
                                    OperatorType.NOT_EQ
                            )), "active", "(true|false"
                    ))
    );

    public Expression(String expression, boolean isValuePart) throws Exception {
        this.expression = expression;
        this.isValuePart = isValuePart;
        parseExpression();
    }

    private void parseExpression() throws Exception {
        commonValidation();
        if (!isValuePart) {
            operatorValidation();
        }

    }

    // Функция валидации оператора для левойго и правого операнда
    private void operatorValidation() throws Exception {
        boolean leftOperandValid = false;
        boolean rightOperandValid = false;
        ColumnValue tempCol = null;
        for (ColumnValue columnValue : colValues) {
            if (Objects.equals(columnValue.getOperandType(leftRightExp.get(2).toString()), columnValue.getType())) {
                OperatorType tempType = columnValue.operatorContains(leftRightExp.get(1).toString());
                if (!Objects.equals(tempType, null)) {
                    operator = tempType;
                    rightOperandValid = true;
                    getRightOperand(leftRightExp.get(2).toString(), columnValue.getType());
                }
            }
            if (columnValue.isTrueName(leftRightExp.get(0).toString())) {
                leftOperandValid = true;
                tempCol = new ColumnValue(columnValue);
            }
        }
        if (!leftOperandValid || !rightOperandValid) {
            throw new Exception("[Validation error in Condition part {invalid operator or operands}] in" + expression);
        }
        leftRightExp.set(0, tempCol);
    }

    // Запись значения правого оператора в тип Value
    private void getRightOperand(String string, Class type) {
        if (string.equals("null")) {
            leftRightExp.set(2, null);
        }
        if (type.equals(Long.class)) {
            leftRightExp.set(2, new Value(Long.class, Long.parseLong(string)));
        }
        if (type.equals(Double.class)) {
            leftRightExp.set(2, new Value(Double.class, Double.parseDouble(string)));
        }
        if (type.equals(Boolean.class)) {
            leftRightExp.set(2, new Value(Boolean.class, Boolean.parseBoolean(string)));
        }
        if (type.equals(String.class)) {
            leftRightExp.set(2, new Value(String.class, string.replace("'","")));
        }
    }

    /*
    * Функция валидации запроса и проверки типов для части значений.
    * */
    private void commonValidation() throws Exception {
        StringBuilder str = new StringBuilder();
        if (isValuePart) {
            str.append(CAPTURE_PATTERN_VALUE_PART);
        } else {
            str.append(CAPTURE_PATTERN_COND_PART);
        }
        Pattern pattern = Pattern.compile(str.toString());
        Matcher matcher = pattern.matcher(expression);
        boolean result = colValues.stream().anyMatch(columnValue -> {
            if (!str.isEmpty()) {
                str.delete(0, str.length());
            }

            str.
                    append("(?i)'").
                    append(columnValue.getName()).
                    append("'([ ]*)(");
            if (isValuePart) {
                str.
                        append("=").
                        append(")([ ]*)(").
                        append(columnValue.getAcceptableValues()).
                        append(")").
                        append("|(?i)null)");
            } else {
                str.
                        append(columnValue.getOperatorPattern()).
                        append(")([ ]*)").
                        append(ALL_COND_PART_OPERAND);

            }
            if (Pattern.matches(str.toString(), expression)) {
                while (matcher.find()) {
                    leftRightExp.add(matcher.group());
                }
                return true;
            } else {
                return false;
            }
        });
        if (!result) {
            if (isValuePart) {
                throw new Exception("[Validation error in ValuesPart] in " + expression);
            }
            throw new Exception("[Validation error in ConditionsPart] in " + expression);
        }

        if (isValuePart) {
            colValues.stream().anyMatch(s -> {
                if (s.getName().equalsIgnoreCase(leftRightExp.get(0).toString().replace("'", ""))) {
                    leftRightExp.set(0, s);
                    return true;
                }
                return false;
            });
            operator = OperatorType.EQ;
            for (ColumnValue columnValue : colValues) {
                if (Objects.equals(columnValue.getOperandType(leftRightExp.get(2).toString()), columnValue.getType())) {
                    getRightOperand(leftRightExp.get(2).toString(), columnValue.getType());
                }
            }
            if (leftRightExp.get(2).toString().equalsIgnoreCase("null")) {
                leftRightExp.set(2, null);
            }

        }

    }

    public String getCol() {
        return ((ColumnValue) (leftRightExp.get(0))).getName();
    }


    public Object getData() {
        if (leftRightExp.get(2) == null) {
            return null;
        }
        return ((Value) (leftRightExp.get(2))).value();
    }

    public Boolean makeComparison(Object colValue) {
        switch (operator) {
            case EQ -> {
                return doEquals(colValue, getData());
            }
            case NOT_EQ -> {
                return doNotEquals(colValue, getData());
            }
            case LESS -> {
                return doLess(colValue, getData());
            }
            case GREATER -> {
                return doGreater(colValue, getData());
            }
            case GREAT_THAN_EQ -> {
                return doGTEquals(colValue, getData());
            }
            case LESS_THAT_EQ -> {
                return doLTEquals(colValue, getData());
            }
            case ILIKE -> {
                return doILike(colValue, getData());
            }
            case LIKE -> {
                return doLike(colValue, getData());
            }
            default -> {
                return false;
            }
        }
    }

    public boolean doEquals(Object leftOp, Object rightOp) {
        if (Objects.equals(leftOp, null)) {
            return false;
        }
        return Objects.equals(leftOp.getClass(), rightOp.getClass()) &&
                Objects.equals(leftOp, rightOp);
    }

    public boolean doNotEquals(Object leftOp, Object rightOp) {
        return Objects.equals(leftOp, null) ||
                !Objects.equals(leftOp.getClass(), rightOp.getClass()) ||
                !Objects.equals(leftOp, rightOp);
    }

    public boolean doGTEquals(Object leftOp, Object rightOp) {
        if (Objects.equals(leftOp, null)) {
            return false;
        }
        if(Objects.equals(leftOp.getClass(), rightOp.getClass())){
            if(Objects.equals(leftOp.getClass(), Double.class)){
                return (double)leftOp >= (double)rightOp;
            }
            return (long)leftOp >= (long)rightOp;
        }
        if(Objects.equals(leftOp.getClass(), Long.class)){
            return Long.valueOf((long)leftOp).doubleValue() >= (double)rightOp;
        }
        return (double)leftOp >= Long.valueOf((long)rightOp).doubleValue();
    }

    public boolean doLTEquals(Object leftOp, Object rightOp) {
        if (Objects.equals(leftOp, null)) {
            return false;
        }
        if(Objects.equals(leftOp.getClass(), rightOp.getClass())){
            if(Objects.equals(leftOp.getClass(), Double.class)){
                return (double)leftOp <= (double)rightOp;
            }
            return (long)leftOp <= (long)rightOp;
        }
        if(Objects.equals(leftOp.getClass(), Long.class)){
            return Long.valueOf((long)leftOp).doubleValue() <= (double)rightOp;
        }
        return (double)leftOp <= Long.valueOf((long)rightOp).doubleValue();
    }

    public boolean doGreater(Object leftOp, Object rightOp) {
        if (Objects.equals(leftOp, null)) {
            return false;
        }
        if(Objects.equals(leftOp.getClass(), rightOp.getClass())){
            if(Objects.equals(leftOp.getClass(), Double.class)){
                return (double)leftOp > (double)rightOp;
            }
            return (long)leftOp > (long)rightOp;
        }
        if(Objects.equals(leftOp.getClass(), Long.class)){
            return Long.valueOf((long)leftOp).doubleValue() > (double)rightOp;
        }
        return (double)leftOp > Long.valueOf((long)rightOp).doubleValue();
    }

    public boolean doLess(Object leftOp, Object rightOp) {
        if (Objects.equals(leftOp, null)) {
            return false;
        }
        if(Objects.equals(leftOp.getClass(), rightOp.getClass())){
            if(Objects.equals(leftOp.getClass(), Double.class)){
                return (double)leftOp < (double)rightOp;
            }
            return (long)leftOp < (long)rightOp;
        }
        if(Objects.equals(leftOp.getClass(), Long.class)){
            return Long.valueOf((long)leftOp).doubleValue() < (double)rightOp;
        }
        return (double)leftOp < Long.valueOf((long)rightOp).doubleValue();
    }

    public boolean doILike(Object leftOp, Object rightOp) {
        if (Objects.equals(leftOp, null)) {
            return false;
        }
        if (Pattern.matches(LIKE_OPERATOR_PATTERN, (String) rightOp)) {
            return Pattern.matches("(?i)(" + ((String) rightOp).replace("%", "[\\wаА-яЯ ]*") + ")",
                    (String) leftOp);
        }
        return false;
    }

    public boolean doLike(Object leftOp, Object rightOp) {
        if (Objects.equals(leftOp, null)) {
            return false;
        }
        if (Pattern.matches(LIKE_OPERATOR_PATTERN, (String) rightOp)) {
            return Pattern.matches(((String) rightOp).replace("%", "[\\wаА-яЯ ]*"), (String) leftOp);
        }
        return false;
    }
}

