package laraknife.francescovirgolini

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ingenieriajhr.blujhr.BluJhr
import laraknife.francescovirgolini.databinding.ActivityControlBinding

class Control : AppCompatActivity() {

    var addres = ""
    lateinit var blu: BluJhr
    var estadoConexion = BluJhr.Connected.False

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityControlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addres = intent.getStringExtra("addres").toString()
        blu = BluJhr(this)

        blu.setDataLoadFinishedListener(object:BluJhr.ConnectedBluetooth{
            override fun onConnectState(state: BluJhr.Connected) {
                when (state) {
                    BluJhr.Connected.True -> {
                        Toast.makeText(applicationContext, "Conectado", Toast.LENGTH_SHORT).show()
                        estadoConexion = state
                    }
                    BluJhr.Connected.Pending -> {
                        Toast.makeText(applicationContext, "Conectando", Toast.LENGTH_SHORT).show()
                        estadoConexion = state
                    }
                    BluJhr.Connected.False -> {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                        estadoConexion = state
                    }
                    BluJhr.Connected.Disconnect -> {
                        Toast.makeText(applicationContext, "Desconectado", Toast.LENGTH_SHORT).show()
                        estadoConexion = state
                        startActivity(Intent(applicationContext,MainActivity::class.java))
                    }
                }
            }
        })

        binding.btnAbajo.setOnClickListener {
            binding.imgState.setImageResource(R.drawable.abajo)
            while (binding.btnAbajo.isPressed){
                // Código instrucción vehículo
                blu.bluTx("B")
            }
            // Texto para que se detenga
            blu.bluTx("S")
            /*
            if (btnAbajo.isPressed){
                // Código instrucción vehículo
                blu.bluTx("B")
            }else{
                // Texto para que se detenga
                blu.bluTx("A")
            }
            */
        }

        binding.btnAdelante.setOnClickListener {
            binding.imgState.setImageResource(R.drawable.arriba)
            while (binding.btnAdelante.isPressed){
                // Código instrucción vehículo
                blu.bluTx("B")
            }
            // Texto para que se detenga
            blu.bluTx("S")
        }

        binding.btnIzquierda.setOnClickListener {
            binding.imgState.setImageResource(R.drawable.izquierda)
            while (binding.btnIzquierda.isPressed){
                // Código instrucción vehículo
                blu.bluTx("B")
            }
            // Texto para que se detenga
            blu.bluTx("S")
        }

        binding.btnDerecha.setOnClickListener {
            binding.imgState.setImageResource(R.drawable.derecha)
            while (binding.btnDerecha.isPressed){
                // Código instrucción vehículo
                blu.bluTx("B")
            }
            // Texto para que se detenga
            blu.bluTx("S")
        }

        binding.btnClaxon.setOnClickListener {
            while (binding.btnClaxon.isPressed){
                blu.bluTx("V")
            }
            blu.bluTx("v")
        }

        binding.btn50.setOnClickListener {
            Toast.makeText(applicationContext, "Más despacio velocista \nEstá en desarrollo XD", Toast.LENGTH_SHORT).show()
        }

        binding.btn100.setOnClickListener {
            Toast.makeText(applicationContext, "Más despacio velocista, \nEstá en desarrollo XD", Toast.LENGTH_SHORT).show()
        }

        binding.btn150.setOnClickListener {
            Toast.makeText(applicationContext, "Más despacio velocista, \nEstá en desarrollo XD", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (estadoConexion != BluJhr.Connected.True){
            blu.connect(addres)
        }
    }

}