package test;

import com.play.openapi.web.master.WebMasterApplication;
import com.play.openapi.web.master.mapper.AdminUserMapper;
import com.play.openapi.web.master.mapper.AdminUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebMasterApplication.class)
public class TestMapper {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Test
    public void test(){
        System.out.println(adminUserMapper.selectByPrimaryKey(1));
    }

}
