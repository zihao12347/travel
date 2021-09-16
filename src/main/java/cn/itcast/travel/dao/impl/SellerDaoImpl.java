package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据route对象的sid属性查询Tab_seller表，卖家详情信息
     * @param sid
     * @return
     */
    @Override
    public Seller findbysid(int sid) {
        Seller seller = jdbcTemplate.queryForObject("select * from tab_seller where sid=?", new BeanPropertyRowMapper<Seller>(Seller.class), sid);
        return seller;
    }
}
