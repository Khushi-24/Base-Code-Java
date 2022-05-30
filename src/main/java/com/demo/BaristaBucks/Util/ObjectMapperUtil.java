package com.demo.BaristaBucks.Util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import javax.servlet.ServletInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ObjectMapperUtil {

    public static final ObjectMapper mapper = new ObjectMapper();
    private static ModelMapper modelMapper = new ModelMapper();


    /**
     * @param jsonObject
     * @param clazz
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @author santosh
     */
    public static <T> T toObject(String jsonObject, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(jsonObject, clazz);
    }

    /**
     * @param inputStream
     * @param clazz
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @author santosh
     */
    public static <T> T toObject(ServletInputStream inputStream, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(convertToString(inputStream), clazz);
    }

    /**
     * @param is
     * @return
     * @author santosh
     */
    public static String convertToString(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            //supressing exception
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }

    /**
     * @param requestParams
     * @param clazz
     * @return
     * @author santosh
     */
    public static <T> T toObject(Map<String, Object> requestParams, Class<T> clazz) {
        return mapper.convertValue(requestParams, clazz);
    }

    public static Map toMap(Object object) {
        return mapper.convertValue(object, Map.class);
    }

    /**
     * convert into Json String
     */
    public static String toString(Object classObject) throws JsonProcessingException {
        return mapper.writeValueAsString(classObject);
    }

    public static Map<String, Object> toMap(String jsonInStringType) throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(jsonInStringType, new TypeReference<HashMap<String, Object>>() {
        });
    }

    public static <T> T toGenericMap(String jsonInStringType, T type) throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(jsonInStringType, new TypeReference<T>() {
        });
    }

    public static <T> Collection<T> toGenericCollection(String jsonString, Class<T> type) throws JsonParseException, JsonMappingException, IOException {
        CollectionType javaType = new ObjectMapper().getTypeFactory().constructCollectionType(Collection.class, type);
        return mapper.readValue(jsonString, javaType);
    }


    /**
     * Model mapper property setting are specified in the following block.
     * Default property matching strategy is set to Strict see {@link MatchingStrategies}
     * Custom mappings are added using {@link ModelMapper#addMappings(PropertyMap)}
     */
    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Hide from public usage.
     */
    private ObjectMapperUtil() {
    }

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param <D>      type of result object.
     * @param <T>      type of source object to map from.
     * @param entity   entity that needs to be mapped.
     * @param outClass class of result object.
     * @return new object of <code>outClass</code> type.
     */
    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param entityList list of entities that needs to be mapped
     * @param outCLass   class of result list element
     * @param <D>        type of objects in result list
     * @param <T>        type of entity in <code>entityList</code>
     * @return list of mapped object with <code><D></code> type.
     */
    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }

    /**
     * Maps {@code source} to {@code destination}.
     *
     * @param source      object to map from
     * @param destination object to map to
     */
    public static <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }
}
