package com.rudearts.randomscore.ui.main

import com.rudearts.randomscore.model.PlayingState
import com.rudearts.randomscore.model.ScoreItem

interface MainContract {

    interface View {
        fun updatePlayingState(state: PlayingState)
        fun updateItems(items: List<ScoreItem>)
    }

    interface Presenter {
        fun start()
        fun stop()
    }
}