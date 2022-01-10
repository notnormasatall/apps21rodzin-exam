package json;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Andrii_Rodionov on 1/3/2017.
 */
public class JsonObject extends Json {
    List<JsonPair> jsonObj = new ArrayList<JsonPair>(){};

    public JsonObject(JsonPair... jsonPairs) {
        for (JsonPair pair: jsonPairs) {
            this.add(pair);
        }
    }

    private void updatePair(JsonPair toUpdate) {
        int idx = 0;

        for (JsonPair pair: jsonObj) {
            if (Objects.equals(pair.key, toUpdate.key)) {
                jsonObj.set(idx, toUpdate);
            }
            idx++;
        }
    }

    @Override
    public String toJson()  {
        return "{" + getJsonBody() + "}";
    }

    private String getJsonBody() {
        StringBuilder jsonStr = new StringBuilder();
        Iterator<JsonPair> jsonIterator = jsonObj.iterator();
        boolean empty = true;

        while (jsonIterator.hasNext()) {
            JsonPair json = jsonIterator.next();
            jsonStr.append("'").append(json.key).append("'");
            jsonStr.append(": ");
            jsonStr.append(json.value.toJson());
            if (jsonIterator.hasNext()) {
                jsonStr.append(", ");
            }
            empty = false;
        }
        if (empty) {
            return "";
        }
        return jsonStr.toString();
    }

    public void add(JsonPair jsonPair) {
        if (this.find(jsonPair.key) != null) {
            this.updatePair(jsonPair);
        } else {
            jsonObj.add(jsonPair);
        }
    }

    public Json find(String name) {
        for (JsonPair pair: jsonObj) {
            if (Objects.equals(pair.key, name)) {
                return pair.value;
            }
        }
        return null;
    }

    public boolean contains(String name) {
        return this.find(name) != null;
    }

    public JsonObject projection(String... names) {
        List<JsonPair> copy = new ArrayList<>(jsonObj);
        List<String> keys = new ArrayList<String>(){};

        keys.addAll(Arrays.asList(names));
        List<JsonPair> res = copy.stream()
                .filter(p -> keys.contains(p.key)).collect(Collectors.toList());

        JsonObject projection = new JsonObject();
        for (JsonPair pair: res) {
            projection.add(pair);
        }
        return projection;
    }
}
