package five;

import javafx.concurrent.Worker;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CellularAutomata {

    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAutomata(Board board){
        this.mainBoard = board;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                mainBoard.commitNewValues();
            }
        });
        this.workers = new Worker[count];
        for(int x = 0; x < board.getMaxX(); x++){
            for (int y = 0; y < board.getMaxY(); y++){
                board.setNewValue(x, y, computeValue(x, y));
            }
        }
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
            return;
        }
    }

    private int computeValue(int x, int y){
        return 0;
    }

    private void start(){
        for(int i = 0; i < workers.length; i++){
            new Thread(workers[i].start());
        }
        mainBoard.waitForConvergence();
    }

    interface Board{
        int getMaxX();
        int getMaxY();
        int getValue(int x, int y);
        int setNewValue(int x, int y, int value);
        void commitNewValues();
        boolean hasConverged();
        void waitForConvergence();

        Board getSubBoard(int numPartitions, int index);
    }
}
