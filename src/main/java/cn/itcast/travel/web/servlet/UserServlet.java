package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 当前的user模块servlet需要继承BaseServlet
 */
@WebServlet("/user/*")//*号表示各个方法的路径。/user/register....
public class UserServlet extends BaseServlet {
    //声明UserService业务对象
    private UserService userService = new UserServiceImpl();

    /**
     * 将方法的访问全限改成public，在BaseServlet中获取访问方法对象，则不会报错
     */

    /**
     * 注册功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置字符编码，这里由于设置了字符编码的过滤器，可以不用再设置
        /**
         * 判断验证码是否正确
         */
        //获取验证码数据
        String check = request.getParameter("check");
        //获取session域中保存的验证码信息
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        //保证验证码的唯一性，这里获取完则，立即将CHECKCODE_SERVER验证码移除session域中
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //当验证码不正确的时候，设置提示信息，
            ResultInfo info = new ResultInfo();
            info.setErrorMsg("验证码错误！");
            response.setContentType("application/json;chartset=utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(info);
            response.getWriter().write(json);
            return;
        } else {//当验证码正确的时候，

            //2.获取数据
            Map<String, String[]> parameterMap = request.getParameterMap();
            //3.使用BeanUtils封装数据
            BeanUtils beanUtils = new BeanUtils();
            //创建User对象
            User user = new User();
            try {
                beanUtils.populate(user, parameterMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            //4.1调用service相关方法的返回值设置提示信息
            //UserService userService = new UserServiceImpl();
            //register()方法返回的是一个boolean类型的值
            boolean flag = userService.register(user);
            //4.2实例化ResultInfo对象，用来封装后台给前台返回数据的实体类
            ResultInfo info = new ResultInfo();
            if (flag) {//表示注册成功
                info.setFlag(true);
            } else {//表示创建失败
                info.setFlag(false);
                info.setErrorMsg("注册失败！");
            }
            //4.3将json数据写给客户端
            //设置响应类型:contenttyep
            response.setContentType("application/json;chartset=utf-8");
            ObjectMapper mapper = new ObjectMapper();
            //将info提示信息对象转化成json数据，
            String json = mapper.writeValueAsString(info);
            //写入前台
            response.getWriter().write(json);
        }


    }

    /**
     * 登录功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();
        //判断验证码是否正确
        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            //响应数据给前台
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        } else {

            //1.获取表单数据
            Map<String, String[]> parameterMap = request.getParameterMap();

            //2.封装数据
            User user = new User();
            try {
                BeanUtils.populate(user, parameterMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //3.调用service的登入方法
           // UserServiceImpl userService = new UserServiceImpl();
            User queryuser = userService.login(user);

            //4.判断用户是否为空
            if (queryuser == null) {//表示数据库没有该用户,设置提示信息
                info.setFlag(false);
                info.setErrorMsg("账户或密码错误！");
            }
            //5.判断用户信息正确后，但是用户并没有激活
            if (queryuser != null && !"Y".equals(queryuser.getStatus())) {
                //用户并没有激活,设置提示信息
                info.setFlag(false);
                info.setErrorMsg("该用户注册后，并未激活！");
            }
            //6.当登入成功后，跳转网站首页
            if (queryuser != null && "Y".equals(queryuser.getStatus())) {
                //这里为了登入成功后，能显示登入的用户名，我们将查询的queryuser对象保存在session中
                request.getSession().setAttribute("user", queryuser);
                info.setFlag(true);
            }
            //响应数据给前台
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);

        }
    }

    /**
     * 查询当个用户功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.从session中获取user对象
        Object user = request.getSession().getAttribute("user");
        //2.将user对象写入到前台
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        response.getWriter().write(json);
    }

    /**
     * 退出功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //将登入时，保存的session销毁
        request.getSession().removeAttribute("user");
        //2.重定向到进行登录的页面：这样不会在转发请求数据了
        response.sendRedirect("http://localhost/travel/login.html");

    }

    /**
     * 激活邮件功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void activeUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取传递的邮件激活码
        String code = request.getParameter("code");
        //2.调用service的激活方法完成激活
        //UserServiceImpl userService = new UserServiceImpl();
        Boolean flag = userService.active(code);


        //判断标记,根据激活方法的返回值，设置输出页面激活成功或者失败提示信息
        String msg;
        if (flag) {//激活成功
            //输出一个跳转到登入页面的链接

            msg = "激活成功，请<a href='http://localhost/travel/login.html'>登录</a>";
        } else {//激活失败
            msg = "激活失败，请联系管理员咨询！";
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }

}
