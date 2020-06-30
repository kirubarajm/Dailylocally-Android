/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.dailylocally.ui.fandsupport;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;


public class FeedbackSupportViewModel extends BaseViewModel<FeedbackSupportNavigator> {


    public final ObservableField<String> offer = new ObservableField<>();
    public final ObservableField<String> offerCost = new ObservableField<>();
    public final ObservableField<String> unit = new ObservableField<>();
    public final ObservableField<String> short_desc = new ObservableField<>();
    public final ObservableField<String> productname = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> mrp = new ObservableField<>();

    public final ObservableField<String> aId = new ObservableField<>();
    public final ObservableBoolean discount_cost_status = new ObservableBoolean();



    public FeedbackSupportViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void goBack() {
        if (getNavigator() != null) {
            getNavigator().goBack();
        }
    }
    public void faq() {
        if (getNavigator() != null) {
            getNavigator().faq();
        }
    }

     public void support() {
        if (getNavigator() != null) {
            getNavigator().support();
        }
    }


     public void termsandC() {
        if (getNavigator() != null) {
            getNavigator().termsAndC();
        }
    }
}
