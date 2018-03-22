package com.rudearts.randomscore.di

import com.rudearts.randomscore.ui.main.MainActivity
import dagger.Component

@ActivityScope
@Component(modules = [(MainModule::class)])
interface MainComponent {
    fun inject(activity: MainActivity)
}