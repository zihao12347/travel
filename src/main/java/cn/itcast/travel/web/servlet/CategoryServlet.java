package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    //声明categoryservice变量
    private CategoryService categoryService=new CategoryServiceImpl();
    /**
     * 查询所有的Category的方法
     * @param request
     * @param response
     */
    public void findall(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //1.调用categoryservice的findall方法查询所有
        List<Category> categoryList = categoryService.findall();
        //2.序列化json返回
       /* ObjectMapper mapper=new ObjectMapper();
        String value = mapper.writeValueAsString(categoryList);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(value);*/
       //这里调用父类baseservlet的writevale()方法简化代码
       writevalue(categoryList,response);

    }
}
