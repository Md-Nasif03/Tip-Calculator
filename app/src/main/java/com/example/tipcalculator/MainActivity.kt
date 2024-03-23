package com.example.tipcalculator

import android.health.connect.datatypes.units.Percentage
import android.icu.util.CurrencyAmount
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipCalculator()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TipCalculator(){
    var amount by remember {
        mutableStateOf("")
    }
    var percentage by remember {
        mutableStateOf("")
    }
    var result by remember {
        mutableStateOf("0")
    }
    var roundoff by remember {
        mutableStateOf(false)
    }
    var billAmount=amount.toDoubleOrNull()?:0.0
    var tipPercentage=percentage.toDoubleOrNull()?:0.0
    var control= LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Tip Calcultor",
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.headlineMedium
            )
        editText(
            value = amount,
            onValuechanged = {amount=it},
            label = "Total amount",
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
                )
        )
        editText(value = percentage,
            onValuechanged = {percentage=it},
            label ="tip percentage" ,
            keyboardOptions =KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
                )
            )
        roundoff(check = roundoff, onCheck ={roundoff=it;
            result=calculate(billAmount,tipPercentage,roundoff)})

        Button(onClick = { result=calculate(billAmount,tipPercentage,roundoff);
                         control?.hide()
                         },
            modifier = Modifier.padding(15.dp)
            ) {
            Text(text = "Calculate")
        }

        Text(text = "Tip amount: $$result",
            modifier = Modifier.padding(10.dp)
            )
    }
}
@Composable
fun editText(
    value: String,
    onValuechanged:(String)->Unit,
    label:String,
    keyboardOptions: KeyboardOptions

){
    OutlinedTextField(value = value, onValueChange =onValuechanged,
        label = { Text(text = label)},
        modifier = Modifier.padding(5.dp,40.dp,15.dp,5.dp),
        keyboardOptions = keyboardOptions
    )
}
@VisibleForTesting
fun calculate(
    amount: Double,
    percentage: Double,
    roundoff:Boolean):String{
    var tip=amount*percentage/100
    if (roundoff){
        var round_off =round(tip)
        return round_off.toString()
    }else{
        return tip.toString()
    }

}
@Composable
fun roundoff(
    check:Boolean,
    onCheck:(Boolean)->Unit,
    modifier: Modifier=Modifier
){
    Row (
        modifier= Modifier
            .fillMaxWidth()
            .wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "Round off")
        Switch(checked =check , onCheckedChange = onCheck,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            )
    }
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorPreview(){
    TipCalculatorTheme {
        TipCalculator()
    }
}


