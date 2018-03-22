package com.rudearts.randomscore.domain

import com.rudearts.randomscore.model.ScoreItem
import com.rudearts.randomscore.scores.ScoresController
import com.rudearts.randomscore.util.ScoreRandomizer
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RandomizeScoreUseCase @Inject constructor(
        val scoresController: ScoresController,
        val randomizer: ScoreRandomizer) {

    companion object {
        private const val INTERVAL = 1L
    }

    fun start():Observable<List<ScoreItem>>
            = Observable.interval(INTERVAL, TimeUnit.SECONDS)
            .map { randomizeScores() }

    private fun randomizeScores() = randomizer.randomize(scoresController.scores)

}