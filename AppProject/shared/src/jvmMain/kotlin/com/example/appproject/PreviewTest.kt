package com.example.appproject

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
@Preview
fun PreviewTest() {
    Column {
        repeat(10) {
            Text("$it")
        }
    }
}
