package keith.dreamland.www.service;

import keith.dreamland.www.entity.Role;

/**
 * Created by 12903 on 2018/4/16.
 */
public interface RoleService {
    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    Role findById(long id);

    /**
     * 添加角色
     * @param role
     * @return
     */
    int add(Role role);
}
