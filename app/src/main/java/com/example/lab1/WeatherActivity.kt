package com.example.lab1

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.intellij.ide.plugins.tryReadPluginIdsFromFile
//import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.sun.istack.Builder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

const val API_key = "53efbd5f8a3d427d957170701240205"
class WeatherActivity: ComponentActivity() {
    lateinit var courseNameTV: TextView
    lateinit var courseDescTV: TextView
    lateinit var courseReqTV: TextView
    lateinit var courseIV: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainWindow()
        }
    }
  //  @OptIn(ExperimentalGlideComposeApi::class)
    @Preview(widthDp = 70, heightDp = 70, showBackground = true)
    @Composable
    fun MainWindow(){
        var city by remember { mutableStateOf("Kazan") }
        val context = LocalContext.current
        val weatherView = ImageView(context)
        val time = remember {
            mutableStateOf("")
        }
        val curT = remember {
            mutableStateOf("")
        }
        val windDir = remember {
            mutableStateOf("")
        }
        val humidity = remember {
            mutableIntStateOf(0)
        }
        val icon = remember {
            mutableStateOf("http://cdn.weatherapi.com/weather/64x64/day/122.png")
        }
        val bitmap = remember { mutableStateOf(null) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(singleLine = true,value = city, onValueChange = {
                city = it
            },modifier = Modifier
                .fillMaxWidth()
                .alpha(0.85f)
                .padding(vertical = 8.dp),
                label = { Text("City") })
        }
        Row {
            Button(onClick = {
                val value = getData(city)
                city = value.city
                time.value = value.time
                curT.value = value.currentTemp
                windDir.value = value.windDir
                humidity.intValue = value.humidity
                icon.value = value.icon
            })
            {
                Text(text = "Get data")
            }
            Text(text = city, color = Color.Cyan)
            Text(text = time.value, color = Color.Cyan)
            Text(text = curT.value, color = Color.Cyan)
            Text(text = windDir.value, color = Color.Cyan)
            Text(text = humidity.intValue.toString(), color = Color.Cyan)
            /*AsyncImage(
                model = icon.value,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "The delasign logo",
            )*/
            //ImageFromURL(url = icon.value)
        }
    }
/*@Composable
fuImageFromURL(url: String) {
    // on below line we are creating a column,
    Column(
        // in this column we are adding modifier
        // to fill max size, mz height and max width
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
            // on below line we are adding
            // padding from all sides.
            .padding(10.dp),
        // on below line we are adding vertical
        // and horizontal arrangement.
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // on below line we are adding image for our image view.
        Image(
            // on below line we are adding the image url
            // from which we will  be loading our image.
            painter = rememberAsyncImagePainter(url),

            // on below line we are adding content
            // description for our image.
            contentDescription = "gfg image",

            // on below line we are adding modifier for our
            // image as wrap content for height and width.
            modifier = Modifier
                .wrapContentSize()
                .wrapContentHeight()
                .wrapContentWidth()
        )
    }
}*/

    fun getData(city: String): WeatherData{
    val url = "http://api.weatherapi.com/v1/current.json?key=$API_key%20&q=$city&aqi=no"
    var data = WeatherData(
        "",
        "",
        "",
        "",
        0,
        ""
    )
        val queue = Volley.newRequestQueue(application.applicationContext)
        val request = runBlocking { async { JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val city1 = response.getJSONObject("location").getString("name")
                data = WeatherData(
                    city1,
                    response.getJSONObject("location").getString("localtime"),
                    response.getJSONObject("current").getString("temp_c"),
                    response.getJSONObject("current").getString("wind_dir"),
                    response.getJSONObject("current").getInt("humidity"),
                    response.getJSONObject("current").getJSONObject("condition").getString("icon")
                )
            } catch (e: Exception) {
                // on below line we are
                // handling our exception.
                Log.d("mytag", e.toString())
            }

        }, { error ->
            // this method is called when we get
            // any error while fetching data from our API
            Log.e("TAG", "RESPONSE IS $error")
            // in this case we are simply displaying a toast message.
        }) }.await()}
        // at last we are adding
        // our request to our queue.
        queue.add(request)
        return data
}
    @OptIn(DelicateCoroutinesApi::class)
    private fun catchError(response: String):WeatherData{
        val url = runBlocking { async {URL(response)}.await() }
        val data = WeatherData(
            "",
            "",
            "",
            "",
            0,
            ""
        )
        return data
    }
/*private fun getWeatherData(response: String): WeatherData{
    if (response.isEmpty()){
        return WeatherData(
            "",
            "",
            "",
            "",
            0,
            ""
        )
    } else{
        val client = OkHttpClient()

        // Создаем запрос
        val request = Request.Builder()
            .url("https://example.com/api/endpoint")
            .build()

        // Отправляем запрос и получаем ответ
        val response = client.newCall(request).execute()
        val mainObjDef = runBlocking { async { JSONObject(stringBuilder.toString()) } }
        val mainObj = runBlocking { mainObjDef.await() }
        val city = mainObj.getJSONObject("location").getString("name")
        return WeatherData(
            city,
            mainObj.getJSONObject("location").getString("localtime"),
            mainObj.getJSONObject("current").getString("temp_c"),
            mainObj.getJSONObject("current").getString("wind_dir"),
            mainObj.getJSONObject("current").getInt("humidity"),
            mainObj.getJSONObject("current").getJSONObject("condition").getString("icon")
        )
    }
}*/
private fun getWeatherData1(response: String): WeatherData{
    if (response.isEmpty()){
        return WeatherData(
            "",
            "",
            "",
            "",
            0,
            ""
        )
    } else{
        val url = runBlocking { async {URL(response)}.await() }
        val connection =  url.openConnection() as HttpURLConnection
        val inputStream =  /*runBlocking { async { */connection.inputStream// }.await() }
        val bufferedReader = /*runBlocking { async {*/ BufferedReader(InputStreamReader(inputStream))// }.await() }
        val stringBuilder = StringBuilder()
        var line: String? = null
        CoroutineScope(Dispatchers.Main).launch { runBlocking { while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        } } }
        try{
            val mainObj =  JSONObject(stringBuilder.toString())
            val city = mainObj.getJSONObject("location").getString("name")
            return WeatherData(
                city,
                mainObj.getJSONObject("location").getString("localtime"),
                mainObj.getJSONObject("current").getString("temp_c"),
                mainObj.getJSONObject("current").getString("wind_dir"),
                mainObj.getJSONObject("current").getInt("humidity"),
                mainObj.getJSONObject("current").getJSONObject("condition").getString("icon")
            )
        }  catch (e: Exception){
            Log.d("mytag","2 - is problem!!!")
            return WeatherData(
            "none",
            "",
            "",
            "",
            0,
            ""
        )}

    }
}
}