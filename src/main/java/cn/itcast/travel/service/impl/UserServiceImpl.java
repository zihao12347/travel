package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;
import com.alibaba.druid.sql.visitor.functions.Concat;

public class UserServiceImpl implements UserService {
    private UserDaoImpl userdao = new UserDaoImpl();

    @Override
    public boolean register(User user) {
        //根据username查询数据库是否已存在该用户名
        User findbyname = userdao.findbyname(user.getUsername());
        if (findbyname != null) {//当查询到有该用户名时，表示数据库中有该用户

            return false;
        } else {//不纯在则保存信息
            //2.1设置用户注册状态码,注册没激活前默认设置为N
            user.setStatus("N");
            //2.2设置用户注册激活码,要求唯一，
            user.setCode( UuidUtil.getUuid());
            //2.3保存用户
            userdao.saveuser(user);

            //3激活邮件，设置邮件激活正文
            String content = "<a href='http://localhost/travel/user/activeUser?code=" +user.getCode() + "'>点击激活【黑马旅游网】</a>";
            MailUtils.sendMail(user.getEmail(), content, "邮件激活！");
            return true;
        }
    }

    /**
     * 激活方法
     *
     * @param code
     * @return
     */
    @Override
    public Boolean active(String code) {
        //1.根据用激活码查询用户
        User user = userdao.findbycode(code);
        //判断查询的用户是否为空
        if (user == null) {//当查询的用户为空时，表示注册激活时
            return false;
        } else {//否则，激活成功，并且更改用户的激活状态码
            userdao.updatestatus(user);
            return true;
        }


    }

    @Override
    public User login(User user) {
        UserDaoImpl userDao = new UserDaoImpl();
        User queryuser = userdao.findbyusernameandpassword(user.getUsername(), user.getPassword());
        return queryuser;
    }


}
