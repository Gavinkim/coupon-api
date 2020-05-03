package com.gavinkim.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils extends ObjectUtils {

    public static final DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static Date LocalDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static boolean nullSafeNotEquals(Object src1,Object src2){
        return !nullSafeEquals(src1,src2);
    }
    public static String generate() {
        return String.format("%s-%s-%s", RandomStringUtils.randomAlphabetic(6),
                RandomStringUtils.randomNumeric(6),
                RandomStringUtils.randomAlphanumeric(6)).toUpperCase();
    }

    public static String toJson(Object from) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.writeValueAsString(from);
        } catch (IOException e) {
            throw new DtoConvertException(e.getMessage());
        }
    }

    public static <T> T toObject(Object from, Class<T> target) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(toJson(from), target);
        } catch (IOException e) {
            throw new DtoConvertException(e.getMessage());
        }
    }

}
