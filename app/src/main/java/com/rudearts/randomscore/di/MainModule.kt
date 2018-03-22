package com.rudearts.randomscore.di

import android.content.Context
import com.rudearts.randomscore.domain.RandomizeScoreUseCase
import com.rudearts.randomscore.domain.ResetScoresUseCase
import com.rudearts.randomscore.scores.ScoresController
import com.rudearts.randomscore.ui.main.MainContract
import com.rudearts.randomscore.ui.main.MainPresenter
import com.rudearts.randomscore.ui.main.ScoreAdapter
import com.rudearts.randomscore.util.ScoreRandomizer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class MainModule(private val context:Context, private val view:MainContract.View) {

    @Provides
    @ActivityScope
    fun providesMainView() = view

    @Provides
    @ActivityScope
    fun providesContext() = context

    @Provides
    fun providesScoreRandomizer() = ScoreRandomizer()


    @Provides
    @ActivityScope
    fun providesScoresController() = ScoresController()

    @Provides
    fun providesScoreUseCase(scoresController: ScoresController, randomizer: ScoreRandomizer) = RandomizeScoreUseCase(scoresController, randomizer)

    @Provides
    fun providesResetUseCase(scoresController: ScoresController) = ResetScoresUseCase(scoresController)

    @Provides
    @ActivityScope
    fun providesMainPresenter(view:MainContract.View,
                              scoreUseCase: RandomizeScoreUseCase,
                              resetUseCase: ResetScoresUseCase):MainContract.Presenter = MainPresenter(view, scoreUseCase, resetUseCase)

    @Provides
    @ActivityScope
    fun providesScoreAdapter() = ScoreAdapter(context)
}