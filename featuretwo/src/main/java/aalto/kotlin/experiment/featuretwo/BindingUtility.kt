package aalto.kotlin.experiment.featuretwo

import aalto.kotlin.experiment.featuretwo.dagger.TestAdapter
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BindingUtility {

    companion object {

        /**
         * Binds recyclerview adapter to viewholder and data
         * @param view
         * @param viewModel
         */
        @BindingAdapter("bind:ViewModel")
        fun bindViewModel(view: RecyclerView,
                          viewModel: FeatureTwoViewModel ) {
            val _layoutManager = LinearLayoutManager(view.context)
            with(view) {
                layoutManager = _layoutManager
                itemAnimator = DefaultItemAnimator()
                setHasFixedSize(true)
                setAdapter( TestAdapter(viewModel) )
            }
        }
    }
}