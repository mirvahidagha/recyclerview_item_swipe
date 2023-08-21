/*
 * Copyright 2019 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.bank.actionmode

import android.content.Context
import android.media.Image
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.mlkit.common.MlKitException
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

/**
 * Analyzes the frames passed in from the camera and returns any detected text within the requested
 * crop region.
 */
class TextAnalyzer(
    private val context: Context,
    private val lifecycle: Lifecycle,
    private val imageCropPercentages: MutableLiveData<Pair<Int, Int>>,
      result: (String) -> Unit,

) : ImageAnalysis.Analyzer {

    private val textRecognizer = TextRecognizer(result)

    // Instantiate TextRecognizer detector

//    init {
//        // Add lifecycle observer to properly close ML Kit detectors
//        lifecycle.addObserver(object : DefaultLifecycleObserver {
//            override fun onDestroy(owner: LifecycleOwner) {
//                super.onDestroy(owner)
//               // textRecognizer.close()
//            }
//        })
//
//    }

    @androidx.camera.core.ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val image = imageProxy.image ?: return

        val rotationDegrees = imageProxy.imageInfo.rotationDegrees


        textRecognizer.recognizeText(image, rotationDegrees){
            imageProxy.close()
            Log.d("note", "recognizeText:")
        }
    }


}

class TextRecognizer(val onTextFound: (String) -> Unit) {

    private val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)


    fun recognizeText(image: Image, rotationDegrees: Int, onResult: (Boolean) -> Unit) {
        val inputImage = InputImage.fromMediaImage(image, rotationDegrees)
        val task: Task<Text> = textRecognizer.process(inputImage)
        task.addOnSuccessListener { text ->

            val detectedText = text.text.findPan()
            if (detectedText != null) {
                Log.d("note recognizedText", "$detectedText")
                onTextFound(detectedText)
            }else
            onResult(true)
        }.addOnFailureListener {
            onResult(false)
        }
    }

    private fun String.findPan(): String? {
        val regex = "\\d{4}\\s{0,4}\\d{4}\\s{0,4}\\d{4}\\s{0,4}\\d{4}".toRegex()
        val matchResult = regex.find(this)
        return matchResult?.value
    }

}