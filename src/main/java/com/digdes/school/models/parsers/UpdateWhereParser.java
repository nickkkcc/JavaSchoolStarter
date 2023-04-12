package com.digdes.school.models.parsers;

import com.digdes.school.interfaces.Parser;
import com.digdes.school.models.partsofquery.Query;

public class UpdateWhereParser extends BaseParser implements Parser {
    @Override
    public void parse(Query query) throws Exception {
        String[] queryParts = query.getQuery().split("[ ]+(?i)where[ ]+");
        parseValuePart(queryParts[0]);
        parseConditionPart(queryParts[1]);
    }
}
