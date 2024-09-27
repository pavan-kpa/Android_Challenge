package com.test.codingchallenge

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.test.codingchallenge.ui.CandidateViewModel
import com.test.codingchallenge.ui.components.CandidateDetails
import com.test.codingchallenge.ui.components.CandidateList


enum class CandidatesScreen(@StringRes val title: Int) {
    List(title = R.string.candidate_list),
    Details(title = R.string.candidate_details)
}

@Composable
fun CandidatesAppBar(
    currentScreen: CandidatesScreen,
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun CandidatesApp(
    vm: CandidateViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CandidatesScreen.valueOf(
        backStackEntry?.destination?.route ?: CandidatesScreen.List.name
    )

    Scaffold(
        topBar = {
            CandidatesAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CandidatesScreen.List.name,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(route = CandidatesScreen.List.name) {
                CandidateList(
                    viewModel = vm,
                    modifier = Modifier,
                    onCandidatePressedCallback = {
                        vm.setCandidateDetail(it)
                        navController.navigate(CandidatesScreen.Details.name)
                    })
            }

            composable(route = CandidatesScreen.Details.name) {
                CandidateDetails(viewModel = vm)
            }
        }
    }
}
