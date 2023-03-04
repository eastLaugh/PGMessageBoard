package com.east.laugh.pgmessageboard.Mapper;

import com.east.laugh.pgmessageboard.entity.MessageBoard;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
public interface MessageBoardMapper {


    @Insert("insert into messageboard (name, message) VALUES (#{name},#{message})")
    public void Insert(@Param("name") String name, @Param("message") String message);


    @Insert("insert into messageboard (name, message,father_id) VALUES (#{name},#{message},#{father_id})")
    public void InsertWithFatherId(@Param("name") String name, @Param("message") String message,@Param("father_id")Integer father_id);
    @Select("select * from messageboard")
    public List<MessageBoard> SelectAll();


}
