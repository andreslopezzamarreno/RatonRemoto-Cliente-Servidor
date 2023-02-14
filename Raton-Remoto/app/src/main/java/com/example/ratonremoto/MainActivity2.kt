package com.example.ratonremoto

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnTouchListener
import androidx.appcompat.app.AppCompatActivity
import com.example.ratonremoto.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity(), OnTouchListener, OnClickListener {

    //En esta clase esta la conexion con servidor,control del surface y control de botones
    private lateinit var binding: ActivityMain2Binding
    private lateinit var hilo: Hilo
    private var posInicialX: Int = 0
    private var posInicialY: Int = 0
    private var x: Int = 0
    private var y: Int = 0
    private lateinit var mensaje: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //Controlo las pulsacion de los botones o del SurfaceView
        binding.surface.setOnTouchListener(this)
        binding.botonDerecho.setOnClickListener(this)
        binding.botonIzquierdo.setOnClickListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        //Controlo SurfaceView
        when (v?.id) {
            binding.surface.id -> {
                if (event?.action == MotionEvent.ACTION_DOWN) {
                    //Detecto toque --> registro coordenada inicial
                    //SIEMPRE al tocar SurfaceView
                    posInicialX = event.x.toInt()
                    posInicialY = event.y.toInt()
                } else if (event?.action == MotionEvent.ACTION_MOVE) {
                    //Cuando se detecta movimiento cojo la cordenada de movimiento --> a donde se ha movido
                    x = event.x.toInt()
                    y = event.y.toInt()
                    //Resto la cordenada de movimiento a la cordenda inicial para ver la diferencia --> direccion, sentido y magnitud
                    //Parte clave para que funcione bien
                    val coordenadas: String =
                        //Multiplico por 3 para que vaya mas rapido el raton
                        ((x - posInicialX) * 3).toString() + " " + ((y - posInicialY) * 3).toString()
                    //Esa coordenada la envio al servidor y controlo el movimiento del raton desde alli
                    if (esRelleno()) {
                        //Necesidad de hilo para hacer conexion a internet --> El Main no deja
                        //Sin añadir el hilo da error -> android.os.NetworkOnMainThreadException
                        //Creo hilo y paso las coordenadas de movimiento al servidor
                        hilo = Hilo(
                            binding.textServidor.text.toString(),
                            binding.textPuerto.text.toString().toInt(),
                            coordenadas
                        )
                        hilo.start()
                    }
                }
            }
        }
        return true
    }

    override fun onClick(v: View?) {
        //Controlo Pulsacion de botones
        when (v?.id) {
            binding.botonDerecho.id -> {
                //Si es boton derecho
                mensaje = "der"
            }
            binding.botonIzquierdo.id -> {
                //Si es boton izquierdo
                mensaje = "izq"
            }
        }
        //Sea boton izquierdo o derecho envio el mensaje y controlo desde servidor
        if (esRelleno()) {
            hilo = Hilo(
                binding.textServidor.text.toString(),
                binding.textPuerto.text.toString().toInt(),
                mensaje
            )
            hilo.start()
        }
    }

    //Metodo para ver si la ip del servidor y el puerto estan puestos
    private fun esRelleno(): Boolean {
        return !(binding.textServidor.text.isEmpty() || binding.textPuerto.text.isEmpty())
    }
}