package com.digdes.school.models.parsers;

import com.digdes.school.interfaces.Parser;
import com.digdes.school.models.Statement;
import com.digdes.school.models.partsofquery.Query;

public final class ParserHelper {
    private Parser parser;

    public Statement getStatement(String request) throws Exception {
        Query query = new Query();
        query.analyseRequest(request);
        chooseParser(query);
        parser.parse(query);
        return query.generateStatement();
    }

    private void chooseParser(Query query) {
        switch (query.getType()) {
            case DELETE -> parser =  new DeleteParser();
            case INSERT -> parser =  new InsertParser();
            case SELECT -> parser =  new SelectParser();
            case UPDATE -> parser =  new UpdateParser();
            case DELETE_WHERE -> parser =  new DeleteWhereParser();
            case SELECT_WHERE -> parser =  new SelectWhereParser();
            case UPDATE_WHERE -> parser =  new UpdateWhereParser();
        }
    }
}
