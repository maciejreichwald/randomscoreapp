package com.rudearts.randomscore.ui.main

import com.rudearts.randomscore.domain.RandomizeScoreUseCase
import com.rudearts.randomscore.domain.ResetScoresUseCase
import com.rudearts.randomscore.extentions.threadToAndroid
import com.rudearts.randomscore.model.PlayingState
import com.rudearts.randomscore.model.ScoreItem
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainPresenter @Inject constructor(
        internal val view:MainContract.View,
        internal val scoreUseCase: RandomizeScoreUseCase,
        internal val resetUseCase: ResetScoresUseCase) : MainContract.Presenter {

    private var disposable:Disposable? = null

    override fun start() {
        disposable?.let { return }

        view.updatePlayingState(PlayingState.STARTED)

        disposable = scoreUseCase.start()
                .threadToAndroid()
                .subscribe({ items -> onItemsGenerated(items) })
    }

    private fun onItemsGenerated(items: List<ScoreItem>) {
        view.updateItems(items)
    }

    override fun stop() {
        view.updatePlayingState(PlayingState.STOPPED)

        when(disposable) {
            null -> onReset()
            else -> onStop()
        }
    }

    private fun onReset() {
        resetUseCase.reset()
        view.updateItems(emptyList())
    }

    private fun onStop() {
        disposable?.dispose()
        disposable = null
    }
}