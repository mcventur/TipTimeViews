package com.mpd.pmdm.tiptimeviews

import android.icu.text.NumberFormat
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorClassTest {
    /**
     * Definimos una regla para lanzar nuestro entorno de pruebas
     * getRule indica que la sentencia debe lanzarse antes de cualquier prueba
     *
     * La clase ActivityScenarioRule es una regla proporcionada por la biblioteca de pruebas de Android
     * que nos permite crear y lanzar escenarios de prueba para actividades.
     */
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun calculate_20_percent_tip(){
        //Usamos funciones de la librería Espresso para interactuar con la IU desde código

        //Seleccionamos un elemento de la interfaz (el edit del coste del servicio), y le
        //escribimos una cantidad: 50
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("50"))
            .perform(ViewActions.closeSoftKeyboard())

        //Lanzamos el cálculo haciendo click en el botón
        onView(withId(R.id.calculate_button)).perform(click())

        //Comprobamos el importe de la propina resultante en el TextView
        //La función check recibe un ViewsAssertion como parámetro. Lo que queremos testear

        //Calculamos el valor correcto, el esperado, igual que se hace en el código a testear
        val expectedTip = java.text.NumberFormat.getCurrencyInstance().format(10)
        val expectedTipText ="Tip Amount: " + expectedTip
        onView(withId(R.id.tip_result)).check(matches(withText(expectedTipText)))
    }

    @Test
    fun calculate_20_percent_round_up(){
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("32"))
            .perform(ViewActions.closeSoftKeyboard())

        //Activamos el switch de redondeo con un click
        onView(withId(R.id.round_up_switch)).perform(click())

        onView(withId(R.id.calculate_button)).perform(click())

        val expectedTip = java.text.NumberFormat.getCurrencyInstance().format(7)
        val expectedTipText ="Tip Amount: " + expectedTip
        onView(withId(R.id.tip_result)).check(matches(withText(expectedTipText)))


    }



}