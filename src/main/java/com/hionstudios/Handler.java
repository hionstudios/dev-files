package com.hionstudios;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.RowListenerAdapter;
import org.json.JSONArray;
import org.postgresql.jdbc.PgArray;
import org.postgresql.util.PGobject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface Handler {
    Logger LOGGER = Logger.getLogger(Handler.class.getName());

    static void write(Object obj, HttpServletResponse res) {
        try (PrintWriter out = res.getWriter()) {
            out.print(obj);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    static List<MapResponse> findAll(String sql, Object... params) {
        List<MapResponse> list = new ArrayList<>();
        findWith(sql, params, new RowListenerAdapter() {
            @Override
            public void onNext(Map<String, Object> map) {
                list.add(parse(map));
            }
        });
        return list;
    }

    static ListResponse firstColumn(String sql, Object... params) {
        ListResponse array = new ListResponse();
        findWith(sql, params, new RowListenerAdapter() {
            @Override
            public void onNext(Map<String, Object> row) {
                array.add(row.values().iterator().next());
            }
        });
        return array;
    }

    static void findWith(String sql, Object[] params, RowListenerAdapter listener) {
        Base.find(sql, params).with(listener);
    }

    static void findWith(String sql, RowListenerAdapter listener) {
        Base.find(sql).with(listener);
    }

    static MapResponse parse(Map<String, Object> map) {
        MapResponse json = new MapResponse();
        map.forEach((a, b) -> {
            try {
                if (b instanceof PgArray) {
                    PgArray pgArray = (PgArray) b;
                    List<Object> list = new ArrayList<>();
                    Object arrayObject = pgArray.getArray();
                    if (arrayObject instanceof Object[]) {
                        Object[] objects = (Object[]) arrayObject;
                        for (Object object : objects) {
                            if (object instanceof PGobject) {
                                list.add(((PGobject) object).getValue());
                            } else {
                                list.add(object);
                            }
                        }
                        json.put(String.valueOf(a), list);
                    } else {
                        json.put(String.valueOf(a), new ListResponse(new JSONArray(pgArray.getArray())));
                    }
                } else {
                    json.put(String.valueOf(a), b);
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        });
        return json;
    }

    static MapResponse findFirst(String sql, Object... params) {
        List<MapResponse> responses = findAll(sql, params);
        return responses.size() > 0 ? responses.get(0) : null;
    }

    static MapResponse toDataTables(String sql, String countSql, String filterSql, String... columns) {
        return toDataTables(null, sql, countSql, filterSql, columns);
    }

    static MapResponse toDataTables(HashMap<String, HashMap<Object, Object>> transformer, String sql, String countSql,
                                    String filterSql, String... columns) {
        List<Object> array = new ArrayList<>();
        boolean toTransform = transformer != null;
        findWith(sql, new RowListenerAdapter() {
            @Override
            public void onNext(Map<String, Object> row) {
                List<Object> rowArray = new ArrayList<>();
                for (String column : columns) {
                    Object value = row.get(column);
                    if (toTransform) {
                        Map<?, ?> t = transformer.get(column);
                        if (t != null) {
                            Object newValue = t.get(value);
                            if (newValue != null) {
                                value = newValue;
                            }
                        }
                    }
                    if (value instanceof PgArray) {
                        rowArray.add(toList((PgArray) value));
                    } else {
                        rowArray.add(value);
                    }
                }
                array.add(rowArray);
            }
        });
        MapResponse response = new MapResponse();
        if (countSql != null) {
            response.put("recordsTotal", findFirst(countSql).get("count"))
                    .put("recordsFiltered", findFirst(filterSql).get("count"));
        }
        return response.put("data", array);
    }

    static MapResponse toDataTables(String sql) {
        List<MapResponse> list = Handler.findAll(sql);
        String[] columns = new String[]{};
        if (list.size() > 0) {
            columns = list.get(0).keySet().toArray(new String[]{});
        }
        ListResponse array = new ListResponse();
        for (String c : columns) {
            array.add(new MapResponse().put("title", c));
        }
        return toDataTables(sql, null, null, columns).put("columns", array);
    }

    static ListResponse toList(PgArray array) {
        try {
            return new ListResponse(new JSONArray(array.getArray()));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    static MapResponse toSelect(String sql, String key, String value) {
        MapResponse map = new MapResponse();
        findWith(sql, new RowListenerAdapter() {
            @Override
            public void onNext(Map<String, Object> row) {
                map.put(String.valueOf(row.get(key)), row.get(value));
            }
        });
        return map;
    }

    static MapResponse toJson(String sql, String id) {
        MapResponse response = new MapResponse();
        Base.find(sql, new RowListenerAdapter() {
            @Override
            public void onNext(Map<String, Object> row) {
                response.put(String.valueOf(row.get(id)), parse(row));
            }
        });
        return response;
    }
}
