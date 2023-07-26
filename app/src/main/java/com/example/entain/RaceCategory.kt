package com.example.entain

enum class RaceCategory(val category:String) {
    Greyhound("9daef0d7-bf3c-4f50-921d-8e818c60fe61"),
    Harness("161d9be2-e909-4326-8c2c-35ed71fb460b"),
    Horse("4a2788f8-e825-4d36-9894-efd4baf1cfae"),
    NONE("")
}

fun String.getFromCategory():RaceCategory{
    return when(this){
        RaceCategory.Greyhound.category -> RaceCategory.Greyhound
        RaceCategory.Harness.category -> RaceCategory.Harness
        RaceCategory.Horse.category -> RaceCategory.Horse
        else ->{
            RaceCategory.NONE
        }
    }
}