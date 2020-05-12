package com.dailylocally.ui.base;


import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.dailylocally.data.DataManager;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel<N> extends ViewModel {

    private CompositeDisposable mCompositeDisposable;
    private final DataManager mDataManager;
    private WeakReference<N> mNavigator;
    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);

    public BaseViewModel(DataManager dataManager) {
        this.mDataManager = dataManager;
        this.mCompositeDisposable = new CompositeDisposable();
    }
    public DataManager getDataManager() {
        return mDataManager;
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(false);
    }
}
