package keith.dreamland.www.service.impl;

import keith.dreamland.www.common.PageHelper;
import keith.dreamland.www.dao.CommentMapper;
import keith.dreamland.www.dao.UserContentMapper;
import keith.dreamland.www.entity.Comment;
import keith.dreamland.www.entity.User;
import keith.dreamland.www.entity.UserContent;
import keith.dreamland.www.service.UserContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserContentServiceImpl implements UserContentService {

    @Autowired
    private UserContentMapper userContentMapper;
    @Resource
    private CommentMapper commentMapper;

    @Override
    @Transactional
    public PageHelper.Page<UserContent> findAll(UserContent content, Integer pageNum, Integer pageSize) {
        //分页查询
        System.out.println("第" + pageNum + "页");
        System.out.println("每页显示" + pageSize + "条");
        PageHelper.startPage(pageNum, pageSize);//分页查询开始
        List<UserContent> list = userContentMapper.findByJoin(content);
        PageHelper.Page endPage = PageHelper.endPage();//分页查询结束
        List<UserContent> result = endPage.getResult();
        return endPage;
    }

    @Override
    public PageHelper.Page<UserContent> findAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
//        Example e = new Example(UserContent.class);
//        e.setOrderByClause("rpt_time DESC");
        List<UserContent> list = userContentMapper.findByJoin(null);
        PageHelper.Page endPage = PageHelper.endPage();
        return endPage;
    }

    @Override
    @Transactional
    public PageHelper.Page<UserContent> findAllByUpvote(UserContent content, Integer pageNum, Integer pageSize) {
        Example e = new Example(UserContent.class);
        e.setOrderByClause("upvote desc");
        PageHelper.startPage(pageNum, pageSize);
        List<UserContent> list = userContentMapper.selectByExample(e);
        PageHelper.Page endPage = PageHelper.endPage();
        return endPage;
    }

    @Override
    public PageHelper.Page<UserContent> findByUpvote(Long uid,Integer pageNum,Integer pageSize) {
//        UserContent userContent = new UserContent();
//        userContent.setuId(uid);
        PageHelper.startPage(pageNum, pageSize);
        userContentMapper.findHotPage(uid);
        PageHelper.Page endPage = PageHelper.endPage();
        return endPage;
    }


    @Override
    @Transactional
    public List<UserContent> findByUserId(Long uid) {
        UserContent userContent = new UserContent();
        userContent.setuId(uid);
        List<UserContent> list = userContentMapper.select(userContent);
        return list;
    }

    @Override
    @Transactional
    public List<UserContent> findAll() {
        return userContentMapper.findByJoin(null);
    }

    @Override
    @Transactional
    public UserContent findById(long id) {
        UserContent userContent = new UserContent();
        userContent.setId(id);
        return userContentMapper.selectOne(userContent);
    }

    @Override
    @Transactional
    public void updateById(UserContent content) {
        userContentMapper.updateByPrimaryKeySelective(content);
    }

    @Override
    public List<UserContent> findCategoryByUid(Long uid) {
        return userContentMapper.findCategoryByUid(uid);
    }

    @Override
    public PageHelper.Page<UserContent> findByCategory(String category, Long uid, Integer pageNum, Integer pageSize) {
        UserContent userContent = new UserContent();
        if (!StringUtils.isBlank(category) && !"null".equals(category)) {
            userContent.setCategory(category);
        }
        userContent.setuId(uid);
        userContent.setPersonal("0");
        PageHelper.startPage(pageNum,pageSize);//开始分页
        userContentMapper.select(userContent);
        PageHelper.Page endPage = PageHelper.endPage();//分页结束
        return endPage;
    }

    @Override
    public PageHelper.Page<UserContent> findPersonal(Long uid, Integer pageNum, Integer pageSize) {
        UserContent userContent = new UserContent();
        userContent.setuId(uid);
        userContent.setPersonal("1");
        PageHelper.startPage(pageNum, pageSize);
        userContentMapper.select(userContent);
        PageHelper.Page endPage = PageHelper.endPage();
        return endPage;
    }

    @Override
    public int addContent(UserContent userContent) {
        return userContentMapper.insertContent(userContent);
    }

    @Override
    public void deleteById(Long cid) {
        userContentMapper.deleteByPrimaryKey(cid);
    }

}
