package com.thishotel.util;

import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class ServiceUtil {
    public <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null){
            setter.accept(value);
        }
    }
}
