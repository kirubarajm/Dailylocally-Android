package com.dailylocally.utilities;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.zendesk.service.ErrorResponse;
import com.zendesk.service.ZendeskCallback;
import com.zopim.android.sdk.api.ZopimChat;

import zendesk.core.ProviderStore;
import zendesk.core.Zendesk;

public class PushUtils {

    /**
     * Check if play services are installed and use able.
     *
     * @param activity An activity
     */
    public static void checkPlayServices(Activity activity) {
        final GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (errorCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(errorCode)) {
                apiAvailability.makeGooglePlayServicesAvailable(activity);
            } else {
                //Toast.makeText(activity, R.string.push_error_device_not_compatible, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void registerWithZendesk() {
        final ProviderStore providerStore = Zendesk.INSTANCE.provider();

        if (providerStore == null) {
            return;
        }

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                          //  Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token

                        Zendesk.INSTANCE.provider().pushRegistrationProvider().registerWithDeviceIdentifier(task.getResult().getToken(), new ZendeskCallback<String>() {
                            @Override
                            public void onSuccess(String result) {

                               Log.e("ZendeskPush","success");

                            }

                            @Override
                            public void onError(ErrorResponse errorResponse) {
                                Log.e("ZendeskPush","success");
                            }
                        });


                       providerStore.pushRegistrationProvider().registerWithDeviceIdentifier(task.getResult().getToken(), null);

                        ZopimChat.setPushToken(task.getResult().getToken());

                    }
                });
    }
}
