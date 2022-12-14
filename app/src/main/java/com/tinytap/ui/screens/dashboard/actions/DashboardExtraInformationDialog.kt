package com.tinytap.ui.screens.dashboard.actions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tinytap.R
import com.tinytap.core.ui.TinyTapAlertDialog
import com.tinytap.model.ui_models.DashboardCardModel

@Composable
fun DashboardExtraInformationDialog(
    model: DashboardCardModel,
    onUserRequestedDismiss: () -> Unit
) {
    TinyTapAlertDialog(
        title = {
            Surface(
                color = Color.Blue,
                contentColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text(
                    text = stringResource(
                        id = R.string.dashboard_extra_information_dialog_Title,
                        model.title
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                )
            }
        },
        text = {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(
                        R.string.dashboard_extra_information_dialog_author,
                        model.author
                    )
                )
            }
        },
        onDismissRequest = {
            onUserRequestedDismiss()
        },
        confirmButton = {
            Button(onClick = { onUserRequestedDismiss() }) {
                Text(text = stringResource(R.string.dashboard_extra_information_dialog_dismiss))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DashboardExtraInformationDialogPreview() {
    DashboardExtraInformationDialog(
        DashboardCardModel(
            "", "https://",
            "TinyTap", "TinyTap Games"
        )
    ) {}
}