package com.dailylocally.ui.joinCommunity.contactWhatsapp;

import com.dailylocally.data.DataManager;
import dagger.Module;
import dagger.Provides;

@Module
public class ContactWhatsAppModule {

    @Provides
    ContactWhatsAppViewModel provideContactWhatsAppViewModel(DataManager dataManager) {
        return new ContactWhatsAppViewModel(dataManager);
    }

}
