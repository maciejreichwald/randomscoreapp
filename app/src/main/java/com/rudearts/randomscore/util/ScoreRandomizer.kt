package com.rudearts.randomscore.util

import android.util.Log
import com.rudearts.randomscore.model.ScoreItem
import com.rudearts.randomscore.model.ScoreItemType
import java.util.*

class ScoreRandomizer {

    companion object {
        private val MIN_ELEMENTS_COUNT = 5
    }

    private val random = Random()

    fun randomize(scores:MutableList<ScoreItem>):List<ScoreItem> = when(scores.size) {
        MIN_ELEMENTS_COUNT -> randomizeScores(scores)
        else -> addRandomElement(scores)
    }

    internal fun addRandomElement(scores: MutableList<ScoreItem>):List<ScoreItem> {
        val score = ScoreItem(type = randomType())
        scores.add(score)
        return scores
    }

    private fun randomType() = when(random.nextInt(2)) {
        0 -> ScoreItemType.BLUE
        else -> ScoreItemType.RED
    }

    internal fun randomizeScores(scores: MutableList<ScoreItem>):List<ScoreItem> = when(random.nextInt(100)) {
        in 0..49 -> increaseRandomScore(scores)
        in 50..84 -> resetRandomScore(scores)
        in 85..94 -> deleteRandomScore(scores)
        else -> mergeRandomScore(scores)
    }

    private fun mergeRandomScore(scores: MutableList<ScoreItem>): List<ScoreItem> {
        val index = randomizeIndex(scores)
        val scoreItem = scores[index]
        val prevScoreItem = itemByPrevIndex(scores, index)

        val mergedScore = scoreItem.score + prevScoreItem.score
        scores[index] = scoreItem.copy(score = mergedScore)

        Log.d("Randomizer", "merge: $index, $mergedScore")
        return scores
    }

    private fun itemByPrevIndex(scores: MutableList<ScoreItem>, index: Int)=  when(index) {
        0 -> scores.last()
        else -> scores[index-1]
    }

    private fun resetRandomScore(scores: MutableList<ScoreItem>): List<ScoreItem> {
        val index = randomizeIndex(scores)
        val scoreItem = scores[index]
        scores[index] = scoreItem.copy(score = 0)

        Log.d("Randomizer", "reset: $index")
        return scores
    }

    private fun deleteRandomScore(scores: MutableList<ScoreItem>): List<ScoreItem> {
        val index = randomizeIndex(scores)
        scores.removeAt(index)

        Log.d("Randomizer", "delete: $index")
        return scores
    }

    private fun increaseRandomScore(scores: MutableList<ScoreItem>): List<ScoreItem> {
        val index = randomizeIndex(scores)
        val scoreItem = scores[index]
        scores[index] = scoreItem.copy(score = scoreItem.score+1)

        Log.d("Randomizer", "increase: $index, ${scores[index].score}")
        return scores
    }

    private fun randomizeIndex(scores: MutableList<ScoreItem>) = random.nextInt(scores.size-1)

}