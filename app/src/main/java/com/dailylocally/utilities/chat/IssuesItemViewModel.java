package com.dailylocally.utilities.chat;


import androidx.databinding.ObservableField;

public class IssuesItemViewModel {


    public final ObservableField<String> ratings = new ObservableField<>();

    public final ObservableField<String> issue_name = new ObservableField<>();

    public final IssueItemViewModelListener mListener;
    public final IssuesListResponse.Result issue;



    public IssuesItemViewModel(IssueItemViewModelListener mListener, IssuesListResponse.Result issue) {
        this.mListener = mListener;
        this.issue = issue;
        this.issue_name.set(issue.getIssues());

    }


    public void onItemClick() {
        mListener.onItemClick(issue);
    }

    public interface IssueItemViewModelListener {
        void onItemClick(IssuesListResponse.Result issue);

    }

}
