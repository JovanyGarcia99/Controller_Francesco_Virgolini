package laraknife.francescovirgolini

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.francescovirgolini)

        blu.setDataLoadFinishedListener(object:BluJhr.ConnectedBluetooth{
            override fun onConnectState(state: BluJhr.Connected) {
                when (state) {
                    BluJhr.Connected.True -> {
                        Toast.makeText(applicationContext, "Conectado", Toast.LENGTH_SHORT).show()
                        mediaPlayer.start()
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
            blu.bluTx("B")
            //blu.bluTx("S") Detenerse
            Handler().postDelayed({
                blu.bluTx("S")
                binding.imgState.setImageResource(R.drawable.stop)
            }, 1000)
        }

        binding.btnAdelante.setOnClickListener {
            binding.imgState.setImageResource(R.drawable.arriba)
            blu.bluTx("F")
        }

        binding.btnIzquierda.setOnClickListener {
            binding.imgState.setImageResource(R.drawable.izquierda)
            blu.bluTx("L")
        }

        binding.btnDerecha.setOnClickListener {
            binding.imgState.setImageResource(R.drawable.derecha)
            blu.bluTx("R")
        }

        binding.btnClaxon.setOnClickListener {
            blu.bluTx("V")
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