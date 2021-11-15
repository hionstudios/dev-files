package com.hionstudios;


import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResponse extends HashMap<String, Object> {
    static String STATUS = "status";
    static String FAILED = "failed";
    static String SUCCESS = "success";
    static String MESSAGE = "message";

    @SuppressWarnings("unchecked")
    public MapResponse(Map map) {
        super(map);
    }

    public MapResponse(JSONObject json) {
        this(json.toMap());
    }

    public MapResponse() {
        super();
    }

    public static MapResponse success() {
        return new MapResponse().put(STATUS, SUCCESS);
    }

    public static MapResponse success(String message) {
        return success().put(MESSAGE, message);
    }

    public static MapResponse success(String key, Object data) {
        return success().put(key, data);
    }

    public static MapResponse failure() {
        return new MapResponse().put(STATUS, FAILED);
    }

    public static MapResponse failure(String message) {
        return failure().put(MESSAGE, message);
    }

    public MapResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public boolean getBoolean(String key) {
        Object value = get(key);
        return value instanceof String ? Boolean.parseBoolean((String) value) : (boolean) value;
    }

    public BigInteger getBigInteger(String key) {
        return (BigInteger) get(key);
    }

    public BigDecimal getBigDecimal(String key) {
        return (BigDecimal) get(key);
    }

    public double getDouble(String key) {
        return (double) get(key);
    }

    public float getFloat(String key) {
        return (float) get(key);
    }

    public int getInt(String key) {
        return (int) get(key);
    }

    public int optInt(String key) {
        Object value = get(key);
        return value != null ? (int) get(key) : 0;
    }

    public long getLong(String key) {
        return (long) get(key);
    }

    public String getString(String key) {
        return (String) get(key);
    }

    public byte[] getBytes(String key) {
        return (byte[]) get(key);
    }

    public ListResponse getList(String key) {
        return new ListResponse((List<?>) get(key));
    }

    public MapResponse getMap(String key) {
        return (MapResponse) get(key);
    }
}
