package com.example.lab1

import android.app.ActivityOptions
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory

import com.example.lab1.ui.theme.Lab1Theme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

@OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HorizontalPageView()
        }
    }
}
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
  //  Text(
    //    text = "Hello $name!",
      //  modifier = modifier
//    )
//}
@Composable
fun writeText(text: String){
    Text(text = text)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginWindow(viewModel: UserViewModel) {
    var password by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var isClicked by remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current
    val label = remember{mutableStateOf("")}
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            singleLine = true,
            value = login,
            onValueChange = {
                if (it.length <= 18) {
                    login = it
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.85f)
                .padding(vertical = 8.dp),
            label = { Text("Email") },
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

        TextField(
            value = password,
            onValueChange = {
                if (it.length <= 18) {
                    password = it
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.85f)
                .padding(vertical = 8.dp),
            label = { Text(text="Password") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            visualTransformation = PasswordVisualTransformation(),
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus()  })
        )
        Row {
            Text(text = label.value, color = Color.Cyan)
            Button(
                onClick = {
                    val users = viewModel.allUsers()
                    var isNtInBase = true
                    for (user in users){
                        if (user.userLogin == login && user.userPassword==password){
                            isNtInBase = false
                        }
                    }
                    if (isNtInBase){
                        label.value = "Неверный логин или пароль"
                    } else{
                        isClicked = true
                        label.value = "Приветствую, $login"
                        val another = Intent(context, WeatherActivity::class.java)
                        val options = ActivityOptions.makeBasic()
                        try {
                            startActivity(context,another,options.toBundle())
                        } catch (e : Exception) {print("Error!")}
                    }
                },
                modifier = Modifier.padding(vertical = 16.dp)
            )
            {
                Text("Login")
            }

        }
    }
}

@Composable
fun RegistrationWindow(viewModel: UserViewModel) {
    var password by remember { mutableStateOf("") }
    var confirmpassword by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
   // val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val label = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            singleLine = true,
            value = login,
            onValueChange = {
                if (it.length <= 18) {
                    login = it
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.85f)
                .padding(vertical = 8.dp),
            label = { Text("Login") },
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

        TextField(
            value = password,
            onValueChange = {
                if (it.length <= 18) {
                    password = it
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.85f)
                .padding(vertical = 8.dp),
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            visualTransformation = PasswordVisualTransformation(),
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
            keyboardActions = KeyboardActions(onDone = { focusManager.moveFocus(FocusDirection.Down) })
        )
        TextField(
            value = confirmpassword,
            onValueChange = {
                if (it.length <= 18) {
                    confirmpassword = it
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.85f)
                .padding(vertical = 8.dp),
            label = { Text("Confirm password") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            visualTransformation = PasswordVisualTransformation(),
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )
        Row {
            Text(text = label.value, color = Color.Cyan)
            Button(
                    onClick = {
                        try {
                            if (password == confirmpassword) {
                                val users = viewModel.allUsers()
                                var isNtInBase = true
                                for (user in users) {
                                    if (user.userLogin == login && user.userPassword == password) {
                                        isNtInBase = false
                                    }
                                }
                                if (isNtInBase) {
                                    viewModel.newUserLogin = login
                                    viewModel.newUserPassword = password
                                    viewModel.addUser()
                                    label.value = "Вы зарегистрированы"
                                }
                            }
                        } catch (e : Exception) {print("Error!")}},

                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text("Sign Up")
            }
        }


    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPageView() {
    var currentPage by remember { mutableStateOf(0) } // Изменено значение текущей страницы на 0
    val pagerState = rememberPagerState(pageCount = { 2 })
    val corutineScope = rememberCoroutineScope()
    val viewModel: UserViewModel = viewModel()
    Column {
        TabRow(selectedTabIndex = currentPage) { // Использование TabRow для вкладок
            Tab(
                selected = pagerState.currentPage == 0,
                onClick = {
                    corutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)

            ) {
                Text(text = "Log In")

            }
            Tab(
                selected = pagerState.currentPage == 1,
                onClick = {
                    corutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)


            ) {
                Text(text = "Sign Up")
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) { page: Int ->
            when (page) {
                0 -> {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Ваша фоновая картинка
                        Image(
                            painter = painterResource(id = R.drawable.background4), // Путь к вашей картинке
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds // Масштабирование изображения для заполнения всей области
                        )
                        LoginWindow(viewModel)
                    }
                }

                1 -> {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Ваша фоновая картинка
                        Image(
                            painter = painterResource(id = R.drawable.background4), // Путь к вашей картинке
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds // Масштабирование изображения для заполнения всей области
                        )

                        RegistrationWindow(viewModel)
                    }
                }
            }
        }
    }

}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    Lab1Theme {
//        Greeting("Android")
//    }
//}