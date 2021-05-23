package mvc;

import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.enjoy.service.UserService;

public class JavaTest {

	private MessageSource messageSource;
	@Test
	public void test1(){
		ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
		UserService user = (UserService) app.getBean("userServiceImpl");
		String str = user.getDetial("1");
		System.out.println(str);
	}
}
