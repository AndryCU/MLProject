package com.example.myapplication

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.ml.Modelo
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.*


class MainActivity : ComponentActivity() {

    private val cameraLauncher = registerForActivityResult(StartActivityForResult()) { photoFile ->
        if (photoFile.resultCode == RESULT_OK) {
            val p = photoFile.data!!.extras!!.get("data") as Bitmap
            var tensorImage = TensorImage(DataType.FLOAT32)
              tensorImage.load(p)
            val model = Modelo.newInstance(this)
            val imageProcessor: ImageProcessor = ImageProcessor.Builder()
                .add(ResizeOp(150, 150, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                .add(TransformToGrayscaleOp())
                .add(NormalizeOp(0f,255.0f))
                .build()
            tensorImage=imageProcessor.process(tensorImage)

            val outputs: Modelo.Outputs = model.process(tensorImage.tensorBuffer)
            val  outputFeature0 = outputs.outputFeature0AsTensorBuffer
            Log.d("PROBABILIDAD", outputFeature0.floatArray[0].toString())
            Toast.makeText(applicationContext, "Mas a 0, gato, mas a 1 perro ${outputFeature0.floatArray[0]}", Toast.LENGTH_LONG)
                .show()
            model.close()
        } else {
            Toast.makeText(applicationContext, "Fallo!", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IconButton(onClick = {
                cameraLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
            }) {
                Icon(imageVector = Icons.Default.Phone, contentDescription = "")
            }
        }
    }


    @Composable
    fun MyImage() {
        Image(
            painterResource(id = R.drawable.imagen), contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )
    }


}
