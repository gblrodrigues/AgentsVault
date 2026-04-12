package com.gblrod.agentsvault.navigation

sealed class Routes(
    val route: String
) {
    object Agents : Routes(
        route = "main"
    )

    object Maps : Routes(
        route = "maps"
    )

    object Favorites : Routes(
        route = "favorites"
    )
}