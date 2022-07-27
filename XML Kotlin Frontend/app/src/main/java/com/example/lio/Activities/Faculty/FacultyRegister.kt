package com.example.lio.Activities.Faculty

//import android.support.v7.app.AppCompatActivity
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.lio.Activities.Explore
import com.example.lio.Helpers.RealPathUtil
import com.example.lio.Helpers.ServiceBuilder
import com.example.lio.Interfaces.Faculty
import com.example.lio.Models.Institute.GetInstitutes
//import androidx.appcompat.app.AppCompatActivity
//import com.example.facultyregister.Models.GetInstitutes
//import com.example.facultyregister.R
//import com.example.facultyregister.services.Faculty
//import com.example.facultyregister.services.ServiceBuilder
//import com.example.upload_form.Helpers.RealPathUtil
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import com.example.lio.R

class FacultyRegister : AppCompatActivity() {
      lateinit var spinner_value : String
    lateinit var image_uri: Uri
    lateinit var real_path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.faculty_register)

      //to choose image from gallery
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

        getInstitutes()
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


    fun validate(v: View?)
    {
        val username_pattern = Regex("[A-Z][a-z]+")
        val email_pattern = Regex("[a-z0-9]+@[a-z]+(\\.[a-z]+)+")
        val qualification_pattern = Regex("[A-Z][a-z]+")
        val institutename_pattern = Regex("[A-Z][a-z]+")
        val phonenumber_pattern = Regex("[0-9]{10}")
        val password_pattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,10}$")
        val confirm_pattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{6,10}\$")

        var username_field = findViewById<EditText>(R.id.username)
        var email_field = findViewById<EditText>(R.id.emailid)
        var qualification_field = findViewById<EditText>(R.id.qualification)
        //var institutename_field = findViewById(R.id.spinner) as EditText
        var phonenumber_field = findViewById<EditText>(R.id.phno)
        var password_field = findViewById<EditText>(R.id.password)
        var confirm_field = findViewById<EditText>(R.id.confirmpassword)

        var username = username_field.text.toString().trim()
        var email = email_field.text.toString().trim()
        var qualification = qualification_field.text.toString().trim()
        //var institutename = institutename_field.text.toString().trim()
        var phonenumber = phonenumber_field.text.toString().trim()
        var password = password_field.text.toString().trim()
        var confirmpassword = confirm_field.text.toString().trim()

//        Toast.makeText(this, email, Toast.LENGTH_LONG).show()

        if (username.isEmpty())
        {
            Toast.makeText(this,"username required", Toast.LENGTH_LONG).show()
            username_field.error = "this field is required"
            return
        }

        if(email.isEmpty())
        {
            Toast.makeText(this, "Email required", Toast.LENGTH_LONG).show()
            email_field.error = "This field is required"
            return
        }

        if( !email_pattern.matches(email) )
        {
            email_field.error = "This email is not valid"
            Toast.makeText(this, "Email Invalid", Toast.LENGTH_LONG).show()
            return
        }

        if (qualification.isEmpty())
        {
            Toast.makeText(this,"qualification is required", Toast.LENGTH_LONG).show()
            qualification_field.error = "this field is required"
            return
        }

//        if (institutename.isEmpty())
//        {
//            Toast.makeText(this,"institute name is required", Toast.LENGTH_LONG).show()
//            institutename_field.error = "this field is required"
//            return
//        }

        if (phonenumber.isEmpty())
        {
            Toast.makeText(this,"phone number is required", Toast.LENGTH_LONG).show()
            phonenumber_field.error = "this field is required"
            return
        }



        if(password.isEmpty())
        {
            Toast.makeText(this, "Password Required", Toast.LENGTH_LONG).show()
            password_field.error = "This field is required"
            return
        }
        if( !password_pattern.matches(password) )
        {
            password_field.error = "This password is not strong"
            Toast.makeText(this, "Password Invalid", Toast.LENGTH_LONG).show()
            return
        }

        if(confirmpassword.isEmpty())
        {
            Toast.makeText(this, "Password Required", Toast.LENGTH_LONG).show()
            confirm_field.error = "This field is required"
            return
        }
        if( password != confirmpassword )
        {
            confirm_field.error = "This password is not strong"
            Toast.makeText(this, "Password Invalid", Toast.LENGTH_LONG).show()
            return
        }

        registerFaculty(v)
    }


    fun getInstitutes()
    {
        val serviceBuilder = ServiceBuilder.buildService(Faculty::class.java)
        val requestCall = serviceBuilder.getInstitutes()

        requestCall.enqueue(object : Callback<List<GetInstitutes>> {
            override fun onResponse(call: Call<List<GetInstitutes>>?, response: Response<List<GetInstitutes>>?)
            {
                if (response != null)
                {
                    if(response.isSuccessful)
                    {
                        var ins : MutableList<String> = mutableListOf()
                        for(insti in response.body()!!)
                            ins.add(insti.i_name)
                        ins.add("None")

                        var institutes = ins.toTypedArray()

                        //adding institutes to the drop down
                        var spinner = findViewById<Spinner>(R.id.spinner)
                        val adapter = ArrayAdapter(this@FacultyRegister, android.R.layout.simple_spinner_item, institutes)
                        spinner.adapter = adapter

                        spinner.onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long)
                            {
                                spinner_value = institutes[position]
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                //empty
                            }

                        }

                    }
                }

            }

            override fun onFailure(call: Call<List<GetInstitutes>>?, t: Throwable?)
            {
                Toast.makeText(this@FacultyRegister, "Unable to get Institutes", Toast.LENGTH_LONG).show()
            }

        })
    }


    fun registerFaculty(v: View?)
    {
        //var f = FacultyRegister()

        var name = findViewById<EditText>(R.id.username).text.toString()
        var email = findViewById<EditText>(R.id.emailid).text.toString()
        var institute = spinner_value
        var password = findViewById<EditText>(R.id.password).text.toString()
        var qualification = findViewById<EditText>(R.id.qualification).text.toString()
        var phone = findViewById<EditText>(R.id.phno).text.toString()

        var file : File = File(real_path)
        //Toast.makeText(this,"After File ",Toast.LENGTH_SHORT).show()

        val requestFile = RequestBody.create(okhttp3.MediaType.parse("image/*"), file)
        //Toast.makeText(this,"After requestion ",Toast.LENGTH_SHORT).show()

        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        //Toast.makeText(this,"after Body ",Toast.LENGTH_SHORT).show()


        //other data
        var n : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, name)
        var e : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, email)
        var i : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, institute)
        var p : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, password)
        var q : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, qualification)
        var ph : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, phone)


        val serviceBuilder = ServiceBuilder.buildService(Faculty::class.java)
        val requestCall = serviceBuilder.registerFaculty(n, e, i, q, p, ph, body)

        requestCall.enqueue(object : Callback<String>
        {
            override fun onResponse(call: Call<String>?, response: Response<String>?)
            {
                if (response != null)
                {
                    if(response.isSuccessful)
                    {
                        Toast.makeText(this@FacultyRegister, response.body().toString(), Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@FacultyRegister, Explore::class.java))
                    }
                }
            }

            override fun onFailure(call: Call<String>?, t: Throwable?)
            {
                if (t != null) {
                    Toast.makeText(this@FacultyRegister, "Failed to register the faculty" + t.message.toString(), Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    }
