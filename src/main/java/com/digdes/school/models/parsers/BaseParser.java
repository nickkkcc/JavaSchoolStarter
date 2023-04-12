package com.digdes.school.models.parsers;

import com.digdes.school.models.partsofquery.Query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseParser {
    protected static final String VALUE_PART_PARSE_PATTERN = "('[\\w]+')[ ]*(=)[ ]*([0-9]+[.][0-9]+|-[0-9]+[.][0-9]+|[0-9]+|-[0-9]+|'[\\wаА-яЯ% ]+'|(?i)true|false|null)";
    protected static final String CONDITION_PART_PARSE_PATTERN = "('[\\w]+')[ ]*((?i)=|!=|>|<|>=|<+|like|ilike)[ ]*([0-9]+[.][0-9]+|-[0-9]+[.][0-9]+|[0-9]+|-[0-9]+|'[\\wаА-яЯ% ]+'|(?i)true|false)([ ]+(?i)(or|and)|$)";

    /*
    Различные варианты функций парсинга для части значений и части условия.
     */
    protected  void parseValuePart(Query query) throws Exception {
        Pattern valuePartPattern = Pattern.compile(VALUE_PART_PARSE_PATTERN);
        Matcher matcher = valuePartPattern.matcher(query.getQuery());
        while(matcher.find()){
            Query.addValuesPartExpression(matcher.group(0));
        }
    }
    protected  void parseConditionPart(Query query) throws Exception {
        Pattern conditionPartValue = Pattern.compile(CONDITION_PART_PARSE_PATTERN);
        Matcher matcher = conditionPartValue.matcher(query.getQuery());
        while(matcher.find()){
            Query.addConditionPartExpressions(matcher.group(1)  + matcher.group(2) +
                    matcher.group(3), matcher.group(5));
        }
    }
    protected  void parseValuePart(String query) throws Exception {
        Pattern valuePartPattern = Pattern.compile(VALUE_PART_PARSE_PATTERN);
        Matcher matcher = valuePartPattern.matcher(query);
        while(matcher.find()){
            Query.addValuesPartExpression(matcher.group(0));
        }
    }
    protected  void parseConditionPart(String query) throws Exception {
        Pattern conditionPartValue = Pattern.compile(CONDITION_PART_PARSE_PATTERN);
        Matcher matcher = conditionPartValue.matcher(query);
        while(matcher.find()){
            Query.addConditionPartExpressions(matcher.group(1) + matcher.group(2) + matcher.group(3), matcher.group(5));
        }
    }


}
