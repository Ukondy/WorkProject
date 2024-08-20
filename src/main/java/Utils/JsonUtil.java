package Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fromJson(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception ignore) {return null;}
    }

    public static <T> Collection<T> fromJsonArr(String json, Class<T> valueType) {
        json = json.substring(1, json.length() - 2);
        List<String> jsonArr = Arrays.stream(json.split("},")).map(s -> s + "}").toList();
        try {
            return jsonArr.stream().map(e -> {
                try {
                    return objectMapper.readValue(e, valueType);
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            }).toList();
        } catch (Exception ignore) {return null;}
    }
}
