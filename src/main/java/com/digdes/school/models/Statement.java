package com.digdes.school.models;

import com.digdes.school.models.enums.QueryType;
import com.digdes.school.models.partsofquery.Query;

import java.util.*;
import java.util.stream.Collectors;

/*
* Класс для высокоуровневой работы с таблицей
*/
public class Statement {
    private final QueryType queryType;
    private final Query query;

    public Statement(QueryType queryType, Query query) {
        this.queryType = queryType;
        this.query = query;
    }

    public List<Map<String, Object>> work(List<Map<String, Object>> db) {
        switch (queryType) {
            case INSERT -> {
                Map<String, Object> row = new HashMap<>();
                query.getValuesPartExpressions().forEach(expression -> {
                    if (expression.getData() != null) {
                        row.put(expression.getCol(), expression.getData());
                    }
                });
                db.add(row);
                return new ArrayList<>(db);
            }
            case SELECT -> {
                return new ArrayList<>(db);
            }
            case DELETE -> {
                ArrayList<Map<String, Object>> outList = new ArrayList<>(db);
                db.clear();
                return new ArrayList<>(outList);
            }
            case UPDATE -> {
                query.getValuesPartExpressions().forEach(expression -> db.forEach(stringObjectMap -> {
                    if (expression.getData() != null) {
                        stringObjectMap.put(expression.getCol(), expression.getData());
                    } else {
                        stringObjectMap.remove(expression.getCol());
                    }
                }));
                return new ArrayList<>(db);
            }
            case SELECT_WHERE -> {
                return db.stream().filter(Query::isSatisfiesWhereCondition).collect(Collectors.toList());
            }
            case DELETE_WHERE -> {
                List<Map<String, Object>> outList = new ArrayList<>();
                db.removeIf(stringObjectMap -> {
                    if (Query.isSatisfiesWhereCondition(stringObjectMap)) {
                        outList.add(stringObjectMap);
                        return true;
                    }
                    return false;
                });
                return outList;
            }
            case UPDATE_WHERE -> {
                List<Map<String, Object>> outList = new ArrayList<>();
                db.forEach(stringObjectMap -> {
                    if (Query.isSatisfiesWhereCondition(stringObjectMap)) {
                        query.getValuesPartExpressions().forEach(expression -> {
                            if (expression.getData() != null) {
                                stringObjectMap.put(expression.getCol(), expression.getData());

                            } else {
                                stringObjectMap.remove(expression.getCol());

                            }
                        });
                        outList.add(new HashMap<>(stringObjectMap));
                    }
                });
                return outList;
            }
        }
        return new ArrayList<>();
    }
}
