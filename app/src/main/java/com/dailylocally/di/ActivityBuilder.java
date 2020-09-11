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

import com.dailylocally.ui.aboutus.AboutUsActivity;
import com.dailylocally.ui.aboutus.AboutUsModule;
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
import com.dailylocally.ui.calendarView.CalendarProvider;
import com.dailylocally.ui.cart.CartProvider;
import com.dailylocally.ui.category.l1.CategoryL1Activity;
import com.dailylocally.ui.category.l1.CategoryL1Module;
import com.dailylocally.ui.category.l2.CategoryL2Activity;
import com.dailylocally.ui.category.l2.CategoryL2Module;
import com.dailylocally.ui.category.l2.products.ProductsProvider;

import com.dailylocally.ui.category.l2.products.filter.FilterProvider;

import com.dailylocally.ui.category.l2.products.sort.SortProvider;

import com.dailylocally.ui.category.viewall.CatProductActivity;
import com.dailylocally.ui.category.viewall.CatProductModule;
import com.dailylocally.ui.category.viewall.products.CatProductFragProvider;
import com.dailylocally.ui.collection.l2.CollectionDetailsActivity;
import com.dailylocally.ui.collection.l2.CollectionDetailsModule;
import com.dailylocally.ui.collection.l2.products.CollectionProductsProvider;

import com.dailylocally.ui.community.CommunityProvider;
import com.dailylocally.ui.community.catlist.CommunityCatProvider;
import com.dailylocally.ui.community.details.EventDetailsActivity;
import com.dailylocally.ui.community.details.EventDetailsModule;
import com.dailylocally.ui.community.event.EventActivity;
import com.dailylocally.ui.community.event.EventModule;
import com.dailylocally.ui.joinCommunity.CommunityActivity;
import com.dailylocally.ui.joinCommunity.CommunityActivityModule;
import com.dailylocally.ui.joinCommunity.communityLocation.CommunityAddressActivity;
import com.dailylocally.ui.joinCommunity.communityLocation.CommunityAddressModule;
import com.dailylocally.ui.communityOnboarding.CommunityOnBoardingActivity;
import com.dailylocally.ui.communityOnboarding.CommunityOnBoardingActivityModule;

import com.dailylocally.ui.coupons.CouponsActivity;
import com.dailylocally.ui.coupons.CouponsModule;

import com.dailylocally.ui.fandsupport.FeedbackSupportActivity;
import com.dailylocally.ui.fandsupport.FeedbackSupportModule;
import com.dailylocally.ui.fandsupport.help.HelpActivity;
import com.dailylocally.ui.fandsupport.help.HelpModule;
import com.dailylocally.ui.fandsupport.support.SupportActivity;
import com.dailylocally.ui.fandsupport.support.SupportModule;
import com.dailylocally.ui.favourites.FavActivity;
import com.dailylocally.ui.favourites.FavModule;
import com.dailylocally.ui.favourites.products.FavProductsProvider;
import com.dailylocally.ui.home.HomeProvider;
import com.dailylocally.ui.joinCommunity.contactWhatsapp.ContactWhatsAppActivity;
import com.dailylocally.ui.joinCommunity.contactWhatsapp.ContactWhatsAppModule;
import com.dailylocally.ui.joinCommunity.viewProfilePic.ViewPhotoActivity;
import com.dailylocally.ui.joinCommunity.viewProfilePic.ViewPhotoModule;
import com.dailylocally.ui.main.MainActivity;
import com.dailylocally.ui.main.MainActivityModule;
import com.dailylocally.ui.onboarding.OnBoardingActivity;
import com.dailylocally.ui.onboarding.OnBoardingActivityModule;
import com.dailylocally.ui.orderplaced.OrderPlacedActivity;
import com.dailylocally.ui.orderplaced.OrderPlacedModule;
import com.dailylocally.ui.productDetail.ProductDetailsActivity;
import com.dailylocally.ui.productDetail.ProductDetailsModule;
import com.dailylocally.ui.productDetail.dialogProductCancel.DialogProductCancelProvider;
import com.dailylocally.ui.productDetail.productDetailCancel.ProductCancelActivity;
import com.dailylocally.ui.productDetail.productDetailCancel.ProductCancelModule;
import com.dailylocally.ui.promotion.AdActivity;
import com.dailylocally.ui.promotion.AdModule;
import com.dailylocally.ui.promotion.bottom.PromotionProvider;
import com.dailylocally.ui.rating.RatingActivity;
import com.dailylocally.ui.rating.RatingModule;
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
import com.dailylocally.ui.transactionHistory.TransactionHistoryActivity;
import com.dailylocally.ui.transactionHistory.TransactionHistoryModule;
import com.dailylocally.ui.transactionHistory.view.TransactionDetailsActivity;
import com.dailylocally.ui.transactionHistory.view.TransactionDetailsModule;
import com.dailylocally.ui.update.UpdateActivity;
import com.dailylocally.ui.update.UpdateModule;
import com.dailylocally.ui.video.VideoActivity;
import com.dailylocally.ui.video.VideoModule;
import com.dailylocally.utilities.datepicker.DatePickerActivity;
import com.dailylocally.utilities.datepicker.DatePickerModule;
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
@ContributesAndroidInjector(modules = {CategoryL2Module.class, ProductsProvider.class, FilterProvider.class, SortProvider.class/*, OrderCanceledProvider.class*/})
    abstract CategoryL2Activity bindCategoryL2Activity();

