package keith.dreamland.www.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import keith.dreamland.www.common.PageHelper;
import keith.dreamland.www.entity.User;
import keith.dreamland.www.entity.UserContent;
import keith.dreamland.www.service.UserContentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PersonalController extends BaseController {
    private final static Logger log = Logger.getLogger(PersonalController.class);

    @Autowired
    private UserContentService userContentService;

    /**
     * @param
     * @return
     * @Author xy
     * @description 初始化个人主页数据
     * @CreateDate
     **/
    @RequestMapping(value = "/list")
    public String findList(Model model, @RequestParam(value = "id", required = false) Long id,
                           @RequestParam(value = "pageNum", required = false) Integer pageNum,
                           @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        User user = (User) getSession().getAttribute("user");
        UserContent userContent = new UserContent();
        UserContent uc = new UserContent();
        if (user != null) {
            model.addAttribute("user", user);
            userContent.setuId(user.getId());
            uc.setuId(user.getId());
        } else {
            return "../login";
        }
        log.info("初始化个人主页");
        //查询博客分类
        List<UserContent> categorys = userContentService.findCategoryByUid(user.getId());
        model.addAttribute("categorys", categorys);
        //发布的博客，不含私密博客
        userContent.setPersonal("0");
        //默认每页显示4篇
        pageSize = 8;
        PageHelper.Page<UserContent> page = findAll(userContent, pageNum, pageSize);
        model.addAttribute("page", page);

        //查询私密博客
        uc.setPersonal("1");
        PageHelper.Page<UserContent> page1 = findAll(uc, pageNum, pageSize);
        model.addAttribute("page1", page1);

        //查询热博客
        UserContent userContent1 = new UserContent();
        userContent1.setPersonal("0");
        PageHelper.Page<UserContent> HotPage = findAllByUpvote(userContent1, pageNum, pageSize);
        model.addAttribute("HotPage", HotPage);
        return "personal/personal";
    }

    @RequestMapping(value = "/findByCategory")
    @ResponseBody
    public Map<String, Object> findByCategory(Model model, @RequestParam(value = "category", required = false) String category,
                                              @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Map map = new HashMap<String, Object>();
        User user = (User) getSession().getAttribute("user");
        if (user == null) {
            map.put("pageCate", "fail");
            return map;
        }
        pageSize = 8;//默认每页显示4篇
        PageHelper.Page<UserContent> pageCate = userContentService.findByCategory(category, user.getId(), pageNum, pageSize);
        map.put("pageCate", pageCate);
        return map;
    }

    @RequestMapping(value = "/findPersonal")
    @ResponseBody
    public Map<String, Object> findPersonal(Model model, @RequestParam(value = "pageNum", required = false)Integer pageNum,
                                            @RequestParam(value = "pageSize",required = false)Integer pageSize) {

        Map map = new HashMap<String, Object>();
        User user = (User) getSession().getAttribute("user");
        if (user == null) {
            map.put("page2", "fail");
            return map;
        }
        pageSize = 8;
        PageHelper.Page<UserContent> page = userContentService.findPersonal(user.getId(), pageNum, pageSize);
        map.put("page2", page);
        return map;
    }
}
