package com.rudearts.randomscore.ui.main

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.nhaarman.mockitokotlin2.*
import com.rudearts.randomscore.R
import com.rudearts.randomscore.model.PlayingState
import com.rudearts.randomscore.model.ScoreItem
import kotlinx.android.synthetic.main.activity_default.*
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
class MainActivityTest {

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()

    @Mock lateinit var presenter: MainContract.Presenter

    @Mock lateinit var adapter: ScoreAdapter

    @InjectMocks @Spy lateinit var activity:MainActivityMock

    private val btnStart = mock<View> {}
    private val lblStop = mock<TextView> {}
    private val progress = mock<ProgressBar> {}
    private val resources = mock<Resources> {}

    @Before
    fun setup() {
        whenever(activity.resources).thenReturn(resources)
        whenever(activity.progressBar).thenReturn(progress)
        whenever(activity.btnStart).thenReturn(btnStart)
        whenever(activity.lblStop).thenReturn(lblStop)
        whenever(activity.progressBarVisibility(any())).then {  }
    }


    @Test
    fun updateItems() {
        val items:List<ScoreItem> = mock {}
        activity.updateItems(items)

        verify(adapter, times(1)).updateItems(items)
    }

    @Test
    fun updateLoadingState_WhenStarted() {
        whenever(resources.getString(R.string.stop)).thenReturn("stop")

        activity.updatePlayingState(PlayingState.STARTED)

        verify(activity, times(1)).progressBarVisibility(eq(true))
        verify(lblStop, times(1)).text = "stop"
        verify(btnStart, times(1)).isSelected = true
    }

    @Test
    fun updateLoadingState_WhenStopped() {
        whenever(resources.getString(R.string.reset)).thenReturn("reset")

        activity.updatePlayingState(PlayingState.STOPPED)

        verify(lblStop, times(1)).text = "reset"
        verify(btnStart, times(1)).isSelected = false
    }

    /**
     * Ok, I gave up here - I had some problems with mocking activity
     * it was holiday, it was late and this "temporary" fix worked like a charm...
     */
    class MainActivityMock : MainActivity() {
        override fun <T : View?> findViewById(id: Int): T {
            return null as T
        }
    }
}