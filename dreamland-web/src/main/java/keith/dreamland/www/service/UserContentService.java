package keith.dreamland.www.service;

import keith.dreamland.www.common.PageHelper;
import keith.dreamland.www.entity.Comment;
import keith.dreamland.www.entity.UserContent;

import java.util.List;

/**
 * Created by 12903 on 2018/4/16.
 */
public interface UserContentService {
    /**
     * 查询所有Content并分页
     * @param content
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageHelper.Page<UserContent> findAll(UserContent content, Integer pageNum, Integer pageSize);
    PageHelper.Page<UserContent> findAll(UserContent content, Comment comment, Integer pageNum, Integer pageSize);
    PageHelper.Page<UserContent> findAllByUpvote(UserContent content, Integer pageNum, Integer pageSize);

    /**
     * 根据用户id查询文章集合
     * @param uid
     * @return
     */
    List<UserContent> findByUserId(Long uid);

    /**
     * 查询所有文章
     * @return
     */
    List<UserContent> findAll();

    /**
     * 根据文章id查找文章
     * @param id
     * @return
     */
    UserContent findById(long id);
    /**
     * 根据文章id更新文章
     * @param content
     * @return
     */
    void updateById(UserContent content);

    /**
     * 根据用户id查询梦分类
     *
     * @param uid
     */
    List<UserContent> findCategoryByUid(Long uid);

    /**
     * 根据文章分类查询所有文章
     *
     * @param category
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageHelper.Page<UserContent> findByCategory(String category, Long uid, Integer pageNum, Integer pageSize);

    /**
     * 根据用户id查询所有私密博客
     *
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageHelper.Page<UserContent> findPersonal(Long id, Integer pageNum, Integer pageSize);

    /**
     * 添加文章
     *
     * @param content
     * @param
     * @return
     */
    int addContent(UserContent userContent);
}
