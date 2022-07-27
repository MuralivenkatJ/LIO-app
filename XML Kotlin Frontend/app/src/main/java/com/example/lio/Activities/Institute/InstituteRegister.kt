package com.example.lio.Activities.Institute

//import android.support.v7.app.AppCompatActivity
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
//import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.lio.Activities.Explore
import com.example.lio.Helpers.RealPathUtil
import com.example.lio.Helpers.ServiceBuilder
import com.example.lio.Interfaces.Institute
//import com.example.register.R
//import com.example.register.Services.Institute
//import com.example.register.Services.ServiceBuilder
//import com.example.upload_form.Helpers.RealPathUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import com.example.lio.R

class InstituteRegister : AppCompatActivity() {

      lateinit var image_uri : Uri
    lateinit var real_path : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.institute_register)

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
        val institute_pattern = Regex("[A-Z][a-z]+")
        val address_pattern = Regex("[A-Z][a-z]+")
        val email_pattern = Regex("[a-z0-9]+@[a-z]+(\\.[a-z]+)+")
        val password_pattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,10}$")
        val confirm_pattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{6,10}\$")


        var institute_field = findViewById(R.id.username) as EditText
        var address_field = findViewById(R.id.address) as EditText
        var email_field = findViewById(R.id.emailid) as EditText
        var website_field = findViewById(R.id.website) as EditText
        var bank_field = findViewById(R.id.bankname) as EditText
        var account_field = findViewById(R.id.accountholder) as EditText
        var accountno_field = findViewById(R.id.accountnumber) as EditText
        var ifsc_field = findViewById(R.id.ifsc) as EditText
        var password_field = findViewById(R.id.password) as EditText
        var confirm_field = findViewById(R.id.confirmpassword) as EditText

        var username = institute_field.text.toString().trim()
        var address = address_field.text.toString().trim()
        var email = email_field.text.toString().trim()
        var website = website_field.toString().trim()
        var bank = bank_field.toString().trim()
        var account = account_field.toString().trim()
        var accountno = accountno_field.toString().trim()
        var ifsc = ifsc_field.toString().trim()
        var password = password_field.text.toString().trim()
        var confirmpassword = confirm_field.text.toString().trim()
        var button : Button
        var imageView : ImageView
//        button = findViewById(R.id.choose_image)
        //imageView = findViewById(R.id.choose_image)

//        button.setOnClickListener{
//            pickImageGallery()
//        }





//        Toast.makeText(this, email, Toast.LENGTH_LONG).show()

        if (username.isEmpty())
        {
            Toast.makeText(this,"institute is required", Toast.LENGTH_LONG).show()
            institute_field.error = "this field is required"
            return
        }

        if (address.isEmpty())
        {
            Toast.makeText(this,"address is required", Toast.LENGTH_LONG).show()
            address_field.error = "this field is required"
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

        if (website.isEmpty())
        {
            Toast.makeText(this,"website is required", Toast.LENGTH_LONG).show()
            website_field.error = "this field is required"
            return
        }

        if (bank.isEmpty())
        {
            Toast.makeText(this,"bank is required", Toast.LENGTH_LONG).show()
            bank_field.error = "this field is required"
            return
        }

        if (account.isEmpty())
        {
            Toast.makeText(this,"account is required", Toast.LENGTH_LONG).show()
            account_field.error = "this field is required"
            return
        }

        if (accountno.isEmpty())
        {
            Toast.makeText(this,"account number is required", Toast.LENGTH_LONG).show()
            accountno_field.error = "this field is required"
            return
        }

        if (ifsc.isEmpty())
        {
            Toast.makeText(this,"ifsc is required", Toast.LENGTH_LONG).show()
            ifsc_field.error = "this field is required"
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
        if(!password.equals(confirmpassword))
        {
            confirm_field.error = "This password is not strong"
            Toast.makeText(this, "Password Invalid", Toast.LENGTH_LONG).show()
            return
        }
        registerInstitute(v)
    }

  /*  fun getInstitutes()
    {
        val serviceBuilder = ServiceBuilder.buildService(Institute::class.java)
        val requestCall = serviceBuilder.getInstitutes()

        requestCall.enqueue(object : Callback<List<GetInstitutes>> {
            override fun onResponse(call: Call<List<GetInstitutes>>?, response: Response<List<GetInstitutes>>?)
            {
                if (response != null)
                {
                    if(response.isSuccessful)
                    {
                        var ins : MutableList<String> = mutableListOf()
                        for(insti in response.body())
                            ins.add(insti.i_name)
                        ins.add("None")

                        var institutes = ins.toTypedArray()



                        }

                    }
                }

            }

            override fun onFailure(call: Call<List<GetInstitutes>>?, t: Throwable?)
            {
                Toast.makeText(this@MainActivity, "Unable to get Institutes", Toast.LENGTH_LONG).show()
            }

        })
    }
*/

    fun registerInstitute(v: View?)
    {
        //var f = FacultyRegister()

        var name = findViewById<EditText>(R.id.username).text.toString()
        var email = findViewById<EditText>(R.id.emailid).text.toString()
        var website = findViewById<EditText>(R.id.website).text.toString()
        var accountholder = findViewById<EditText>(R.id.accountholder).text.toString()
        var accountnumber = findViewById<EditText>(R.id.accountnumber).text.toString()
        var ifsc = findViewById<EditText>(R.id.ifsc).text.toString()
        var password = findViewById<EditText>(R.id.password).text.toString()



        var file : File = File(real_path)
        //Toast.makeText(this,"After File ",Toast.LENGTH_SHORT).show()

        val requestFile = RequestBody.create(okhttp3.MediaType.parse("image/*"), file)
        //Toast.makeText(this,"After requestion ",Toast.LENGTH_SHORT).show()

        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        //Toast.makeText(this,"after Body ",Toast.LENGTH_SHORT).show()


        //other data
        //other data
        var n : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, name)
        var e : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, email)
        var w : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, website)
        var ah : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, accountholder)
        var an : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, accountnumber)
        var i : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, ifsc)
        var p : RequestBody = RequestBody.create(okhttp3.MultipartBody.FORM, password)


        val serviceBuilder = ServiceBuilder.buildService(Institute::class.java)
        val requestCall = serviceBuilder.registerFaculty(n,e,w,ah,an,i, p,body)

        requestCall.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>?, response: Response<String>?)
            {
                if (response != null)
                {
                    if(response.isSuccessful)
                    {
                        Toast.makeText(this@InstituteRegister, response.body().toString(), Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@InstituteRegister, Explore::class.java))
                    }
                }
            }

            override fun onFailure(call: Call<String>?, t: Throwable?)
            {
                if (t != null) {
                    Toast.makeText(this@InstituteRegister, "Failed to register the faculty" + t.message.toString(), Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    
}