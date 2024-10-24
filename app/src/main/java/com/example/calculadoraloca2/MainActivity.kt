package com.example.calculadoraloca2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraloca2.ui.theme.CalculadoraLoca2Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraLoca2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Calculadora Loca",
                        modifier = Modifier.padding(innerPadding)
                    )
                    CalculadoraLocav2()
                }
            }
        }
    }
}
@Composable
fun CalculadoraLocav2(modifier: Modifier = Modifier) {
    var expresion by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize() // Asegúrate de que llene todo el espacio disponible
            .background(Color.LightGray) // Cambia el color aquí
            .padding(40.dp), // Mantén el padding
        horizontalAlignment = Alignment.CenterHorizontally, // Alinea los elementos horizontalmente al centro
        verticalArrangement = Arrangement.SpaceBetween // Distribuye el contenido con espacio entre ellos
    ) {
        // Mostrar la expresión y el resultado en la parte superior
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$expresion",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center // Centra el texto
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Blue)
                    .padding(16.dp)
            ) {  // Mostrar el resultado
                Text(
                    text = " $resultado",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center // Centra el texto dentro del Row
                )
            }
        }

        // Espacio intermedio para que el contenido se distribuya equitativamente
        Spacer(modifier = Modifier.height(16.dp)) // Ajusta el espacio aquí
        Row {
            estiloBotones(texto = "1", onClick = { expresion += "3" }, modifier = Modifier.weight(1.5f))
            estiloBotones(texto = "2", onClick = { expresion += "4" }, modifier = Modifier.weight(1.5f))
            estiloBotones(texto = "3", onClick = { expresion += "6" }, modifier = Modifier.weight(1.5f))
        }
    Row{
        estiloBotones(texto = "4", onClick = { expresion += "7" }, modifier = Modifier.weight(1.5f))
        estiloBotones(texto = "6", onClick = { expresion += "8" }, modifier = Modifier.weight(1.5f))
        estiloBotones(texto = "7", onClick = { expresion += "9" }, modifier = Modifier.weight(1.5f))

    }
        Row{
            estiloBotones(texto = "8", onClick = { expresion += "0" }, modifier = Modifier.weight(1.5f))
            estiloBotones(texto = "9", onClick = { expresion += "1" }, modifier = Modifier.weight(1.5f))
            estiloBotones(texto = "0", onClick = { expresion += "2" }, modifier = Modifier.weight(1.5f))

        }
        Row {
            estiloBotones(texto = "+", onClick = { expresion += "+" }, modifier = Modifier.weight(1.5f))
            estiloBotones(texto = "-", onClick = { expresion += "-" }, modifier = Modifier.weight(1.5f))
            estiloBotones(texto = "*", onClick = { expresion += "*" }, modifier = Modifier.weight(1.5f))
            estiloBotones(texto = "/", onClick = { expresion += "/" }, modifier = Modifier.weight(1.5f))
        }
Row {
    // Botón de igual
    estiloBotones(texto = "=", onClick = {
        resultado = calcularOperacion(expresion) // Calcula la expresión
    }, modifier = Modifier.weight(1.5f))

    // Botón de borrar
    estiloBotones(texto = "C", onClick = {
        expresion = "" // Borra la expresión
        resultado = "" // Borra el resultado
    }, modifier = Modifier.weight(1.5f))
}
    }
}


@Composable
fun estiloBotones(texto: String, onClick: () -> Unit, modifier: Modifier = Modifier){
    Button(
        onClick = onClick,
        modifier = modifier
            .width(80.dp)
            .height(60.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue, // Color de fondo (anteriormente backgroundColor)
            contentColor = Color.White // Color del texto
        )
    ) {
        Text(text = texto, fontSize = 20.sp) // Aumenta el tamaño del texto
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Calculadora Loca",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculadoraLoca2Theme {
        Greeting("Android")
    }
}

fun calcularOperacion(expresion: String): String {

    // Validamos que la expresión no esté vacía
    if (expresion.isBlank()) return "Error"

    return try {
        // Intentamos evaluar la expresión
        val resultado = evaluarExpresion(expresion)
        resultado.toString()
    } catch (e: ArithmeticException) {
        // Manejo de excepciones específicas como división por cero
        "Error: División por cero"
    } catch (e: Exception) {
        // Si ocurre cualquier otro error, devolvemos un mensaje genérico
        "Error"
    }
}


fun evaluarExpresion(expresion: String): Double {
    // Lista para guardar los números y operadores
    val numeros = mutableListOf<Double>()
    val operadores = mutableListOf<Char>()

    var i = 0
    while (i < expresion.length) {
        when (val c = expresion[i]) {
            in '0'..'9' -> {
                // Si es un número, parsearlo completo
                var numStr = ""
                while (i < expresion.length && (expresion[i].isDigit() || expresion[i] == '.')) {
                    numStr += expresion[i]
                    i++
                }
                // Convertir a número y aplicar la lógica para evitar 5, 15, 25, ...
                val numero = numStr.toDouble()
                if (numero % 10 == 5.0) {
                    // Si el número es 5, lo cambiamos a 6
                    numeros.add(6.0)
                } else if (numero > 0 && numero % 10 == 5.0) {
                    // Si el número termina en 5 (15, 25, 35...), lo cambiamos a uno más
                    numeros.add(numero + 1)
                } else {
                    numeros.add(numero)
                }
                continue
            }

            '+', '-', '*', '/' -> {
                // Si es un operador, agregarlo a la lista
                operadores.add(c)
            }
        }
        i++
    }

    // Primero, manejamos multiplicación y división
    var index = 0
    while (index < operadores.size) {
        if (operadores[index] == '*' || operadores[index] == '/') {
            val operando1 = numeros[index]
            val operando2 = numeros[index + 1]
            val resultado = if (operadores[index] == '*') {
                operando1 * operando2
            } else {
                if (operando2 == 0.0) throw ArithmeticException("División por cero")
                operando1 / operando2
            }

            // Aplicar la lógica de reemplazo para evitar 5
            val nuevoResultado = when {
                resultado == 5.0 -> if (operadores[index] == '*') 6.0 else 4.0
                resultado % 10 == 5.0 -> resultado + 1 // Para 15, 25, 35, etc.
                else -> resultado
            }

            numeros[index] = nuevoResultado
            numeros.removeAt(index + 1)
            operadores.removeAt(index)
        } else {
            index++
        }
    }

    // Luego, manejamos suma y resta
    var resultado = numeros[0]
    for (j in operadores.indices) {
        resultado = if (operadores[j] == '+') {
            resultado + numeros[j + 1]
        } else {
            resultado - numeros[j + 1]
        }

        // Aplicar la lógica de reemplazo para evitar 5
        resultado = when {
            resultado == 5.0 -> 6.0 // Para suma
            resultado == 4.0 -> 4.0 // Para resta (ya está bien)
            resultado % 10 == 5.0 -> resultado + 1 // Para 15, 25, 35, etc.
            else -> resultado
        }
    }

    return resultado
}