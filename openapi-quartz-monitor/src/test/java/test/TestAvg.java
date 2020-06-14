package test;

import com.play.openapi.monitor.MonitorApplication;
import com.play.openapi.monitor.feign.client.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonitorApplication.class)
public class TestAvg {

    @Resource
    private SearchService searchService;
    @Test
    public void test() throws Exception{
        System.out.println(searchService.statAvg(1591645260000l,1565928809999l));
    }
}
