package com.digdes.school.models.partsofquery;

import com.digdes.school.models.Statement;
import com.digdes.school.models.enums.QueryType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Query {
    private static ConditionPart conditionPart;
    private static ValuesPart valuesPart;
    private String query;

    private static QueryType type;

    /*Паттерны регулярных для запросов общего вида*/
    private static final HashMap<QueryType, String> queryTypePatterns = new HashMap<>() {{

        put(QueryType.INSERT, "^((?i)insert values[ ]+)(('[\\wаА-яЯ]+'[ ]*=[ ]*('[\\wаА-яЯ ]+'|[0-9]+[.][0-9]+|-[0-9]+[.][0-9]+|" +
                "[0-9]+|-[0-9]+|(?i)null|true|false))([ ]*,[ ]*(?=')|$))+");
        put(QueryType.SELECT, "^((?i)select)$");
        put(QueryType.DELETE, "^((?i)delete)$");
        put(QueryType.UPDATE, "^((?i)update values[ ]+)(('[\\wаА-яЯ]+'[ ]*=[ ]*('[\\wаА-яЯ ]+'|[0-9]+[.][0-9]+|-[0-9]+[.][0-9]+|[0-9]+|" +
                "-[0-9]+|(?i)null|true|false))([ ]*,[ ]*(?=')|$))+");
        put(QueryType.SELECT_WHERE, "^((?i)select[ ]+where[ ]+)('[\\wаА-яЯ]+'[ ]*((?i)>=|<=|>|<|=|!=|like|ilike)[ ]*('[\\wаА-яЯ% ]+'|[0-9]+" +
                "[.][0-9]+|-[0-9]+[.][0-9]+|[0-9]+|-[0-9]+|(?i)true|false)([ ]*((?i)and|or)[ ]*(?=')|$))+");
        put(QueryType.DELETE_WHERE, "^((?i)delete[ ]+where[ ]+)('[\\wаА-яЯ]+'[ ]*((?i)>=|<=|>|<|=|!=|like|ilike)[ ]*('[\\wаА-яЯ% ]+'|[0-9]+" +
                "[.][0-9]+|-[0-9]+[.][0-9]+|[0-9]+|-[0-9]+|(?i)true|false)([ ]*((?i)and|or)[ ]*(?=')|$))+");
        put(QueryType.UPDATE_WHERE, "((?i)update values[ ]+)(('[\\wаА-яЯ]+'[ ]*=[ ]*('[\\wаА-яЯ ]+'|[0-9]+[.][0-9]+|-[0-9]+[.][0-9]+|" +
                "[0-9]+|-[0-9]+|(?i)null|true|false))([ ]*,[ ]*(?=')|))+([ ]+(?i)where[ ]+)('[\\wаА-яЯ]+'[ ]*((?i)>=|<=|>|<|=|!=|like" +
                "|ilike)[ ]*('[\\wаА-яЯ% ]+'|[0-9]+[.][0-9]+|-[0-9]+[.][0-9]+|[0-9]+|-[0-9]+|(?i)true|false)([ ]*((?i)and|or)[ ]*(?=')|$))+");
    }};

    public Query() {
        conditionPart = new ConditionPart();
        valuesPart = new ValuesPart();
    }
    /*
    * Функция для анализа входного запроса через регулярки (вид <select|....> <values part> <where> <condition part>)*/
    public void analyseRequest(String request) throws Exception {
        if (request.length() == 0) {
            throw new Exception("[Request error] request is empty");
        }
        boolean result = queryTypePatterns.entrySet().stream().anyMatch(queryTypeStringEntry -> {
            if (Pattern.compile(queryTypeStringEntry.getValue()).matcher(request).matches()) {
                type = queryTypeStringEntry.getKey();
                query = request;
                return true;
            }
            return false;
        });
        if (!result) {
            throw new Exception("[Request grammar error] in " + request);
        }
    }

    public QueryType getType() {
        return type;
    }

    public static void addValuesPartExpression(String exp) throws Exception {
        valuesPart.addExpression(exp);
    }

    public String getQuery() {
        return query;
    }

    public Statement generateStatement() {
        return new Statement(type, this);
    }

    public static void addConditionPartExpressions(String expression, String conditionOperator) throws Exception {
        conditionPart.addExpression(expression, conditionOperator);
    }
    public ArrayList<Expression> getValuesPartExpressions() {
        return valuesPart.getExpressions();
    }

    /*
    * Функция проверки подходит ли строка под условную часть*/
    public static boolean isSatisfiesWhereCondition(Map<String, Object> row) {
        return conditionPart.isSatisfiesWhereCondition(row);
    }
}
