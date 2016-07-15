package commanutil.utl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import commanutil.base.BaseApplication;

/**
 * Created by zhanglin on 4/19/16.
 */
public class ImageUtil {

    public static String reSizeImageFile(String filepath, int width, int height, Bitmap.CompressFormat compressFormat) throws IOException {
        File file = new File(filepath);
        if (!file.exists()) {
            throw new FileNotFoundException("");
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
//        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(filepath, options);
        float inwidth = bitmap.getWidth();
        float inheight = bitmap.getHeight();
        LogManager.e(inwidth / inheight + "|" + ((width + 0.0f) / height));
        int outwidth;
        int outheight;
        if (inwidth / inheight > (width + 0.0f) / height) {
            outwidth = width;
            outheight = (int) (inheight * (width / inwidth));
        } else {
            outheight = height;
            outwidth = (int) (inwidth * (height / inheight));
        }
        LogManager.e(outwidth + " | " + outheight);
        Bitmap.Config config = Bitmap.Config.RGB_565;
        Bitmap outbitmap = Bitmap.createBitmap(outwidth, outheight, config);
        Canvas canvas = new Canvas(outbitmap);
        Paint paint = new Paint();
        paint.setDither(true);
        canvas.drawBitmap(bitmap, new Rect(0, 0, (int) inwidth, (int) inheight), new Rect(0, 0, outwidth, outheight), paint);
        File outFile = new File(BaseApplication.context.getCacheDir(), file.getName());
        FileOutputStream outputStream = new FileOutputStream(outFile);
        outbitmap.compress(compressFormat, 80, outputStream);
        outputStream.flush();
        outputStream.close();
        bitmap.recycle();
        outbitmap.recycle();
        return outFile.getAbsolutePath();
    }


}
