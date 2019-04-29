/*
 * ************************************************************
 * 文件：PageListViewModel.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.viewmodel;

import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.PageInfo;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.factory.PageListDataSourceFactory;
import com.cody.component.handler.interfaces.OnRequestPageListener;
import com.cody.component.handler.mapper.IDataMapper;
import com.cody.component.handler.source.DataSourceWrapper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * Created by xu.yi. on 2019/4/8.
 * 数据仓库，获取列表数据
 */
public abstract class PageListViewModel<VD extends MaskViewData> extends FriendlyViewModel<VD>
        implements OnRequestPageListener, IDataMapper {
    private final PageListDataSourceFactory mSourceFactory;
    private DataSourceWrapper mWrapper;
    private LiveData<PagedList<ItemViewDataHolder>> mPagedList;
    protected List<ItemViewDataHolder> mOldList = new ArrayList<>();

    public PageListViewModel(final VD friendlyViewData) {
        super(friendlyViewData);
        mSourceFactory = new PageListDataSourceFactory(this);
        mWrapper = new DataSourceWrapper(mSourceFactory.getRequestStatusLive(), mSourceFactory.getDataSource());
    }

    @Override
    public void OnInit() {
        mPagedList = new LivePagedListBuilder<>(mSourceFactory, initPageListConfig()).build();
        mPagedList.observe(mLifecycleOwner, itemViewDataHolders -> mOldList = new ArrayList<>(itemViewDataHolders));
    }

    @Override
    public <T extends BaseViewModel> T setLifecycleOwner(final LifecycleOwner lifecycleOwner) {
        return super.setLifecycleOwner(lifecycleOwner);
    }

    public LiveData<PagedList<ItemViewDataHolder>> getPagedList() {
        return mPagedList;
    }

    @NonNull
    @Override
    public RequestStatus getRequestStatus() {
        return mWrapper.getRequestStatus();
    }

    @Override
    public MutableLiveData<RequestStatus> getRequestStatusLive() {
        return mWrapper.getRequestStatusLive();
    }

    @Override
    public <ItemBean> List<ItemViewDataHolder> mapperList(final Operation operation, final List<ItemBean> beanDataList) {
        return mapperList(operation, mOldList, beanDataList);
    }

    @Override
    public void refresh() {
        mWrapper.refresh();
    }

    @Override
    public void retry() {
        mWrapper.retry();
    }

    @Override
    public void onSuccess() {
        mWrapper.onSuccess();
        getRequestStatusLive().postValue(getRequestStatus().loaded());
    }

    @Override
    public void onFailure(final String message) {
        mWrapper.onFailure(message);
        getRequestStatusLive().postValue(getRequestStatus().error(message));
    }

    /**
     * 分页数据配置
     */
    protected PagedList.Config initPageListConfig() {
        return (new PagedList.Config.Builder())
                .setPrefetchDistance(PageInfo.DEFAULT_PREFETCH_DISTANCE)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(PageInfo.DEFAULT_PAGE_SIZE)
                .setPageSize(PageInfo.DEFAULT_PAGE_SIZE)
                .build();
    }
}