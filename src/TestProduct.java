public class TestProduct implements Runnable {
    TestQueue obj;


    public TestProduct(TestQueue tq) {
        this.obj = tq;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++){
            try {
                obj.product("test" + i);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
