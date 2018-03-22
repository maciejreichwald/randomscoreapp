package com.rudearts.randomscore.model

import com.rudearts.randomscore.R

enum class PlayingState constructor(val stopId:Int) {
    STARTED(R.string.stop), STOPPED(R.string.reset)
}