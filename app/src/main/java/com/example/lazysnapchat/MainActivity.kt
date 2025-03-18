package com.example.lazysnapchat

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val REQUEST_OVERLAY_PERMISSION = 1001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the app has permission to draw overlays (required for automation)
        if (!Settings.canDrawOverlays(this)) {
            // If not, request the permission
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION)
        } else {
            // Permission is already granted, start the automation service
            startAutomationService()
        }

        // Optionally, close the activity if no UI is needed
        finish()
    }

    // Handle the result when the user allows or denies the overlay permission
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
            if (Settings.canDrawOverlays(this)) {
                // Permission granted, start the automation service
                startAutomationService()
            } else {
                // Permission denied, show a message
                Toast.makeText(this, "Overlay permission is required for automation", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startAutomationService() {
        // Start the automation service
        val intent = Intent(this, AutomationService::class.java)
        startService(intent)
    }
}

