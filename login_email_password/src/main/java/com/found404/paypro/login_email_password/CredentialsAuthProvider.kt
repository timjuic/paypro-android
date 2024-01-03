package com.found404.paypro.login_email_password


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.found404.core.exceptions.ServerUnreachableException
import com.found404.core.AuthCallbacks
import com.found404.core.AuthModule
import com.found404.core.AuthProviderClickListener
import com.found404.core.models.LoginCredentials
import com.found404.core.models.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class CredentialsAuthProvider(private val baseUrl: String) : AuthModule<LoginCredentials, LoginResponse> {
    private val gson = Gson()
    private val client = OkHttpClient()

    override suspend fun loginUser(
        endpointPath: String,
        loginCredentials: LoginCredentials,
        callbacks: AuthCallbacks<LoginResponse>
    ) {
        val requestBody = gson.toJson(
            mapOf(
                "emailAddress" to loginCredentials.email,
                "password" to LoginUtils.hashPassword(loginCredentials.password)
            )
        ).toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("${baseUrl}${endpointPath}")
            .post(requestBody)
            .build()

        withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                val loginResponse = gson.fromJson(responseBody, LoginResponse::class.java)

                if (loginResponse.success) {
                    callbacks.onSuccessfulLogin(loginResponse)
                } else {
                    callbacks.onFailedLogin(loginResponse)
                }
            } catch (e: Exception) {
                callbacks.onServerUnreachable(ServerUnreachableException("Server couldn't be reached! Please try again later."))
            }
        }
    }

    @Composable
    override fun DisplayButton() {
        val customFontFamily = FontFamily(
            Font(R.font.montserrat_bold, FontWeight.Bold),
        )

        OutlinedButton(
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(2.dp, Color.Black),
            shape = RoundedCornerShape(15.dp)
        ) {

//                leadingIcon?.let {
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_google),
//                        contentDescription = null,
//                        modifier = Modifier.padding(end = 12.dp),
//                    )
//                }

            Text(
                text = "Login with email",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = customFontFamily
            )
        }

        Button(onClick = {

        }) {
            Text("Credentials Auth")
        }
    }
}