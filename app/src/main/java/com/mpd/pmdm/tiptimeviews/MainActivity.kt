package com.mpd.pmdm.tiptimeviews

import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        //Empezamos a asociar eventos. Ojo a la forma de la llamada a setOnClickListener, con llaves
        binding.calculateButton.setOnClickListener{calculateTip()}
    }

    private fun calculateTip() {
        //Recogemos el coste, y el porcentaje de propina de la IU
        val cost = binding.costOfService.text.toString().toDouble()
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
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}