package com.example.whileseries

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CyclesScreen()
                }
            }
        }
    }
}

@Composable
fun CyclesScreen(modifier: Modifier = Modifier) {
    var inputX by remember { mutableStateOf("2.0") }
    var inputEpsilon by remember { mutableStateOf("0.0001") }

    var resultSum by remember { mutableStateOf("") }
    var resultLastTerm by remember { mutableStateOf("") }
    var resultCount by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Вычисление ряда", fontSize = 24.sp, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = inputX,
            onValueChange = { inputX = it },
            label = { Text("Введите число x (не равно 0)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = inputEpsilon,
            onValueChange = { inputEpsilon = it },
            label = { Text("Введите эпсилон (например, 0.0001)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val x = inputX.toDoubleOrNull()
                val epsilon = inputEpsilon.toDoubleOrNull()

                if (x == null || epsilon == null || epsilon <= 0) {
                    resultSum = "Ошибка: введите корректные числа"
                    resultLastTerm = ""
                    resultCount = ""
                } else if (x == 0.0) {
                    resultSum = "Ошибка: x не может быть равен 0"
                    resultLastTerm = ""
                    resultCount = ""
                } else {
                    val result = calculateSeries(x, epsilon)
                    resultSum = "Сумма: %.6f".format(result.sum)
                    resultLastTerm = "Последнее слагаемое: %.6f".format(result.lastTerm)
                    resultCount = "Повторений цикла: ${result.count}"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Вычислить")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = resultSum, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = resultLastTerm, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = resultCount, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
    }
}

data class CalculationResult(val sum: Double, val lastTerm: Double, val count: Int)

fun calculateSeries(x: Double, epsilon: Double): CalculationResult {
    var sum = 0.0
    var term = 1.0 / x
    var count = 0
    var lastAddedTerm = 0.0
    var den = 1.0


    while (abs(term) >= epsilon) {
        lastAddedTerm = term
        sum += term
        count++


        term = -term * den / (den + 2.0) / (x * x)
        den += 2.0
    }

    return CalculationResult(sum, lastAddedTerm, count)
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CyclesScreenPreview() {
    MaterialTheme {
        CyclesScreen()
    }
}