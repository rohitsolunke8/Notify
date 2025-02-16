package com.example.notify.ui_layer.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.notify.ui_layer.composables.Buttons
import com.example.notify.ui_layer.composables.PasswordField
import com.example.notify.ui_layer.composables.SignupActionText
import com.example.notify.ui_layer.composables.ValidatingInputTextField
import com.example.notify.ui_layer.navigation.Signup
import com.example.notify.ui_layer.viewmodel.FormViewModel
import com.example.notify.utils.NetworkResult

@Composable
fun SigninScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val userAuthState by viewModel.existingUserViewModel.collectAsState()
    val formViewModel: FormViewModel = viewModel()
    val inputFieldState by formViewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Welcome Back !",
            style = MaterialTheme.typography.displayLarge
        )

        ValidatingInputTextField(
            inputContent = inputFieldState.email,
            labelText = "Enter email",
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            onValueChange = { formViewModel.updateUsername(it) },
            validatorHasErrors = formViewModel.validator(),
        )

        PasswordField(
            password = inputFieldState.password,
            placeholder = "Enter Password",
            passwordHasError = false,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            modifier = Modifier,
            onValueChange = { formViewModel.updatePassword(it) }
        )

        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(16.dp))

        Buttons(
            modifier = Modifier,
            contentText = "Sign-In",
            enabled = formViewModel.validator(),
            loading = userAuthState.loading,
            onClick = {
                if (!formViewModel.validator().first) {
                    Toast.makeText(context, formViewModel.validator().second, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    viewModel.loginAuth(formViewModel.getUserRequest())


                }
            },
        )

        SignupActionText(
            onClick = {
                navController.navigate(Signup)
            },
            entryText = "Don't have an account? ",
            entryOption = "Sign-Up"
        )

        when (userAuthState) {

            is NetworkResult.Error -> {
                Toast.makeText(context, userAuthState.message, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }

            is NetworkResult.Idel -> {

            }

            is NetworkResult.Loading -> {

            }

            is NetworkResult.Success -> {

            }

        }
    }
}