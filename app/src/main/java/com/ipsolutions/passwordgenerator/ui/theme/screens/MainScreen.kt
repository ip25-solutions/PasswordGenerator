package com.ipsolutions.passwordgenerator.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ipsolutions.passwordgenerator.ui.theme.states.PasswordViewModel

@Composable
fun MainScreen(vm: PasswordViewModel = viewModel()) {
    val passwordState by vm.password.collectAsState()
    val lengthState by vm.passwordLength.collectAsState()
    val includeNumbers by vm.includeNumbers.collectAsState()
    val includeSymbols by vm.includeSymbols.collectAsState()
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    // Fondo con degradado
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFB0E0E6), Color(0xFF838383))
                )
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF292929))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF383838)),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = passwordState,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )

                    IconButton(onClick = {
                        clipboardManager.setText(AnnotatedString(passwordState))
                        Toast.makeText(context, "Contraseña copiada", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Copiar", tint = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Longitud: $lengthState", color = Color.White, fontSize = 16.sp)
            Slider(
                value = lengthState.toFloat(),
                onValueChange = { vm.updateLength(it.toInt()) },
                valueRange = 4f..20f,
                steps = 16,
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFE67E22),
                    activeTrackColor = Color(0xFFE67E22),
                    inactiveTrackColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Incluir Números", color = Color.White, fontSize = 16.sp)
                Switch(
                    checked = includeNumbers,
                    onCheckedChange = { vm.toggleNumbers() },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFE67E22))
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Incluir Símbolos", color = Color.White, fontSize = 16.sp)
                Switch(
                    checked = includeSymbols,
                    onCheckedChange = { vm.toggleSymbols() },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFE67E22))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { vm.generatePassword() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE67E22),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .shadow(8.dp, RoundedCornerShape(12.dp))
            ) {
                Text(text = "Generar Contraseña", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

