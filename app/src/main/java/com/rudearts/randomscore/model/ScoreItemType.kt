package com.rudearts.randomscore.model

import com.rudearts.randomscore.R

enum class ScoreItemType constructor(val ballId:Int, val multiplier:Int) {
    RED(R.drawable.red_ball, 1), BLUE(R.drawable.blue_ball, 3)
}