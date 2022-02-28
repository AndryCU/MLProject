package com.example.myapplication
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
             MyText()
        }
    }
}

@Composable
fun MyImage(){
    Image(painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "")
}

@Composable
fun MyText(){
    Column {
        Text("Hola Jetpack Composeeeee")
        Text( "Preparado?")
        MyImage()
    }
}

@Preview
@Composable
fun PreviewText(){
    MyText()
}
