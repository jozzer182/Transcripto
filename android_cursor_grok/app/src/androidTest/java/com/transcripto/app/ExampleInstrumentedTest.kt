package com.transcripto.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHomeScreenDisplaysTitle() {
        composeTestRule.setContent {
            TranscriptoTheme {
                HomeScreen(viewModel = TranscriptoViewModel(...)) // Mock or provide
            }
        }
        composeTestRule.onNodeWithText("Transcripto").assertIsDisplayed()
    }
}
