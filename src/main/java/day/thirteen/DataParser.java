package day.thirteen;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class DataParser {

    public List<Object> parsePacket(String inputLine) {
        var json = JsonParser.parseString(inputLine);
        return parsePacketFromJsonArray(json.getAsJsonArray());
    }

    private List<Object> parsePacketFromJsonArray(JsonArray elements) {
        var items = new ArrayList<>();

        elements.forEach(e -> {
            if (e.isJsonArray())
                items.add(parsePacketFromJsonArray(e.getAsJsonArray()));
            else
                items.add(e.getAsInt());
        });

        return items;
    }
}
