package com.rudearts.randomscore.ui.main

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.rudearts.randomscore.R
import com.rudearts.randomscore.di.DaggerMainComponent
import com.rudearts.randomscore.di.MainModule
import com.rudearts.randomscore.extentions.bind
import com.rudearts.randomscore.model.PlayingState
import com.rudearts.randomscore.model.ScoreItem
import com.rudearts.randomscore.ui.ToolbarActivity
import javax.inject.Inject


/**
 * Yes, it is open, you can see in MainActivityTest bottom comment why
 */
open class MainActivity : ToolbarActivity(), MainContract.View {

    @Inject internal lateinit var presenter: MainContract.Presenter
    @Inject internal lateinit var adapter: ScoreAdapter

    internal val listItems: RecyclerView by bind(R.id.items_list)
    internal val btnStart: View by bind(R.id.start_btn)
    internal val btnStop: View by bind(R.id.stop_btn)
    internal val lblStop: TextView by bind(R.id.stop_lbl)

    override fun provideSubContentViewId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    internal fun setup() {
        inject()
        setupTitle()
        setupViews()

        updatePlayingState(PlayingState.STOPPED)
    }

    internal fun inject() {
        createComponent().apply {
            this.inject(this@MainActivity)
        }
    }

    internal fun createComponent() = DaggerMainComponent.builder()
            .mainModule(MainModule(this, this))
            .build()

    internal fun setupTitle() {
        title = getString(R.string.app_name)
    }

    internal fun setupViews() {
        setupList()
        setupBtn()
    }

    internal fun setupList() {
        listItems.adapter = adapter
        listItems.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    internal fun setupBtn() {
        btnStart.setOnClickListener { presenter.start() }
        btnStop.setOnClickListener { presenter.stop() }
    }

    override fun updatePlayingState(state: PlayingState) {
        progressBarVisibility(state == PlayingState.STARTED)
        lblStop.text = resources.getString(state.stopId)
        btnStart.isSelected = state == PlayingState.STARTED
    }

    override fun updateItems(items: List<ScoreItem>) {
        adapter.updateItems(items)
    }



}
