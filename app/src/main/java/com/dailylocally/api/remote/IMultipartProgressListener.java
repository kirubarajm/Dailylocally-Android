package com.dailylocally.api.remote;

public interface IMultipartProgressListener {
    void transferred(long transferred, int progress);
}