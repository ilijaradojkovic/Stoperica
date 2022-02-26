package com.example.counterapp

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope


import com.example.counterapp.ui.theme.CounterAppTheme
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : ComponentActivity() {
    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()

        }

    }
}
@DelicateCoroutinesApi
@Preview
@Composable
fun MainApp(){
    var timerText by remember{ mutableStateOf(0)}
    var timerTextMiliseconds by remember{ mutableStateOf(0)}
    var canStart by remember{ mutableStateOf(true)}
    var clicked by remember{ mutableStateOf(false)}
    var job:Job?=null
    Column(modifier= Modifier
        .fillMaxSize()
        .background(Color.Gray)) {

        Column(modifier=Modifier.weight(2f).fillMaxSize(),verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
            var textT="$timerText"
            var textS="$timerTextMiliseconds"
            if(timerText<=9)  textT="0$timerText"
            if(timerTextMiliseconds<=9)  textS="0$timerTextMiliseconds"
                Text(" $textT:$textS ",color=Color.White,fontSize = 30.sp)
        }
        Column(modifier=Modifier.weight(1f).fillMaxSize(),verticalArrangement = Arrangement.SpaceAround,horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                             if(canStart)
                             {
                                 canStart=false
                                 clicked=true
                                 job= kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
                                     while(clicked) {

                                         delay(10)
                                         if(!canStart){
                                             timerTextMiliseconds++
                                             if(timerTextMiliseconds==60){
                                                 timerText++
                                                 timerTextMiliseconds=0
                                             }
                                         }

                                     }
                                 }
                             }

            },shape = RoundedCornerShape(10.dp),modifier = Modifier.fillMaxWidth(0.5f)){
                Text("Start")
            }
            Button(onClick = {

                job?.cancel()
                canStart=true
                clicked=false

            },shape = RoundedCornerShape(10.dp),modifier=Modifier.fillMaxWidth(0.5f)){
                Text("Stop")
            }
            Button(onClick = {
               if(canStart){
                   timerText=0
                   timerTextMiliseconds=0
               }

            },shape = RoundedCornerShape(10.dp),modifier=Modifier.fillMaxWidth(0.5f)){
                Text("Restart")
            }
        }
        Column(modifier=Modifier.weight(1f).fillMaxSize()) {

        }
    }
}

