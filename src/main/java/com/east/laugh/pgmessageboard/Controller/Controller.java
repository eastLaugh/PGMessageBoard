package com.east.laugh.pgmessageboard.Controller;


import com.east.laugh.pgmessageboard.Mapper.MessageBoardMapper;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api")
public class Controller {

    @Autowired
    private MessageBoardMapper messageBoardMapper;

    @GetMapping("/getall")
    public Object GetAll(){
        return messageBoardMapper.SelectAll();
    }


    @PostMapping("/send")
    public Object Send(@RequestParam("name") String name,@RequestParam("content") String content){
        if(!name.trim().isEmpty() && !content.trim().isEmpty())
            messageBoardMapper.Insert(name,content);
        return null;
    }


    @PostMapping("/review")
    public Object Review(@RequestParam("name") String name,@RequestParam("content") String content,@RequestParam("father_id")Integer fatherId){
        if(!name.trim().isEmpty() && !content.trim().isEmpty())
            messageBoardMapper.Insert(name,content);
        return null;
    }

}
