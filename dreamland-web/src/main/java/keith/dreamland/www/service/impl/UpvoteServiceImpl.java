package keith.dreamland.www.service.impl;

import keith.dreamland.www.dao.UpvoteMapper;
import keith.dreamland.www.entity.Upvote;
import keith.dreamland.www.service.UpvoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpvoteServiceImpl implements UpvoteService {
    @Autowired
    private UpvoteMapper upvoteMapper;

    @Override
    @Transactional
    public Upvote findByUidAndConId(Upvote upvote) {
        return upvoteMapper.selectOne(upvote);
    }

    @Override
    @Transactional
    public int add(Upvote upvote) {
        return upvoteMapper.insert(upvote);
    }

    @Override
    @Transactional
    public Upvote getByUid(Upvote upvote) {
        return upvoteMapper.selectOne(upvote);
    }

    @Override
    @Transactional
    public void update(Upvote upvote) {
        upvoteMapper.updateByPrimaryKey(upvote);
    }

    @Override
    public void deleteByContentId(Long cid) {
        upvoteMapper.deleteByPrimaryKey(cid);
    }
}
