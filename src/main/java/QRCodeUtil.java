import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.javafx.geom.RoundRectangle2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * @author ：DELL
 * @date ：Created in 2022/6/12 15:46
 * @description：
 * @version:
 */
public class QRCodeUtil {
    private static final String CHARSET = "UTF-8";
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int LOGO_WIDTH = 80;
    // LOGO高度
    private static final int LOGO_HEIGHT = 80;
    public static BufferedImage createImagee(String content) throws WriterException {
        Hashtable<EncodeHintType,Object> hints= new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET,CHARSET);
        hints.put(EncodeHintType.MARGIN,1);
        BitMatrix bitMatrix= null;

        bitMatrix= new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,QRCODE_SIZE,QRCODE_SIZE,hints);

        int width = bitMatrix.getWidth();
        int height= bitMatrix.getHeight();
        BufferedImage image= new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        for (int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                image.setRGB(x,y,bitMatrix.get(x,y)?0xFF000000:0xFFFFFFFF);
            }
        }
        return image;

    }

    public static void insertImage(BufferedImage source, InputStream logoPath, boolean needCompress) throws IOException {
        Image src=null;
        src = ImageIO.read(logoPath);
        int width = src.getHeight(null);
        int height = src.getWidth(null);
        if (needCompress){
            // 压缩LOGO
            if (width > LOGO_WIDTH) {
                width = LOGO_WIDTH;
            }
            if (height > LOGO_HEIGHT) {
                height = LOGO_HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        Graphics2D graphics2D= source.createGraphics();
        int x= (QRCODE_SIZE-width)/2;
        int y=(QRCODE_SIZE-height)/2;
        graphics2D.drawImage(src,x,y,width,height,null);
        Shape shape = (Shape) new RoundRectangle2D(x, y, width, width, 12, 12);
        graphics2D.setStroke(new BasicStroke(3f));
        graphics2D.draw(shape);
        graphics2D.dispose();
    }
}
