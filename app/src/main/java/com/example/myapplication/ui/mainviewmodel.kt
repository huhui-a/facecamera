package com.example.myapplication.ui


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.facetrans
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceDetectorOptions.CLASSIFICATION_MODE_ALL


sealed interface mainuistate{
    object success : mainuistate
    object loading: mainuistate
    object error: mainuistate
    object starting: mainuistate
    object processing:mainuistate
}

class mainviewmodel():ViewModel() {
    var uiState: mainuistate by mutableStateOf(mainuistate.starting)
        private set

    private lateinit var cameraController: LifecycleCameraController

    lateinit var captureimage: ImageBitmap
    lateinit var resultImage: ImageBitmap

    val options:FaceDetectorOptions = FaceDetectorOptions.Builder()
        .setClassificationMode(CLASSIFICATION_MODE_ALL)
        .build()
    val detector: com.google.mlkit.vision.face.FaceDetector = FaceDetection.getClient(options)


    fun initialcamera(context: Context,lifecycleOwner: LifecycleOwner){
        cameraController=LifecycleCameraController(context).apply {
            bindToLifecycle(lifecycleOwner)
        }
    }

    fun getcameracontroller()=cameraController

    fun rotateBitmap(source: Bitmap, angle: Int): Bitmap {
        val matrix = android.graphics.Matrix().apply { postRotate(angle.toFloat()) }
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    fun takephoto(context: Context){
        val executor =ContextCompat.getMainExecutor(context)
        cameraController.takePicture(executor,object :ImageCapture.OnImageCapturedCallback(){
            @OptIn(ExperimentalGetImage::class)
            override fun onCaptureSuccess(image:ImageProxy){
                try {
                    val bitmap=image.toBitmap()
                    val robitmap=rotateBitmap(bitmap, 90)
                    captureimage=robitmap.asImageBitmap()

                    uiState=mainuistate.processing
                }finally {
                    image.close()
                }
            }
        })
    }

//    fun textrecongize(context: Context){
//        val recognizer = TextRecognition.getClient(ChineseTextRecognizerOptions.Builder().build())
////        val testimagg=BitmapFactory.decodeResource(context.resources,R.drawable.tsimage2)
////            val image=InputImage.fromBitmap(testimagg,0)
//        val image = InputImage.fromBitmap(captureimage.asAndroidBitmap(), 0)
//            recognizer.process(image)
//                .addOnSuccessListener {
//                        text->
//                    val resulttext:String = text.text
//                    Toast.makeText(context,resulttext, Toast.LENGTH_LONG).show()
//                 // uiState= mainuistate.success()
//                }
//                .addOnFailureListener{
//                        e->
//                    Toast.makeText(context,"error!!!!!!!!!", Toast.LENGTH_LONG).show()
//                    uiState=mainuistate.error
//                }
  //  }



    fun facemotion(face: Face):ImageBitmap{
        return facetrans.getEmotionImage(face)
    }

    fun facedetect(context: Context){
//        val testimagg= BitmapFactory.decodeResource(context.resources, R.drawable.img2)
//        val mutableBitmap = testimagg.copy(Bitmap.Config.ARGB_8888, true)
//        val image=InputImage.fromBitmap(testimagg,0)
        val mutableBitmap = captureimage.asAndroidBitmap().copy(Bitmap.Config.ARGB_8888, true)
       val image=InputImage.fromBitmap(captureimage.asAndroidBitmap(),0)

        detector.process(image)
            .addOnSuccessListener {faces->
                val canvas= android.graphics.Canvas(mutableBitmap)
                val paint=Paint()
                    for (face in faces){
                        val boud=face.boundingBox
                        val repalceface:Bitmap=facemotion(face).asAndroidBitmap()
                        Toast.makeText(context,"识别成功", Toast.LENGTH_LONG).show()
                        canvas.drawBitmap(repalceface,boud.left.toFloat(),boud.top.toFloat(),paint)
                      //  Toast.makeText(context,face.smilingProbability.toString(), Toast.LENGTH_LONG).show()


                    }
                    resultImage = mutableBitmap.asImageBitmap()
                    uiState=mainuistate.success


            }
            .addOnFailureListener{
                Toast.makeText(context,"asdasdasdaaaasssssssssssssssss", Toast.LENGTH_LONG).show()
                uiState=mainuistate.error
            }

    }




}