package com.example.myretrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myretrofit.ui.theme.MyRetrofitTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MyRetrofitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }

            getAllCommments()

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyRetrofitTheme {
        Greeting("Android")
    }
}

fun getAllCommments(){
    val BASE_URL = "https://jsonplaceholder.typicode.com/"
    val TAG : String = "Check_Respond"

    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MyApi::class.java)

    api.getComments().enqueue(object : Callback<List<Comments>>{
        override fun onResponse(
            call: Call<List<Comments>>,
            response: Response<List<Comments>>
        ) {
            if (response.isSuccessful){
                response.body()?.let {
                    for (comment in it){
                        Log.i(TAG,"onResponse: ${comment.email}")
                    }
                }
            }
        }

        override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
            Log.i(TAG,"onFailure: ${t.message}")
        }

    })
}