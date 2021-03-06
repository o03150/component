/*
 * ************************************************************
 * 文件：IImageViewListener.java  模块：image-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月21日 10:52:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.image;

import android.content.Intent;

import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;


/**
 * Created by cody.yi on 2017/5/19.
 * 上传图片，预览图片
 */
public interface IImageViewListener {
    /**
     * 预览图片
     * @param path 地址
     */
    void preview(String path);

    /**
     * 预览图片 可以删除
     *
     * @param items    图片列表
     * @param position 图片所在列表中的位置
     */
    void preview(final ArrayList<ImageItem> items, final int position);

    /**
     * 直接选择图片
     *
     * @param limit  还可以添加的图片数量 limit = LIMIT - items.size()
     * @param isCrop 是否裁剪
     */
    void selectImage(final int limit, boolean isCrop); // 获取图片

    /**
     * 获取图片，弹出选择照相或者从相册选择,可选择是否裁剪
     *
     * @param limit  还可以添加的图片数量 limit = LIMIT - items.size()
     * @param isCrop 是否裁剪
     */
    void pickImage(final int limit, boolean isCrop); // 获取图片

    /**
     * 代理 activity or fragment 的 onActivityResult
     *
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        data
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
