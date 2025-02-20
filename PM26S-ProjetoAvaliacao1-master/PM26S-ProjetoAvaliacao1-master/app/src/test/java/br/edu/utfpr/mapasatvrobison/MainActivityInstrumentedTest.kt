package br.edu.utfpr.mapasatvrobison

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.edu.utfpr.mapasatvrobison.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    //teste da api externa


    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun `testa se obterEndereco chama callback corretamente`() {
        val mockActivity = mock(MainActivity::class.java)
        val latitude = -25.4284
        val longitude = -49.2733

        doAnswer {
            val callback = it.getArgument<(String) -> Unit>(2)
            callback("Curitiba, PR, Brasil")
        }.`when`(mockActivity).obterEndereco(eq(latitude), eq(longitude), any())

        mockActivity.obterEndereco(latitude, longitude) { endereco ->
            assert(endereco == "Curitiba, PR, Brasil")
        }

        verify(mockActivity).obterEndereco(eq(latitude), eq(longitude), any())
    }
}
