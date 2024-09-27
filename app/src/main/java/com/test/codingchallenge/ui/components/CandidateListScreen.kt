package com.test.codingchallenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.codingchallenge.data.Candidate
import com.test.codingchallenge.ui.CandidateViewModel
import com.test.codingchallenge.ui.theme.CodingChallengeTheme
import com.test.codingchallenge.util.DateUtil
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun CandidateList(
    viewModel: CandidateViewModel,
    onCandidatePressedCallback: (Candidate) -> Unit,
    modifier: Modifier,
) {

    // Launch effect to retrieve the candidate list
    LaunchedEffect(Unit) {
        viewModel.retrieveCandidates()
    }

    if (viewModel.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = modifier
                    .width(48.dp)
            )
        }
    } else {
        if (viewModel.errorMessage.isEmpty()) {
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                // Search bar to filter candidates by name
                TextField(
                    value = viewModel.searchQuery,
                    onValueChange = { viewModel.searchQuery = it },
                    label = { Text("Search by Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                // Display filtered list of candidates
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                ) {
                    items(viewModel.filteredCandidates) { currentCandidate ->
                        CandidateRow(
                            candidate = currentCandidate,
                            modifier = modifier.clickable(onClick = {
                                onCandidatePressedCallback(currentCandidate)
                            })
                        )
                    }
                }
            }
        } else {
            // Error message with retry button
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = viewModel.errorMessage)
                Button(onClick = { viewModel.retrieveCandidates() }) {
                    Text(text = "Retry")
                }
            }
        }
    }
}

@Composable
fun CandidateRow(candidate: Candidate, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = candidate.name,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Row {
            Column(
                modifier = modifier
                    .widthIn(0.dp, 150.dp)
            ) {
                InfoItem(key = "Position", value = candidate.positionApplied)
                InfoItem(key = "Age", value = "${DateUtil.calculateAge(candidate.birthDate)}")
                InfoItem(key = "email", value = candidate.email)
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                InfoItem(key = "Application Date", value = candidate.applicationDate)
                InfoItem(key = "Years of Experience", value = "${candidate.yearsOfExperience}")
                StatusPill(candidate.status)
            }
        }
        Divider(Modifier.padding(vertical = 10.dp))
    }
}

@Composable
fun InfoItem(key: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(text = "$key:", fontWeight = FontWeight.Bold, maxLines = 1, fontSize = 15.sp)
        Text(text = value, overflow = TextOverflow.Ellipsis, maxLines = 1, fontSize = 15.sp)
    }
}

@Composable
fun StatusPill(status: String) {
    val background = when (status) {
        "approved" -> Color.Green
        "waiting" -> Color.Yellow
        "rejected" -> Color.Red
        else -> Color.Unspecified
    }

    Column(modifier = Modifier.wrapContentSize(Alignment.Center)) {
        Box(
            modifier = Modifier
                .background(background, RoundedCornerShape(10.dp))
        ) {
            Text(status.capitalize(), Modifier.padding(horizontal = 10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CandidateRowPreview() {
    val formatter = SimpleDateFormat("MM/dd/yyyy")
    val candidate = Candidate(
        id = 0,
        name = "Sean Nixon",
        email = "sean.nixon@testuser.de",
        birthDate = formatter.format(Date()),
        positionApplied = "Sales Agent",
        applicationDate = formatter.format(Date()),
        yearsOfExperience = 2,
        status = "waiting"
    )

    CodingChallengeTheme {
        CandidateRow(candidate = candidate)
    }
}
