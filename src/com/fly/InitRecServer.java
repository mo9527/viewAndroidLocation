package com.fly;

import com.aliyun.openservices.ons.api.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by John on 2016/12/16.
 */

public class InitRecServer extends HttpServlet{
    public static String message;
    private int status = 0;
    private int checkStatus = 0;


    /**
     * 初始化消息接收服务器
     */
    public void init(){

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkStatus++;
                if (Math.abs(status - checkStatus) > 30){
                    setMessage(null);
                    checkStatus = 0;
                }
            }
        },0, 10000);

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, "CID_WEIWT_REC");// 您在MQ控制台创建的Consumer ID
        properties.put(PropertyKeyConst.AccessKey, "");// 鉴权用AccessKey，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, "");// 鉴权用SecretKey，在阿里云服务器管理控制台创建
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("location", "*", new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                System.out.println("Receive: " + message);
                try {
                    String msg = new String(message.getBody(), "UTF-8");
                    System.out.println("消息内容：" + msg);
                    setMessage(msg);
                    status = 0;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Action.CommitMessage;
            }
        });
        consumer.start();
        System.out.println("Consumer Started");
        System.out.println("消息服务器已完成初始化");
    }


    public static String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        System.out.println("had set message **********************");
        System.out.println("the message is " + message);
        this.message = message;
    }


}
