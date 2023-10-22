package com.mpd.pmdm.tiptimeviews

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
        typeCostOfService("50")

        clickCalculateAndCheckResult(10)
    }

    @Test
    fun calculate_20_percent_round_up(){
        typeCostOfService("32")

        //Activamos el switch de redondeo con un click
        onView(withId(R.id.round_up_switch)).perform(click())

        clickCalculateAndCheckResult(7)
    }


    @Test
    fun calculate_15_percent_tip(){
        typeCostOfService("100");

        val interactionFifteenOption =  onView(withId(R.id.option_fifteen_percent))
        onView(withId(R.id.option_fifteen_percent)).perform(click())
        clickCalculateAndCheckResult(15)

    }

    /**
     * Introduce el texto en el View cost_of_service_edit_text
     */
    private fun typeCostOfService(costOfService: String) {
        //Usamos funciones de la librería Espresso para interactuar con la IU desde código

        //Seleccionamos un elemento de la interfaz (el edit del coste del servicio), y le
        //escribimos una cantidad: 50
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText(costOfService))
            .perform(ViewActions.closeSoftKeyboard())
    }

    /**
     * Pulsa el botón y chequea el resultado esperado
     */
    private fun clickCalculateAndCheckResult(value:Int) {
        onView(withId(R.id.calculate_button)).perform(click())

        val expectedTip = java.text.NumberFormat.getCurrencyInstance().format(value)
        val expectedTipText = "Tip Amount: " + expectedTip
        onView(withId(R.id.tip_result)).check(matches(withText(expectedTipText)))
    }



}