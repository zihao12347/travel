package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavourageDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavourageDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

/**
 * 线路service
 */
public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    //实例化注入RouteImgDao对象
    private RouteImgDao routeImgDao=new RouteImgDaoImpl();
    //实例化注入SellerDao对象
    private SellerDao sellerDao=new SellerDaoImpl();
    //实例化FavourateDao对象
    private FavourageDao favourageDao=new FavourageDaoImpl();
    /**
     * 封装pagebean
     *
     * @param cid
     * @param currentpage
     * @param rows
     * @return
     */
    @Override
    public PageBean<Route> pagequery(int cid, int currentpage, int rows,String rname) {
        //创建pagebean对象
        PageBean<Route> pageBean = new PageBean<Route>();
        //1.封装当前页面
        pageBean.setCurrentpage(currentpage);
        //2.封装每页显示的行数
        pageBean.setRows(rows);
        int findtotalcount = routeDao.findtotalcount(cid,rname);
        //3.设置总记录数
        pageBean.setTotalcount(findtotalcount);
        //4.根据总记录数计算totalpage: 总记录数%rows==0?总记录数/rows:总记录数%rows+1
        int totalpage = findtotalcount % rows == 0 ? findtotalcount / rows : (findtotalcount / rows) + 1;
        //设置总页数
        pageBean.setTotalpage(totalpage);
        //5.查询每页显示的数据集合
        //计算start
        int start = (currentpage - 1) * rows;
        List<Route> findbypage = routeDao.findbypage(cid,rname,start, rows);

        pageBean.setList(findbypage);

        return pageBean;
    }

    /**
     * 一：根据rid查询route对象，实现查询旅游路线的详情
     * 二：根据查询的route对象的rid，查询路线收藏次数数据
     * @param rid
     * @return
     */
    @Override
    public Route findone(String rid) {
        //一：1.查询route对象
        RouteDao routeDao=new RouteDaoImpl();
        Route route = routeDao.findone(Integer.parseInt(rid));
        //2.根据route对象rid查询tab_route_imag表
        List<RouteImg> routeImgs = routeImgDao.findimg(route.getRid());
        //将查询到的routeimg对象保存到route对象中的routeimglist参数中
        route.setRouteImgList(routeImgs);
        //3.根据route的sid参数查询tab_seller表，卖家信息
        Seller seller = sellerDao.findbysid(route.getSid());
        route.setSeller(seller);
        //二：1.根据查询到的route对象的rid查询路线收藏次数
        int i = favourageDao.findfavourateaccount(Integer.parseInt(rid));
        //2.设置旅游路线收藏的次数
        route.setCount(i);
        return route;

    }
}
