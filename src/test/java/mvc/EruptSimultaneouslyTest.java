package mvc;

import com.enjoy.service.EruptSimultaneouslyService;
import com.enjoy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.*;

/**
 * 并发测试类
 */
public class EruptSimultaneouslyTest {
    private static final int MAX_THREADS=1000;//模拟1000个用户
    private static CountDownLatch countDownLatch = new CountDownLatch(MAX_THREADS);
    //将服务打包成一个任务
    static class Task implements Runnable {
        //private OrderService orderServiceImpl;
        private EruptSimultaneouslyService eruptSimultaneouslyService;
        public Task (EruptSimultaneouslyService orderServiceImpl){
            this.eruptSimultaneouslyService=orderServiceImpl;
        }
        @Override
        public void run() {
            countDownLatch.countDown();
            //orderServiceImpl.getDetail("1");
            try {
                eruptSimultaneouslyService.queryOrderInfo(1);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
        for (int i=0;i<MAX_THREADS;i++){
            new Thread(new Task((EruptSimultaneouslyService) app.getBean("eruptSimultaneouslyService"))).start();
        }
        countDownLatch.await();
        System.out.println("complate!!");
    }
}
