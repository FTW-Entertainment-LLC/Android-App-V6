package tv.animeftw.app.utils;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import tv.animeftw.app.R;

public class DataBindingUtils {

    @BindingAdapter("app:imageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .showImageOnLoading(R.drawable.video_holder)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

            ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
        }
    }
}
