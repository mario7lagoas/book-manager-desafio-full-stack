package com.labs.vgx.books.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookUtil {
    public boolean checkDataNullAndEmpty(String data) {

        if (data != null && !data.isEmpty())
            return true;

        return false;
    }

    public String generateGuid() {
        return UUID.randomUUID().toString();
    }
}
