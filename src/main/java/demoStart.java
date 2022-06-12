import com.google.zxing.WriterException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.UUID;

/**
 * @author ：DELL
 * @date ：Created in 2022/6/12 16:01
 * @description：
 * @version:
 */
public class demoStart {
    public static void main(String[] args) throws WriterException, IOException {
        BufferedImage image = QRCodeUtil.createImagee(UUID.randomUUID().toString());
        String filename = "D:\\" + "二维码" + ".jpg";  //下载路径及下载图片名称
        File file = new File(filename);
        ImageIO.write(image,"jpg",file);

    }
}
