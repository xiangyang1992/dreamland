import keith.dreamland.www.entity.User;
import keith.dreamland.www.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {"classpath:spring-mybatis.xml","classpath:spring-mvc.xml"})
public class TestTransaction extends AbstractJUnit4SpringContextTests{
    @Autowired
    private UserService userService;

    @Test
    public void testSave() {
        User user = new User();
        user.setNickName("Keith");
        user.setEmail("670627055@qq.com");
        userService.regist(user);
    }




}
