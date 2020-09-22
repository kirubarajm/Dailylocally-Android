package com.dailylocally.utilities;

import im.getsocial.sdk.actions.ActionListener;
import im.getsocial.sdk.notifications.OnNotificationClickedListener;

public interface DependenciesContainer {

	NotificationsManager notificationsManager();

	ActionListener actionListener();

	OnNotificationClickedListener notificationHandler();

}