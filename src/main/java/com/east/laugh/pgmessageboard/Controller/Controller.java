package com.east.laugh.pgmessageboard.Controller;


import com.east.laugh.pgmessageboard.Mapper.MessageBoardMapper;
import com.east.laugh.pgmessageboard.Mapper.Wechat;
import com.east.laugh.pgmessageboard.entity.WechatResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api")
public class Controller {

    @Autowired
    private MessageBoardMapper messageBoardMapper;


    /**
     * 返回全部根帖子
     * @return
     */
    @GetMapping("/getall")
    public Object GetAll(){
        return messageBoardMapper.SelectAll();
    }

    @GetMapping("/getdetails")
    public Object GetDetails(@RequestParam("post_id")int id){
        return messageBoardMapper.SelectByFatherID(id);
    }


    @PostMapping("/send")
    public Object Send(@RequestParam("name") String name,@RequestParam("content") String content,String session_key){
        if(  !content.trim().isEmpty())
            messageBoardMapper.Insert(name,content,Sessions.get(session_key));
        return null;
    }


    @PostMapping("/review")
    public Object Review(@RequestParam("name") String name,@RequestParam("content") String content,String session_key,@RequestParam("father_id")Integer fatherId){
        if( !content.trim().isEmpty())
            messageBoardMapper.InsertWithFatherId(name,content,Sessions.get(session_key),fatherId);
        return null;
    }

    @Autowired
    private Wechat wechat;
    /**
     * 核验自定义登录态，如果成功则
     * @return 用户所有个人信息
     */
    @PostMapping("/verify")
    public Result Verify(@RequestParam("session_key")String EncodedSessionId){
        String sessionKey = DecodeSessionKey(EncodedSessionId);
        if(Sessions.containsKey(sessionKey)){
            String openid = Sessions.get(sessionKey);

            if(!wechat.OpenIDExist(openid)) {
                wechat.Insert(openid);
            }
            return new Result(wechat.GetOpenUserInfo(openid));

        }else{
            return new Result(false);
        }

    }

    @PostMapping("/update")
    public Object Update(String session_key,String nickname){
        System.out.println(session_key);
        System.out.println(nickname);
        Result verify = Verify(session_key);
        if(verify.success){
            wechat.UpdateNickName(Sessions.get(session_key),nickname);

        }else{
            return new Result(false);
        }
        return new Result(true);
    }
    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.secret}")
    private String secret;

    @Value("${wechat.grant_type}")
    private String grant_type;

    private Map<String,String> Sessions = new HashMap<>();
    @GetMapping("/login")
    public String Login(HttpServletRequest request) throws IOException {

        var js_code = request.getHeader("code");
        var response = CallWechatLoginApi(js_code);
        System.out.println(response);
        var encode = EncodeSessionKey(response.getSession_key());
        Sessions.put(encode,response.getOpenid() );
        return String.format("{\"session_key\":\"%s\"}",encode);
    }

    private String EncodeSessionKey(String session_key){
        return session_key;
    }

    private String DecodeSessionKey(String session_key){
        return session_key;
    }
    private WechatResponse CallWechatLoginApi(String js_code) throws IOException{
        String urlstr = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=%s", appid, secret, js_code, grant_type);

        URL url = new URL(urlstr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            return new ObjectMapper().readValue(response.toString(),WechatResponse.class);
        } else {
            System.out.println("GET request failed: " + responseCode);
            throw new RuntimeException();
        }

    }

}
