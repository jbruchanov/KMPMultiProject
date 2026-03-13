package com.jibru.koolbox.compose.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

/**
 * Controls which layout [FitOrFallbackLayout] picks.
 *
 * @see FitOrFallbackLayout
 */
enum class LayoutPolicy {
    /** Measure [default]; use it if it fits, otherwise fall back. (default behaviour) */
    Auto,

    /** Always use [default], regardless of available space. */
    Default,

    /** Always use [fallback], regardless of available space. */
    Fallback
}

/**
 * A composable that switches between two layouts based on available space,
 * or is forced into one of them via [policy].
 *
 * When [policy] is [LayoutPolicy.Auto] it first measures [default] with
 * unconstrained width.  If that intrinsic size fits the parent's constraints
 * it is placed; otherwise [fallback] is placed instead.
 *
 * [LayoutPolicy.Default] and [LayoutPolicy.Fallback] skip the
 * measurement check entirely and always place the corresponding slot.
 *
 * @param policy   Determines the selection strategy.  Defaults to [LayoutPolicy.Auto].
 * @param modifier Modifier applied to the outer Layout.
 * @param default  The preferred layout, used when it fits the parent (or enforced).
 * @param fallback The alternative layout, used when [default] does not fit (or enforced).
 */
@Composable
fun FitOrFallbackLayout(
    modifier: Modifier = Modifier,
    policy: LayoutPolicy = LayoutPolicy.Auto,
    default: @Composable () -> Unit,
    fallback: @Composable () -> Unit
) {
    // Compose both branches so Compose can track their state/effects.
    // Layout will decide which one to actually place.

    val layoutDecision = remember { mutableStateOf<Pair<Int, Boolean>?>(null) }
    val workaround = false

    Layout(
        modifier = modifier,
        content = {
            // Slot 0 — default
            default()
            // Slot 1 — fallback  (won't be visible unless chosen)
            fallback()
        }
    ) { measurables, constraints ->
        val defaultMeasurable = measurables[0]
        val fallbackMeasurable = measurables[1]

        // Decide which slot wins ─────────────────────────────────────────────
        // We use maxIntrinsicWidth instead of a throwaway measure() because
        // Compose only allows measure() to be called once per Measurable.
        val useDefault: Boolean = when (policy) {
            LayoutPolicy.Default -> true
            LayoutPolicy.Fallback -> false
            LayoutPolicy.Auto -> {
                if (workaround) {
                    // Check if we've already decided for these constraints
                    val cached = layoutDecision.value
                    if (cached != null && cached.first == constraints.maxWidth) {
                        // Use cached decision for same constraints
                        cached.second
                    } else {
                        // New constraints, make fresh decision
                        val neededWidth = defaultMeasurable.maxIntrinsicWidth(constraints.maxHeight)
                        val decision = neededWidth <= constraints.maxWidth
                        layoutDecision.value = constraints.maxWidth to decision
                        decision
                    }
                } else {
                    val neededWidth = defaultMeasurable.maxIntrinsicWidth(constraints.maxHeight)
                    neededWidth <= constraints.maxWidth
                }
            }
        }

        // Place the winner (measure called exactly once) ────────────────────
        val placeable = if (useDefault) {
            defaultMeasurable.measure(constraints)
        } else {
            fallbackMeasurable.measure(constraints)
        }
        println("placeable:${placeable.width}x${placeable.height}")

        layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    }
}
