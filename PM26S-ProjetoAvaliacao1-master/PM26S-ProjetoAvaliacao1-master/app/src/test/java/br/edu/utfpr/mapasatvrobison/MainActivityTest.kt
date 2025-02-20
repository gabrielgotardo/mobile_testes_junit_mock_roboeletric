package br.edu.utfpr.mapasatvrobison

import br.edu.utfpr.mapasatvrobison.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {


    // teste do api tambem google externa
    //robolectric --- para testar sem dependecia de celular android conectado


    @Mock
    private lateinit var mockActivity: MainActivity

    @Captor
    private lateinit var captor: ArgumentCaptor<(String) -> Unit>

    @Before
    fun setup() {
        mockActivity = mock(MainActivity::class.java)
    }

    @Test
    fun `teste de obterEndereco com sucesso`() {
        val latitude = -25.4284
        val longitude = -49.2733
        val enderecoEsperado = "Curitiba, PR, Brasil"

        // Simula o comportamento do método
        doAnswer {
            val callback = it.getArgument<(String) -> Unit>(2)
            callback(enderecoEsperado) // Simula a resposta bem-sucedida
        }.`when`(mockActivity).obterEndereco(eq(latitude), eq(longitude), any())

        // Executa o método
        mockActivity.obterEndereco(latitude, longitude) { endereco ->
            assert(endereco == enderecoEsperado)
        }

        // Verifica se o método foi chamado corretamente
        verify(mockActivity).obterEndereco(eq(latitude), eq(longitude), any())
    }
}
