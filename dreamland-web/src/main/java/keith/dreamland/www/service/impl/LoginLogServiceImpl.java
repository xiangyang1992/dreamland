package keith.dreamland.www.service.impl;

import keith.dreamland.www.dao.LoginLogMapper;
import keith.dreamland.www.entity.LoginLog;
import keith.dreamland.www.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoginLogServiceImpl implements LoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    @Transactional
    public int add(LoginLog loginLog) {
        return loginLogMapper.insert(loginLog);
    }

    @Override
    @Transactional
    public List<LoginLog> findAll() {
        return loginLogMapper.select(null);
    }

    @Override
    @Transactional
    public List<LoginLog> findByUid(Long uid) {
        LoginLog loginLog = new LoginLog();
        loginLog.setuId(uid);
        return loginLogMapper.select(loginLog);
    }
}
