package com.example.libsproject

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.oldguy.common.io.File
import com.oldguy.common.io.FileMode
import com.oldguy.common.io.ZipFile
import kotlinx.coroutines.launch
import platform.Foundation.NSFileManager
import platform.Foundation.temporaryDirectory

@Composable
actual fun PlatformScreen(modifier: Modifier) {
    Column {
        val scope = rememberCoroutineScope()
        Button(onClick = {
            scope.launch {
                val destinationPath = NSFileManager.defaultManager.temporaryDirectory.path!!
                val zip = File("$destinationPath/test.zip")
                println("zip:$zip exists:${zip.exists}")
                zip.delete()
                val zipFile = ZipFile(zip, FileMode.Write)
                zipFile.close()
                println(zip.path)
            }
        }) {
            Text("ZIP")
        }
    }
}