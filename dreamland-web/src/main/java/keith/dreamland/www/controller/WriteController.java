package keith.dreamland.www.controller;


import keith.dreamland.www.entity.User;
import keith.dreamland.www.entity.UserContent;
import keith.dreamland.www.service.UserContentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class WriteController extends BaseController {
    private final static Logger log = Logger.getLogger(WriteController.class);

    @Autowired
    private UserContentService userContentService;

    @RequestMapping(value = "/writedream")
    public String WriteDream(Model model) {
        User user = (User) getSession().getAttribute("user");
        model.addAttribute("user", user);
        return "write/writedream";
    }


    @RequestMapping(value = "/doWritedream")
    @ResponseBody
    public String  doWritedream(Model model, HttpServletRequest request) {
        String id = getRequest().getParameter("id");
        String category = getRequest().getParameter("category");
        String txtTitle = getRequest().getParameter("txtT_itle");
        String content = getRequest().getParameter("content");
        String private_dream = getRequest().getParameter("private_dream");
        User user = (User) getSession().getAttribute("user");
        if (user == null) {
           //未登陆
            model.addAttribute("error", "请先登陆!");
            return "../login";
        }
        UserContent userContent = new UserContent();
        userContent.setCategory(category);
        userContent.setContent(content);
        userContent.setRptTime(new Date());
        userContent.setTitle(txtTitle);
        String imgUrl = user.getImgUrl();
        if (StringUtils.isEmpty(imgUrl)) {
            userContent.setImgUrl("/images/icon_m.jpg");
        } else {
            userContent.setImgUrl(imgUrl);
        }
        if ("on".equals(private_dream)) {
            userContent.setPersonal("1");
        } else {
            userContent.setPersonal("0");
        }
        userContent.setUpvote(0);
        userContent.setDownvote(0);
        userContent.setCommentNum(0);
        userContent.setuId(user.getId());
        userContent.setNickName(user.getNickName());
        userContentService.addContent(userContent);
        return "write/writesuccess";
    }

}
