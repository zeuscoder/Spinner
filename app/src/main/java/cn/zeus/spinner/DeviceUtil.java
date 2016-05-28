package cn.zeus.spinner;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zeus on 16-5-28.
 */
public class DeviceUtil {

    /**
     * 根据屏幕获取字体大小
     */
    public static int getFontSize(Context context) {
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        if (screenWidth <= 240) {        // 240X320 屏幕
            return 11;
        } else if (screenWidth <= 320) {   // 320X480 屏幕
            return 12;
        } else if (screenWidth <= 480) {   // 480X800 或 480X854 屏幕
            return 13;
        } else if (screenWidth <= 540) {   // 540X960 屏幕
            return 14;
        } else if (screenWidth <= 800) {    // 800X1280 屏幕
            return 16;
        } else {                          // 大于 800X1280
            return 17;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * @param context
     * @param fileName: assets下文件路径
     * @return
     */
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            Log.i("Fail to open image", e.toString());
        }
        return image;
    }

    /**
     * @param radius
     * @param borderLength: 一般取较小的值，比如10以内
     * @param borderColor
     * @param isBackground：设置为背景颜色或者边框颜色,true为背景颜色
     * @return
     */
    public static ShapeDrawable createRoundCornerShapeDrawable(float radius, float borderLength, int borderColor, boolean isBackground) {
        ShapeDrawable sd;
        float[] outerRadii = new float[8];
        float[] innerRadii = new float[8];
        for (int i = 0; i < 8; i++) {
            outerRadii[i] = radius + borderLength;
            innerRadii[i] = radius;
        }

        if (isBackground) {
            sd = new ShapeDrawable(new RoundRectShape(outerRadii, null, innerRadii));
            sd.getPaint().setColor(borderColor);
        } else {
            sd = new ShapeDrawable(new RoundRectShape(outerRadii, new RectF(borderLength, borderLength,
                    borderLength, borderLength), innerRadii));
            sd.getPaint().setColor(borderColor);
        }

        return sd;
    }

}
