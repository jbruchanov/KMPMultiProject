package com.example.appproject

import androidx.compose.ui.window.DialogProperties

// useSoftwareKeyboardInset = false: the dialog no longer excludes the IME from its content, so the
// content reads the raw WindowInsets.ime — which is where the stuck value (compose-multiplatform#4618)
// becomes observable after a rotation.
actual fun reproDialogProperties(): DialogProperties =
    DialogProperties(usePlatformDefaultWidth = false, useSoftwareKeyboardInset = false)