@ContributesAndroidInjector(modules = {CatProductModule.class, CatProductFragProvider.class, FilterProvider.class, SortProvider.class/*, OrderCanceledProvider.class*/})
    abstract CatProductActivity bindCatProduct();


    @ContributesAndroidInjector(modules = {CollectionDetailsModule.class, CollectionProductsProvider.class, FilterProvider.class,SortProvider.class/*, OrderCanceledProvider.class*/})
    abstract CollectionDetailsActivity bindCollectionDetailsActivity();

 @ContributesAndroidInjector(modules = {FavModule.class, FavProductsProvider.class, SortProvider.class/*, OrderCanceledProvider.class*/})
    abstract FavActivity bindFavActivity();

    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            HomeProvider.class,
            CartProvider.class,
            SearchProvider.class,
            CalendarProvider.class,
            PromotionProvider.class,
            CommunityProvider.class,
            CommunityCatProvider.class,
            MyAccountProvider.class
    })
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = HelpModule.class)
    abstract HelpActivity bindHelpActivity();
 @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity bindSplashActivity();

 @ContributesAndroidInjector(modules = FeedbackSupportModule.class)
    abstract FeedbackSupportActivity bindFeedbackSupportActivity();

@ContributesAndroidInjector(modules = DatePickerModule.class)
    abstract DatePickerActivity bindDatePickerActivity();



 @ContributesAndroidInjector(modules = EventDetailsModule.class)
    abstract EventDetailsActivity bindEventDetailsActivity();

 @ContributesAndroidInjector(modules = EventModule.class)
    abstract EventActivity bindEventActivity();


 @ContributesAndroidInjector(modules = VideoModule.class)
    abstract VideoActivity bindVideoActivity();

 @ContributesAndroidInjector(modules = AboutUsModule.class)
    abstract AboutUsActivity bindAboutUsActivity();


@ContributesAndroidInjector(modules = SupportModule.class)
    abstract SupportActivity bindSupportActivity();

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


    @ContributesAndroidInjector(modules = {AddressNewModule.class})
    abstract AddressNewActivity bindAddressNewActivity();

    @ContributesAndroidInjector(modules = {SaveAddressModule.class})
    abstract SaveAddressActivity bindSaveAddressActivity();

    @ContributesAndroidInjector(modules = {ViewAddressModule.class})
    abstract ViewAddressActivity bindViewAddressActivity();

    @ContributesAndroidInjector(modules = {ProductDetailsModule.class})
    abstract ProductDetailsActivity bindProductDetailsActivity();

    @ContributesAndroidInjector(modules = {ProductCancelModule.class, DialogProductCancelProvider.class})
    abstract ProductCancelActivity bindProductCancelActivity();

    @ContributesAndroidInjector(modules = {TransactionDetailsModule.class})
    abstract TransactionDetailsActivity bindTransactionDetailsActivity();

    @ContributesAndroidInjector(modules = {TransactionHistoryModule.class})
    abstract TransactionHistoryActivity bindTransactionHistoryActivity();

    @ContributesAndroidInjector(modules = {CouponsModule.class})
    abstract CouponsActivity bindCouponsActivity();

    @ContributesAndroidInjector(modules = {RatingModule.class})
    abstract RatingActivity bindRatingActivity();

    @ContributesAndroidInjector(modules = {AdModule.class, PromotionProvider.class})
    abstract AdActivity bindAdActivity();

    @ContributesAndroidInjector(modules = {CommunityOnBoardingActivityModule.class})
    abstract CommunityOnBoardingActivity bindCommunityOnBoardingActivity();

    @ContributesAndroidInjector(modules = {CommunityActivityModule.class})
    abstract CommunityActivity bindCommunityActivity();

    @ContributesAndroidInjector(modules = {CommunityAddressModule.class})
    abstract CommunityAddressActivity bindCommunityAddressActivity();

    @ContributesAndroidInjector(modules = {ContactWhatsAppModule.class})
    abstract ContactWhatsAppActivity bindContactWhatsAppActivity();

    @ContributesAndroidInjector(modules = {ViewPhotoModule.class})
    abstract ViewPhotoActivity bindViewPhotoActivity();

}
