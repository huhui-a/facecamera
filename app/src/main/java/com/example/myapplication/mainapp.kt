package com.example.myapplication

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.screens.mainscreen
import com.example.myapplication.ui.mainviewmodel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.facetrans


@Composable
fun mainapp(){
    val mainviewmodel: mainviewmodel = viewModel ()
    val context= LocalContext.current
    Scaffold(
        topBar = { topappbar() },
        bottomBar ={ bottombar(mainviewmodel,context) },
    ) {
    //    Toast.makeText(context,mainviewmodel.uiState.toString(), Toast.LENGTH_LONG).show()
        facetrans.loadImages()
        mainscreen(
            viewmodel = mainviewmodel,
            uiState = mainviewmodel.uiState,
            contentPadding=it
        )
    //    testscreen(contentPadding = it)
    }

}



@Composable
fun topappbar(){}


@Composable
fun bottombar(
    viewmodel:mainviewmodel,
    context: Context
){
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(8.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(onClick = { /*TODO*/ }) {
                Text(text = "打开相册")
            }
            IconButton(onClick = { viewmodel.takephoto(context = context) }) {
                Icon(
                    painter = painterResource(id = R.drawable.shoticon),
                    contentDescription = "点击拍照"
                )
            }
            IconButton(onClick = { viewmodel.facedetect(context=context)
            }) {
                Text(text = "人脸检测")
            }
        }
    }
}
