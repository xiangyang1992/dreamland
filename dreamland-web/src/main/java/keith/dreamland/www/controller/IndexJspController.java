package keith.dreamland.www.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import keith.dreamland.www.common.DateUtils;
import keith.dreamland.www.common.PageHelper;
import keith.dreamland.www.common.StringUtil;
import keith.dreamland.www.entity.Comment;
import keith.dreamland.www.entity.Upvote;
import keith.dreamland.www.entity.User;
import keith.dreamland.www.entity.UserContent;
import keith.dreamland.www.service.CommentService;
import keith.dreamland.www.service.UpvoteService;
import keith.dreamland.www.service.UserContentService;
import keith.dreamland.www.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexJspController extends BaseController {
    public final static Logger log = Logger.getLogger(IndexJspController.class);

    @Autowired
    private UserContentService userContentService;

    @Autowired
    private UpvoteService upvoteService;

    @Resource(name = "commentService")
    private CommentService commentService;

    @Resource(name = "userService")
    private UserService userService;

    /**
     * @param
     * @return
     * @Author xy
     * @description 分页查询
     * @CreateDate
     **/
    @RequestMapping(value = "/index_list")
    public String findAllList(Model model, @RequestParam(value = "id", required = false) String id,
                              @RequestParam(value = "pageNum", required = false) Integer pageNum,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("--------进入index_list------");
        User user = (User) getSession().getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        PageHelper.Page<UserContent> page = findAll(null, pageNum, pageSize);
        model.addAttribute("page", page);
        return "../index";
    }

    /**
     * @param
     * @return
     * @Author xy
     * @description 点赞和踩
     * @CreateDate
     **/
    @RequestMapping(value = "/upvote")
    @ResponseBody
    public Map<String, Object> upvote(Model model, @RequestParam(value = "id", required = false) long id,
                                      @RequestParam(value = "uid", required = false) long uid,
                                      @RequestParam(value = "upvote", required = false) Integer upvote) {
        log.info("id=" + id + "uid=" + uid + "upvote=" + upvote);
        Map<String, Object> map = new HashMap<>();
        User user = (User) getSession().getAttribute("user");
        if (user == null) {
            map.put("data", "fail");
            return map;
        }
        Upvote upvote1 = new Upvote();
        upvote1.setContentId(id);
        upvote1.setuId(user.getId());
        Upvote up = upvoteService.findByUidAndConId(upvote1);
        UserContent userContent = userContentService.findById(id);
        if (upvote == -1) {
            if (up != null) {
                log.info(up.toString() + "----------");
                if ("1".equals(up.getDownvote())) {
                    map.put("data", "downvote");
                    return map;
                }
                up.setDownvote("1");
                up.setUpvoteTime(new Date());
                up.setIp(getClientIpAddress());
                upvoteService.update(up);
            } else {
                upvote1.setDownvote("1");
                upvote1.setUpvoteTime(new Date());
                upvote1.setIp(getClientIpAddress());
                upvoteService.add(upvote1);
            }
            userContent.setDownvote(userContent.getDownvote() + upvote);
        } else if (upvote == 1) {
            if (up != null) {
                log.info(up.toString() + "----------");
                if ("1".equals(up.getUpvote())) {
                    map.put("data", "upvote");
                    return map;
                }
                up.setUpvote("1");
                up.setUpvoteTime(new Date());
                up.setIp(getClientIpAddress());
                upvoteService.update(up);
            } else {
                upvote1.setUpvote("1");
                upvote1.setUpvoteTime(new Date());
                upvote1.setIp(getClientIpAddress());
                upvoteService.add(upvote1);
            }
            userContent.setUpvote(userContent.getUpvote() + upvote);
        }
        userContentService.updateById(userContent);
        map.put("data", "success");
        return map;
    }

    //评论，回复
    @RequestMapping(value = "/reply")
    @ResponseBody
    public Map<String, Object> reply(Model model, @RequestParam(value = "content_id", required = false) Long content_id) {
        Map map = new HashMap<String, Object>();
        List<Comment> commentList = commentService.findAllFirstComment(content_id);
        if (commentList != null && commentList.size() > 0) {
            for (Comment c : commentList) {
                List<Comment> list = commentService.findAllChildrenComment(c.getConId(), c.getChildren());
                if (list != null && list.size() > 0) {
                    for (Comment comm : list) {
                        if (comm.getById() != null) {
                            User ByUser = userService.findById(comm.getById());
                            comm.setByUser(ByUser);
                        }
                    }
                }
                c.setComments(list);
            }
        }
        map.put("list", commentList);
        return map;
    }


    @RequestMapping(value = "/comment")
    @ResponseBody
    public Map<String, Object> comment(Model model, @RequestParam(value = "id", required = false) Long id,
                                       @RequestParam(value = "cont_id", required = false) Long cont_id,
                                       @RequestParam(value = "uid", required = false) Long uid,
                                       @RequestParam(value = "by_id", required = false) Long bid,
                                       @RequestParam(value = "oSize", required = false) String oSize,
                                       @RequestParam(value = "comment_time", required = false) String comment_time,
                                       @RequestParam(value = "upvote", required = false) Integer upvote) {

        Map map = new HashMap<String, Object>();
        User user = (User) getSession().getAttribute("user");
        if (user == null) {
            map.put("data", "fail");
            return map;
        }
        if (id == null) {
            Date date = DateUtils.StringtoDate(comment_time, "yyyy-MM-dd HH:mm:ss");
            Comment comment = new Comment();
            comment.setComContent(oSize);
            comment.setCommTime(date);
            comment.setConId(cont_id);
            comment.setComId(uid);
            if (upvote == null) {
                upvote = 0;
            }
            comment.setById(bid);
            comment.setUpvote(upvote);
            User u = userService.findById(uid);
            comment.setUser(u);
            commentService.add(comment);
            map.put("data", comment);

            UserContent userContent = userContentService.findById(cont_id);
            Integer num = userContent.getCommentNum();
            userContent.setCommentNum(num + 1);
            userContentService.updateById(userContent);
        } else {
            //点赞
            Comment c = commentService.findById(id);
            c.setUpvote(upvote);
            commentService.update(c);
        }
        return map;
    }


    @RequestMapping(value = "/deleteComment")
    @ResponseBody
    public Map<String, Object> deleteComment(Model model, @RequestParam(value = "cont_id", required = false) Long cont_id,
                                             @RequestParam(value = "uid", required = false) Long uid,
                                             @RequestParam(value = "id", required = false) Long id,
                                             @RequestParam(value = "fid", required = false) Long fid) {
        int num = 0;
        Map map = new HashMap<String, Object>();
        User user = (User) getSession().getAttribute("user");
        if (user == null) {
            map.put("data", "fail");
        } else {
            if (user.getId().equals(uid)) {
                Comment comment = commentService.findById(id);
                if (StringUtils.isBlank(comment.getChildren())) {
                    if (fid != null) {
                        //去掉id
                        Comment fcomment = commentService.findById(fid);
                        String child = StringUtil.getString(fcomment.getChildren(), id);
                        fcomment.setChildren(child);
                        commentService.update(fcomment);
                    }
                    commentService.deleteById(id);
                    num = num + 1;
                } else {
                    String children = comment.getChildren();
                    commentService.deleteChildrenComment(children);
                    String[] arr = children.split(",");
                    commentService.deleteById(id);
                    num = num + arr.length + 1;
                }
                UserContent userContent = userContentService.findById(cont_id);
                if (userContent != null) {
                    if (userContent.getCommentNum() - num >= 0) {
                        userContent.setCommentNum(userContent.getCommentNum() - num);
                    } else {
                        userContent.setCommentNum(0);
                    }
                    userContentService.updateById(userContent);
                }
                map.put("data", userContent != null ? userContent.getCommentNum() : null);
            } else {
                map.put("data", "no-access");
            }
        }
        return map;
    }

    @RequestMapping(value = "/comment_pl")
    @ResponseBody
    public Map<String ,Object> addCommentChild(Model model,@RequestParam(value = "cont_id",required = false)Long cont_id,
                                          @RequestParam(value = "uid",required = false)Long uid,
                                          @RequestParam(value = "by_id",required = false)Long bid,
                                          @RequestParam(value = "oSize",required = false)String  oSize,
                                          @RequestParam(value = "pl_time",required = false)String comment_time,
                                          @RequestParam(value = "id",required = false)Long id,
                                          @RequestParam(value = "upvote",required = false)Integer upvote) {
        Map map = new HashMap<String, Object>();
        User user = (User) getSession().getAttribute("user");
        if (user == null) {
            map.put("data", "fail");
            return map;
        }
        Date date = DateUtils.StringtoDate(comment_time, "yyyy-MM-dd HH:mm:ss");
        Comment comment = new Comment();
        comment.setConId(cont_id);
        comment.setComContent(oSize);
        comment.setCommTime(date);
        comment.setComId(uid);
        if (upvote == null) {
            upvote = 0;
        }
        comment.setById(bid);
        comment.setUpvote(upvote);
        User u = userService.findById(uid);
        comment.setUser(u);
        commentService.add(comment);

        Comment comm = commentService.findById(id);
        if (StringUtils.isBlank(comm.getChildren())) {
            comm.setChildren(comment.getId().toString());
        } else {
            comm.setChildren(comm.getChildren() + "," + comment.getId());
        }
        commentService.update(comm);
        map.put("data", comment);
        UserContent userContent = userContentService.findById(cont_id);
        Integer num = userContent.getCommentNum();
        userContent.setCommentNum(num+1);
        userContentService.updateById(userContent);
        return map;
    }
}
