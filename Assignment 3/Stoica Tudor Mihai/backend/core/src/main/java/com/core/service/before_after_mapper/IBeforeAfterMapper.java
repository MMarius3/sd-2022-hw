package com.core.service.before_after_mapper;

public interface IBeforeAfterMapper<From, To>{
    To before(From from);
    To after(From from, To partiallyMappedDestination) throws Exception;
}
