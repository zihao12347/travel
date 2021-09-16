package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    /**
     * 根据用户名数据查询改用户名是否存在
     */
    User findbyname(String username);
    /**
     * 保存用户方法
     */
    void saveuser(User user);

    /**
     * 根据激活码查询用户，
     * @param code
     */
    User findbycode(String code);

    /**
     * 更新注册激活状态为Y，注册时默认设置的是N
     * @param user
     */
    void updatestatus(User user);

    /**
     * 根据账户和密码查询用户
     * @param username
     * @param password
     * @return
     */
    User findbyusernameandpassword(String username, String password);
}
