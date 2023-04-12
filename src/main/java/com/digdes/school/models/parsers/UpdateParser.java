package com.digdes.school.models.parsers;

import com.digdes.school.interfaces.Parser;
import com.digdes.school.models.partsofquery.Query;

public class UpdateParser extends BaseParser implements Parser {
    @Override
    public void parse(Query query) throws Exception {
        parseValuePart(query);
    }
}
