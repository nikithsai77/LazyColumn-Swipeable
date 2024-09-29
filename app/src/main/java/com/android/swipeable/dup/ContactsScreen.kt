package com.android.swipeable.dup

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ContactScreen() {
    val context = LocalContext.current
    val contacts = remember {
        mutableStateListOf<ContactUi>()
    }
    if (contacts.isEmpty()) {
        for (i in 1..100) {
            contacts.add(ContactUi(id = i, name = "Contact $i", isOptionsRevealed = false))
        }
    }
    LazyColumn {
        itemsIndexed(
            items = contacts
        ) { index, contact ->
            SwappableItemWithActions(
                contactUi = contact,
                content = {
                    Text(
                        text = "Contact ${contact.id}",
                        modifier = Modifier.padding(8.dp)
                    )
                },
                actions = {
                    ActionIcon(
                        onClick = {
                            Toast.makeText(
                                context,
                                "Contact ${contact.id} was deleted",
                                Toast.LENGTH_SHORT
                            ).show()
                            contacts.remove(contact)
                        },
                        backgroundColor = Color.Red,
                        icon = Icons.Default.Delete,
                        modifier = Modifier.fillMaxHeight()
                    )
                    ActionIcon(
                        onClick = {
                            Toast.makeText(
                                context,
                                "Contact ${contact.id} was sent an email",
                                Toast.LENGTH_SHORT
                            ).show()
                            contacts[index] =  contact.copy(isOptionsRevealed = !contact.isOptionsRevealed)
                        },
                        backgroundColor = Color.Yellow,
                        icon = Icons.Default.Email,
                        modifier = Modifier.fillMaxHeight()
                    )
                    ActionIcon(
                        onClick = {
                            contacts[index] = contact.copy(isOptionsRevealed = !contact.isOptionsRevealed)
                            Toast.makeText(
                                context,
                                "Contact ${contact.id} was shared",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        backgroundColor = Color.Magenta,
                        icon = Icons.Default.Share,
                        modifier = Modifier.fillMaxHeight()
                    )
                },
            )
        }
    }
}