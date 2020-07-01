package com.dailylocally.ui.fandsupport.help;

import android.widget.Toast;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dailylocally.api.remote.GsonRequest;
import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;

import com.dailylocally.utilities.AppConstants;
import com.dailylocally.utilities.CommonResponse;
import com.dailylocally.utilities.DailylocallyApp;

import com.dailylocally.utilities.chat.IssuesListResponse;
import com.dailylocally.utilities.chat.IssuesRequest;

import java.util.List;

public class HelpViewModel extends BaseViewModel<HelpNavigator> {

    public final ObservableField<String> deliveryName = new ObservableField<>();
    public final ObservableField<String> deliveryNumber = new ObservableField<>();
    public final ObservableField<String> serviceCharges = new ObservableField<>();
    public final ObservableField<String> cancelationMessage = new ObservableField<>();



    public ObservableBoolean cancelClicked = new ObservableBoolean();
    public ObservableBoolean deliveryClicked = new ObservableBoolean();
    public ObservableBoolean deliveryAssigned = new ObservableBoolean();
    public ObservableBoolean otherReason = new ObservableBoolean();


    public ObservableList<IssuesListResponse.Result> issuesObservableList = new ObservableArrayList<>();
    public MutableLiveData<List<IssuesListResponse.Result>> issuesLiveData;

public String orderid;

    public HelpViewModel(DataManager dataManager) {
        super(dataManager);
        otherReason.set(false);
        issuesLiveData = new MutableLiveData<>();

        getIssuesList(3);
    }

    public void goBack() {
        getNavigator().goBack();
    }



    public MutableLiveData<List<IssuesListResponse.Result>> getIssuesLiveData() {
        return issuesLiveData;
    }

    public void setIssuesLiveData(MutableLiveData<List<IssuesListResponse.Result>> issuesLiveData) {
        this.issuesLiveData = issuesLiveData;
    }

    public void addIssuesListItemsToList(List<IssuesListResponse.Result> issuesList) {
        issuesObservableList.clear();
        issuesObservableList.addAll(issuesList);
    }



 public void getIssuesList(int type) {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {

            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_CHAT_ISSUES_URL, IssuesListResponse.class, new IssuesRequest(type,getDataManager().getCurrentUserId()), new Response.Listener<IssuesListResponse>() {
                @Override
                public void onResponse(IssuesListResponse response) {
                    if (response != null) {
                        if (response.getResult()!=null&&response.getResult().size()>0) {
                            issuesLiveData.setValue(response.getResult());
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    setIsLoading(false);

                }
            }, AppConstants.API_VERSION_ONE);

            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);

        } catch (Exception ee) {

            ee.printStackTrace();

        }


    }



public void getChatDetails(int type) {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {

            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_CHAT_ISSUES_URL, IssuesListResponse.class, new IssuesRequest(type,getDataManager().getCurrentUserId()), new Response.Listener<IssuesListResponse>() {
                @Override
                public void onResponse(IssuesListResponse response) {
                    if (response != null) {
                        if (response.getResult()!=null&&response.getResult().size()>0) {
                            issuesLiveData.setValue(response.getResult());

                            if (getNavigator()!=null){
                                //getNavigator().createChat();
                            }


                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    setIsLoading(false);

                }
            }, AppConstants.API_VERSION_ONE);

            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);

        } catch (Exception ee) {

            ee.printStackTrace();

        }


    }

    public void getIssuesNote(int type,int issueid) {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {

            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_CHAT_ISSUES_NOTE_URL, IssuesListResponse.class, new IssuesRequest(getDataManager().getCurrentUserId(),issueid), new Response.Listener<IssuesListResponse>() {
                @Override
                public void onResponse(IssuesListResponse response) {
                    if (response != null) {
                        if (response.getResult()!=null&&response.getResult().size()>0) {

                            if (getNavigator()!=null)
                                getNavigator().createChat(response.getResult().get(0).getDepartmentName(),response.getResult().get(0).getTagName(),response.getResult().get(0).getNote());
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    setIsLoading(false);

                }
            }, AppConstants.API_VERSION_ONE);

            DailylocallyApp.getInstance().addToRequestQueue(gsonRequest);

        } catch (Exception ee) {

            ee.printStackTrace();

        }


    }


}
