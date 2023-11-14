package com.example.jetshoes

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.jetshoes.model.FakeRewardDataSource
import com.example.jetshoes.ui.navigation.Screen
import com.example.jetshoes.ui.theme.JetShoesTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JetRewardAppTest{

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp(){
        composeTestRule.setContent {
            JetShoesTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetRewardApp(navController = navController)
            }
        }
    }

    @Test
    fun navHostVerifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData(){
        composeTestRule.onNodeWithTag("RewardList").performScrollToIndex(10)
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[11].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailReward.route)
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[11].title).assertIsDisplayed()
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_cart).performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithTag("RewardList").performScrollToIndex(1)
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[10].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailReward.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_checkout_rightBackStack() {
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[1].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailReward.route)
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }
}