package com.dailylocally.ui.joinCommunity;


import androidx.databinding.ObservableField;

public class CommunityItemViewModel {

    public final ObservableField<String> communityName = new ObservableField<>();
    public final ObservableField<String> location = new ObservableField<>();
    public final ObservableField<String> residencesCount = new ObservableField<>();
    public final ObservableField<String> distance = new ObservableField<>();

    public final TransactionHistoryViewModelListener mListener;
    private final CommunityResponse.Result result;

    public CommunityItemViewModel(CommunityResponse.Result result, TransactionHistoryViewModelListener mListener) {
        this.result = result;
        this.mListener = mListener;

        communityName.set(result.getCommunityname());
        location.set(result.getCommunityAddress());
        residencesCount.set(result.getNoOfApartments() + " Members");

        distance.set(result.getDistance()+" "+result.getDistance_text());
    }

    public void onItemClick() {
        mListener.onItemClick(result);
    }

    public interface TransactionHistoryViewModelListener {
        void onItemClick(CommunityResponse.Result cartdetail);
    }

}
