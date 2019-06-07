package com.umbum.pos.repository;

import java.util.List;

import com.umbum.pos.model.Event;

public interface EventRepo {
    public List<Event> readAll(long productId);
}
