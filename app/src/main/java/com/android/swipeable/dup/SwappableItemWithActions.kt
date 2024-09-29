package com.android.swipeable.dup

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwappableItemWithActions(
    contactUi: ContactUi,
    content: @Composable () -> Unit,
    actions: @Composable () -> Unit
) {
    var contextMenuWidth = 0f

    val offset = remember {
        Animatable(initialValue = 0f)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = contactUi) {
        offset.animateTo(targetValue = 0f)
    }

    Box(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.onSizeChanged {
                    contextMenuWidth = it.width.toFloat()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            actions()
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offset.value.roundToInt(), 0) }
                .pointerInput(key1 = true) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset =
                                    (offset.value + dragAmount).coerceIn(0f, contextMenuWidth)
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            when {
                                offset.value >= contextMenuWidth / 2f -> {
                                    scope.launch {
                                        offset.animateTo(targetValue = contextMenuWidth)
                                    }
                                }

                                else -> {
                                    scope.launch {
                                        offset.animateTo(targetValue = 0f)
                                    }
                                }
                            }
                        }
                    )
                }
        ) {
            content()
        }
    }
}