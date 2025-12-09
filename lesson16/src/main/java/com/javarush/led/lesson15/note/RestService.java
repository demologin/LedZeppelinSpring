package com.javarush.led.lesson15.note;

import java.util.List;

public interface RestService<Q,A> {
    List<A> findAll();

    A findById(long id);

    A create(Q userTo);

    A update(Q userTo);

    boolean removeById(Long id);
}
