package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavourageDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavourageDaoImpl implements FavourageDao {
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite findbyridanduid(int rid, int uid) {
        //这里，当查询的favourate为空时，则直接返回null,如果不为空时，放回查询结果；
        Favorite favorite=null;
        //surrend/with的快捷键ctrl+alt+t
        //处理异常，防止查询为空
        try {
            favorite = jdbcTemplate.queryForObject("select * from tab_favorite where rid=? and uid=?", new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return favorite;
    }

    /**
     * 根据rid查询当前线路被收藏多少次
     * @param rid
     * @return
     */
    @Override
    public int findfavourateaccount(int rid) {
        return  jdbcTemplate.queryForObject("select count(*) from tab_favorite where rid= ?", Integer.class, rid);
    }

    /**
     * 根据路线的rid和用户的uid完成点击添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void addfavourate(int rid, int uid) {
        jdbcTemplate.update("insert into tab_favorite values (?,?,?)",rid,new Date(),uid);
    }
}
