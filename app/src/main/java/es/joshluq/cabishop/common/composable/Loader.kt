package es.joshluq.cabishop.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import es.joshluq.cabishop.ui.theme.CabiShopTheme

@Composable
fun LoaderDialog(onDismiss: () -> Unit = {}) {

    Dialog(
        onDismissRequest = onDismiss,
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
                Text(
                    text = "Loading... Please wait.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(8.dp), textAlign = TextAlign.Center
                )

                CircularProgressIndicator(
                    strokeWidth = 4.dp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun LoadingDialogPreview() {
    CabiShopTheme {
        LoaderDialog {

        }
    }
}