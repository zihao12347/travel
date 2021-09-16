package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * 查询Tab_route_img表
 */
public interface RouteImgDao {
    //根据route的rid查询RouteImg对象；
    List<RouteImg> findimg(int rid);
}
