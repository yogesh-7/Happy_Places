package com.happyplaces.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.happyplaces.activities.AddHappyPlaceActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

object ImageUtils {

    private const val IMAGE_DIRECTORY = "HappyPlacesImages"

    fun getBitmapFromUri(context: Context, imageUri: Uri): Bitmap {
        val stream = context.contentResolver?.openInputStream(imageUri)
        return BitmapFactory.decodeStream(stream)


    }

    fun saveImageToInternalStorage(context: Context,bitmap: Bitmap): Uri {

        // Get the context wrapper instance
        val wrapper = ContextWrapper(context)

        // Initializing a new file
        // The bellow line return a directory in internal storage
        /**
         * The Mode Private here is
         * File creation mode: the default mode, where the created file can only
         * be accessed by the calling application (or all applications sharing the
         * same user ID).
         */
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)

        // Create a file to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException) { // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image uri
        return Uri.parse(file.absolutePath)
    }


    fun getFileFromUri(context: Context, imageUri: Uri): File {
        val streamInput = context.contentResolver?.openInputStream(imageUri)
        val bitmap= BitmapFactory.decodeStream(streamInput)

        val wrapper = ContextWrapper(context)
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)

        // Mention a file name to save the image
        file = File(file, "${UUID.randomUUID()}.png")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException) { // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image absolute path
       // Timber.d("ImagePath:::: ${file.absolutePath}")

        return file
    }


}