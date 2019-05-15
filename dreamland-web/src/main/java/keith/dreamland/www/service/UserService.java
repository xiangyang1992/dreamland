package keith.dreamland.www.service;

import keith.dreamland.www.entity.User;

/**
 * Created by xiangyang on 2019/3/10 0010 17:01
 */
public interface UserService {
    /**
     *@Description: 用户注册
     *@date: 2019/3/10 0010 17:02
     *@atuthor: xiangyang
     *@return: 
     */
    int regist(User user);
    /**
     *@Description: 用户登陆
     *@date: 2019/3/10 0010 17:02
     *@atuthor: xiangyang
     *@return: 
     */
    User Login(String email,String password);
    /**
     *@Description: 根据用户手机号码查询用户
     *@date: 2019/3/10 0010 17:03
     *@atuthor: xiangyang
     *@return: 
     */
    User findByPhone(String phone);
    /**
     *@Description: 根据用户邮箱查询用户
     *@date: 2019/3/10 0010 17:04
     *@atuthor: xiangyang
     *@return: 
     */
    User findByEmial(String email);
    /**
     *@Description: 根据id查询用户
     *@date: 2019/3/10 0010 17:04
     *@atuthor: xiangyang
     *@return: 
     */
    User findById(Long id);
    /**
     *@Description: 根据邮箱删除用户
     *@date: 2019/3/10 0010 17:05
     *@atuthor: xiangyang
     *@return: 
     */
    void deleteByEmail(String email);
    /**
     *@Description: 更新用户
     *@date: 2019/3/10 0010 17:05
     *@atuthor: xiangyang
     *@return: 
     */
    void update(User user);
}
