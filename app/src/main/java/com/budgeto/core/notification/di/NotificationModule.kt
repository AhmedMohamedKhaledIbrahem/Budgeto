package com.budgeto.core.notification.di

import com.budgeto.core.notification.NotificationHelper
import com.budgeto.core.notification.NotificationHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {
    @Binds
    @Singleton
    abstract fun bindNotificationHelper(impl: NotificationHelperImpl): NotificationHelper
}
