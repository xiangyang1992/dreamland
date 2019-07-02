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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class WriteController extends BaseController {
    private final static Logger log = Logger.getLogger(WriteController.class);

    @Autowired
    private UserContentService userContentService;

    @RequestMapping(value = "/writedream")
    public String WriteDream(Model model,@RequestParam(value = "cid",required = false)Long id) {
        User user = (User) getSession().getAttribute("user");
        if (id != null) {
            UserContent userContent = userContentService.findById(id);
            model.addAttribute("cont", userContent);
        }
        model.addAttribute("user", user);
        return "write/writedream";
    }


    @RequestMapping(value = "/doWritedream")
    public String doWritedream(Model model, HttpServletRequest request,@RequestParam(value = "cid",required = false)Long cid) {
        String id = getRequest().getParameter("id");
        String category = getRequest().getParameter("category");
        String txtTitle = getRequest().getParameter("txtT_itle");
        String content = getRequest().getParameter("content");
        String private_dream = getRequest().getParameter("private_dream");
        log.info("---进入写博客Controller---");
        User user = (User) getSession().getAttribute("user");
        if (user == null) {
            //未登陆
            model.addAttribute("error", "请先登陆!");
            return "../login";
        }
        UserContent userContent = new UserContent();
        if(cid!=null){
            userContent = userContentService.findById(cid);
        }
        userContent.setCategory( category );
        userContent.setContent( content );
        userContent.setRptTime( new Date( ) );
        String imgUrl = user.getImgUrl();
        if(StringUtils.isEmpty( imgUrl )){
            userContent.setImgUrl( "/images/icon_m.jpg" );
        }else {
            userContent.setImgUrl( imgUrl );
        }
        if("on".equals( private_dream )){
            userContent.setPersonal( "1" );
        }else{
            userContent.setPersonal( "0" );
        }
        userContent.setTitle( txtTitle );
        userContent.setuId( user.getId() );
        userContent.setNickName( user.getNickName() );

        if(cid ==null){
            userContent.setUpvote( 0 );
            userContent.setDownvote( 0 );
            userContent.setCommentNum( 0 );
            userContentService.addContent( userContent );
        }else {
            userContentService.updateById(userContent);
        }
        model.addAttribute("cont",userContent);
        return "write/writesuccess";
    }


    @RequestMapping(value = "/watch")
    public String watch(Model model, HttpServletRequest request,@RequestParam(value = "cid")Long cid) {
        User user = (User) getSession().getAttribute("user");
        if (user == null) {
            return "../login";
        } else {
            model.addAttribute("user", user);
            UserContent userContent = userContentService.findById(cid);
            model.addAttribute("cont", userContent);
            return "personal/watch";
        }

    }

}
