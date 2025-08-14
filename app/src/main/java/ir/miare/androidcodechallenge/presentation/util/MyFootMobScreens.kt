package ir.miare.androidcodechallenge.presentation.util

import kotlinx.serialization.Serializable

sealed class MyFootMobScreens {

    @Serializable
    object MyFootMob : MyFootMobScreens()

    @Serializable
    object LeagueInfo : MyFootMobScreens()

    @Serializable
    data class FollowedPlayers(val categoryId: Int?) : MyFootMobScreens()

}
