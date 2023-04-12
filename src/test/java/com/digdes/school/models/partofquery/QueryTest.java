package com.digdes.school.models.partofquery;

import com.digdes.school.models.enums.QueryType;
import com.digdes.school.models.partsofquery.Query;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;


/*
 * Check common query view.
 */
public class QueryTest {
    private final static HashMap<QueryType, String> trueRequests= new HashMap<>(){{
        put(QueryType.DELETE,"Delete");
        put(QueryType.DELETE_WHERE,"deLete where 'age'=5 and 'lastName'='Vasya'");
        put(QueryType.SELECT,"seLect");
        put(QueryType.SELECT_WHERE,"Select WHERE 'age'=5 and 'lastName'='Vasya'");
        put(QueryType.UPDATE, "updatE VALUES 'age'=5, 'lastName'='Vasya'");
        put(QueryType.UPDATE_WHERE, "updatE VALUES 'age'=5, 'lastName'='Vasya' where 'age'=5 and 'lastName'='Vasya'");
        put(QueryType.INSERT, "insert VALUES 'age'=5, 'lastName'='Vasya'");
    }};

    private final static HashMap<QueryType, String> notTrueRequests= new HashMap<>(){{
        put(QueryType.DELETE,"Delete    ");
        put(QueryType.DELETE_WHERE,"deLete where 'age'=5  'lastName'='Vasya'");
        put(QueryType.SELECT,"seLect    ");
        put(QueryType.SELECT_WHERE,"Select WHERE 'age'=5 , 'lastName'='Vasya'");
        put(QueryType.UPDATE, "updatE VALUES 'age'=5 where 'lastName' > 'Vasya'");
        put(QueryType.UPDATE_WHERE, "updatE VALUES asdnf 'age'=5, 'lastName'='Vasya' where 'age'=5 and 'lastName'='Vasya'");
        put(QueryType.INSERT, "insert VALUES 'age'=5 'lastName'='Vasya'");
    }};


    @Test()
    public void requestTypeSelectionTest(){
        Query query = new Query();
        trueRequests.forEach((key, value) -> {
            try {
                query.analyseRequest(value);
                Assert.assertEquals(key, query.getType());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    @Test()
    public void requestTypeSelectionTestException(){
        Query query = new Query();
        notTrueRequests.forEach((key, value) -> {
            try {
                query.analyseRequest(value);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
