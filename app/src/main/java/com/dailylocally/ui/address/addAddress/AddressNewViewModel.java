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

package com.dailylocally.ui.address.addAddress;

import androidx.databinding.ObservableBoolean;

import com.dailylocally.data.DataManager;
import com.dailylocally.ui.base.BaseViewModel;

public class AddressNewViewModel extends BaseViewModel<AddressNewNavigator> {

    public final ObservableBoolean apartmentOrIndividual = new ObservableBoolean();


    public AddressNewViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void apartment(){
        if (getNavigator()!=null){
            getNavigator().apartmentClick();
        }
    }

    public void individual(){
        if (getNavigator()!=null){
            getNavigator().individualClick();
        }
    }

    public void confirmClick(){
        if (getNavigator()!=null){
            getNavigator().confirmClick();
        }
    }
}
