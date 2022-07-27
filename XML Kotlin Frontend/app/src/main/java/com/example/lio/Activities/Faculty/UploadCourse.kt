package com.example.lio.Activities.Faculty

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.*
import com.example.lio.Activities.BaseDrawer
import com.example.lio.Activities.Explore
import com.example.lio.Helpers.RealPathUtil
import com.example.lio.Helpers.ServiceBuilder
import com.example.lio.Interfaces.CourseInterface
import com.example.lio.Models.MessageResponse
import com.example.lio.R
import com.example.lio.databinding.UploadCourseBinding
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadCourse : BaseDrawer()
{

    //for menu bar
    lateinit var binding: UploadCourseBinding

        lateinit var title : EditText
        lateinit var choose_image : Button
        lateinit var specialization : EditText
        lateinit var playlist_id : EditText
        lateinit var spinner : Spinner
        lateinit var description : EditText
        lateinit var skills : EditText
        lateinit var price : EditText

        lateinit var spinner_value: String
        lateinit var image_uri : Uri
        lateinit var filePath : String
        lateinit var real_path : String


    var accessToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //for menu bar
        binding = UploadCourseBinding.inflate(layoutInflater)
        allocateActivityTitle("Upload Course")
        setContentView(R.layout.upload_course)
        //for menu bar

            title = findViewById(R.id.title1) as EditText
            choose_image = findViewById(R.id.choose_image) as Button
            specialization = findViewById(R.id.specialization) as EditText
            playlist_id = findViewById(R.id.playlist_id) as EditText
            spinner = findViewById(R.id.spinner) as Spinner
            description = findViewById(R.id.description) as EditText
            skills = findViewById(R.id.skills) as EditText
            price = findViewById(R.id.price) as EditText


        //getting the accessToken
        var shrdPref: SharedPreferences = getSharedPreferences("login_credentials", MODE_PRIVATE)
        accessToken = shrdPref.getString("accessToken", "None")
        if(accessToken == "None" || accessToken == null)
        {
            var editor: SharedPreferences.Editor = shrdPref.edit()
            editor.clear()
            editor.apply()

            Toast.makeText(this, "You have to log in", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, Explore::class.java))
            return
        }


            val level = arrayOf("Beginner", "Intermediate", "Advanced")

            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, level)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    v: View?,
                    position: Int,
                    id: Long
                ) {
                    spinner_value = level[position]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //empty
                }
            }


            //choose image
            val choose_image = findViewById<Button>(R.id.choose_image)
            choose_image.setOnClickListener {
                pickImageGallery()
            }

            val submit = findViewById<Button>(R.id.delete_btn)
            submit.setOnClickListener {
                uploadCourse()
            }
        }

        companion object {
            private val IMAGE_REQUEST_CODE = 1000;
            private val PICK_IMAGE = 100;
            //private val PERMISSION_CODE = 1001;
        }


        private fun pickImageGallery() {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "image"), 10)

        }



        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if(requestCode == 10 && resultCode == Activity.RESULT_OK)
            {

                if (data != null) {
                    image_uri = data.data!!
                    //            val filePathColumn = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
                    //            val cursor = contentResolver.query(image_uri, filePathColumn, null, null, null)
                    //                ?: return
                    //
                    //            cursor.moveToFirst()
                    //
                    //            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    //             filePath = cursor.getString(columnIndex)
                    //            cursor.close()

                    val context: Context = this@UploadCourse
                    val realPathObj : RealPathUtil = RealPathUtil()
                    real_path = realPathObj.getPath(this, image_uri).toString()
                    //Toast.makeText(this,"image uploaded " + real_path + " |", Toast.LENGTH_SHORT).show()

                    //identifier.toString()
                }
            }
        }

        fun uploadCourse()
        {
            var name = title.text.toString()
            var desc = description.text.toString()
            var spec = specialization.text.toString()
            var price = price.text.toString()
            val guided_project : Boolean
            var level = spinner_value
            var playlist=playlist_id.text.toString()
            var skills=skills.text.toString()


            //Radio button data
            val selectedOption : Int = findViewById<RadioGroup>(R.id.radioGroup)!!.checkedRadioButtonId
            var selectedBtn = findViewById<RadioButton>(selectedOption)
            if(selectedBtn.text.toString() == "Course")
                guided_project = false
            else
                guided_project = true

            val path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM
            )

            var file : File = File(real_path)
            //Toast.makeText(this,"After File ", Toast.LENGTH_SHORT).show()
            //var requestFile : RequestBody = RequestBody.create(MediaType.parse("mulitpart/form-data"), file)



            //val file: File = File(filePath)

            val requestFile = RequestBody.create(
                okhttp3.MediaType.parse("image/*"), file
            )
            //Toast.makeText(this,"After requestion ", Toast.LENGTH_SHORT).show()

            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
            //Toast.makeText(this,"after Body ", Toast.LENGTH_SHORT).show()


            //other data
            var n : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, name)
            var d : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, desc)
            var s : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, spec)
            var p : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, price)
            var l : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, level)
            var g : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, guided_project.toString())
            var pl : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, playlist)
            var sk : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, skills)

            var headers: HashMap<String, String> = HashMap()
            if(accessToken != null)
                headers.put("Authorization", "Bearer " + accessToken)

            var serviceBuilder = ServiceBuilder.buildService(CourseInterface::class.java)
            var requestCall = serviceBuilder.uploadCourse(headers, n, d, s, p, l, g, pl, sk,body)


            requestCall.enqueue(object : Callback<MessageResponse>
            {
                override fun onResponse(call: Call<MessageResponse>?, response: Response<MessageResponse>?) {
                    if (response != null) {
                        Toast.makeText(this@UploadCourse, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@UploadCourse, MyCoursesFaculty::class.java))
                    }
                }

                override fun onFailure(call: Call<MessageResponse>?, t: Throwable?) {
                    if (t != null) {
                        Toast.makeText(this@UploadCourse, "Error : " + t.message, Toast.LENGTH_SHORT).show()
                    }
                }

            })


        }
    }


