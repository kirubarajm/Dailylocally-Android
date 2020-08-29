package com.dailylocally.ui.community;


import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;

import java.util.List;

import im.getsocial.sdk.communities.GetSocialActivity;

public class CommunityViewModel extends BaseViewModel<CommunityNavigator> {

    public final ObservableField<String> addressTitle = new ObservableField<>();

    public final ObservableBoolean cart = new ObservableBoolean();
    public final ObservableBoolean fullEmpty = new ObservableBoolean();
    public final ObservableBoolean kitchenListLoading = new ObservableBoolean();
    public final ObservableField<String> emptyImageUrl = new ObservableField<>();
    public final ObservableField<String> emptyContent = new ObservableField<>();
    public final ObservableField<String> headerContent = new ObservableField<>();
    public final ObservableField<String> emptySubContent = new ObservableField<>();
    public final ObservableField<String> headerSubContent = new ObservableField<>();
    public final ObservableField<String> categoryTitle = new ObservableField<>();
    public final ObservableField<String> collectionTitle = new ObservableField<>();
    public final ObservableField<String> ratingTitle = new ObservableField<>();
    public final ObservableField<String> ratingDate = new ObservableField<>();
    public final ObservableField<String> eta = new ObservableField<>();
    public final ObservableField<String> kitchenImage = new ObservableField<>();
    public final ObservableField<String> products = new ObservableField<>();
    public final ObservableField<String> unserviceableTitle = new ObservableField<>();
    public final ObservableField<String> unserviceableSubTitle = new ObservableField<>();
    public final ObservableBoolean serviceable = new ObservableBoolean();
    public final ObservableBoolean categoryLoading = new ObservableBoolean();
    public final ObservableBoolean showRating = new ObservableBoolean();

    public final ObservableField<String> updateTitle = new ObservableField<>();
    public final ObservableField<String> updateAction = new ObservableField<>();
    public final ObservableField<String> screenName = new ObservableField<>();
    public final ObservableBoolean updateAvailable = new ObservableBoolean();
    public final ObservableBoolean enableLater = new ObservableBoolean();
    public final ObservableBoolean update = new ObservableBoolean();


    public final ObservableField<String> eventImageUrl = new ObservableField<>();
    public final ObservableField<String> catImageUrl = new ObservableField<>();
    public final ObservableField<String> whatsImageUrl = new ObservableField<>();
    public final ObservableField<String> aboutUsTitle = new ObservableField<>();
    public final ObservableField<String> aboutUsDes = new ObservableField<>();
    public final ObservableField<String> whatsTitle = new ObservableField<>();
    public final ObservableField<String> whatsDes = new ObservableField<>();
    public final ObservableField<String> sneakTitle = new ObservableField<>();
    public final ObservableField<String> sneakDes = new ObservableField<>();
    public final ObservableField<String> postDes = new ObservableField<>();
    public final ObservableField<String> postTitle = new ObservableField<>();
    public final ObservableField<String> postDate = new ObservableField<>();
    public final ObservableField<String> actionText = new ObservableField<>();
    public final ObservableField<String> icon = new ObservableField<>();
    public final ObservableField<String> image1 = new ObservableField<>();
    public final ObservableField<String> image2 = new ObservableField<>();

    public final ObservableBoolean showAction = new ObservableBoolean();
    public final ObservableBoolean singleImage = new ObservableBoolean();
    public final ObservableBoolean attachmentAvailable = new ObservableBoolean();

    public ObservableList<GetSocialActivity> getSocialActivities = new ObservableArrayList<>();
    public String ratingDOID = "0";
    private MutableLiveData<List<GetSocialActivity>> socialActivitiesListLiveData;


    public CommunityViewModel(DataManager dataManager) {
        super(dataManager);
        socialActivitiesListLiveData = new MutableLiveData<>();
        updateAvailable.set(getDataManager().isUpdateAvailable());

        eventImageUrl.set("https://eattovo.s3.ap-south-1.amazonaws.com/upload/admin/makeit/product/1595868674050-DLV2%20Category-Bakery.jpg");
        catImageUrl.set("https://eattovo.s3.ap-south-1.amazonaws.com/upload/admin/makeit/product/1595847977688-Category%20Milk-01.jpg");
        whatsImageUrl.set("https://dailylocally.s3.ap-south-1.amazonaws.com/admin/1596196480459-DLV2%20Category-Spreads.jpg");
        aboutUsTitle.set("About Us");
        aboutUsDes.set("Know More about daily locally Exclusive");
        sneakTitle.set("Sneak Peak");
        sneakDes.set("Watch a short video on Daily Locally Exclusive");
        whatsTitle.set("What's Cooking in community");
        whatsDes.set("Join your community's WhatsApp group and socialize with the members");

    }

    public MutableLiveData<List<GetSocialActivity>> getsocialActivitiesListLiveData() {
        return socialActivitiesListLiveData;
    }


    public void addCommunityPostToList(List<GetSocialActivity> items) {
        getSocialActivities.clear();
        getSocialActivities.addAll(items);

    }


}