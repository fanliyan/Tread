public class TestMain {
    public static void main(String[] args) {
        TestQueue tq = new TestQueue();
        TestProduct tp = new TestProduct(tq);
        TestConsumer tc = new TestConsumer(tq);

        Thread t1 = new Thread(tp);
        Thread t2 = new Thread(tc);
        t1.start();
        t2.start();
    }
}
