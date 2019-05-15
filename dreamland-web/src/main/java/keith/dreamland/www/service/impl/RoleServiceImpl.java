package keith.dreamland.www.service.impl;

import keith.dreamland.www.dao.RoleMapper;
import keith.dreamland.www.entity.Role;
import keith.dreamland.www.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional
    public Role findById(long id) {
        Role role = new Role();
        role.setId(id);
        return roleMapper.selectOne(role);
    }

    @Override
    @Transactional
    public int add(Role role) {
        return roleMapper.insert(role);
    }
}
