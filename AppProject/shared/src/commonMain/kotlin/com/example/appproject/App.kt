package com.example.appproject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * Minimal reproducer for JetBrains YouTrack CMP-9856 (see also GitHub compose-multiplatform#4618).
 *
 * On iOS, WindowInsets.ime (and thus safeDrawingPadding's bottom) stays stuck at its last positive
 * value after the keyboard is dismissed by a device rotation, instead of settling back to 0. Root
 * cause is in the framework: ComposeSceneKeyboardOffsetManager only recomputes the keyboard overlap
 * on UIKeyboardWillChangeFrameNotification (keyboardWillHide is a no-op) and never re-derives it on
 * an orientation change, so a rotation while the keyboard is visible leaves the inset stale until
 * the next focus/keyboard event.
 *
 * The dialog mirrors the real screen: portrait shows a "map" + text field, landscape falls back to
 * a "Landscape" label (the same height-based pickerFits decision). The single-line "imeBottom:"
 * readout is shown in both orientations.
 *
 * Repro steps (iOS device or simulator, start in portrait):
 *   1. Tap "Open dialog".
 *   2. Tap the text field  ->  keyboard appears  ->  "imeBottom:" goes > 0 (red).
 *   3. Rotate to landscape (shows "Landscape"), then back to portrait — the keyboard was up when
 *      you began rotating.
 *   4. BUG: the keyboard is gone, but "imeBottom:" is still > 0 (stuck, red). Tapping the field
 *      again (fresh focus) resets it to 0.
 *
 * The dialog uses useSoftwareKeyboardInset = false (see [reproDialogProperties]) — that is what
 * makes the dialog content read the raw, un-excluded WindowInsets.ime and therefore observe the
 * stuck value. Does NOT reproduce on Android.
 */
expect fun reproDialogProperties(): DialogProperties

@Composable
fun App() {
    MaterialTheme {
        var showDialog by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier.fillMaxSize().safeDrawingPadding(),
            contentAlignment = Alignment.Center,
        ) {
            Button(onClick = { showDialog = true }) { Text("Open dialog") }
        }
        if (showDialog) {
            LocationPickerRepro(onDismiss = { showDialog = false })
        }
    }
}

@Composable
private fun LocationPickerRepro(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss, properties = reproDialogProperties()) {
        // top-anchored, and the IME is excluded so the keyboard just covers the empty space below —
        // same layout stance as the real LocationPicker dialog
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing.exclude(WindowInsets.ime))
                .padding(16.dp),
        ) {
            Surface(color = MaterialTheme.colors.surface) {
                // same decision as the real picker: portrait is tall enough (>= 520.dp) to show the
                // picker; landscape falls back to a "rotate to portrait" label. Keyed on width so a
                // rising keyboard (which only changes height) never re-decides.
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    val pickerFits = remember(maxWidth) { maxHeight >= 520.dp }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                    ) {
                        var name by remember { mutableStateOf("") }
                        // the raw inset the bug corrupts — read exactly where the real picker reads it,
                        // shown in BOTH orientations, single line
                        val imeBottom = WindowInsets.ime.getBottom(LocalDensity.current)
                        val imeText = @Composable {
                            Text(
                                text = "imeBottom: $imeBottom",
                                color = if (imeBottom > 0) Color(0xFFD32F2F) else Color(0xFF2E7D32),
                                fontFamily = FontFamily.Monospace,
                                maxLines = 1,
                            )
                        }

                        if (pickerFits) {
                            // "map" placeholder — fixed-height Box, no real map needed for the repro
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth().height(260.dp).background(Color(0xFFDDDDDD)),
                            ) {
                                Text("map placeholder (fixed 260.dp)")
                            }

                            imeText()

                            TextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Name") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                TextButton(onClick = onDismiss) { Text("Cancel") }
                                Button(onClick = onDismiss) { Text("OK") }
                            }
                        } else {
                            Text("Landscape")
                            imeText()
                        }
                    }
                }
            }
        }
    }
}
