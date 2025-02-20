package br.edu.utfpr.mapasatvrobison

import android.Manifest
import android.os.Build
import androidx.core.app.ActivityCompat
import br.edu.utfpr.mapasatvrobison.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.Robolectric
import org.robolectric.annotation.Config

@RunWith(MockitoJUnitRunner::class)
@Config(sdk = [Build.VERSION_CODES.UPSIDE_DOWN_CAKE]) // Simula Android 14
class MainActivityPermissionTest {

    private lateinit var activity: MainActivity

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
    }

    @Test
    fun `testa se permissoes sao solicitadas quando nao concedidas`() {
        val spyActivity = spy(activity)

        // Simula que as permissões não foram concedidas
        doReturn(false).`when`(spyActivity).hasPermissions()

        // Chama o método a ser testado
        spyActivity.checkLocationPermissions()

        // Verifica se as permissões foram solicitadas corretamente
        verify(spyActivity).hasPermissions()
        val LOCATION_PERMISSION_REQUEST_CODE = 0
        verify(spyActivity).requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.FOREGROUND_SERVICE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    @Test
    fun `testa se permissoes nao sao solicitadas quando ja concedidas`() {
        val spyActivity = spy(activity)

        // Simula que as permissões já foram concedidas
        doReturn(true).`when`(spyActivity).hasPermissions()

        // Chama o método a ser testado
        spyActivity.checkLocationPermissions()

        // Verifica que as permissões **não** foram solicitadas novamente
        verify(spyActivity).hasPermissions()
        verify(spyActivity, never()).requestPermissions(any(), anyInt())
    }
}