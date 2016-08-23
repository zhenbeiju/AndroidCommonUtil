package commanutil.utl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import commanutil.base.BaseApplication;

/**
 * Created by zhanglin on 15-12-16.
 */
public class BitmapUtil {


    public Bitmap handleImage(Bitmap bm, int flag) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Bitmap.Config.ARGB_8888);
        // 创建一个相同尺寸的可变的位图区,用于绘制调色后的图片
        Canvas canvas = new Canvas(bmp); // 得到画笔对象
        Paint paint = new Paint(); // 新建paint
        paint.setAntiAlias(true); // 设置抗锯齿,也即是边缘做平滑处理
        ColorMatrix mAllMatrix;
        ColorMatrix mLightnessMatrix;
        ColorMatrix mSaturationMatrix;
        ColorMatrix mHueMatrix;
        mAllMatrix = new ColorMatrix();
        mLightnessMatrix = new ColorMatrix(); // 用于颜色变换的矩阵，android位图颜色变化处理主要是靠该对象完成
        mSaturationMatrix = new ColorMatrix();
        mHueMatrix = new ColorMatrix();

//        switch (flag) {
//            case FLAG_HUE: // 需要改变色相
//                mHueMatrix.reset();
//                mHueMatrix.setScale(mHueValue, mHueValue, mHueValue, 1); // 红、绿、蓝三分量按相同的比例,最后一个参数1表示透明度不做变化，此函数详细说明参考
//                // // android
//                // doc
//                break;
//            case FLAG_SATURATION: // 需要改变饱和度
//                // saturation 饱和度值，最小可设为0，此时对应的是灰度图(也就是俗话的“黑白图”)，
//                // 为1表示饱和度不变，设置大于1，就显示过饱和
//                mSaturationMatrix.reset();
//                mSaturationMatrix.setSaturation(mSaturationValue);
//                break;
//            case FLAG_LUM: // 亮度
//                // hueColor就是色轮旋转的角度,正值表示顺时针旋转，负值表示逆时针旋转
//                mLightnessMatrix.reset(); // 设为默认值
//                mLightnessMatrix.setRotate(0, mLumValue); // 控制让红色区在色轮上旋转的角度
//                mLightnessMatrix.setRotate(1, mLumValue); // 控制让绿红色区在色轮上旋转的角度
//                mLightnessMatrix.setRotate(2, mLumValue); // 控制让蓝色区在色轮上旋转的角度
//                // 这里相当于改变的是全图的色相
//                break;
//        }
        mAllMatrix.reset();
        mAllMatrix.postConcat(mHueMatrix);
        mAllMatrix.postConcat(mSaturationMatrix); // 效果叠加
        mAllMatrix.postConcat(mLightnessMatrix); // 效果叠加

        paint.setColorFilter(new ColorMatrixColorFilter(mAllMatrix));// 设置颜色变换效果
        canvas.drawBitmap(bm, 0, 0, paint); // 将颜色变化后的图片输出到新创建的位图区
        // 返回新的位图，也即调色处理后的图片
        return bmp;
    }


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
