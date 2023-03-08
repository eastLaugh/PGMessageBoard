package com.east.laugh.pgmessageboard.Mapper;


import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface Wechat {


    @Insert("insert into wechat (openid) VALUES (#{openid})")
    public void Insert(@Param("openid")String openID);


    @Select("select nickname from wechat where openid =#{openid}")
    public String GetNickNameByOpenId(@Param("openid")String openID);

    @Select("select exists(select 1 from wechat where openid =#{openid})")
    public boolean OpenIDExist(@Param("openid")String openID);

    @Update("update wechat set nickname = #{nickname} where openid=#{openid}")
    public int UpdateNickName(@Param("openid")String openID,String nickname);

    @Select("select nickname from wechat where openid = #{openid}")
    public Map<String,String> GetOpenUserInfo(@Param("openid")String openid);
}
