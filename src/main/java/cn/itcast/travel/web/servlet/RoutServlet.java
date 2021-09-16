package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavourageService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavourageServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RoutServlet extends BaseServlet {
    //声明RouteServiceImpl对象
    private RouteServiceImpl routeService = new RouteServiceImpl();
    //实例化FavourateService对象
    private FavourageService favourageService = new FavourageServiceImpl();

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pagequery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /**
         * 1.获取前台传入的参数；
         */
        //获取前台转入的currentpage:当前页面参数，
        String currentpagestr = request.getParameter("currentpage");
        //获取rows：每页显示的条数的参数
        String rowsstr = request.getParameter("rows");
        //获取cid：分页id
        String cidstr = request.getParameter("cid");
        //获取rname，旅游路线搜索的参数
        String rname = request.getParameter("rname");
        //解决中文乱码问题
        if (rname != null && rname.length() > 0) {
            rname = new String(rname.getBytes("ISO-8859-1"), "utf-8");
        }
        /**
         * 2.处理参数：由于获取的参数都是string类型的数据，我们要转化成int类型，方便数据库查询
         */
        int cid = 0;
        if (cidstr != null && cidstr.length() > 0 && !"null".equals(cidstr)) {
            cid = Integer.parseInt(cidstr);
        }
        int currentpage = 0;//当前页面，不传值，则设置一个默认的值为1
        if (currentpagestr != null && currentpagestr.length() > 0) {
            currentpage = Integer.parseInt(currentpagestr);
        } else {
            currentpage = 1;
        }
        int rows = 0;//每页显示的条数，不传值，则设置一个默认的值为5
        if (rowsstr != null && rowsstr.length() > 0) {
            rows = Integer.parseInt(rowsstr);
        } else {
            rows = 5;
        }

        /**
         * 3.调用service，完成查询pagebean
         */
        PageBean<Route> pageBean = routeService.pagequery(cid, currentpage, rows, rname);

        /**
         * 4.将查询的数据转化成json，返回前台
         */
        writevalue(pageBean, response);
    }

    /**
     * 查询旅游线路的详情/查询旅游路线的收藏次数
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取rid参数
        String rid = request.getParameter("rid");

        //2.调用service,查询route对象
        RouteService routeService = new RouteServiceImpl();
        Route route = routeService.findone(rid);
        //3.将route对象，转化成json数据，写到前台
        writevalue(route, response);
    }

    /**
     * 判断当前用户当前旅游路线是否被收藏
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isfavourage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取rid参数
        String rid = request.getParameter("rid");
        //2.从session域中获取当前用户是否登入
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {//当用户没有登入时
            uid = 0;
        } else {//当用户登入时：
            uid = user.getUid();
        }
        //3.调用FavourageServic查询用户是否收藏当前旅游线路
        boolean isfavourate = favourageService.isfavourate(rid, uid);

        //4.写回客户端flag标记
        writevalue(isfavourate, response);
    }

    /**
     * 根据路线rid和用户的uid完成点击添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addfavourate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取路线的rid
        String rid = request.getParameter("rid");
        //2.获取用户的uid
        User user =(User) request.getSession().getAttribute("user");
        int uid;
        if (user != null) {//用户已经登入
            uid=user.getUid();
        }else {
            return;
        }
        //3.调用service完成添加收藏
        favourageService.addfavourate(rid,uid);


    }

}
