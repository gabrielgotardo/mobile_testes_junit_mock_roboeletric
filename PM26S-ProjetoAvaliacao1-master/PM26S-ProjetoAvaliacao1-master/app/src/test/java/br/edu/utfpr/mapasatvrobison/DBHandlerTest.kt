package br.edu.utfpr.mapasatvrobison

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class DBHandlerTest {

    private lateinit var dbHandler: DBHandler
    private lateinit var mockDb: SQLiteDatabase
    private lateinit var mockCursor: Cursor

    @Before
    fun setup() {
        val mockContext = mock(android.content.Context::class.java)
        dbHandler = DBHandler(mockContext)

        mockDb = mock(SQLiteDatabase::class.java)
        mockCursor = mock(Cursor::class.java)
    }

    @Test
    fun `getPontoTuristicoById retorna PontoTuristico quando ID existe`() {
        val expectedPonto = PontoTuristico(
            id = 1,
            name = "Cristo Redentor",
            description = "Um dos pontos turísticos mais famosos do Brasil.",
            endereco = "Rio de Janeiro",
            latitude = -22.9519,
            longitude = -43.2105,
            photo = null
        )

        `when`(mockDb.query(
            anyString(), any(), anyString(), any(), any(), any(), any()
        )).thenReturn(mockCursor)

        `when`(mockCursor.moveToFirst()).thenReturn(true)
        `when`(mockCursor.getInt(mockCursor.getColumnIndexOrThrow("id"))).thenReturn(expectedPonto.id)
        `when`(mockCursor.getString(mockCursor.getColumnIndexOrThrow("name"))).thenReturn(expectedPonto.name)
        `when`(mockCursor.getString(mockCursor.getColumnIndexOrThrow("description"))).thenReturn(expectedPonto.description)
        `when`(mockCursor.getString(mockCursor.getColumnIndexOrThrow("endereco"))).thenReturn(expectedPonto.endereco)
        `when`(mockCursor.getDouble(mockCursor.getColumnIndexOrThrow("latitude"))).thenReturn(expectedPonto.latitude)
        `when`(mockCursor.getDouble(mockCursor.getColumnIndexOrThrow("longitude"))).thenReturn(expectedPonto.longitude)

        val result = dbHandler.getPontoTuristicoById(1)

        assertNotNull(result)
        assertEquals(expectedPonto.id, result?.id)
        assertEquals(expectedPonto.name, result?.name)
        assertEquals(expectedPonto.description, result?.description)
        assertEquals(expectedPonto.endereco, result?.endereco)
        //assertEquals(expectedPonto.latitude, result?.latitude, 0.0001)
        //assertEquals(expectedPonto.longitude, result?.longitude, 0.0001)
    }

    @Test
    fun `getPontoTuristicoById retorna null quando ID não existe`() {
        `when`(mockDb.query(
            anyString(), any(), anyString(), any(), any(), any(), any()
        )).thenReturn(mockCursor)

        `when`(mockCursor.moveToFirst()).thenReturn(false)

        val result = dbHandler.getPontoTuristicoById(999)

        assertNull(result)
    }
}
