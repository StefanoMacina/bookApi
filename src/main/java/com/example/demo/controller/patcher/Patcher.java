package com.example.demo.controller.patcher;
import java.lang.reflect.Field;

public class Patcher<T> {

    private final Class<T> c;

    public Patcher(Class<T> c) {
        this.c = c;
    }

    public void patcher(T existingIntern, T incompleteIntern) throws IllegalAccessException {

        Field[] authorDtoFields = c.getDeclaredFields();
        for(Field f : authorDtoFields){
            f.setAccessible(true);
            Object value = f.get(incompleteIntern);
            if(value!=null) {
                f.set(existingIntern, value);
            }
            f.setAccessible(false);
        }
    }
}
