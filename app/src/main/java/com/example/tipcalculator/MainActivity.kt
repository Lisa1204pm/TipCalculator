package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}
@Composable
fun TipCalculatorScreen() {
    var serviceCostAmountInput by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf(15.0) }
    val amount = serviceCostAmountInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount, tipPercent)
    val total = calculateTotal(amount, tip)

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.tip_calculator_heading),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { serviceCostAmountInput = (1..100).random().toString() },
                modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.generate_random_bill))
            }
        }
        EditServiceCostField(value = serviceCostAmountInput,
            onValueChange = { serviceCostAmountInput = it }
        )
        Spacer(Modifier.height(24.dp))
        Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Row() {
                Button(onClick = {tipPercent=10.0}) {
                    Text(
                        text = stringResource(R.string.ten),
                    )
                }
                Spacer(Modifier.width(24.dp))
                Button(onClick = {tipPercent=15.0}) {
                    Text(
                        text = stringResource(R.string.fifteen),
                    )
                }
            }
        }
        Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Row() {
                Button(onClick = { tipPercent=18.0}) {
                    Text(
                        text = stringResource(R.string.eighteen),
                    )
                }
                Spacer(Modifier.width(24.dp))
                Button(onClick = { tipPercent=20.0}) {
                    Text(
                        text = stringResource(R.string.twenty),
                    )
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.bill_amount, NumberFormat.getCurrencyInstance().format(amount)),
            modifier = Modifier.align(Alignment.End),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.tip_amount, NumberFormat.getCurrencyInstance().format(tip)),
            modifier = Modifier.align(Alignment.End),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Divider(color = Color.Gray, thickness = 1.dp, startIndent = 16.dp)
        Text(
            text = stringResource(R.string.total, NumberFormat.getCurrencyInstance().format(total)),
            modifier = Modifier.align(Alignment.End),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EditServiceCostField(value: String,
                         onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.cost_of_service)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

private fun calculateTip(
    amount: Double,
    tipPercent: Double
): Double {
    val tip = tipPercent / 100 * amount
    return tip
}

private fun calculateTotal(
    amount: Double,
    tip: Double
): Double {
    val total = tip + amount
    return total
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}