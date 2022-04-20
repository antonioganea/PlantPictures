package com.antonioganea.plantpictures


import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment

class PhotoActivity : AppCompatActivity() {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private lateinit var takePhotoBtn: Button
    private lateinit var exportBtn: Button

    private lateinit var ivPhoto: ImageView

    fun getLocalBitmapUri(imageView: ImageView): Uri? {
        if (imageView.drawable !is BitmapDrawable) {
            return null;
        }

        var bmp: Bitmap = (imageView.drawable as BitmapDrawable).bitmap
        var bmpURI: Uri? = null

        try {
            val file = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()

            bmpURI = FileProvider.getUriForFile(this, "com.antonioganea.fileprovider", file);
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bmpURI
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_layout)

        ivPhoto = findViewById(R.id.imageView)

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    handleCameraImage(result.data)
                }
            }

        takePhotoBtn = findViewById(R.id.takePhoto)
        takePhotoBtn.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(cameraIntent)
        }

        exportBtn = findViewById(R.id.shareButton)
        exportBtn.setOnClickListener {
            val bmpURI = getLocalBitmapUri(ivPhoto);
            if (bmpURI != null) {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpURI);
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, "Share Image"));
            }
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.photo

        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.catalog -> {
                    startActivity(Intent(applicationContext, CatalogActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.photo -> return@OnNavigationItemSelectedListener true
            }
            false
        })
    }

    private fun handleCameraImage(intent: Intent?) {
        val bitmap = intent?.extras?.get("data") as Bitmap
        ivPhoto.setImageBitmap(bitmap)
    }
}