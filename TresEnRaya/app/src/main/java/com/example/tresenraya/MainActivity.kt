package com.example.tresenraya

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.tresenraya.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding /* Esto hay que hacelo siempre -> Grade Scripts > build.gradle.kts(app) */
    private var game_finish = false
    private var turnoX = true
    private var numBtnPressed : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setMyListener() // mi escuchador
        onResetPressed()
    }

    private fun setMyListener(){
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)

        binding.btnReset.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        when(view){
            binding.btnReset -> onResetPressed()
            binding.btn1 -> onButtonPressed(binding.btn1)
            binding.btn2 -> onButtonPressed(binding.btn2)
            binding.btn3 -> onButtonPressed(binding.btn3)
            binding.btn4 -> onButtonPressed(binding.btn4)
            binding.btn5 -> onButtonPressed(binding.btn5)
            binding.btn6 -> onButtonPressed(binding.btn6)
            binding.btn7 -> onButtonPressed(binding.btn7)
            binding.btn8 -> onButtonPressed(binding.btn8)
            binding.btn9 -> onButtonPressed(binding.btn9)
        }
    }

    fun onResetPressed(){
        binding.btn1.text = ""
        binding.btn2.text = ""
        binding.btn3.text = ""
        binding.btn4.text = ""
        binding.btn5.text = ""
        binding.btn6.text = ""
        binding.btn7.text = ""
        binding.btn8.text = ""
        binding.btn9.text = ""

        game_finish = false // ponemos el juego a false para no cambiar mas botones
        numBtnPressed = 0 // resetamos el contador
        // establecemos turno aleatorio
        turnoX = Random.nextBoolean()
        // pintamos turno
        if (turnoX) {
            binding.textView.text = getString(R.string.TurnoX)
        } else {
            binding.textView.text = getString(R.string.TurnoO)
        }
    }

    fun onButtonPressed(btn: AppCompatButton) {

        if (!game_finish) {

            if (turnoX && btn.text.toString() == "") { //si es turnoX y el boton esta vacio
                // Cambiamos el texto del boton a X
                btn.text = getString(R.string.X)
                // Le ponemos un estilo de themes( para el color & size)
                btn.setTextAppearance(R.style.ButtonX)
                // Establecemos turno de O
                binding.textView.text = getString(R.string.TurnoO)
                turnoX = false

            } else if (!turnoX && btn.text.toString() == "") {
                // Cambiamos el texto del boton a O
                btn.text = getString(R.string.O)
                // Le ponemos un estilo de themes( style --> color & size)
                btn.setTextAppearance(R.style.ButtonO)
                // Establecemos turno de X
                binding.textView.text = getString(R.string.TurnoX)
                turnoX = true

            }
            numBtnPressed += 1
            // Para  ver quien gana, miramos segun la posicion del ultimo btn pulsado, los dos números que hay en la horizontal
            // y vertical (miramos de 2 a 4 direcciones)
            when (btn) {
                binding.btn1 -> if ((btn.text == binding.btn2.text && btn.text == binding.btn3.text)
                    || (btn.text == binding.btn4.text && btn.text == binding.btn7.text)
                    || (btn.text == binding.btn5.text && btn.text == binding.btn9.text)
                )
                    whoWin(turnoX)

                binding.btn2 -> if ((btn.text == binding.btn1.text && btn.text == binding.btn3.text)
                    || (btn.text == binding.btn5.text && btn.text == binding.btn8.text)
                )
                    whoWin(turnoX)

                binding.btn3 -> if ((btn.text == binding.btn2.text && btn.text == binding.btn1.text)
                    || (btn.text == binding.btn6.text && btn.text == binding.btn9.text)
                    || (btn.text == binding.btn5.text && btn.text == binding.btn7.text)
                )
                    whoWin(turnoX)

                binding.btn4 -> if ((btn.text == binding.btn1.text && btn.text == binding.btn7.text)
                    || (btn.text == binding.btn6.text && btn.text == binding.btn5.text)
                )
                    whoWin(turnoX)

                binding.btn5 -> if ((btn.text == binding.btn4.text && btn.text == binding.btn6.text)
                    || (btn.text == binding.btn8.text && btn.text == binding.btn2.text)
                    || (btn.text == binding.btn1.text && btn.text == binding.btn9.text)
                    || (btn.text == binding.btn7.text && btn.text == binding.btn3.text)
                )
                    whoWin(turnoX)

                binding.btn6 -> if ((btn.text == binding.btn3.text && btn.text == binding.btn9.text)
                    || (btn.text == binding.btn4.text && btn.text == binding.btn5.text)
                )
                    whoWin(turnoX)

                binding.btn7 -> if ((btn.text == binding.btn8.text && btn.text == binding.btn9.text)
                    || (btn.text == binding.btn4.text && btn.text == binding.btn1.text)
                    || (btn.text == binding.btn5.text && btn.text == binding.btn3.text)
                )
                    whoWin(turnoX)

                binding.btn8 -> if ((btn.text == binding.btn7.text && btn.text == binding.btn9.text)
                    || (btn.text == binding.btn5.text && btn.text == binding.btn2.text)
                )
                    whoWin(turnoX)

                binding.btn9 -> if ((btn.text == binding.btn8.text && btn.text == binding.btn7.text)
                    || (btn.text == binding.btn6.text && btn.text == binding.btn3.text)
                    || (btn.text == binding.btn5.text && btn.text == binding.btn1.text)
                )
                    whoWin(turnoX)

            }
            // Como el codigo sigue ejecutandose aunque gane, tenemos que asegurarnos que si gana en el
            // ultimo turno no ponga empate, para ello comprobamos de que la partida haya terminado
            if ((numBtnPressed == 9) && game_finish == false){
                game_finish = true
                binding.textView.text = getString(R.string.draw)
            }
        }
    }

    fun whoWin(turnoX: Boolean){
        // terminamos el juego para no pulsar mas botones
        game_finish = true
        if(!turnoX){ // como cambiamos el turno al pulsar el boton tenemos que mirar el contrario
            binding.textView.text = getString(R.string.WinnerX)
        } else {
            binding.textView.text = getString(R.string.WinnerO)
        }
    }

   // Para guardar los datos cuando pongamos la aplicacion landscape o portrait
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
       outState.putString("btn1", binding.btn1.text.toString())
       outState.putString("btn2", binding.btn2.text.toString())
       outState.putString("btn3", binding.btn3.text.toString())
       outState.putString("btn4", binding.btn4.text.toString())
       outState.putString("btn5", binding.btn5.text.toString())
       outState.putString("btn6", binding.btn6.text.toString())
       outState.putString("btn7", binding.btn7.text.toString())
       outState.putString("btn8", binding.btn8.text.toString())
       outState.putString("btn9", binding.btn9.text.toString())
       outState.putBoolean("turnoX", turnoX)
       outState.putString("textView", binding.textView.text.toString())
       outState.putInt("contador", numBtnPressed)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.btn1.text = savedInstanceState.getString("btn1")
        binding.btn2.text = savedInstanceState.getString("btn2")
        binding.btn3.text = savedInstanceState.getString("btn3")
        binding.btn4.text = savedInstanceState.getString("btn4")
        binding.btn5.text = savedInstanceState.getString("btn5")
        binding.btn6.text = savedInstanceState.getString("btn6")
        binding.btn7.text = savedInstanceState.getString("btn7")
        binding.btn8.text = savedInstanceState.getString("btn8")
        binding.btn9.text = savedInstanceState.getString("btn9")
        turnoX = savedInstanceState.getBoolean("turnoX")
        binding.textView.text = savedInstanceState.getString("textView")
        numBtnPressed = savedInstanceState.getInt("contador")

        // !! Problema, no puedo guardar el estilo
        // Para ello, hago un loop donde recorro una lista de todos los botones, si tenian X o O le pongo el estilo
        var listaBtn = listOf(binding.btn1, binding.btn2, binding.btn3, binding.btn4, binding.btn5,
            binding.btn6, binding.btn7, binding.btn8, binding.btn9)

        for (btn in listaBtn){
            if(btn.text == getString(R.string.X)){
                btn.setTextAppearance(R.style.ButtonX)
            } else if (btn.text == getString(R.string.O)){
                btn.setTextAppearance(R.style.ButtonO)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity","onDestroy")
    }
}