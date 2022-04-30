package com.assignment2.book_store.data.map;

import com.assignment2.book_store.data.dto.user.UserReportUseful;
import com.assignment2.book_store.data.entity.jpa.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersReportMapper {

    UserReportUseful toCsvUseful(User user);

}
