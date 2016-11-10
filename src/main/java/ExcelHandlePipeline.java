import com.google.gson.Gson;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * 用一句话描述该文件作用
 *
 * @Author pengsamkee
 * create at 2016/11/10 15:36
 */
public class ExcelHandlePipeline implements Runnable {

    private Executor fileHandleExecutor;

    private Queue<File> fileHandleQueue;

    private boolean running;

    private long sleepTime;

    public ExcelHandlePipeline(Queue<File> queue) {
        this.fileHandleQueue = queue;
        this.running = true;
        this.fileHandleExecutor = new ThreadPoolExecutor(16, 32, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(10 * 16), Executors.defaultThreadFactory());
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void run() {
        while (this.running) {
            try {
                if ((fileHandleQueue != null) && (fileHandleQueue.size() > 0)) {
                    ExcelHandleWorker protocolHandleWorker = new ExcelHandleWorker(fileHandleQueue);
                    this.fileHandleExecutor.execute(protocolHandleWorker);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(this.sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private final class ExcelHandleWorker implements Runnable {

        private File file;

        private ExcelHandleWorker(Queue<File> fileHandleQueue) {
            this.file = fileHandleQueue.poll();
        }

        public void run() {
            try {
                System.err.println("====================================");
                System.err.println("start handel excel: " + this.file.getAbsolutePath());
                ExcelReader reader = new ExcelReader(this.file.getAbsolutePath());
                Gson gson = new Gson();
                System.err.println(gson.toJson(reader.mapDatas));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
}
