package com.example.demo.mappers;

public interface Mapper<T, S> {

    S mapTo(T s);

    T mapFrom(S t);

}
