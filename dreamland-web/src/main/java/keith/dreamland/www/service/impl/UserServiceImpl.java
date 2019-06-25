package keith.dreamland.www.service.impl;

import keith.dreamland.www.dao.UserMapper;
import keith.dreamland.www.entity.User;
import keith.dreamland.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by xiangyang on 2019/3/10 0010 17:10
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public int regist(User user) {
        return userMapper.insert(user);
//        int i = userMapper.insert(user);
//        i = i / 0;
//        return i;
    }

    @Override
    public User Login(String name, String password) {
        User user = new User();
        user.setEmail(name);
        user.setPassword(password);
        return userMapper.selectOne(user);
    }

    @Override
    @Transactional
    public User findByPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        return userMapper.selectOne(user);
    }


    @Override
    @Transactional
    public User findByEmial(String email) {
        User user = new User();
        user.setEmail(email);
        return userMapper.selectOne(user);
    }

    @Override
    @Transactional
    public User findById(Long id) {
        User user = new User();
        user.setId(id);
        return userMapper.selectOne(user);
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        userMapper.delete(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }
}
