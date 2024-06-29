package com.example.myapplication.model


import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R

import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector


object facetrans {
    lateinit var happyface: ImageBitmap
    lateinit var sadface: ImageBitmap
    lateinit var angryface: ImageBitmap
    lateinit var confusedface: ImageBitmap
    lateinit var cryface: ImageBitmap
    lateinit var gazeface: ImageBitmap
    lateinit var shockedface: ImageBitmap

//    @Composable
//    fun loadImages() {
//        val context = LocalContext.current
//        happyface = loadImage(R.drawable.happy).asImageBitmap
//        confusedface= imageResource(context.resources,R.drawable.confuse).
//        cryface= ImageBitmap.imageResource(context.resources,R.drawable.cry)
//        gazeface= ImageBitmap.imageResource(context.resources,R.drawable.gaze)
//        happyface = ImageBitmap.imageResource(context.resources, R.drawable.happy)
//        sadface = ImageBitmap.imageResource(context.resources, R.drawable.sad)
//        shockedface= ImageBitmap.imageResource(context.resources, R.drawable.shocked)
//
//    }
@Composable
fun loadImages() {
    val context = LocalContext.current
    happyface = loadImage(context, R.drawable.happy)
    sadface = loadImage(context, R.drawable.sad)
    angryface = loadImage(context, R.drawable.angry)
    confusedface = loadImage(context, R.drawable.confuse)
    cryface = loadImage(context, R.drawable.cry)
    gazeface = loadImage(context, R.drawable.gaze)
    shockedface = loadImage(context, R.drawable.shocked)
}

    private fun loadImage(context: android.content.Context, resId: Int): ImageBitmap {
        val bitmap = BitmapFactory.decodeResource(context.resources, resId)
        return bitmap.asImageBitmap()
    }

    fun getEmotionImage(face: Face): ImageBitmap {
        val smilingProbability = face.smilingProbability
        val rightEyeOpenProbability = face.rightEyeOpenProbability
        val leftEyeOpenProbability = face.leftEyeOpenProbability
        val Eyeopen = (leftEyeOpenProbability ?: 0f) + (rightEyeOpenProbability ?: 0f)
        return when {
            smilingProbability != null && smilingProbability > 0.8 -> happyface
            smilingProbability != null && smilingProbability <= 0.4 &&smilingProbability>=0.2 -> sadface
            smilingProbability!=null&&smilingProbability<0.2&&Eyeopen>=1-> angryface
            smilingProbability!=null&&smilingProbability<0.2&&Eyeopen<1-> cryface
            smilingProbability!=null&&smilingProbability<=0.8&&smilingProbability>0.4&&Eyeopen>1.6-> gazeface
            smilingProbability!=null&&smilingProbability<=0.8&&smilingProbability>0.4&&Eyeopen<0.6-> confusedface
            else -> shockedface
        }
    }
}