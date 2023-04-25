package a;

import org.junit.Test;
import com.wzw.tiff.util.TiffUtil;

import java.io.File;
import java.util.concurrent.CountDownLatch;

public class Test1 {
    @Test
    public void test1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        TiffUtil tiffUtil = new TiffUtil();
        new Thread(() -> {
            tiffUtil.tifGetFirstFrameExclusively(new File("D:/images/3.tif"), new File("D:/images/333.png"));
        }).start();
        new Thread(() -> {
            tiffUtil.tifGetFirstFrameExclusively(new File("D:/images/444/3.tif"), new File("D:/images/333.png"));
        }).start();
        countDownLatch.await();
    }
}
