package ir.miare.androidcodechallenge.core.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.core.network.NetworkDataSource
import ir.miare.androidcodechallenge.core.network.RetrofitNetwork

@Module
@InstallIn(SingletonComponent::class)
internal interface FlavoredNetworkModule {

    @Binds
    fun binds(retrofitNetwork: RetrofitNetwork): NetworkDataSource
}
