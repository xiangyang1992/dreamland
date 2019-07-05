package keith.dreamland.www.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import keith.dreamland.www.common.Constants;
import keith.dreamland.www.common.DateUtils;
import keith.dreamland.www.common.MD5Util;
import keith.dreamland.www.common.PageHelper;
import keith.dreamland.www.entity.User;
import keith.dreamland.www.entity.UserContent;
import keith.dreamland.www.entity.UserInfo;
import keith.dreamland.www.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PersonalController extends BaseController {
    private final static Logger log = Logger.getLogger(PersonalController.class);

    @Autowired
    private UserContentService userContentService;
    @Autowired
    private CommentService commentService;

    @Autowired
    private UpvoteService upvoteService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;
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
                           @RequestParam(value = "pageSize", required = false) Integer pageSize,
                           @RequestParam(value = "manage", required = false) String manage) {
        if (StringUtils.isNotBlank(manage)) {
            model.addAttribute("manage", manage);
        }
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
        pageSize = 5;
        PageHelper.Page<UserContent> page = findAll(userContent, pageNum, pageSize);
        model.addAttribute("page", page);

        //查询私密博客
        uc.setPersonal("1");
        PageHelper.Page<UserContent> page1 = findAll(uc, pageNum, pageSize);
        model.addAttribute("page1", page1);

        //查询热博客
        PageHelper.Page<UserContent> HotPage = userContentService.findByUpvote(user.getId(), pageNum, pageSize);
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
        pageSize = 5;//默认每页显示4篇
        PageHelper.Page<UserContent> pageCate = userContentService.findByCategory(category, user.getId(), pageNum, pageSize);
        map.put("pageCate", pageCate);
        return map;
    }

    @RequestMapping(value = "/findPersonal")
    @ResponseBody
    public Map<String, Object> findPersonal(Model model, @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                            @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        Map map = new HashMap<String, Object>();
        User user = (User) getSession().getAttribute("user");
        if (user == null) {
            map.put("page2", "fail");
            return map;
        }
        pageSize = 5;
        PageHelper.Page<UserContent> page = userContentService.findPersonal(user.getId(), pageNum, pageSize);
        map.put("page2", page);
        return map;
    }

    @RequestMapping(value = "/deleteContent")
    public String deleteContent(Model model, @RequestParam(value = "cid", required = false) Long cid) {
        User user = (User) getSession().getAttribute("user");
        if (user == null) {
            return "../login";
        }
        commentService.deleteByContentId(cid);
        upvoteService.deleteByContentId(cid);
        userContentService.deleteById(cid);
        return "redirect:/list?manage=manage";
    }

    /**
     * 修改个人资料
     */
    @RequestMapping(value = "/profile")
    public String updateProfile(Model model, HttpServletRequest request) {
        log.info("---修改个人资料---");
        User user = (User) getSession().getAttribute("user");
        if (user == null) {
            return "../login";
        }
        String uid = getRequest().getParameter("id");
        UserInfo userInfo = userInfoService.findByUid(Long.valueOf(uid));
        model.addAttribute("user", user);
        model.addAttribute("userInfo", userInfo);
        return "profile/profile";
    }

    @RequestMapping(value = "/saveImg")
    @ResponseBody
    public Map<String, Object> saveImg(Model model, HttpServletRequest request) {
        log.info("保存图片成功");
        Map map = new HashMap<String, Object>();
        String imgUrl = getRequest().getParameter("url");
        User user = (User) getSession().getAttribute("user");
        user.setImgUrl(imgUrl);
        userService.update(user);
        map.put("msg", "success");
        return map;
    }

    //保存个人信息
    @RequestMapping(value = "/saveUserInfo")
    public String  saveUserInfo(Model model, HttpServletRequest request,@RequestParam(value = "birthday",required = false)String  birthday) {
        log.info("保存个人信息成功");
        User user = (User) getSession().getAttribute("user");
        String name = getRequest().getParameter("name");
        String nickName = getRequest().getParameter("nick_name");
        String sex = getRequest().getParameter("sex");
        String address = getRequest().getParameter("address");
        UserInfo userInfo = userInfoService.findByUid(user.getId());
        userInfo.setAddress(address);
        Date bir = DateUtils.StringtoDate(birthday, "yyyy-MM-dd");
        userInfo.setBirthday(bir);
        if (sex == "0" || "0".equals(sex)) {
            userInfo.setSex("男");
        } else {
            userInfo.setSex("女");
        }
        userInfo.setName(name);
        userInfoService.update(userInfo);
        user.setNickName(nickName);
        userService.update(user);
        model.addAttribute("user", user);
        model.addAttribute("userInfo", userInfo);
        return "profile/profile";
    }


    //修改密码跳转
    @RequestMapping(value = "/repassword")
    public String updatePassword(Model model) {
        User user = (User) getSession().getAttribute("user");
        if (user == null) {
            return "../login";
        } else {
            model.addAttribute("user", user);
            return "profile/repassword";
        }
    }

    //修改密码
    @RequestMapping(value = "/updatePassword")
    public String updatePassword(Model model,@RequestParam(value = "old_password",required = false)String oldPassword,
                                 @RequestParam(value = "password",required = false)String password) {
        User user = (User) getSession().getAttribute("user");
        if (user != null) {
            oldPassword = MD5Util.encodeToHex(Constants.SALT+oldPassword);
            if (user.getPassword().equals(oldPassword)) {
                password = MD5Util.encodeToHex(Constants.SALT + password);
                user.setPassword(password);
                userService.update(user);
                model.addAttribute("msg", "success");
            } else {
                model.addAttribute("msg", "fail");
            }
        }
        model.addAttribute("user", user);
        return "profile/passwordSuccess";
    }
}
