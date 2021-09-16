package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    /**
     * 根据cid查询路线的总记录条数
     */
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());



    @Override
    public int findtotalcount(int cid, String rname) {
        //多参数的动态查询
        //1.定义一个sql模板
        String sql = "select count(*) from tab_route where 1=1 ";
        //2.定义一个StringBuilder构造器,方便生成动态sql语句
        StringBuilder sb = new StringBuilder(sql);
        //3.定义一个list集合,方便在查询传递参数的时候，可以按照参数的个数传递，
        List<Object> params=new ArrayList<Object>();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);//添加查询的参数
        }
        if (rname != null && rname.length() > 0 &&!"null".equals(rname)) {

            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }

        Integer queryForObject = jdbcTemplate.queryForObject(sb.toString(), Integer.class, params.toArray()
        );
        return queryForObject;
    }

    /**
     * 根据cid,start,rows查询分页数据集合
     *
     * @param cid
     * @param start
     * @param rows
     * @return
     */
    @Override
    public List<Route> findbypage(int cid,String rname, int start, int rows) {
        //1.定义一个sql模板
        String sql="select * from tab_route where 1=1 ";
        //2.定义一个StringBuilder构造器，方便动态的生成sql语句
        StringBuilder sb=new StringBuilder(sql);
        //3.定义一个List集合，方便保存可变的查询参数
        List<Object> params=new ArrayList<Object>();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname!=null && rname.length()>0 &&!"null".equals(rname)){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ?, ? ");

        params.add(start);
        params.add(rows);

        List<Route> routeList = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class), params.toArray()
        );
        return routeList;
    }

    /**
     * 根据rid查询route对象
     * @param rid
     * @return
     */
    @Override
    public Route findone(int rid) {
        Route route = jdbcTemplate.queryForObject("select * from tab_route where rid=?", new BeanPropertyRowMapper<Route>(Route.class), rid);



        return route;
    }


}
