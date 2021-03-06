package keith.dreamland.www.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.common.Mapper;
import keith.dreamland.www.entity.UserContent;

import java.util.List;

/**
 * Created by Administrator on 2018/1/9.
 */
public interface UserContentMapper extends Mapper<UserContent> {

    List<UserContent> findCategoryByUid(@Param(value = "uid") Long uid);

    int insertContent(UserContent userContent);

    List<UserContent> findHotPage(@Param(value = "uid") Long uid);

    List<UserContent> findByJoin(UserContent userContent);
}
