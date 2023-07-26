package com.example.entain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.entain.api.pojos.AdvertisedStart
import com.example.entain.api.pojos.RaceDetails

@Composable
fun RacesScreen(vm:RacesViewModel){

    val data = vm.racesFlow.collectAsState()
    if(data.value.isNotEmpty()){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp10)),
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


