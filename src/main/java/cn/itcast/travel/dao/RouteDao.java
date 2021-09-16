package cn.itcast.travel.dao;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import javax.crypto.Cipher;
import java.util.List;

/**
 * 路线持久层
 */
public interface RouteDao {
    /**
     * 根据cid查询路线总记录数
     */
    int findtotalcount(int cid,String rname);


    /**
     * 根据cid，start,rows,查询每页显示的数据集合
     */
    List<Route> findbypage(int cid,String rname, int start, int rows);

    /**
     * 根据rid查询route对象
     * @param rid
     * @return
     */
    Route findone(int rid);
}
