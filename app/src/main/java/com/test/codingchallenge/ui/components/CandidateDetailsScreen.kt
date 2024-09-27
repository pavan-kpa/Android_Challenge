package com.test.codingchallenge.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.test.codingchallenge.ui.CandidateViewModel

@Composable
fun CandidateDetails(
    viewModel: CandidateViewModel
) {
    val data = viewModel.candidateDetail.value
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "${data?.id}")
        Text(text = "${data?.name}")
        Text(text = "${data?.email}")
        Text(text = "${data?.birthDate}")
        Text(text = "${data?.yearsOfExperience}")
        Text(text = "${data?.positionApplied}")
        Text(text = "${data?.status}")
    }
}