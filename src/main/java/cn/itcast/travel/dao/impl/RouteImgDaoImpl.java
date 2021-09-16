package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据route对象的rid查询RouteImg对象的集合，也就是旅游路线详情的图片
     * @param rid
     * @return
     */
    @Override
    public List<RouteImg> findimg(int rid) {
        List<RouteImg> routeImgs = jdbcTemplate.query("select * from tab_route_img where rid=?", new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        return routeImgs;
    }
}
