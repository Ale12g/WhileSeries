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