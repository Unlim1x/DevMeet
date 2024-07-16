package ru.unlim1x.wb_project.ui.navigation

sealed class AuthNavGraphNodes(val route:String) {
    data object PhoneNode:AuthNavGraphNodes(route = "PhoneScreen")
    data object CodeNode:AuthNavGraphNodes(route = "CodeScreen")
    data object MainGraphNode:AuthNavGraphNodes(route = "NavigationScreen")
    data object ProfileNode:AuthNavGraphNodes(route="SettingProfile")
}