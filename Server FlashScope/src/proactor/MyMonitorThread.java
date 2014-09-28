package proactor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class MyMonitorThread implements Runnable
{
    private ThreadPoolExecutor executor;

    private int seconds;

    private boolean run=true;
    
    public MyMonitorThread(ExecutorService executor, int delay)
    {
        this.executor = (ThreadPoolExecutor)executor;
        this.seconds=delay;
    }

    public void shutdown(){
        this.run=false;
    }

    @Override
    public void run()
    {
        while(run){
                System.out.println(
                		 String.format("[monitor] [%d/%d], Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
                                 this.executor.getPoolSize(),   // 대기 + 실행중
                                 this.executor.getCorePoolSize(),  // max
                                 this.executor.getActiveCount(),   // 실행중인 thread       ->0
                        this.executor.getCompletedTaskCount(),		// 완료된 작업의 누적 수
                        this.executor.getTaskCount(),				// 완료된 + 실행중 작업의 수
                        this.executor.isShutdown(),
                        this.executor.isTerminated()));
                
                long time = System.currentTimeMillis(); 
				SimpleDateFormat dayTime = new SimpleDateFormat("mm:ss");
				String str = dayTime.format(new Date(time));
				
				System.out.println("TIME:" + str);
				
				try {
                    Thread.sleep(seconds*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

    }
}