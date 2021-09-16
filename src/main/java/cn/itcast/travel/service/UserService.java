package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     * 注册用户
     */
    boolean register(User user);

    /**
     * 邮件激活方法
     * @param code
     * @return
     */
    Boolean active(String code);

    User login(User user);
}
