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

import com.dailylocally.ui.account.MyAccountProvider;
import com.dailylocally.ui.account.referrals.ReferralsActivity;
import com.dailylocally.ui.account.referrals.ReferralsActivityModule;
import com.dailylocally.ui.address.googleAddress.GoogleAddressActivity;
import com.dailylocally.ui.address.googleAddress.GoogleAddressModule;
import com.dailylocally.ui.address.addAddress.AddressNewActivity;
import com.dailylocally.ui.address.addAddress.AddressNewModule;
import com.dailylocally.ui.address.saveAddress.SaveAddressActivity;
import com.dailylocally.ui.address.saveAddress.SaveAddressModule;
import com.dailylocally.ui.address.viewAddress.ViewAddressActivity;
import com.dailylocally.ui.address.viewAddress.ViewAddressModule;
import com.dailylocally.ui.calendarView.CalendarActivity;
import com.dailylocally.ui.calendarView.CalendarModule;
import com.dailylocally.ui.cart.CartProvider;
import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.category.l1.CategoryL1Module;
import com.dailylocally.ui.category.l2.CategoryL2Activity;
import com.dailylocally.ui.category.l2.CategoryL2Module;
import com.dailylocally.ui.category.l2.products.ProductsProvider;

import com.dailylocally.ui.favorites.FavoritesActivity;
import com.dailylocally.ui.favorites.FavoritesModule;
import com.dailylocally.ui.home.HomeProvider;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.main.MainActivityModule;
import com.dailylocally.ui.onboarding.OnBoardingActivity;
import com.dailylocally.ui.onboarding.OnBoardingActivityModule;
import com.dailylocally.ui.orderplaced.OrderPlacedActivity;
import com.dailylocally.ui.orderplaced.OrderPlacedModule;
import com.dailylocally.ui.productDetail.ProductDetailsActivity;
import com.dailylocally.ui.productDetail.ProductDetailsModule;
import com.dailylocally.ui.search.SearchProvider;
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
import com.dailylocally.ui.subscription.SubscriptionActivity;
import com.dailylocally.ui.subscription.SubscriptionModule;
import com.dailylocally.ui.update.UpdateActivity;
import com.dailylocally.ui.update.UpdateModule;
import com.dailylocally.utilities.nointernet.InternetErrorFragment;
import com.dailylocally.utilities.nointernet.InternetErrorModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {SubscriptionModule.class/*, OrderCanceledProvider.class*/})
    abstract SubscriptionActivity bindSubscriptionActivity();


@ContributesAndroidInjector(modules = {OrderPlacedModule.class/*, OrderCanceledProvider.class*/})
    abstract OrderPlacedActivity bindOrderPlacedActivity();

 @ContributesAndroidInjector(modules = {FaqFragmentModule.class/*, OrderCanceledProvider.class*/})
    abstract FaqActivity bindFaqActivity();

 @ContributesAndroidInjector(modules = {CategoryL1Module.class/*, OrderCanceledProvider.class*/})
    abstract CategoryL1Activity bindCategoryL1Activity();
@ContributesAndroidInjector(modules = {CategoryL2Module.class, ProductsProvider.class/*, OrderCanceledProvider.class*/})
    abstract CategoryL2Activity bindCategoryL2Activity();
    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            HomeProvider.class,
            CartProvider.class,
            SearchProvider.class,
            MyAccountProvider.class
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

    @ContributesAndroidInjector(modules = GoogleAddressModule.class)
    abstract GoogleAddressActivity bindAddAddressActivity();

    @ContributesAndroidInjector(modules = CalendarModule.class)
    abstract CalendarActivity bindCalendarActivity();

    @ContributesAndroidInjector(modules = ReferralsActivityModule.class)
    abstract ReferralsActivity bindReferralActivity();

    @ContributesAndroidInjector(modules = FavoritesModule.class)
    abstract FavoritesActivity bindFavoritesActivity();

    @ContributesAndroidInjector(modules = {AddressNewModule.class})
    abstract AddressNewActivity bindAddressNewActivity();

    @ContributesAndroidInjector(modules = {SaveAddressModule.class})
    abstract SaveAddressActivity bindSaveAddressActivity();

    @ContributesAndroidInjector(modules = {ViewAddressModule.class})
    abstract ViewAddressActivity bindViewAddressActivity();

    @ContributesAndroidInjector(modules = {ProductDetailsModule.class})
    abstract ProductDetailsActivity bindProductDetailsActivity();

}
