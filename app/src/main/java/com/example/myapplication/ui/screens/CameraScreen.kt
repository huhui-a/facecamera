package com.example.myapplication.ui.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.mainviewmodel

@Composable
fun CameraScreen(viewmodel:mainviewmodel=viewModel()){
    val context= LocalContext.current
    val lifecycleOwner= LocalLifecycleOwner.current
//    LaunchedEffect(true) {
//    viewmodel.initialcamera(context,lifecycleOwner)
//    }
    CameraContent(viewmodel)
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun CameraContent(viewmodel:mainviewmodel){
    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current
   // val cameraController = remember { LifecycleCameraController(context)}
    viewmodel.initialcamera(context, lifeCycleOwner )
    val cameraController=viewmodel.getcameracontroller()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ){
        paddingValues : PaddingValues ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            factory = { context ->
            PreviewView(context).apply {
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                setBackgroundColor(Color.BLACK)
                scaleType = PreviewView.ScaleType.FILL_START
            }.also{ previewView ->
                previewView.controller = cameraController
              //  cameraController.bindToLifecycle(lifeCycleOwner)
            }
        })
        
    }
}

