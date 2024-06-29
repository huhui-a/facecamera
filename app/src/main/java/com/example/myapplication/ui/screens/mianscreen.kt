package com.example.myapplication.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Picture
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.ui.mainuistate
import com.example.myapplication.ui.mainviewmodel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@Composable
fun mainscreen(
    viewmodel:mainviewmodel= viewModel(),
    uiState: mainuistate,
    contentPadding:PaddingValues=PaddingValues(10.dp),
    modifier: Modifier=Modifier

){
    val context= LocalContext.current
  //  Toast.makeText(context,uiState.toString(), Toast.LENGTH_LONG).show()
    when(uiState){
        is mainuistate.starting-> Startingscreen()
        is mainuistate.loading-> Loadingsreen(modifier = modifier.fillMaxSize())
        is mainuistate.success->Successscreen(
           viewmodel,modifier=modifier.fillMaxSize()
        )
        is mainuistate.error->Errorscreen()
        is mainuistate.processing->Processingscreen(viewmodel)
    }
}

@Composable
fun Loadingsreen(modifier:Modifier=Modifier){
    val context = LocalContext.current

    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(id = R.drawable.loading_img),
        contentDescription = "加载中..."
    )
}

@Composable
fun Successscreen(
    viewmodel: mainviewmodel,
    modifier: Modifier
    ){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = "ssssssssssssssssssssssssssssssssssssssssssssssssssssssss")
        Image(viewmodel.resultImage, contentDescription = "result")
    }
}

@Composable
fun Errorscreen(){
    Text(text = "Errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr")
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Startingscreen() {
   val cameraPermissionSate:PermissionState= rememberPermissionState(permission = android.Manifest.permission.CAMERA)
    val hasPermission=cameraPermissionSate.status.isGranted
    if (hasPermission){
        CameraScreen()
    }
    else{
        NoPermissionScreen(cameraPermissionSate::launchPermissionRequest)
    }
}


@Composable
fun Processingscreen(viewmodel: mainviewmodel){
        Image(
            bitmap = viewmodel.captureimage,
            contentDescription = "picutrecaputured",
            modifier = Modifier.fillMaxSize()
        )
}