package com.rudearts.randomscore.util

import android.util.Log
import com.rudearts.randomscore.model.ScoreItem
import com.rudearts.randomscore.model.ScoreItemType
import java.util.*

class ScoreRandomizer {

    companion object {
        private const val TAG = "Randomizer"
        private const val MIN_ELEMENTS_COUNT = 5
        private const val PERCENTAGE_MAX = 100
        private const val INCREASE_VALUE = 1
        private val SCORE_ITEM_TYPES_COUNT = ScoreItemType.values().size
    }

    private val random = Random()

    fun randomize(scores:MutableList<ScoreItem>) = when(scores.size) {
        MIN_ELEMENTS_COUNT -> randomizeScores(scores)
        else -> addRandomElement(scores)
    }

    internal fun addRandomElement(scores: MutableList<ScoreItem>) = scores.apply {
        val score = ScoreItem(type = randomType())
        add(score)
    }

    private fun randomType() = when(randomNextInt(SCORE_ITEM_TYPES_COUNT)) {
        0 -> ScoreItemType.BLUE
        else -> ScoreItemType.RED
    }

    internal fun randomizeScores(scores: MutableList<ScoreItem>) = when(randomNextInt(PERCENTAGE_MAX)) {
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
        val multipliedMergedScore = mergedScore * scoreItem.type.multiplier
        scores[index] = scoreItem.copy(score = mergedScore, visibleScore = multipliedMergedScore)

        Log.d(TAG, "merge: $index, $mergedScore")
        return scores
    }

    private fun itemByPrevIndex(scores: MutableList<ScoreItem>, index: Int)=  when(index) {
        0 -> scores.last()
        else -> scores[index-1]
    }

    private fun resetRandomScore(scores: MutableList<ScoreItem>): List<ScoreItem> {
        val index = randomizeIndex(scores)
        val scoreItem = scores[index]
        scores[index] = scoreItem.copy(score = 0, visibleScore = 0)

        Log.d(TAG, "reset: $index")
        return scores
    }

    private fun deleteRandomScore(scores: MutableList<ScoreItem>): List<ScoreItem> {
        val index = randomizeIndex(scores)
        scores.removeAt(index)

        Log.d(TAG, "delete: $index")
        return scores
    }

    private fun increaseRandomScore(scores: MutableList<ScoreItem>): List<ScoreItem> {
        val index = randomizeIndex(scores)
        val scoreItem = scores[index]
        val increasedScore = scoreItem.score + INCREASE_VALUE
        val multipliedScore = increasedScore * scoreItem.type.multiplier
        scores[index] = scoreItem.copy(score = increasedScore, visibleScore = multipliedScore)

        Log.d(TAG, "increase: $index, ${scores[index].score}")
        return scores
    }

    private fun randomizeIndex(scores: MutableList<ScoreItem>) = randomNextInt(scores.size-1)

    private fun randomNextInt(max:Int) = random.nextInt(max)

}