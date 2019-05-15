package keith.dreamland.www.controller;


import keith.dreamland.www.common.CodeCaptchaServlet;
import keith.dreamland.www.common.MD5Util;
import keith.dreamland.www.common.mail.SendEmail;
import keith.dreamland.www.entity.User;
import keith.dreamland.www.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Controller
public class RegisterController {
    private final static Logger log = Logger.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String ,String> redisTemplate;

    @RequestMapping("/checkPhone")
    @ResponseBody
    public Map<String, Object> checkPhone(Model model, @RequestParam(value = "phone") String phone) {
        log.debug("注册+判断手机号"+phone+"是否可用");
        Map<String,Object> map = new HashMap<>();
        User user = userService.findByPhone(phone);
        if (user == null) {//未注册
            map.put("message", "success");
        } else {//已注册
            map.put("message","fail");
        }
    return map;
    }

    @RequestMapping("/checkEmail")
    @ResponseBody
    public Map<String ,Object> checkEmail(Model model,@RequestParam(value = "email") String email) {
        log.debug("注册+判断邮箱账号"+email+"是否已注册");
        Map<String ,Object> map = new HashMap<>();
        User user = userService.findByEmial(email);
        if (user == null) {
            map.put("message", "success");
        } else {
            map.put("message","fail");
        }
        return map;
    }

    @RequestMapping("/checkCode")
    @ResponseBody
    public Map<String ,Object> checkCode(Model model,@RequestParam(value = "code",required = false) String code) {
        log.debug("注册+判断验证码"+code+"是否正确");
        Map<String ,Object> map = new HashMap<>();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String vcode = (String) attributes.getRequest().getSession().getAttribute(CodeCaptchaServlet.VERCODE_KEY);
        if (code.equals(vcode)) {
            map.put("message", "success");
        } else {
            map.put("message","fail");
        }
        return map;
    }

    @RequestMapping("/doRegister")
    public String  doRegister(Model model,@RequestParam(value = "phone") String phone
            ,@RequestParam(value = "email") String email
            ,@RequestParam(value = "password") String password
            ,@RequestParam(value = "nickName") String nickName
            ,@RequestParam(value = "code") String code) {
        log.debug("注册+判断账号");
        if (StringUtils.isBlank(code)) {
            model.addAttribute("error","非法注册，清重新输入!");
            return "../register";
        }

        int b = checkValidateCode(code);
        if (b == -1) {
            model.addAttribute("error","验证码超时，请重新输入!");
            return "../register";
        } else if (b == 0) {
            model.addAttribute("error","验证码错误，请重新输入!");
            return "../register";
        }
        User user = userService.findByEmial(email);
        if (user != null) {
            model.addAttribute("error","该邮箱已注册");
            return "../register";
        } else {
            user = new User();
            user.setEmail(email);
            user.setNickName(nickName);
            user.setPassword(MD5Util.encodeToHex("salt"+password));
            user.setPhone(phone);
            user.setState("0");
            user.setEnable("0");
            user.setImgUrl("/images/icon_m.jpg");
            //邮件激活码
            String validateCode = MD5Util.encodeToHex("salt" + email + password);
                redisTemplate.opsForValue().set(email,validateCode,24, TimeUnit.HOURS);//24小时有效  redis保存激活码
            userService.regist(user);
            log.info("注册成功");
            SendEmail.sendEmailMessage(email, validateCode);
            String message = email + "," + validateCode;
            model.addAttribute("message", message);
            return "/regist/registerSuccess";
        }
    }

    //检查session中的code是否与用户填写的code相等
    private int checkValidateCode(String code) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String vcode = (String) attributes.getRequest().getSession().getAttribute(CodeCaptchaServlet.VERCODE_KEY);
        if (null == vcode) {
            return -1;
        }
        if (!code.equalsIgnoreCase(vcode.toString())) {
            return 0;
        } else {
            return 1;
        }

    }

    @RequestMapping(value = "/sendEmail")
    @ResponseBody
    public Map<String, Object> sendEmail(Model model) {
        Map<String, Object> map = new HashMap<>();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String validateCode = attributes.getRequest().getParameter("validateCode");
        String email = attributes.getRequest().getParameter("email");
        SendEmail.sendEmailMessage(email,validateCode);
        map.put("success","success");
        return map;
    }

    @RequestMapping(value = "/activeCode")
    public String activeCode(Model model) {
        log.info("激活验证");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String validateCode = attributes.getRequest().getParameter("validateCode");
        String email = attributes.getRequest().getParameter("email");
        String code = redisTemplate.opsForValue().get(email);
        log.info("验证邮箱为：" + email + ",激活码为：" + code + ",用户链接的激活码为：" + validateCode);
        User user2 = userService.findByEmial(email);
        if (user2 != null && "1".equals(user2.getState())) {
            model.addAttribute("success","您已激活，请直接登录!");
            return "../login";
        }
        if (code == null) {
            model.addAttribute("fail","您的激活码已过期，请重新注册!");
            userService.deleteByEmail(email);
            return "/regist/activeFail";
        }
        if (StringUtils.isNotBlank(validateCode) && validateCode.equals(code)) {
            user2.setEnable("1");
            user2.setState("1");
            userService.update(user2);
            model.addAttribute("email", email);
            return "/regist/activeSuccess";
        } else {
            model.addAttribute("fail","激活码错误!");
            return "/regist/activeFail";
        }

    }
    @RequestMapping(value = "/register")
    public String register(Model model) {

        log.info("进入注册页面");

        return "../register";
    }
}
