package com.example.apteczka.drugs.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun DrugProgressIndicator(
    total: Int,
    remaining: Int,
    modifier: Modifier = Modifier
) {
    val percentage = if (total > 0) remaining.toFloat() / total else 0f

    // Pobierz kolor tutaj, w composable
    val primaryColor = MaterialTheme.colorScheme.primary

    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        Canvas(modifier = Modifier.size(100.dp)) {
            drawArc(
                color = Color.LightGray,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 16f)
            )
            drawArc(
                color = primaryColor,  // Używaj już pobranego koloru
                startAngle = -90f,
                sweepAngle = 360f * percentage,
                useCenter = false,
                style = Stroke(width = 16f, cap = StrokeCap.Round)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${(percentage * 100).roundToInt()}% pozostało",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

