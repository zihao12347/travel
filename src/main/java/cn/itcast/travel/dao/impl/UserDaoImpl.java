package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import jdk.nashorn.internal.scripts.JD;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.jws.soap.SOAPBinding;
import java.util.List;

public class UserDaoImpl implements UserDao {
    //实例化JdbcTemplate对象
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findbyname(String username) {
        User user = null;   //当findbyname方法出现异常，user还是为空
        try {
            //1.定义sql
            String sql = "select * from tab_user where username = ?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {//处理异常

        }
        return user;
    }

    @Override
    public void saveuser(User user) {
        template.update("insert into tab_user (username,password,name,birthday,sex,telephone,email,status,code)values (?,?,?,?,?,?,?,?,?)", user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
    }

    @Override
    public User findbycode(String code) {
        User user = null;
        try {
            user = template.queryForObject("select * from tab_user where code=?", new BeanPropertyRowMapper<User>(User.class), code);
        } catch (Exception e) {

        }
        return user;
    }

    @Override
    public void updatestatus(User user) {
        template.update("update tab_user set status ='Y' where uid=?", user.getUid());

    }

    @Override
    public User findbyusernameandpassword(String username, String password) {
        User user=null;
        try {
            user = template.queryForObject("select * from tab_user where username=? and password=?", new BeanPropertyRowMapper<User>(User.class), username, password);

        }catch (Exception e){

        }
        return user;
    }

}

