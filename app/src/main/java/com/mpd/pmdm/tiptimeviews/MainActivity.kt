package com.mpd.pmdm.tiptimeviews

import android.icu.text.NumberFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mpd.pmdm.tiptimeviews.databinding.ActivityMainBinding
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    //Nos comprometemos a inicializar posteriormente esta propiedad con lateinit
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}