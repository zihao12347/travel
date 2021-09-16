package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * BaseServlet不需要被访问到，所以不需要设置访问路径
 * 他完成的只是分发各个servlet模块方法分发任务
 */

public class BaseServlet extends HttpServlet {
    /**
     * 使用service方法完成对各个模块方法的分发，
     * 当servlet模块的的方法被访问时，
     * 当前的BaseServlet的service方法也会被访问，
     * 因为各个servlet模块继承当前的BaseServlet；
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("当前的BaseServlet的service方法被访问！");
        /**
         * 完成方法的分发，类似于使用动态代理技术完成方法的分发
         */

        //1获取当前访问的URI
        String requestURI = req.getRequestURI();//travel/user/add

        //2.获取当前访问的方法名
        String method = requestURI.substring(requestURI.lastIndexOf("/") + 1);

        //通过substring()截取字符串方法，,lastIndexOf()方法以某个字符开始后面的字符串，+1，去掉 /；
        //System.out.println(method);
        try {
            //3.获取该method方法对象,，this：表示谁调用我，我代表谁，因此this应该表示当前访问的Servlet：如UserServlet;
            // 通过获取当前类的字节码文件，然后获取方法对象，
            //getDeclaredMethod()，忽略访问全限，获取方法对象
            Method methodobject = this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
            //System.out.println(methodobject);
            //暴力反射,设置可获取
             //methodobject.setAccessible(true);
            //4.执行方法；
            methodobject.invoke(this, req, resp);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将传入的obj对象序列化为json数据，然后写入客户端
     * @param obj
     * @param response
     */
    public void writevalue(Object obj,HttpServletResponse response) throws IOException {
        //2.序列化json返回
        ObjectMapper mapper=new ObjectMapper();
        String value = mapper.writeValueAsString(obj);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(value);
    }

    /**
     * 将传入的obj对象序列化为json数据，然后返回
     * @param obj
     * @param response
     */
    public String wirtevalueasstring(Object obj,HttpServletResponse response) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        String writeValueAsString = mapper.writeValueAsString(obj);
        return writeValueAsString;
    }
}
