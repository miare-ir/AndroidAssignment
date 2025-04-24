package ir.miare.androidcodechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.miare.androidcodechallenge.theme.Black
import ir.miare.androidcodechallenge.theme.Gray
import ir.miare.androidcodechallenge.theme.Indigo_200
import ir.miare.androidcodechallenge.theme.Indigo_700
import ir.miare.androidcodechallenge.theme.LightGray
import ir.miare.androidcodechallenge.theme.MiareTheme
import ir.miare.androidcodechallenge.theme.Teal_700

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MiareTheme{
                val (state, event) = use(viewModel = viewModel)
                RankingScreen(state, event)
            }
        }
    }
}

@Composable
fun RankingScreen(
    state: MainContract.State,
    event: (MainContract.Event) -> Unit
) {

    var showPlayerDetailsBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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

        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
        ) {

            items(state.topPlayers){
                TopItem(fakeData = it, onPlayerClicked = {
                    showPlayerDetailsBottomSheet = true
                })
            }
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
            modifier = Modifier.fillMaxWidth()
                .background(color = Gray).padding(16.dp),
            text = fakeData.league.name.plus(" - ")
                .plus(fakeData.league.country),
            textAlign = TextAlign.Center,
            fontSize = 18.sp
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
        modifier = Modifier.fillMaxWidth()
            .clickable { onPlayerClicked(player) },
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
                fontSize = 12.sp
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
            text = stringResource(R.string.sort_title)
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

