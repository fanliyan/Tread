public class TestConsumer implements  Runnable {

    TestQueue obj;

    public TestConsumer(TestQueue tq){
        this.obj = tq;
    }

    @Override
    public void run() {
        try {
        for(int i = 0; i < 10; i++){

                obj.consumer();
        }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
