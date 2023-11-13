package com.douglaszucolotto.passwordstrengthcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.douglaszucolotto.passwordstrengthcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    enum class StrengthLevel {
        FRACO,
        MEDIO,
        FORTE,
        MUITO_FORTE
    }

    private val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        // adiciona um listener de alteração de texto no input de senha
        mainBinding.edPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(password: CharSequence, p1: Int, p2: Int, p3: Int) {
                // Remove espaço
                if (password.trim().isNotEmpty()) {

                    // Verifica se tem letras minúsculas
                    val hasLowerCase = password.any { it.isLowerCase() }
                    uptadeStatusUI(hasLowerCase, mainBinding.lowerCaseImg, mainBinding.lowerCaseTxt)

                    // Verifica se tem letras maiúsculas
                    val hasUpperCase = password.any { it.isUpperCase() }
                    uptadeStatusUI(hasUpperCase, mainBinding.upperCaseImg, mainBinding.upperCaseTxt)

                    // Verifica se tem dígitos
                    val hasDigit = password.any { it.isDigit() }
                    uptadeStatusUI(hasDigit, mainBinding.digitImg, mainBinding.digitTxt)

                    // Verifica se tem caracteres especiais
                    val hasSpecialChar =
                        password.contains(Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\",./<>?\\\\|]"))
                    uptadeStatusUI(
                        hasSpecialChar, mainBinding.specialCharImg, mainBinding.specialCharTxt
                    )


                    calculateStrength(
                        password, hasLowerCase, hasUpperCase, hasDigit, hasSpecialChar
                    )
                } else {
                    mainBinding.strengthLevelTxt.text = ""
                    mainBinding.strengthLevelTxt.setTextColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.darkGray
                        )
                    )
                    mainBinding.strengthLevelInd.setBackgroundColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.darkGray
                        )
                    )

                    uptadeStatusUI(false, mainBinding.lowerCaseImg, mainBinding.lowerCaseTxt)
                    uptadeStatusUI(false, mainBinding.upperCaseImg, mainBinding.upperCaseTxt)
                    uptadeStatusUI(false, mainBinding.digitImg, mainBinding.digitTxt)
                    uptadeStatusUI(
                        false,
                        mainBinding.specialCharImg,
                        mainBinding.specialCharTxt
                    )
                }
            }

            override fun afterTextChanged(password: Editable) {}
        })

        mainBinding.loginBtn.setOnClickListener {
            Toast.makeText(this, "Sucesso!", Toast.LENGTH_LONG).show()
        }

    }

    private fun displayStrengthLevel(strengthLevel: StrengthLevel, strengthColor: Int) {
        mainBinding.loginBtn.isEnabled =
            StrengthLevel.MUITO_FORTE == strengthLevel || StrengthLevel.FORTE == strengthLevel

        mainBinding.strengthLevelTxt.text = strengthLevel.name
        mainBinding.strengthLevelTxt.setTextColor(ContextCompat.getColor(this, strengthColor))
        mainBinding.strengthLevelInd.setBackgroundColor(ContextCompat.getColor(this, strengthColor))

    }

    private fun calculateStrength(
        password: CharSequence,
        hasLowerCase: Boolean,
        hasUpperCase: Boolean,
        hasDigit: Boolean,
        hasSpecialChar: Boolean
    ) {

        val strengthLevel: StrengthLevel
        val strengthColor: Int

        when (password.length) {
            in 0..7 -> {
                strengthLevel = StrengthLevel.FRACO
                strengthColor = R.color.fraco
            }

            in 8..10 -> {
                if (hasLowerCase || hasUpperCase || hasDigit || hasSpecialChar) {
                    strengthLevel = StrengthLevel.MEDIO
                    strengthColor = R.color.medio
                } else {
                    strengthLevel = StrengthLevel.FRACO
                    strengthColor = R.color.fraco
                }
            }

            in 11..16 -> {
                if (hasLowerCase && hasUpperCase) {
                    strengthLevel = StrengthLevel.FORTE
                    strengthColor = R.color.forte
                } else if (hasLowerCase || hasUpperCase || hasDigit || hasSpecialChar) {
                    strengthLevel = StrengthLevel.MEDIO
                    strengthColor = R.color.medio
                } else {
                    strengthLevel = StrengthLevel.FRACO
                    strengthColor = R.color.fraco
                }
            }

            else -> {
                if (hasLowerCase && hasUpperCase && hasDigit && hasSpecialChar) {
                    strengthLevel = StrengthLevel.MUITO_FORTE
                    strengthColor = R.color.muito_forte
                } else if (hasLowerCase && hasUpperCase) {
                    strengthLevel = StrengthLevel.FORTE
                    strengthColor = R.color.forte
                } else if (hasLowerCase || hasUpperCase || hasDigit || hasSpecialChar) {
                    strengthLevel = StrengthLevel.MEDIO
                    strengthColor = R.color.medio
                } else {
                    strengthLevel = StrengthLevel.FRACO
                    strengthColor = R.color.fraco
                }
            }
        }
        displayStrengthLevel(strengthLevel, strengthColor)

    }

    private fun uptadeStatusUI(
        condition: Boolean,
        imageView: ImageView,
        textView: TextView
    ) {
        if (condition) {
            imageView.setImageResource(R.drawable.ic_correct)
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.muito_forte))
            textView.setTextColor(ContextCompat.getColor(this, R.color.muito_forte))
        } else {
            imageView.setImageResource(R.drawable.ic_wrong)
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.darkGray))
            textView.setTextColor(ContextCompat.getColor(this, R.color.darkGray))
        }
    }
}