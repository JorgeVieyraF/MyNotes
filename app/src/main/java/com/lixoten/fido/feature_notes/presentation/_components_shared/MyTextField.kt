package com.lixoten.fido.feature_notes.presentation._components_shared

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit = { },
    @StringRes labelResId: Int,
    @StringRes placeHolderResId: Int,
    singleLine: Boolean = true,
    error: Boolean = false,
) {
    OutlinedTextField(
        modifier = modifier.padding(4.dp),
        value = value,
        onValueChange = { onValueChange(it) },
        singleLine = singleLine,
        isError = error,
        label = { Text(text = stringResource(labelResId)) },
        placeholder = { Text(text = stringResource(placeHolderResId)) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = if (singleLine) ImeAction.Done else ImeAction.Default
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        ),
    )
}