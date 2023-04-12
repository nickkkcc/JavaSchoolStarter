package com.digdes.school;

import com.digdes.school.models.Statement;
import com.digdes.school.models.parsers.ParserHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaSchoolStarter {
    private final List<Map<String, Object>> db = new ArrayList<>();
    private final ParserHelper parserHelper = new ParserHelper();

    public JavaSchoolStarter() {    }


    public List<Map<String, Object>> execute(String request) throws Exception {
        Statement statement = parserHelper.getStatement(request);
        return statement.work(db);
    }
}
