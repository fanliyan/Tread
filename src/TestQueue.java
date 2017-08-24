import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class TestQueue {
    public static Object signal = new Object();
    boolean bFull = false;
    private List thingsList = new ArrayList();
    private final ReentrantLock lock = new ReentrantLock(true);
    BlockingQueue q = new ArrayBlockingQueue(10);


    /**
     * 生产
     * @param thing
     * @throws Exception
     */
    public void product(String thing) throws Exception{
        synchronized (signal){
            if(!bFull){
                bFull = true;
                //产生一些东西，放到thingsList 共享资源中
                System.out.println("product");
                System.out.println("仓库已满，正等待消费...");
                thingsList.add(thing);
                signal.notify();      //通知消费者
            }
            signal.wait();        //然后自己进入signal待召队列
        }
    }

    /**
     * @link http://blog.csdn.net/autumn20080101/article/details/9491159
     * 消费
     * @return
     * @throws Exception
     */
    public String consumer() throws Exception{
        synchronized (signal){
            if(!bFull){
                signal.wait();       //进入signal待召队列，等待生产者通知
            }
            bFull = false;
            //读取buf共享资源里面的东西
            System.out.println("consume");
            System.out.println("仓库已空，正在等待生产..");
            signal.notify();    //然后通知生产者
        }
        String result = "";
        if(thingsList.size() > 0){
            result = thingsList.get(thingsList.size() - 1).toString();
            thingsList.remove(thingsList.size() - 1);
        }
        return result;
    }
}
