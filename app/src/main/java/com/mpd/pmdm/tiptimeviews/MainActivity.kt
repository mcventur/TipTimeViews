package com.mpd.pmdm.tiptimeviews

import android.content.Context
import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.OnKeyListener
import android.view.inputmethod.InputMethodManager
import com.mpd.pmdm.tiptimeviews.databinding.ActivityMainBinding
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    //Nos comprometemos a inicializar posteriormente esta propiedad con lateinit
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Empezamos a asociar eventos. Ojo a la forma de la llamada a setOnClickListener con llaves: trailing lambda
        binding.calculateButton.setOnClickListener{calculateTip()}

//        implementacion anonima de la interface OnKeyList
//        val onKeyListener = object : OnKeyListener{
//            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {

//            }
//        }

        binding.costOfServiceEditText.setOnKeyListener{
            //El tercer parámetro (event : KeyEvent?) no nos interesa. Le da _ como convención
            view:View, keyCode: Int, _ -> manejaEventoTeclado(view, keyCode)
        }



    }

    /**
     * Ocultamos el teclado del View llamante si la tecla pulsada es Enter
     */
    private fun manejaEventoTeclado(view: View, keyCode: Int):Boolean {
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            ocultarTeclado(view)
            return true
        }
        return false
    }

    private fun ocultarTeclado(view: View) {
        //apuntamos con una variable al servicio de manejo del teclado
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun calculateTip() {
        //Recogemos el coste, y el porcentaje de propina de la IU
        val costString = binding.costOfServiceEditText.text.toString()
        val cost = costString?.toDoubleOrNull() ?: 0.0
        val selectedId = binding.tipOptions.checkedRadioButtonId
        val tipPercentage = when(selectedId){
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        //Calculamos la propina
        var tip = cost * tipPercentage
        //Aplicamos el redondeo si se ha indicado con el switch
        if(binding.roundUpSwitch.isChecked){
            tip = ceil(tip)
        }

        //Lo formateamos con el formateador de divisa configurado en el dispositivo
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

        //Mostramos el valor formateado en el TextView
        binding.tipResult.text = getString(R.string.tip_amount,formattedTip)
    }
}