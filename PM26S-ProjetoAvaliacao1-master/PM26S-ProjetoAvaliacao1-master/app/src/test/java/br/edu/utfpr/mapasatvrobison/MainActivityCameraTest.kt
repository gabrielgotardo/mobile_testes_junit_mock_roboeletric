package br.edu.utfpr.mapasatvrobison


import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.provider.MediaStore
import android.provider.Telephony
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityCameraTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        Telephony.Sms.Intents.init() // Inicializa o Espresso Intents para monitorar intents
    }

    @After
    fun teardown() {
        Telephony.Sms.Intents.release() // Libera os recursos do Espresso Intents
    }

    @Test
    fun `testa se capturarFoto dispara intent correta`() {
        // Lança a Activity
        ActivityScenario.launch(MainActivity::class.java).use {

            // Simula o retorno da câmera como se tivesse tirado uma foto
            val resultado = Instrumentation.ActivityResult(Activity.RESULT_OK, Intent())

            // Configura o Espresso para interceptar a intent de captura de imagem
            Intents.intending(IntentMatchers.hasAction(MediaStore.ACTION_IMAGE_CAPTURE))
                .respondWith(resultado)

            // Executa o método que deve disparar a intent
            it.onActivity { activity ->
                activity.capturarFoto()
            }

            // Verifica se a intent correta foi disparada
            Intents.intended(IntentMatchers.hasAction(MediaStore.ACTION_IMAGE_CAPTURE))
        }
    }
}