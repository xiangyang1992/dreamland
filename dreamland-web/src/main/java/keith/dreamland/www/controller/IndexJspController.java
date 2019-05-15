package keith.dreamland.www.controller;

import keith.dreamland.www.common.PageHelper;
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

@Controller
public class IndexJspController extends BaseController {
    public final static Logger log = Logger.getLogger(IndexJspController.class);

    @Autowired
    private UserContentService userContentService;

    @Autowired
    private UpvoteService upvoteService;

    @RequestMapping(value = "/index_list")
    public String findAllList(Model model, @RequestParam(value = "id",required = false) String  id,
                              @RequestParam(value = "pageNum",required = false ) Integer pageNum,
                              @RequestParam(value = "pageSize",required = false) Integer pageSize) {
        log.info("--------进入index_list------");
        User user = (User) getSession().getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        PageHelper.Page<UserContent> page = findAll(null, pageNum, pageSize);
        model.addAttribute("page", page);
        return "../index";
    }


}
