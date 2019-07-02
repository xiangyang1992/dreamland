package keith.dreamland.www.service;

import keith.dreamland.www.entity.Upvote;

/**
 * Created by 12903 on 2018/4/16.
 */
public interface UpvoteService {
    /**
     * 根据用户id和文章id查询
     * @param upvote
     * @return
     */
   Upvote findByUidAndConId(Upvote upvote);

    /**
     * 添加upvote
     * @param upvote
     * @return
     */
   int add(Upvote upvote);

    /**
     * 根据用户id查询最后一次登录的Upvote
     * @param upvote
     * @return
     */
   Upvote getByUid(Upvote upvote);

    /**
     * 更新Upvote
     * @param upvote
     */
   void update(Upvote upvote);

    /**
     * 根据文章id删除清空点赞数
     */
    void deleteByContentId(Long cid);
}
