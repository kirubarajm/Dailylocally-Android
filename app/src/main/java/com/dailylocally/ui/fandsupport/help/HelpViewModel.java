package com.dailylocally.ui.fandsupport.help;

import androidx.databinding.ObservableArrayList;
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
import com.dailylocally.utilities.DailylocallyApp;
import com.dailylocally.utilities.chat.IssuesListResponse;
import com.dailylocally.utilities.chat.IssuesRequest;
import com.dailylocally.utilities.chat.MapOrderidChatRequest;

import java.util.List;

public class HelpViewModel extends BaseViewModel<HelpNavigator> {

    public ObservableList<IssuesListResponse.Result> issuesObservableList = new ObservableArrayList<>();
    public MutableLiveData<List<IssuesListResponse.Result>> issuesLiveData;

public String orderid;
public Integer stype;

    public HelpViewModel(DataManager dataManager) {
        super(dataManager);
        issuesLiveData = new MutableLiveData<>();

    }


    public void goBack() {
        getNavigator().goBack();
    }




    public MutableLiveData<List<IssuesListResponse.Result>> getIssuesLiveData() {
        return issuesLiveData;
    }


    public void addIssuesListItemsToList(List<IssuesListResponse.Result> issuesList) {
        issuesObservableList.clear();
        issuesObservableList.addAll(issuesList);
    }


 public void getIssuesList(int type) {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {

            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_CHAT_ISSUES, IssuesListResponse.class, new IssuesRequest(type,getDataManager().getCurrentUserId()), new Response.Listener<IssuesListResponse>() {
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

    public void mapTicketidToOrderid(int issueid, int tid, String tag, String department, String note) {

        if (!DailylocallyApp.getInstance().onCheckNetWork()) return;
        try {

            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_CHAT_MAP_ORDERID, CommonResponse.class, new MapOrderidChatRequest(getDataManager().getCurrentUserId(),orderid,issueid,stype), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {
                        if (response.isStatus()){
                            if (getNavigator()!=null)
                                getNavigator().createChat(department,tag,note);
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
