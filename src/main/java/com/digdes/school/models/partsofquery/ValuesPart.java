package com.digdes.school.models.partsofquery;

import java.util.ArrayList;

public class ValuesPart {
    private final ArrayList<Expression> expressions;

    public ValuesPart() {
        expressions = new ArrayList<>();
    }
    public void addExpression(String exp) throws Exception {
        expressions.add(new Expression(exp, true));
    }

    public ArrayList<Expression> getExpressions() {
        return expressions;
    }
}
