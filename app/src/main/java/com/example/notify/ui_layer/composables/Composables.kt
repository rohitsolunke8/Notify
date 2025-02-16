package com.example.notify.ui_layer.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notify.ui_layer.viewmodel.FormViewModel

@Composable
fun ValidatingInputTextField(
    inputContent: String,
    labelText: String,
    validatorHasErrors: Pair<Boolean, String>,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    onValueChange: (String) -> Unit,
) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
//            .padding(10.dp),
        value = inputContent,
        onValueChange = onValueChange,
        label = { Text(labelText) },
        isError = validatorHasErrors.first,
        supportingText = {
            if (validatorHasErrors.first) {
                Text(validatorHasErrors.second)
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Composable
fun PasswordField(
    modifier: Modifier,
    password: String,
    placeholder: String,
    passwordHasError: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    formViewModel: FormViewModel = hiltViewModel()
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onValueChange,
        label = { Text(placeholder) },
        isError = passwordHasError,
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                if (passwordVisibility) {
                    Icon(Icons.Outlined.Visibility, "Password visible")
                } else {
                    Icon(Icons.Outlined.VisibilityOff, "Visibility Off")
                }
            }
        },

        visualTransformation = if (passwordVisibility) VisualTransformation.None
        else PasswordVisualTransformation(),
        supportingText = {
            if (passwordHasError) {
                Text("Password mismatch")
            }
        },

        modifier = Modifier
            .fillMaxWidth(),
//            .padding(10.dp),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Composable
fun Buttons(
    onClick: () -> Unit,
    modifier: Modifier,
    contentText: String,
    enabled: Pair<Boolean, String>,
    loading: Boolean
) {
    Button(
        onClick = onClick,
        enabled = true,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        if (loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .size(30.dp)
            )
        } else {
            Text(
                text = contentText,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}


@Composable
fun SignupActionText(
    onClick: () -> Unit,
    entryText: String,
    entryOption: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextButton(
            onClick = onClick
        ) {
            Text(
                text = entryText,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = entryOption,
                color = Color.Blue
            )
        }
    }
}

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    onClick: () -> Unit
) {
    IconButton(
        onClick = {
            useDarkTheme != useDarkTheme
        },
        modifier = Modifier,
    ) {
        if (useDarkTheme) {
            Icon(Icons.Outlined.DarkMode, contentDescription = "Dark mode toggle")
        } else {
            Icon(Icons.Outlined.LightMode, contentDescription = "Light mode toggle")
        }
    }
}

@Composable
fun MakeToast(
    context: Context,
    message: String
) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}



