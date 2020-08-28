package aalto.kotlin.experiment.featuretwo.databinding

import aalto.kotlin.experiment.featuretwo.FeatureTwoViewModel
import aalto.kotlin.experiment.featuretwo.TestAdapter
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Binds recyclerview adapter to viewholder and data
 * @param view
 * @param viewModel
 */
@BindingAdapter("viewModelForTestAdapter")
fun bindViewModelForTestAdapter(view: RecyclerView,
                                viewModel: FeatureTwoViewModel) {

    // https://codelabs.developers.google.com/codelabs/android-databinding/#2

    val _layoutManager = LinearLayoutManager(view.context)
    with(view) {
        layoutManager = _layoutManager
        itemAnimator = DefaultItemAnimator()
        setHasFixedSize(true)
        adapter = viewModel.adapter
    }
}





