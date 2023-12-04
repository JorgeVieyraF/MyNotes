package com.lixoten.fido.feature_notes.presentation._components_shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lixoten.fido.R
import com.lixoten.fido.ui.theme.Violet

@Composable
fun MyBottomBar(
    isPinned: Boolean = false,
    onPinRecord: () -> Unit = { },
    isChecked: Boolean = false,
    hasDelete: Boolean = false,
    onDeleteClick:  () -> Unit = { },
    onCheckedChange: () -> Unit = { }
) {
    BottomAppBar(
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onPinRecord
                ) {
                    Icon(
                        painter = if (isPinned) painterResource(id = R.drawable.ic_pin_filled)
                        else painterResource(id = R.drawable.ic_pin),
                        contentDescription = "pin",
                        tint = Violet,
                        modifier = Modifier.size(24.dp),
                    )
                }
                if (hasDelete) {
                    IconButton(
                        onClick = onDeleteClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.LightGray
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Status :")
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = {onCheckedChange()
                        }
                    )
                }
            }
        },
    )
}
