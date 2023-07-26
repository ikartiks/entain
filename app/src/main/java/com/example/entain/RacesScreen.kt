package com.example.entain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.entain.api.pojos.AdvertisedStart
import com.example.entain.api.pojos.RaceDetails

@Composable
fun RacesScreen(vm:RacesViewModel){

    Column(modifier = Modifier.padding(20.dp)) {
        RadioButtonSample(vm)
        RacesList(vm = vm)
    }

}

@Composable
fun RadioButtonSample(vm:RacesViewModel) {

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
                    modifier = Modifier.padding(start = 16.dp,top= 10.dp)
                )
            }
        }
    }
}

@Composable
fun RacesList(vm:RacesViewModel){

    val data = vm.racesFlow.collectAsState()
    if(data.value.isNotEmpty()){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp10)),
            modifier = Modifier.padding(top = 20.dp)
        ) {
            items(items = data.value){raceDetailsDisplay->
                SingleRaceComposable(raceDetailsDisplay)
            }
        }
    }
    LaunchedEffect(key1 = Unit ){
        vm.doStuff()
    }
}

@Composable
@Preview
fun SingleRaceComposable(@PreviewParameter(DummyRaceDetailsPreviewProvider::class)  raceDetailsDisplay: RaceDetailsDisplay,
                         modifier:Modifier = Modifier){
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
        AppText(raceDetailsDisplay.raceDetails.raceName)
        AppText(raceDetailsDisplay.raceDetails.venueName)
        AppText(raceDetailsDisplay.startTime)
        AppText(raceDetailsDisplay.raceCategory.name)
    }

}

class DummyRaceDetailsPreviewProvider : PreviewParameterProvider<RaceDetailsDisplay> {

    override val values = sequenceOf(
        RaceDetailsDisplay(
            RaceDetails("1", "Fake race name", 1,
            "asdads","asdad","1", AdvertisedStart(50000), null,
            "Au","Australia","QLD","AU"),
            "05:01:01", false,RaceCategory.Harness
        )
    )
}


