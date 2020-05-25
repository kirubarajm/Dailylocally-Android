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

package com.dailylocally.di;

import com.dailylocally.ui.address.add.AddAddressActivity;
import com.dailylocally.ui.address.add.AddAddressModule;
import com.dailylocally.ui.home.HomeProvider;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.main.MainActivityModule;
import com.dailylocally.ui.onboarding.OnBoardingActivity;
import com.dailylocally.ui.onboarding.OnBoardingActivityModule;
import com.dailylocally.ui.signup.SignUpActivity;
import com.dailylocally.ui.signup.SignUpActivityModule;
import com.dailylocally.ui.signup.fagsandsupport.FaqsAndSupportActivity;
import com.dailylocally.ui.signup.fagsandsupport.FaqsAndSupportModule;
import com.dailylocally.ui.signup.faqs.FaqActivity;
import com.dailylocally.ui.signup.faqs.FaqFragmentModule;
import com.dailylocally.ui.signup.opt.OtpActivity;
import com.dailylocally.ui.signup.opt.OtpActivityModule;
import com.dailylocally.ui.signup.privacy.PrivacyActivity;
import com.dailylocally.ui.signup.privacy.PrivacyModule;
import com.dailylocally.ui.signup.registration.RegistrationActivity;
import com.dailylocally.ui.signup.registration.RegistrationModule;
import com.dailylocally.ui.signup.tandc.TermsAndConditionActivity;
import com.dailylocally.ui.signup.tandc.TermsAndConditionModule;
import com.dailylocally.ui.splash.SplashActivity;
import com.dailylocally.ui.splash.SplashModule;
import com.dailylocally.ui.update.UpdateActivity;
import com.dailylocally.ui.update.UpdateModule;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.dailylocally.utilities.nointernet.InternetErrorModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {FaqFragmentModule.class/*, OrderCanceledProvider.class*/})
    abstract FaqActivity bindFaqActivity();

    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            HomeProvider.class

    })
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = OnBoardingActivityModule.class)
    abstract OnBoardingActivity bindOnBoardingActivity();
    @ContributesAndroidInjector(modules = {UpdateModule.class/*, OrderCanceledProvider.class*/})
    abstract UpdateActivity bindUpdateActivity();

    @ContributesAndroidInjector(modules = {InternetErrorModule.class})
    abstract InternetErrorFragment bindInternetActivity();

    @ContributesAndroidInjector(modules = {SignUpActivityModule.class})
    abstract SignUpActivity bindSignUpActivity();

    @ContributesAndroidInjector(modules = OtpActivityModule.class)
    abstract OtpActivity bindOtpActivity();


    @ContributesAndroidInjector(modules = TermsAndConditionModule.class)
    abstract TermsAndConditionActivity bindTermsAndConditionActivity();


    @ContributesAndroidInjector(modules = RegistrationModule.class)
    abstract RegistrationActivity bindRegistrationActivity();


    @ContributesAndroidInjector(modules = PrivacyModule.class)
    abstract PrivacyActivity bindPrivacyActivity();

    @ContributesAndroidInjector(modules = FaqsAndSupportModule.class)
    abstract FaqsAndSupportActivity bindFaqAndSupportActivity();

    @ContributesAndroidInjector(modules = AddAddressModule.class)
    abstract AddAddressActivity bindAddAddressActivity();

}
