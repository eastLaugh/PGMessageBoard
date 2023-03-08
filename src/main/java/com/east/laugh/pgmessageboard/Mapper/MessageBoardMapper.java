package com.east.laugh.pgmessageboard.Mapper;

import com.east.laugh.pgmessageboard.entity.MessageBoard;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
public interface MessageBoardMapper {


    @Insert("insert into messageboard (name, message, openid) VALUES (#{name},#{message},#{openid})")
    public void Insert(@Param("name") String name, @Param("message") String message,String openid);


    @Insert("insert into messageboard (name, message,openid,father_id) VALUES (#{name},#{message},#{openid},#{father_id})")
    public void InsertWithFatherId(@Param("name") String name, @Param("message") String message,String openid,@Param("father_id")Integer father_id);

    @Results({
        @Result(property = "openid",column = "openid"),
        @Result(property = "name",column = "openid",one = @One(select = "com.east.laugh.pgmessageboard.Mapper.Wechat.GetNickNameByOpenId"))
    })
    @Select("select * from messageboard where father_id is null")
    public List<MessageBoard> SelectAll();


    @Select("select * from messageboard where father_id = #{father_id} or id = #{father_id}")
    @Results({
            @Result(property = "openid",column = "openid"),
            @Result(property = "name",column = "openid",one = @One(select = "com.east.laugh.pgmessageboard.Mapper.Wechat.GetNickNameByOpenId"))
    })
    List<MessageBoard> SelectByFatherID(@Param("father_id") int id);


}
