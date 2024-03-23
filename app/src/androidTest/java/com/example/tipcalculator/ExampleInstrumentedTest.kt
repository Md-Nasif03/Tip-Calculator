package com.example.tipcalculator

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {



    @Test
    fun tip(){
        createComposeRule().setContent {
            TipCalculatorTheme(){
                TipCalculator()
            }
        }
        createComposeRule().onNodeWithText("Total amount")
            .performTextInput("20")
        createComposeRule().onNodeWithText("tip percentage")
            .performTextInput("10")

        createComposeRule().onNodeWithText("Tip amount: $${calculate(10.0,20.0,false)}")
            .assertExists("something is wrong")
    }

}