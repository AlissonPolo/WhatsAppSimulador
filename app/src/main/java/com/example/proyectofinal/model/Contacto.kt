import com.example.proyectofinal.R
import com.example.proyectofinal.model.Mensaje
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Contacto(
    val id: Int,
    val nombre: String,
    var ultimoMensaje: String = "",
    var horaUltimoMensaje: String = "",
    val fotoPerfilResId: Int = R.drawable.ic_person,
    var fotoUri: String? = null,
    val mensajes: MutableList<Mensaje> = mutableListOf()
) {
    fun agregarMensaje(mensaje: Mensaje) {
        mensajes.add(mensaje)
        ultimoMensaje = mensaje.texto ?: ""
        horaUltimoMensaje = SimpleDateFormat("hh:mm a", Locale.getDefault())
            .format(Date(mensaje.timestamp))
    }
}
