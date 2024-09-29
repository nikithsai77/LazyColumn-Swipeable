package com.android.swipeable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.swipeable.dup.ContactScreen
import com.android.swipeable.ui.theme.SwipeableTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwipeableTheme {
                ContactScreen()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ContactScreen()
}