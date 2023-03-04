package com.east.laugh.pgmessageboard;

import com.east.laugh.pgmessageboard.Mapper.MessageBoardMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PgMessageBoardApplicationTests {



    @Autowired
    private MessageBoardMapper messageBoardMapper;

    @Test
    void contextLoads() {
        System.out.println(messageBoardMapper.SelectAll());

    }

}
