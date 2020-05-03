package com.gavinkim.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UtilsTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Test
    public void generate() {
        //given
        int count = 5;
        //when
        List<String> coupons = Stream.generate(()->Utils.generate()).limit(count).collect(Collectors.toList());
        //then
        assertThat(coupons.size()).isEqualTo(count);
        log.info("code: {}",coupons);
    }
}