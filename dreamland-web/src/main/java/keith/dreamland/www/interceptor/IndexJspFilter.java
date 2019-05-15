package keith.dreamland.www.interceptor;

import keith.dreamland.www.common.PageHelper;
import keith.dreamland.www.dao.UserContentMapper;
import keith.dreamland.www.entity.UserContent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;

import java.io.IOException;
import java.util.List;

public class IndexJspFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("---------自定义过滤器--------");
        ServletContext context = request.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        UserContentMapper userContentMapper = ctx.getBean(UserContentMapper.class);
        PageHelper.startPage(null, null);//分页开始
        List<UserContent> list = userContentMapper.select(null);
        PageHelper.Page endPage = PageHelper.endPage();//分页结束
        request.setAttribute("page", endPage);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }


}
