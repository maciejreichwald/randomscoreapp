package com.rudearts.randomscore.api

import com.nhaarman.mockitokotlin2.*
import com.rudearts.randomscore.model.ScoreItem
import com.rudearts.randomscore.model.ScoreItemType
import com.rudearts.randomscore.ui.main.ScoreAdapter
import com.rudearts.randomscore.util.ScoreRandomizer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ScoreRandomizerTest {

    val randomizer = mock<ScoreRandomizer>()

    @Before
    fun setup() {
        whenever(randomizer.randomize(any())).thenCallRealMethod()
    }

    @Test
    fun randomize_WithEmptyList() {
        randomizer.randomize(mutableListOf())

        verify(randomizer).addRandomElement(any())
        verify(randomizer, never()).randomizeScores(any())
    }

    @Test
    fun randomize_WithMinSizeList() {
        val list = MutableList(5, { ScoreItem(type = ScoreItemType.BLUE) })

        randomizer.randomize(list)

        verify(randomizer, never()).addRandomElement(any())
        verify(randomizer).randomizeScores(any())
    }
}