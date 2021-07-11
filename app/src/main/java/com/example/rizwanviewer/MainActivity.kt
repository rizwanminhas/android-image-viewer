package com.example.rizwanviewer

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    // A simple app to input user data and print it
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }
//
//    fun onRegister(view: View) {
//        val firstName: EditText = findViewById(R.id.firstName)
//        val lastName: EditText = findViewById(R.id.lastName)
//        val email: EditText = findViewById(R.id.email)
//
//        val result: TextView = findViewById(R.id.result)
//        println(result)
//        result.setText("firstName=" + firstName.text.toString() +"\nlastName=" + lastName.text.toString() + "\nemail=" + email.text.toString())
//    }

    private val PERMISSION_REQUEST_CODE = 200
    private var imagePaths: ArrayList<String> = ArrayList<String>()
    private var imagesRV: RecyclerView? = null
    private var imageRVAdapter: RecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissions()

        imagePaths = ArrayList()
        imagesRV = findViewById(R.id.idRVImages)

        prepareRecyclerView()
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        if (checkPermission()) {
            Toast.makeText(this, "Permissions granted..", Toast.LENGTH_SHORT).show()
            getImagePath()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun prepareRecyclerView() {
        imageRVAdapter = RecyclerViewAdapter(this, imagePaths)

        val manager = GridLayoutManager(this, 4)

        imagesRV!!.layoutManager = manager
        imagesRV!!.adapter = imageRVAdapter
    }

    private fun getImagePath() {
        val isSDPresent = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        if (isSDPresent) {
            val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)

            val orderBy = MediaStore.Images.Media._ID

            val cursor: Cursor? = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns,
                null,
                null,
                orderBy
            )

            val count: Int = cursor!!.getCount()

            for (i in 0 until count) {
                cursor.moveToPosition(i)

                val dataColumnIndex: Int = cursor!!.getColumnIndex(MediaStore.Images.Media.DATA)

                imagePaths!!.add(cursor!!.getString(dataColumnIndex))
            }
            imageRVAdapter?.notifyDataSetChanged()
            cursor.close()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE ->
                if (grantResults.size > 0) {
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (storageAccepted) {
                        Toast.makeText(this, "Permissions Granted..", Toast.LENGTH_SHORT).show()
                        getImagePath()
                    } else {
                        Toast.makeText(
                            this,
                            "Permissions denined, Permissions are required to use the app..",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}