/*
 * ************************************************************
 * 文件：DataBindingAdapterDefines.java  模块：bind-adapter  项目：component
 * 当前修改时间：2019年04月13日 08:43:55
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-adapter
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.text.method.TextKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import androidx.databinding.BindingAdapter;

/**
 * Created by xu.yi. on 2019/4/4.
 * component 定义项目的data binding全局adapter
 * 类似图片加载，动态设定View的宽度高度等对data binding的扩宽支持
 */
public class DataBindingAdapterDefines {
    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter({"gifSrc"})
    public static void setGifSrc(ImageView view, int gif) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);
        Glide.with(view.getContext()).asGif().load(gif).apply(options).into(view);
    }

    @BindingAdapter(value = {"roundImageUrl", "error", "placeholder"}, requireAll = false)
    public static void setRoundImageUrl(ImageView view, String roundImageUrl, Drawable error, Drawable placeholder) {
        Context context = view.getContext();
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new CircleCrop())
                .placeholder(placeholder)
                .error(error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        if (!TextUtils.isEmpty(roundImageUrl) && !roundImageUrl.startsWith("http")) {
            Glide.with(context).load(roundImageUrl).apply(options).into(view);
            return;
        }
        Glide.with(context)
                .load(roundImageUrl)
                .apply(options)
                .into(view);
    }

    @BindingAdapter(value = {"rectImageUrl", "error", "placeholder"}, requireAll = false)
    public static void setRectImageUrl(ImageView view, String rectImageUrl, Drawable error, Drawable placeholder) {
        Context context = view.getContext();
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholder)
                .error(error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        if (!TextUtils.isEmpty(rectImageUrl) && !rectImageUrl.startsWith("http")) {
            Glide.with(context).load(rectImageUrl).apply(options).into(view);
            return;
        }
        Glide.with(context)
                .load(rectImageUrl)
                .apply(options)
                .into(view);
    }

    @BindingAdapter("android:layout_width")
    public static void setLayoutWidth(View view, float width) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (int) width;
        view.setLayoutParams(params);
    }

    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(View view, float height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (int) height;
        view.setLayoutParams(params);
    }

    @BindingAdapter({"strike"})
    public static void setStrike(TextView view, boolean strike) {
        if (strike) {
            view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            view.setPaintFlags(view.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }
}
