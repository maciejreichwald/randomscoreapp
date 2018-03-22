package com.rudearts.randomscore.scores

import com.rudearts.randomscore.model.ScoreItem

class ScoresController {

    val scores:MutableList<ScoreItem> = mutableListOf()

    fun resetScores() {
        scores.removeAll { it != null }
    }

}