package keith.dreamland.www.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import keith.dreamland.www.common.PageHelper;
import keith.dreamland.www.entity.Upvote;
import keith.dreamland.www.entity.User;
import keith.dreamland.www.entity.UserContent;
import keith.dreamland.www.service.UpvoteService;
import keith.dreamland.www.service.UserContentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexJspController extends BaseController {
    public final static Logger log = Logger.getLogger(IndexJspController.class);

    @Autowired
    private UserContentService userContentService;

    @Autowired
    private UpvoteService upvoteService;


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
     * @Author xy
     * @description 点赞和踩
     * @CreateDate
     * @param
     * @return
     **/
    @RequestMapping(value = "/upvote")
    @ResponseBody
    public Map<String, Object> upvote(Model model,@RequestParam(value = "id",required = false)long id,
                                      @RequestParam(value = "uid",required = false)long uid,
                                      @RequestParam(value = "upvote",required = false)Integer upvote) {
        log.info("id=" + id + "uid=" + uid + "upvote=" + upvote);
        Map<String, Object> map = new HashMap<>();
        User user = (User) getSession().getAttribute("user");
        if (user == null) {
            map.put("data","fail");
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
                if ("1".equals(up.getUpvote())){
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



}
