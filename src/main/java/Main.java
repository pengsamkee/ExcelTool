import util.FileUtil;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 用一句话描述该文件作用
 *
 * @Author pengsamkee
 * create at 2016/11/10 15:35
 */
public class Main {

    public static void main(String[] args) {
        File excelFile = new File(System.getProperty("user.dir") + "\\excel");
        Queue<File> fileQueue = new ConcurrentLinkedQueue<File>();
        ExcelHandlePipeline handlePipeline = new ExcelHandlePipeline(fileQueue);
        handlePipeline.setSleepTime(500);
        new Thread(handlePipeline).start();
        FileUtil.getFileQueue(excelFile, fileQueue);
    }
}
