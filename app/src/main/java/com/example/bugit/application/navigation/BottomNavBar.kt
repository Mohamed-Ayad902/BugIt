package com.example.bugit.application.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bugit.R
import com.example.bugit.android.reusable_components.AppText
import com.example.bugit.android.theme.AppTheme.colors
import com.example.bugit.android.theme.AppTheme.dimens
import com.example.bugit.android.theme.AppTheme.shapes
import com.example.bugit.android.theme.AppTheme.typography

data class NavItem(
    val id: Int,
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val screen: Screens
) {
    companion object {
        val list = listOf(
            NavItem(
                0,
                R.drawable.bug,
                R.string.new_bug,
                Screens.NewBug
            ),
            NavItem(1, R.drawable.list, R.string.history, Screens.BugsList)
        )
    }
}

@Composable
fun BottomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navItems = NavItem.list
    val selectedIndex = navItems.indexOfFirst { item ->
        currentDestination?.hierarchy?.any { it.hasRoute(item.screen::class) } == true
    }.takeIf { it != -1 } ?: 0

    val showBottomNav = navItems.map { it.screen::class }.any { route ->
        currentDestination?.hierarchy?.any {
            it.hasRoute(route)
        } == true
    }

    if (showBottomNav) {
        BottomNavBarContent(
            navItems = navItems,
            selectedIndex = selectedIndex,
            onItemSelected = { item ->
                if (item.id != selectedIndex) {
                    navController.navigate(item.screen) {
                        popUpTo(Screens.NewBug) { inclusive = false }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            modifier = modifier
        )
    }
}

@Composable
private fun BottomNavBarContent(
    navItems: List<NavItem>,
    selectedIndex: Int,
    onItemSelected: (NavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.surface)
            .navigationBarsPadding()
            .height(dimens.extraHuge),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navItems.forEachIndexed { _, item ->
            val isSelected = item.id == selectedIndex

            val animDuration = 200
            val easingCurve = FastOutSlowInEasing

            val animatedColor by animateColorAsState(
                targetValue = if (isSelected) colors.primary else colors.onSurface.copy(alpha = 0.6f),
                animationSpec = tween(durationMillis = animDuration, easing = easingCurve),
                label = "color_animation"
            )

            val animatedIconSize by animateDpAsState(
                targetValue = if (isSelected) dimens.medium else dimens.smallMedium,
                animationSpec = tween(durationMillis = animDuration, easing = easingCurve),
                label = "icon_size_animation"
            )

            val animatedPadding by animateDpAsState(
                targetValue = if (isSelected) dimens.small else dimens.extraSmall,
                animationSpec = tween(durationMillis = animDuration, easing = easingCurve),
                label = "padding_animation"
            )

            val animatedTextScale by animateFloatAsState(
                targetValue = if (isSelected) 1.1f else 1.0f,
                animationSpec = tween(durationMillis = animDuration, easing = easingCurve),
                label = "text_scale_animation"
            )

            Column(
                modifier = Modifier
                    .clip(shapes.smallMedium)
                    .clickable { onItemSelected(item) }
                    .padding(horizontal = dimens.medium, vertical = dimens.small),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = stringResource(id = item.label),
                    tint = animatedColor,
                    modifier = Modifier.size(animatedIconSize)
                )

                AppText(
                    modifier = Modifier
                        .padding(top = animatedPadding)
                        .graphicsLayer {
                            scaleX = animatedTextScale
                            scaleY = animatedTextScale
                        },
                    text = stringResource(id = item.label),
                    color = animatedColor,
                    style = typography.labelSmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBottomNavBarNewBug() {
    BottomNavBarContent(
        navItems = NavItem.list,
        selectedIndex = 0,
        onItemSelected = {}
    )
}