import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinal.R

class PerfilActivity : AppCompatActivity() {

    private lateinit var ivFotoPerfil: ImageView
    private lateinit var btnCambiarFoto: Button

    private val seleccionarImagenLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            ivFotoPerfil.setImageURI(it)
            // Aquí puedes guardar la URI en tu modelo o base de datos si quieres persistir
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfi)

        ivFotoPerfil = findViewById(R.id.ivFotoPerfil)
        btnCambiarFoto = findViewById(R.id.btnCambiarFoto)

        btnCambiarFoto.setOnClickListener {
            seleccionarImagenLauncher.launch("image/*")
        }
    }
}
