//package com.example.tt_nsk.dao;
//
//import com.example.tt_nsk.entity.JsonForListPlayers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
//import javax.persistence.Converter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
////@Component
//@Converter
//public interface JsonForListPlayersDao
//        extends AttributeConverter<List<JsonForListPlayers>, String> {
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    default String convertToDatabaseColumn(List<JsonForListPlayers> attribute){
//        String jsonString = "";
//        try {
//
//            // convert list of POJO to json
//            jsonString = objectMapper.writeValueAsString(attribute);
//
//        } catch (JsonProcessingException ex) {
//        }
//        return jsonString;
//    }
//    @Override
//    default List<JsonForListPlayers> convertToEntityAttribute(String dbData) {
//        List<JsonForListPlayers> list = new ArrayList<>();
//        try {
//            // convert json to list of POJO
//            list = Arrays.asList(objectMapper.readValue(dbData,
//                    JsonForListPlayers[].class));
//        } catch (IOException ex) {
//        }
//        return list;
//    }
//}
