package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * 路线service
 */
public interface RouteService {
    /**
     * 查询
     * @param cid
     * @param currentpage
     * @param rows
     * @return
     */
    PageBean<Route> pagequery(int cid, int currentpage, int rows,String rname);

    /**
     * 根据rid完成旅游路线的详情查询
     * @param rid
     * @return
     */
    Route findone(String rid);
}
