package keith.dreamland.www.controller;

import keith.dreamland.www.common.CodeCaptchaServlet;
import keith.dreamland.www.common.Constants;
import keith.dreamland.www.common.MD5Util;
import keith.dreamland.www.entity.User;
import keith.dreamland.www.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Controller
public class LoginController extends BaseController {

    private final static Logger log = Logger.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login")
    public String login(Model model) {
        User user = (User) getSession().getAttribute("user");
        if (user != null) {
            return "personal/personal";
        } else {
            return "../login";
        }
    }

    //登录
    @RequestMapping(value = "/doLogin")
    public String  Login(Model model, @RequestParam(value = "username")String email,
                                     @RequestParam(value = "password")String password,
                                     @RequestParam(value = "code")String code) {
        if (StringUtils.isBlank(code)) {
            model.addAttribute("error","fail");
            return "../login";
        }

        int b = checkValidateCode(code);
        if (b == -1) {
            model.addAttribute("error","fail");
            return "../login";
        } else if (b == 0) {
            model.addAttribute("error","fail");
            return "../login";
        }
        password = MD5Util.encodeToHex(Constants.SALT+password);
        User user = userService.Login(email,password);
        if (user != null) {
            if ("0".equals(user.getState())) {
                model.addAttribute("email", email);
                model.addAttribute("error", "active");
                return "../login";
            }
            log.info("用户登录成功！");
            model.addAttribute("user", user);
            return "/personal/personal";
        } else {
            log.info("用户登录失败！");
            model.addAttribute("email", email);
            model.addAttribute("error", "fail");
            model.addAttribute("error", "unzhuce");
            return "../login";
        }
    }

    //退出登录
    @RequestMapping(value = "/loginout")
    public String LoginOut(Model model) {
        log.info("退出登录");
        getSession().removeAttribute("user");
        getSession().invalidate();
        return "../login";
    }


    //校验验证码
    public int checkValidateCode(String code) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String validateCode = (String) attributes.getRequest().getSession().getAttribute(CodeCaptchaServlet.VERCODE_KEY);
        if (null == validateCode ) {
            return -1;
        } else if (!code.equalsIgnoreCase(validateCode.toString())) {
            return 0;
        } else {
            return 1;
        }
    }

}
