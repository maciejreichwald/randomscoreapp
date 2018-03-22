package com.rudearts.randomscore.domain

import com.rudearts.randomscore.scores.ScoresController
import javax.inject.Inject


class ResetScoresUseCase @Inject constructor(
        val scoresController: ScoresController) {


    fun reset() {
        scoresController.resetScores()
    }

}