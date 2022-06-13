package org.vir1ibus.onlinestore.utils;

import org.json.JSONArray;
import org.springframework.data.domain.Page;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JSONConverter {

    public static JSONArray toJsonArray(Set<? extends CustomJSONObject> inputSet) {
        JSONArray resultJSONArray = new JSONArray();
        for(CustomJSONObject element : inputSet) {
            resultJSONArray.put(element.toJSONObject());
        }
        return resultJSONArray;
    }

    public static JSONArray toJsonArray(List<? extends CustomJSONObject> inputSet) {
        JSONArray resultJSONArray = new JSONArray();
        for(CustomJSONObject element : inputSet) {
            resultJSONArray.put(element.toJSONObject());
        }
        return resultJSONArray;
    }

    public static JSONArray toJsonArray(Page<? extends CustomJSONObject> inputSet) {
        JSONArray resultJSONArray = new JSONArray();
        for(CustomJSONObject element : inputSet) {
            resultJSONArray.put(element.toJSONObject());
        }
        return resultJSONArray;
    }

    public static JSONArray toMinimalJsonArray(Set<? extends CustomJSONObject> inputSet) {
        JSONArray resultJSONArray = new JSONArray();
        for(CustomJSONObject element : inputSet) {
            resultJSONArray.put(element.toMinimalJSONObject());
        }
        return resultJSONArray;
    }

    public static JSONArray toMinimalJsonArray(Iterable<? extends CustomJSONObject> inputSet) {
        JSONArray resultJSONArray = new JSONArray();
        for(CustomJSONObject element : inputSet) {
            resultJSONArray.put(element.toMinimalJSONObject());
        }
        return resultJSONArray;
    }

    public static JSONArray toMinimalJsonArray(Page<? extends CustomJSONObject> inputSet) {
        JSONArray resultJSONArray = new JSONArray();
        for(CustomJSONObject element : inputSet) {
            resultJSONArray.put(element.toMinimalJSONObject());
        }
        return resultJSONArray;
    }
}
