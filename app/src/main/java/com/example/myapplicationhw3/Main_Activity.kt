package com.example.myapplicationhw3

    import android.R.attr.password
    import android.content.Intent
    import android.content.SharedPreferences
    import android.os.Bundle
    import android.text.TextUtils
    import android.util.Log
    import android.view.View
    import android.widget.Button
    import android.widget.EditText
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.content.edit
    import com.google.android.gms.tasks.OnCompleteListener
    import com.google.firebase.auth.AuthResult
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.FirebaseUser
    import com.google.firebase.auth.UserProfileChangeRequest


    class Main_Activity : AppCompatActivity() {
        lateinit var mNameField : EditText
        lateinit var mEmailField : EditText
        lateinit var mPassword : EditText

        lateinit var mPreferences: SharedPreferences
        var sharedPrefFile : String = "com.example.myapplicationhw3"

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.mainactivity)

            mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE)

            mNameField = findViewById(R.id.editTextTextPersonName)
            mEmailField = findViewById(R.id.editTextEmail)
            mPassword = findViewById(R.id.editTextPassword)

            mNameField.setText(mPreferences.getString("name", "name"))
            mEmailField.setText(mPreferences.getString("email", "email"))
            mPassword.setText(mPreferences.getString("password", "password"))
        }

        fun logIn(view: View?) {
            Log.d("FIREBASE", "click")
            signIn()
        }

        fun ZListBtn(view: View?) {
            val intent = Intent(this, ZombieMovies::class.java)
            startActivity(intent)
        }


        fun CameraBtn(view: View?) {
            val intent = Intent(this, Cameras::class.java)
            startActivity(intent)
        }

        fun MapBtn(view: View?) {
            val intent = Intent(this, CameraMap::class.java)
            startActivity(intent)
        }


        fun validateLogin (name: String, email : String, password: String): Boolean {
            var validated = true
            if (TextUtils.isEmpty(email)) {
                mEmailField.error = "Email Required"
                validated = false
            } else {
                mEmailField.error = null
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.error = "Password Required"
                validated = false
            } else {
                mPassword.error = null
            }
            if (TextUtils.isEmpty(name)) {
                mNameField.error = "Username Required"
                validated = false
            } else {
                mNameField.error = null
            }
            return validated
        }

        private fun signIn() {
            Log.d("FIREBASE", "signIn")

            // 1 - validate name, email, and password entries
            var name : String = mNameField.text.toString()
            var email : String = mEmailField.text.toString()
            var password : String = mPassword.text.toString()

            if (!validateLogin(name, email, password)) {
                return
            }
            // 2 - save valid entries to shared preferences

            mPreferences.edit(true) { putString("name", name)}
            mPreferences.edit(true) { putString("email", email)}
            mPreferences.edit(true) { putString("password", password)}

            // 3 - sign into Firebase
            val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    Log.d("FIREBASE", "signIn:onComplete:" + task.isSuccessful)
                    if (task.isSuccessful) {
                        // update profile
                        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
                        val profileUpdates: UserProfileChangeRequest =
                            UserProfileChangeRequest.Builder()
                                .setDisplayName(mNameField.text.toString())
                                .build()
                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("FIREBASE", "User profile updated.")
                                    // Go to FirebaseActivity
                                    startActivity(
                                        Intent(
                                            this@Main_Activity,
                                            FirebaseActivity::class.java
                                        )
                                    )
                                }
                            }
                    } else {
                        Log.d("FIREBASE", "sign-in failed")
                        Toast.makeText(
                            this@Main_Activity, "Sign In Failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }






    }
