package util;

import java.io.File;
import java.util.List;
import java.util.Queue;

/**
 * 用一句话描述该文件作用
 *
 * @Author pengsamkee
 * create at 2016/11/10 15:17
 */
public class FileUtil {

    public static Queue<File> getFileQueue(File dir, Queue<File> queue) {
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileQueue(files[i], queue); // 获取文件绝对路径
                } else if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) { // 判断文件名是否是excel文件
                    queue.add(files[i]);
                } else {
                    continue;
                }
            }
        }
        return queue;
    }

}
