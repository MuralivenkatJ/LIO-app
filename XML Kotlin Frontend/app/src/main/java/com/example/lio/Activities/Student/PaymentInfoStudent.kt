package com.example.lio.Activities.Student

//import android.support.v7.app.AppCompatActivity
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lio.Activities.Explore
import com.example.lio.Helpers.RealPathUtil
import com.example.lio.Helpers.ServiceBuilder
import com.example.lio.Interfaces.Student
import com.example.lio.Models.MessageResponse
import com.example.lio.R
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PaymentInfoStudent : AppCompatActivity()
{
    lateinit var c_id: String

    lateinit var image_uri: Uri
    lateinit var real_path: String

    var accessToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment_info_student)

        var i = intent


        findViewById<TextView>(R.id.c_name).text = i.getStringExtra("c_name")
        findViewById<TextView>(R.id.a_value).text = i.getStringExtra("price")
        findViewById<TextView>(R.id.ac_no).text = i.getStringExtra("ac_no")
        findViewById<TextView>(R.id.ifsc_code).text = i.getStringExtra("ifsc")
        findViewById<TextView>(R.id.ah_name).text =  i.getStringExtra("ac_name")

        c_id = i.getStringExtra("c_id")!!

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


        var choose_image = findViewById<Button>(R.id.choose_image) as Button
        choose_image.setOnClickListener{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                {
                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)
                }
                else
                {
                    pickImageGallery()
                }
            }
            else
            {
                pickImageGallery()
            }
        }
    }

    private  companion object {
        private val IMAGE_REQUEST_CODE = 1000
        private val PERMISSION_CODE = 1001
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

                val context: Context = this
                val realPathObj : RealPathUtil = RealPathUtil()
                real_path = realPathObj.getPath(this, image_uri).toString()
                //Toast.makeText(this,"image uploaded " + real_path + " |",Toast.LENGTH_SHORT).show()

            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode)
        {
            PERMISSION_CODE -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    pickImageGallery()
                }
                else
                {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    fun uploadScreenshot(v: View?)
    {
        if(c_id == null || c_id == "")
        {
            Toast.makeText(this@PaymentInfoStudent, "Erroe : c_id", Toast.LENGTH_LONG).show()
            return
        }

        var utrid = findViewById<EditText>(R.id.utrid).text.toString()
        var auth = "Bearer " + accessToken

        var file : File = File(real_path)
        //Toast.makeText(this,"After File ",Toast.LENGTH_SHORT).show()

        val requestFile = RequestBody.create(okhttp3.MediaType.parse("image/*"), file)
        //Toast.makeText(this,"After requestion ",Toast.LENGTH_SHORT).show()

        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        //Toast.makeText(this,"after Body ",Toast.LENGTH_SHORT).show()

        //data in request body
        var utr: RequestBody = RequestBody.create(MultipartBody.FORM, utrid)
        var c: RequestBody = RequestBody.create(MultipartBody.FORM, c_id)
        var a : RequestBody = RequestBody.create(MultipartBody.FORM, auth)

        val headers: HashMap<String, String> = HashMap()
        headers.put("Authorization", auth)

        var serviceBuilder = ServiceBuilder.buildService(Student::class.java)
        var reqeuestCall = serviceBuilder.uploadScreenshot(headers, c, utr, body)

        reqeuestCall.enqueue(object: Callback<MessageResponse>{
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>)
            {
                Toast.makeText(this@PaymentInfoStudent, response.body()!!.msg, Toast.LENGTH_LONG).show()

                startActivity(Intent(this@PaymentInfoStudent, Explore::class.java))
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable)
            {
                Toast.makeText(this@PaymentInfoStudent, "Error : " + t.message, Toast.LENGTH_LONG).show()
            }

        })
    }


}