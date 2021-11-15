package com.hionstudios;

import org.json.JSONArray;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ListResponse extends ArrayList<Object> {
    public ListResponse() {
    }

    public ListResponse(List<?> list) {
        super(list);
    }

    public ListResponse(JSONArray array) {
        this(array.toList());
    }

    public ListResponse(Object[] objects) {
        this(Arrays.asList(objects));
    }

    public ListResponse put(Object obj) {
        this.add(obj);
        return this;
    }

    public boolean getBoolean(int index) {
        Object value = get(index);
        return value instanceof String ? Boolean.parseBoolean((String) value) : (boolean) value;
    }

    public BigInteger getBigInteger(int index) {
        return (BigInteger) get(index);
    }

    public BigDecimal getBigDecimal(int index) {
        return (BigDecimal) get(index);
    }

    public double getDouble(int index) {
        return (double) get(index);
    }

    public float getFloat(int index) {
        return (float) get(index);
    }

    public int getInt(int index) {
        return (int) get(index);
    }

    public long getLong(int index) {
        return (long) get(index);
    }

    public String getString(int index) {
        return (String) get(index);
    }

    public MapResponse getMap(int index) {
        return new MapResponse((Map<?, ?>) get(index));
    }
}
