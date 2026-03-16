package com.example.bugit.common.mockup

import com.example.core.feature.bug_reporting.domain.model.Bug

private fun getImageUrl(id: String) = "https://picsum.photos/seed/$id/400/300"

val dummyBugs = listOf(
    Bug(
        id = "1",
        description = "App crashes when clicking the 'Submit' button on the checkout screen.",
        screenshotUri = getImageUrl("1"),
        dynamicFields = mapOf("Severity" to "High", "Device" to "Pixel 7", "Version" to "2.4.1"),
        createdAt = "2024-03-15 10:30 AM"
    ),
    Bug(
        id = "2",
        description = "The login screen font is too small on low-resolution devices.",
        screenshotUri = getImageUrl("2"),
        dynamicFields = mapOf("Severity" to "Low", "Priority" to "Medium", "OS" to "Android 11"),
        createdAt = "2024-03-15 11:15 AM"
    ),
    Bug(
        id = "3",
        description = "Dark mode makes the navigation text invisible.",
        screenshotUri = getImageUrl("3"),
        dynamicFields = mapOf("Severity" to "High", "Theme" to "Dark", "BugType" to "UI/UX"),
        createdAt = "2024-03-15 01:45 PM"
    ),
    Bug(
        id = "4",
        description = "API returns 500 error when refreshing the profile page.",
        screenshotUri = getImageUrl("4"),
        dynamicFields = mapOf("Severity" to "High", "Endpoint" to "/api/v1/profile"),
        createdAt = "2024-03-16 09:00 AM"
    ),
    Bug(
        id = "5",
        description = "Search results are duplicated when scrolling too fast.",
        screenshotUri = getImageUrl("5"),
        dynamicFields = mapOf("Severity" to "Medium", "ListType" to "RecyclerView"),
        createdAt = "2024-03-16 10:10 AM"
    ),
    Bug(
        id = "6",
        description = "Profile picture fails to upload when size exceeds 2MB.",
        screenshotUri = getImageUrl("6"),
        dynamicFields = mapOf("Severity" to "Medium", "Feature" to "Onboarding"),
        createdAt = "2024-03-16 11:30 AM"
    ),
    Bug(
        id = "7",
        description = "Incorrect currency symbol showing in the 'Total Amount' field.",
        screenshotUri = getImageUrl("7"),
        dynamicFields = mapOf("Severity" to "High", "Locale" to "en-UK"),
        createdAt = "2024-03-16 02:20 PM"
    ),
    Bug(
        id = "8",
        description = "Notification sounds play even when the app is on 'Mute'.",
        screenshotUri = getImageUrl("8"),
        dynamicFields = mapOf("Severity" to "Low", "AudioState" to "Silent"),
        createdAt = "2024-03-17 08:45 AM"
    ),
    Bug(
        id = "9",
        description = "The 'Back' button doesn't work on the 'Settings' sub-menu.",
        screenshotUri = getImageUrl("9"),
        dynamicFields = mapOf("Severity" to "Medium", "Fragment" to "SettingsDetail"),
        createdAt = "2024-03-17 12:00 PM"
    ),
    Bug(
        id = "10",
        description = "Images are taking more than 10 seconds to load on slow Wi-Fi.",
        screenshotUri = getImageUrl("10"),
        dynamicFields = mapOf("Severity" to "Low", "Network" to "Slow WiFi"),
        createdAt = "2024-03-17 03:55 PM"
    )
)