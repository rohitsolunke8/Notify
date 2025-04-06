package com.example.notify.ui_layer.signup

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notify.ui_layer.composables.Buttons
import com.example.notify.ui_layer.composables.MakeToast
import com.example.notify.ui_layer.composables.PasswordField
import com.example.notify.ui_layer.composables.SignupActionText
import com.example.notify.ui_layer.composables.ValidatingInputTextField
import com.example.notify.ui_layer.navigation.Login
import com.example.notify.ui_layer.navigation.Note
import com.example.notify.ui_layer.viewmodel.FormViewModel
import com.example.notify.utils.NetworkResult
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpScreen(navController: NavHostController, viewModel: AuthViewModel = hiltViewModel()) {

    val formViewModel: FormViewModel = hiltViewModel()

    val userState by viewModel.newUser.collectAsState()
    val inputFieldState = formViewModel.uiState.collectAsState()

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    when (userState) {

        is NetworkResult.Error -> {
            MakeToast(
                context = context,
                message = (userState as NetworkResult.Error).message
            )
        }

        NetworkResult.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .wrapContentSize()
            )
        }

        is NetworkResult.Success<*> -> {
            navController.navigate(Note)
        }

        else -> {
            NetworkResult.Ideal
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .bringIntoViewRequester(bringIntoViewRequester)
    ) {

        Text(
            text = "Welcome !",
            style = MaterialTheme.typography.displayLarge
        )

        ValidatingInputTextField(
            inputContent = inputFieldState.value.username,

            labelText = "Enter username",
            validatorHasErrors = formViewModel.validator(isLogin = false),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            onValueChange = { formViewModel.updateUsername(it) },
        )

        ValidatingInputTextField(
            inputContent = inputFieldState.value.email,

            labelText = "Enter email",
            validatorHasErrors = formViewModel.validator(isLogin = false),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            onValueChange = { formViewModel.updateEmail(it) }
        )

        PasswordField(
            password = inputFieldState.value.password,
            placeholder = "Enter Password",
            passwordHasError = false,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            modifier = Modifier,
            onValueChange = { formViewModel.updatePassword(it) }
        )

        PasswordField(
            modifier = Modifier
                .bringIntoViewRequester(bringIntoViewRequester)
                .onFocusEvent { focusState ->
                    if (focusState.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            password = inputFieldState.value.confirmedPassword,

            placeholder = "Confirm password",
            passwordHasError = formViewModel.passwordHasError,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            onValueChange = { formViewModel.updateConfirmPassword(it) }
        )

        Text(
            formViewModel.validator(isLogin = false).second,
            color = Color.Red
        )

        HorizontalDivider(
            thickness = 2.dp,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Buttons(
            modifier = Modifier
                .imePadding(),
            contentText = "Sign-Up",
            enabled = formViewModel.validator(),
            onClick = {
                if (!formViewModel.validator(isLogin = false).first) {
                    Toast.makeText(
                        context,
                        formViewModel.validator(isLogin = false).second,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    viewModel.userAuth(formViewModel.getUserRequest())
                }
            },
        )

        SignupActionText(
            onClick = {
                navController.navigate(Login)
            },
            entryText = "Already have an account? ",
            entryOption = "Sign-In"
        )
    }
}

@Preview(showSystemUi = true, device = "spec:parent=pixel_5,navigation=buttons")
@Composable
fun LoginPreview(modifier: Modifier = Modifier) {

}