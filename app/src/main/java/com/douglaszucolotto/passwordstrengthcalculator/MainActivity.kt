package com.douglaszucolotto.passwordstrengthcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.douglaszucolotto.passwordstrengthcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    enum class strengthLevel {
        WEAK,
        MEDIUM,
        STRONG,
        VERY_STRONG
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
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(password: Editable) {
                // Remove espaço
                if (password.trim().isNotEmpty()){

                    // Verifica se tem letras minúsculas
                    val hasLowerCase = password.any{ it.isLowerCase()}

                    // Verifica se tem letras maiúsculas
                    val hasUpperCase = password.any{ it.isUpperCase()}

                    // Verifica se tem dígitos
                    val hasDigit = password.any{ it.isDigit()}

                    // Verifica se tem caracteres especiais
                    val hasSpecialChar = password.contains(Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\",./<>?\\\\|]"))

                }
            }

        })
    }

    private fun uptadeStatusUI(
        condition : Boolean,
        imageView: ImageView,
        textView: TextView
    ){
        if (condition){
            imageView.setImageResource(R.drawable.ic_correct)
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.bulletProof))
            textView.setTextColor(ContextCompat.getColor(this, R.color.bulletProof))
        }else{
            imageView.setImageResource(R.drawable.ic_wrong)
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.darkGray))
            textView.setTextColor(ContextCompat.getColor(this, R.color.darkGray))
        }
    }
}