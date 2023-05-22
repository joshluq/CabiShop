package es.joshluq.cabishop.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import es.joshluq.cabishop.ui.theme.CabiShopTheme

@Composable
fun SimpleDialog(
    description: String = "",
    buttonText: String = "Ok",
    onDismissRequest: () -> Unit = {},
    onClickButton: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier,
        ) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(12.dp)
            ) {

                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(8.dp), textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onClickButton
                    ) {
                        Text(text = buttonText)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SimpleDialogPreview() {
    CabiShopTheme {
        Column {
            SimpleDialog(description = "Description") {

            }
        }
    }
}