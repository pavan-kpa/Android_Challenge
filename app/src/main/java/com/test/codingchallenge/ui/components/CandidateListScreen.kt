package com.test.codingchallenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun CandidateList(
    viewModel: CandidateViewModel,
    onCandidatePressedCallback: (Candidate) -> Unit,
    modifier: Modifier,
) {
    // Observe the filtered list and search query state from the ViewModel
    val candidateList by viewModel.candidateList
    val searchQuery by viewModel.searchQuery

    LaunchedEffect(Unit, block = {
        viewModel.retrieveCandidates()
    })

    val scope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    // State to manage the selected radio button
    var selectedNameOption by remember { mutableStateOf("ASC") }
    var selectedPOSOption by remember { mutableStateOf("ASC") }

    var selectedYOEOption by remember { mutableStateOf("ASC") }

    var selectedDOAOption by remember { mutableStateOf("ASC") }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            // Content of the Bottom Sheet
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Sort", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))
                // Multiselect Buttons
                MultiSelectButtons(viewModel)
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
//                    Button(onClick = {
//                        scope.launch {
//                            if (bottomSheetScaffoldState.bottomSheetState.hasExpandedState) {
//                                bottomSheetScaffoldState.bottomSheetState.hide()
//                            } else {
//                                bottomSheetScaffoldState.bottomSheetState.partialExpand() // Use expand if needed
//                            }
//                        }
//                    }) {
//                        Text("Close")
//                    }
                    Button(onClick = {
                        //scope.launch { bottomSheetScaffoldState.bottomSheetState.hide() }
                        viewModel.onApply()
                    }) {
                        Text("Apply")
                    }
                }
                Text("Filter", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Column {
                        Text("Name", style = MaterialTheme.typography.titleSmall)
                        Row {
                            RadioButton(
                                selected = selectedNameOption == "ASC",
                                onClick = {
                                    selectedNameOption = "ASC"
                                    viewModel.nameSort("ASC")
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Asc")
                        }
                        Row {
                            RadioButton(
                                selected = selectedNameOption == "DSC",
                                onClick = {
                                    selectedNameOption = "DSC"
                                    viewModel.nameSort("DSC")
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Dsc")
                        }
                    }
                    Column {
                        Text("POA", style = MaterialTheme.typography.titleSmall)
                        Row {
                            RadioButton(
                                selected = selectedPOSOption == "ASC",
                                onClick = {
                                    selectedPOSOption = "ASC"
                                    viewModel.positionSort("ASC")
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Asc")
                        }
                        Row {
                            RadioButton(
                                selected = selectedPOSOption == "DSC",
                                onClick = {
                                    selectedPOSOption = "DSC"
                                    viewModel.positionSort("DSC")
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Dsc")
                        }
                    }
                    Column {
                        Text("YOE", style = MaterialTheme.typography.titleSmall)
                        Row {
                            RadioButton(
                                selected = selectedYOEOption == "ASC",
                                onClick = {
                                    selectedYOEOption = "ASC"
                                    viewModel.yoeSort("ASC")
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Asc")
                        }
                        Row {
                            RadioButton(
                                selected = selectedYOEOption == "DSC",
                                onClick = {
                                    selectedYOEOption = "DSC"
                                    viewModel.yoeSort("DSC")
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Dsc")
                        }
                    }
                    Column {
                        Text("DOA", style = MaterialTheme.typography.titleSmall)
                        Row {
                            RadioButton(
                                selected = selectedDOAOption == "ASC",
                                onClick = {
                                    selectedDOAOption = "ASC"
                                    viewModel.dateSort("ASC")
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Asc")
                        }
                        Row {
                            RadioButton(
                                selected = selectedDOAOption == "DSC",
                                onClick = {
                                    selectedDOAOption = "DSC"
                                    viewModel.dateSort("DSC")
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Dsc")
                        }
                    }
                }
            }
        }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchQueryChanged(it) },
                    label = { Text("Search") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))
                // Button to open Bottom Sheet
                Button(onClick = {
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                }) {
                    Text("Filter")
                }
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
                    LazyColumn(
                        modifier = modifier.fillMaxSize(),
                    ) {
                        items(candidateList) { currentCandidate ->
                            CandidateRow(
                                candidate = currentCandidate,
                                modifier = modifier.clickable(onClick = {
                                    onCandidatePressedCallback(currentCandidate)
                                })
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = viewModel.errorMessage)
                        Button(onClick = { viewModel.retrieveCandidates() }) {
                            Text(text = "retry")
                        }
                    }
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
        Text(text = "${key}:", fontWeight = FontWeight.Bold, maxLines = 1, fontSize = 15.sp)
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

    Column(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
    ) {
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
    var candidate = Candidate(
        0,
        "Sean Nixon",
        "sean.nixon@testuser.de",
        formatter.format(Date()),
        "Sales Agent",
        formatter.format(Date()),
        2,
        "waiting"
    )

    CodingChallengeTheme {
        CandidateRow(candidate = candidate)
    }
}

@Composable
fun MultiSelectButtons(listViewModel: CandidateViewModel) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // First Multiselect Button
            OutlinedButton(
                onClick = { listViewModel.toggleApproved() },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (listViewModel.isApproved.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            ) {
                Text("Approved")
            }

            // Second Multiselect Button
            OutlinedButton(
                onClick = { listViewModel.toggleReject() },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (listViewModel.isReject.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            ) {
                Text("Rejected")
            }

            // Third Multiselect Button
            OutlinedButton(
                onClick = { listViewModel.toggleWaiting() },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (listViewModel.isWaiting.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            ) {
                Text("Waiting")
            }
        }
    }
}