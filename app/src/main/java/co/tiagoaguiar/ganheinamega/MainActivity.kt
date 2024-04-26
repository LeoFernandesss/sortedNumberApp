package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        //DATA STORAGE OF LATEST GENERATED NUMBERS
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)
        result?.let {
            txtResult.text = "Last luck numbers:\n $it"
        }

        btnGenerate.setOnClickListener {

            val text = editText.text.toString()

            numberGenerator(text, txtResult)
        }

    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        //First Failure
        if (text.isEmpty()) {
            Toast.makeText(this, "Type one number between 6 and 15", Toast.LENGTH_LONG).show()
            return
        }

        val amt = text.toInt() //Convert String for Int

        //Second Failure
        if (amt < 6 || amt > 15) {
            Toast.makeText(this, "Type one number between 6 and 15", Toast.LENGTH_LONG).show()
            return
        }
        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60) //0..59
            numbers.add(number + 1)

            if (numbers.size == amt) {
                break
            }
        }

        txtResult.text = numbers.joinToString(" - ")

        prefs.edit().apply {
            putString("result", txtResult.text.toString())
            apply()

        }

    }


}