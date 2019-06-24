package keith.dreamland.www.service.impl;

import keith.dreamland.www.dao.CommentMapper;
import keith.dreamland.www.entity.Comment;
import keith.dreamland.www.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transactional
    public int add(Comment comment) {
        return commentMapper.insertComment(comment);
    }

    @Override
    @Transactional
    public void update(Comment comment) {
        commentMapper.updateByPrimaryKey(comment);
    }

    @Override
    @Transactional
    public List<Comment> findAll(Long content_id) {
        return commentMapper.selectAll(content_id);
    }

    @Override
    @Transactional
    public Comment findById(Long id) {
        Comment comment = new Comment();
        comment.setId(id);
        return commentMapper.selectOne(comment);
    }

    @Override
    @Transactional
    public List<Comment> findAllFirstComment(Long content_id) {
        return commentMapper.selectAllFirstComment(content_id);
    }

    @Override
    @Transactional
    public List<Comment> findAllChildrenComment(Long content_id, String children) {
        return commentMapper.selectAllChildrenComment(content_id, children);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Comment comment = new Comment();
        comment.setId(id);
        commentMapper.delete(comment);
    }

    @Override
    @Transactional
    public void deleteChildrenComment(String children) {
        Example e = new Example(Comment.class);
        Example.Criteria criteria =  e.createCriteria();
        List<Object> list = new ArrayList<Object>();
        String[] split =children.split(",");
        for (int i = 0; i < split.length ; i++) {
            list.add(split[i]);
        }
        criteria.andIn("id", list);
        commentMapper.deleteByExample(e);
    }
}
