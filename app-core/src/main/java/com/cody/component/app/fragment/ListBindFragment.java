/*
 * ************************************************************
 * 文件：ListBindFragment.java  模块：app-core  项目：component
 * 当前修改时间：2019年07月22日 14:14:00
 * 上次修改时间：2019年07月03日 17:54:13
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cody.component.app.IBaseListView;
import com.cody.component.app.R;
import com.cody.component.app.databinding.FragmentListBinding;
import com.cody.component.app.widget.friendly.FriendlyLayout;
import com.cody.component.bind.adapter.list.MultiBindingListAdapter;
import com.cody.component.bind.adapter.list.OnBindingItemClickListener;
import com.cody.component.handler.data.FriendlyViewData;
import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.viewmodel.ListViewModel;

/**
 * 使用List 做列表页面，自动分页，刷新，初始化，加载更多，出错提示，重试，下拉刷新
 */
public abstract class ListBindFragment<VM extends ListViewModel<FriendlyViewData, ItemViewDataHolder, ?>> extends FriendlyBindFragment<FragmentListBinding, VM, FriendlyViewData> implements IBaseListView, OnBindingItemClickListener {
    protected MultiBindingListAdapter mListAdapter;

    @NonNull
    @Override
    public LinearLayoutManager buildLayoutManager() {
        return new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
    }

    @Override
    public FriendlyLayout getFriendlyLayout() {
        return getBinding().friendlyView;
    }

    @Override
    public boolean childHandleScrollVertically(final View target, final int direction) {
        int topRowVerticalPosition = getBinding().recyclerView.getChildCount() == 0 ? 0 : getBinding().recyclerView.getChildAt(0).getTop();
        return topRowVerticalPosition < 0 || getBinding().recyclerView.canScrollVertically(direction);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_list;
    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        mListAdapter = buildListAdapter();
        mListAdapter.setItemClickListener(this);
        mListAdapter.setItemLongClickListener(this);
        getBinding().recyclerView.setLayoutManager(buildLayoutManager());
        getBinding().recyclerView.setAdapter(mListAdapter);

        getFriendlyViewModel().getItems().observe(this, items -> getFriendlyViewModel().getRequestStatusLive().observe(this, new Observer<RequestStatus>() {
            @Override
            public void onChanged(final RequestStatus requestStatus) {
                if (requestStatus.isLoaded()) {
                    getFriendlyViewModel().getRequestStatusLive().removeObserver(this);
                    mListAdapter.submitList(items);
                }
            }
        }));
    }

    @Override
    protected void onRequestStatus(final RequestStatus requestStatus) {
        super.onRequestStatus(requestStatus);
        if (mListAdapter != null) {
            mListAdapter.setRequestStatus(requestStatus);
        }
    }

    @Override
    public void scrollToTop() {
    }
}