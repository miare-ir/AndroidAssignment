package ir.miare.androidcodechallenge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import ir.miare.androidcodechallenge.R
import ir.miare.androidcodechallenge.domain.FakeData
import ir.miare.androidcodechallenge.domain.Player
import ir.miare.androidcodechallenge.theme.Black
import ir.miare.androidcodechallenge.theme.Gray
import ir.miare.androidcodechallenge.theme.Indigo_200
import ir.miare.androidcodechallenge.theme.Indigo_500
import ir.miare.androidcodechallenge.theme.Indigo_700
import ir.miare.androidcodechallenge.theme.LightGray
import ir.miare.androidcodechallenge.theme.MiareTheme
import ir.miare.androidcodechallenge.theme.Teal_700
import ir.miare.androidcodechallenge.theme.White
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MiareTheme{
                val (state, event) = use(viewModel = viewModel)
                RankingScreen(
                    state = state,
                    event = event,
                    viewModel = viewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(
    state: MainContract.State,
    event: (MainContract.Event) -> Unit,
    viewModel: MainViewModel
) {

    var showPlayerDetailsBottomSheet by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val showLoading = viewModel.showLoading.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            SortingSection(
                state = state,
                event = event,
                onSortTypeSelected = {
                    event.invoke(MainContract.Event.OnSortTypeSelected(it))
                }
            )
        }
    ) { innerPadding ->

        val lifecycle = LocalLifecycleOwner.current.lifecycle
        LaunchedEffect("events") {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.errorMessage.collectLatest { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }

        if (showPlayerDetailsBottomSheet){

            ModalBottomSheet(
                onDismissRequest = { showPlayerDetailsBottomSheet = false },
                modifier = Modifier,
                sheetState = SheetState(skipPartiallyExpanded = false),
                shape =  RectangleShape,
                containerColor = Color.White,
                dragHandle = {}
            ) {
                BottomSheetContent(
                    state = state,
                    onDisMissRequest = {
                        showPlayerDetailsBottomSheet = false
                    }
                )
            }
        }


        val sortedList = viewModel.sortedList.collectAsState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {

            items(sortedList.value){
                TopItem(fakeData = it, onPlayerClicked = {
                    event.invoke(MainContract.Event.OnPlayerClicked(it))
                    showPlayerDetailsBottomSheet = true
                })
            }
        }


        if (showLoading.value){

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun BottomSheetContent(
    state: MainContract.State,
    onDisMissRequest: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = state.selectedPlayer?.name?:"",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Black
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp,Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
            ) {
            Text(
                modifier = Modifier,
                text = stringResource(R.string.club),
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                color = Gray
            )

            Text(
                modifier = Modifier,
                text = state.selectedPlayer?.team?.name?:"",
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                color = Black
            )
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp,Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(R.string.goals),
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                color = Gray
            )

            Text(
                modifier = Modifier,
                text = state.selectedPlayer?.totalGoal.toString(),
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                color = Black
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 32.dp, end = 32.dp, bottom = 32.dp),
            colors = ButtonColors(
                containerColor = Indigo_500,
                disabledContainerColor = Indigo_500,
                contentColor = White,
                disabledContentColor = White
            ),
            shape = RectangleShape,
            onClick = {
                onDisMissRequest()
            }
        ) {
            Text(
                text = stringResource(R.string.back),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }


    }
}

@Composable
fun TopItem(
    fakeData: FakeData,
    onPlayerClicked: (Player) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = LightGray)
                .padding(16.dp),
            text = fakeData.league.name.plus(" - ")
                .plus(fakeData.league.country),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = Gray
        )

        for(i in 0..2){
            PlayerItem(
                fakeData.players[i],
                onPlayerClicked = { onPlayerClicked(it) }
            )
        }
    }
}

@Composable
fun PlayerItem(
    player: Player,
    onPlayerClicked: (Player) -> Unit
) {

    Row(
        modifier = Modifier
            .clickable { onPlayerClicked(player) }
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier.padding(16.dp),
            text = player.team.rank.toString(),
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            color = Black
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = player.name,
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                color = Black
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = player.team.name,
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
                color = Gray
            )
        }
    }

    HorizontalDivider(thickness = 1.dp, color = LightGray)
}

@Composable
fun SortingSection(
    state: MainContract.State,
    event: (MainContract.Event) -> Unit,
    onSortTypeSelected: (SortType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Indigo_200)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            text = stringResource(R.string.sort_title),
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(16.dp))

        val radioOptions = SortType.entries
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(state.selectedSortType) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            radioOptions.forEach { sortEntry ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (sortEntry.title == selectedOption.title),
                            onClick = {
                                onOptionSelected(sortEntry)
                                onSortTypeSelected(sortEntry)
                            }
                        ),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = (sortEntry == selectedOption),
                        onClick = {
                            onOptionSelected(sortEntry)
                            onSortTypeSelected(sortEntry)
                        },
                        modifier = Modifier,
                        colors = RadioButtonColors(
                            selectedColor = Teal_700,
                            unselectedColor = RadioButtonDefaults.colors().unselectedColor,
                            disabledSelectedColor = RadioButtonDefaults.colors().disabledSelectedColor,
                            disabledUnselectedColor = RadioButtonDefaults.colors().disabledUnselectedColor
                        ),
                        enabled = true,
                    )

                    Text(
                        text = sortEntry.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        textAlign = TextAlign.Start,
                        color = Indigo_700,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

    }
}

