package com.enjoy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;

/**
 * 并发远程调用
 */
@Service("eruptSimultaneouslyService")
public class EruptSimultaneouslyService {
    //请求参数封装
    public class Request {
        private String serialNo;
        private CompletableFuture<Response> future;
        //非高并发的一些请求参数
        private Integer id;
        public String getSerialNo() {
            return serialNo;
        }
        public void setSerialNo(String serialNo) {
            this.serialNo = serialNo;
        }
    }
    //响应
    public static class Response {
        private String serialNo;
        private Integer orderMoney;
        public String getSerialNo() {
            return serialNo;
        }
        public void setSerialNo(String serialNo) {
            this.serialNo = serialNo;
        }
        public Integer getOrderMoney() {
            return orderMoney;
        }
        public void setOrderMoney(Integer orderMoney) {
            this.orderMoney = orderMoney;
        }
    }
    @Autowired
    private OrderService orderServiceImpl;
    //分布式中这里的jvm队列该为消息中间件
    private LinkedBlockingDeque<Request> queue = new LinkedBlockingDeque<>();
    public Response queryOrderInfo(int id) throws ExecutionException, InterruptedException {
        //1-生成唯一序列号做请求的标识
        String serialNo = UUID.randomUUID().toString();
        //2-把响应的结果通过唯一序列号分配给对应请求
        CompletableFuture<Response> future = new CompletableFuture<>();
        //3-封装请求参数
        Request request = new Request();
        request.id=id;
        request.serialNo=serialNo;
        request.future=future;
        //4-把请求放进队列
        queue.add(request);
        //8-从监听中获取对应的结果
        return future.get();//获取对应请求线程的结果
    }
    //5-定时任务去从请求队列中获取所有数据集合
    @PostConstruct  //启动应用时运行
    public void init(){
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);
        //参数含义：执行的任务，上一个任务完成后延时多久才开始第二个，调完后间隔多久再执行第二个，时间单位
        scheduled.scheduleAtFixedRate(() -> {
            //保存临时请求集合
            List<Request> requests = new ArrayList<>();
            //判断队列中是否有请求
            int size = queue.size();
            if( size== 0) return;
            //有请求(分布式中从中间件获取queue.poll())
            for(int i=0;i<size;i++){
                //临时保存10毫秒内的所有请求
                requests.add(queue.poll());
            }
            System.out.println("批量处理的数据量为："+size);
            //6-让第三方服务支持批量查询,拿到结果集（第三方请求参数是没有CompletableFuture的）
            List<Response> detailBatch = orderServiceImpl.getDetailBatch(requests);
            //根据serialNo一一对比,绑定
            Map<String,Response> responses = new HashMap<>();
            for(Response response:detailBatch){
                String serialNo = response.getSerialNo();
                responses.put(serialNo,response);
            }
            //7-把拿到的结果通过CompletableFuture去进行通知完成，让监听的future去拿结果
            for(Request request:requests){
                Response result = responses.get(request.serialNo);
                request.future.complete(result);
            }
        },0,10,TimeUnit.MILLISECONDS);
    }
}
