package com.example.appproject

import androidx.compose.ui.window.DialogProperties

// Android has no useSoftwareKeyboardInset knob and no stuck-inset bug; the default properties are fine.
actual fun reproDialogProperties(): DialogProperties =
    DialogProperties(usePlatformDefaultWidth = false)
