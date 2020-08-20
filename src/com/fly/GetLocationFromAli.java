package com.fly;

import javax.servlet.*;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by John on 2016/12/16.
 */
public class GetLocationFromAli implements Servlet{
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        System.out.println("我是service");
        String message = InitRecServer.message;
        PrintWriter out = response.getWriter();
        if (message != null){
            Map<String, String> jingweiMap = getJW(message);

            String jingdu = jingweiMap.get("jingdu");
            String weidu = jingweiMap.get("weidu");

            request.setAttribute("jingdu",jingdu);
            request.setAttribute("weidu", weidu);

            out.print("{\"jingdu\":"+jingdu+", \"weidu\":"+weidu+"}");

            System.out.println("jingdu  " + jingweiMap.get("jingdu"));
            System.out.println("weidu  " + jingweiMap.get("weidu"));
        }else {
            System.out.println("获取消息为空");
        }

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }

    //解析经纬度
    private Map<String, String> getJW(String msg){
        Map<String, String> jingwei = new HashMap<String, String>();
        try {
            String[] tmpArray = msg.split("\\n");
            for (String s : tmpArray) {
                if (s.indexOf("lontitude") != -1){
                    jingwei.put("jingdu", s.split(":")[1].trim());
                }
                if (s.indexOf("latitude") != -1){
                    jingwei.put("weidu", s.split(":")[1].trim());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return jingwei;
    }
}
