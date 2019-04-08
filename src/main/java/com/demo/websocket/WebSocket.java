package com.demo.websocket;

import com.alibaba.fastjson.JSON;
import com.demo.bean.ReceiveMessage;
import com.demo.util.Print;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/websocket")
@Component    //此注解千万千万不要忘记，它的主要作用就是将这个监听器纳入到Spring容器中进行管理
public class WebSocket {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("有新连接加入！");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        System.out.println("有一连接关闭！");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        //处理发送来的信息
        ReceiveMessage receiveMessage = JSON.parseObject(message,ReceiveMessage.class);
        Print p = new Print();
        p.printExec(receiveMessage);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 向浏览器发送消息
     *
     * @return
     * @Author ZhangGang
     * @Date 2019/3/5 10:08
     * @Param
     **/
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

}