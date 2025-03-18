package com.example.lazysnapchat

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiObjectNotFoundException
import androidx.test.uiautomator.UiSelector
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SnapchatAutomatorTest {

    @Test
    @Throws(UiObjectNotFoundException::class)
    fun swipeUpAndOpenSnapchat() {
        // Get the context and device
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()
        device.waitForIdle()
        // Swipe up to open the app drawer
        device.swipe(500, 1500, 500, 500, 10) // Modify coordinates as per your device

        // Wait for the app drawer to open
        device.waitForIdle()

        // Create a UiSelector for the Snapchat app
        val snapchatSelector = UiSelector().text("Snapchat")

        // Scroll down manually to find Snapchat
        var found = false
        while (!found) {
            val snapchatApp = UiObject(snapchatSelector)
            if (snapchatApp.exists()) {
                snapchatApp.click()
                found = true
            } else {
                // Scroll down
                device.swipe(500, 1500, 500, 750, 10) // Swipe from middle to bottom to scroll down
                device.waitForIdle() // Wait for the scroll action to complete
            }
        }
        Thread.sleep(2000)
        device.click(340,2122)
        Thread.sleep(1000)
        device.click(224,366)
        Thread.sleep(1000)
        handleNewSnaps()

        device.click(540,2122)
        Thread.sleep(1000)
        device.click(540,1900)
        Thread.sleep(1000)
        val sendselector = UiSelector().textContains("Send To")
        val sendSnapObject = UiObject(sendselector)
        sendSnapObject.click()
    }
}

fun handleNewSnaps() {
    // Get the context and device
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    // Scroll through the app drawer or feed
    var scrollcount=0
    while (scrollcount<3) {
        var foundNewSnap = false

        // Look for "New Snap"
        val newSnapSelector = UiSelector().textContains("New Snap")
        val newSnapObject = UiObject(newSnapSelector)

        // Check for existence of "New Snap"
        if (newSnapObject.exists()) {
            foundNewSnap = true
            // Click on "New Snap"
            newSnapObject.click()

            // Wait for 1 second
            Thread.sleep(1000)

            // Click in the center of the screen
            val displaySize = device.displayWidth to device.displayHeight
            device.click(displaySize.first / 2, displaySize.second / 2)

            // Wait for the action to complete
            device.waitForIdle()
        }

        // If no "New Snap" was found, scroll down and continue
        if (!foundNewSnap) {
            device.swipe(500, 1500, 500, 1000, 10) // Adjust swipe coordinates as needed
            device.waitForIdle() // Wait for the scroll action to complete
            scrollcount++
        }

    }
}