package com.example.entain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.entain.api.pojos.AdvertisedStart
import com.example.entain.api.pojos.RaceDetails
import java.util.Date
import kotlinx.coroutines.delay


@Composable
fun RacesScreen(vm: RacesViewModel) {

    Column(modifier = Modifier.padding(20.dp)) {
        RadioButtonSample(vm)
        RacesList(vm = vm)
    }

}

@Composable
fun RadioButtonSample(vm: RacesViewModel) {

    AppText("Filters:")
    val radioOptions = RaceCategory.values()
    val selectedOption = vm.filter.collectAsState()

    Column {
        radioOptions.forEach { raceCategory ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (raceCategory == selectedOption.value),
                        onClick = {
                            vm.updateFilter(raceCategory)
                        }
                    )
                    .padding(horizontal = 16.dp)
            ) {
                RadioButton(
                    selected = (raceCategory == selectedOption.value),
                    onClick = { vm.updateFilter(raceCategory) }
                )
                AppText(
                    text = raceCategory.name,
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp)
                )
            }
        }
    }
}

@Composable
fun RacesList(vm: RacesViewModel) {

    val data = vm.racesFlow.collectAsState()
    if (data.value.isNotEmpty()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp10)),
            modifier = Modifier.padding(top = 20.dp)
        ) {
            items(items = data.value) { raceDetailsDisplay ->
                SingleRaceComposable(raceDetailsDisplay, vm)
            }
        }
    }else{
        Column(
            modifier= Modifier.fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            val strokeWidth = 5.dp
            CircularProgressIndicator(
                modifier = Modifier
                    .drawBehind {
                        drawCircle(
                            Color.Blue,
                            radius = size.width / 2 - strokeWidth.toPx() / 2,
                            style = Stroke(strokeWidth.toPx())
                        )
                    },
                color = Color.LightGray,
                strokeWidth = strokeWidth
            )
        }
    }
    LaunchedEffect(key1 = Unit) {
        vm.fetchLatestData()
    }
}

@Composable
fun SingleRaceComposable(
    raceDetailsDisplay: RaceDetailsDisplay,
    vm: RacesViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = colorResource(id = R.color.purple_200),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp10))
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(dimensionResource(id = R.dimen.dp15)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppText("Meeting name: ${raceDetailsDisplay.raceDetails.meetingName}")
        AppText("Race no: ${raceDetailsDisplay.raceDetails.raceNumber}")
        AppText(raceDetailsDisplay.startTime)
        AppText(raceDetailsDisplay.raceCategory.name)

        val now = Date()
        var timeInMillis = raceDetailsDisplay.raceDetails.advertisedStart.seconds.toLong() * 1000L
        val raceDate = Date(timeInMillis)

        var timer = remember { mutableStateOf((raceDate.time - now.time) / 1000) }
        LaunchedEffect(key1 = raceDetailsDisplay.raceDetails.raceId) {
            while (true) {
                if (timer.value > -60) {
                    delay(1000)
                    timer.value -= 1
                } else {
                    vm.fetchLatestData()
                    break
                }
            }
        }
        if (timer.value > 0) {
            AppText(
                text = "timer ${timer.value}"
            )
        } else {
            AppText(
                text = "Game started"
            )
        }
    }

}

class DummyRaceDetailsPreviewProvider : PreviewParameterProvider<RaceDetailsDisplay> {

    override val values = sequenceOf(
        RaceDetailsDisplay(
            RaceDetails(
                "1", "Fake race name", 1,
                "asdads", "asdad", "1", AdvertisedStart(50000), null,
                "Au", "Australia", "QLD", "AU"
            ),
            "05:01:01", false, RaceCategory.Harness
        )
    )
}


