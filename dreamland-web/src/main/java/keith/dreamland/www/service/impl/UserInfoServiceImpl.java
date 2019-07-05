package keith.dreamland.www.service.impl;

import keith.dreamland.www.dao.UserInfoMapper;
import keith.dreamland.www.entity.UserInfo;
import keith.dreamland.www.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    @Transactional
    public UserInfo findByUid(Long id) {
        UserInfo userInfo = new UserInfo();
        userInfo.setuId(id);
        return userInfoMapper.selectOne(userInfo);
    }

    @Override
    @Transactional
    public void update(UserInfo userInfo) {
        userInfoMapper.updateByPrimaryKey(userInfo);
    }

    @Override
    @Transactional
    public void add(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }
}
