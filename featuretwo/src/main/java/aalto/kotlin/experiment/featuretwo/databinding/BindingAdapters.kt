package aalto.kotlin.experiment.featuretwo.databinding

import aalto.kotlin.experiment.featuretwo.FeatureTwoViewModel
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Binds Parent RecyclerView adapter to viewholder and data
 * @param view
 * @param viewModel
 */
@BindingAdapter("viewModelForParentAdapter")
fun bindViewModelForParentAdapter(view: RecyclerView,
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

/**
 * Binding ViewModel and Characters String data ArrayList for Child RecyclerView
 */
@BindingAdapter("viewModel_of","data_of")
fun bindViewModelForCharactersAdapter(view: RecyclerView,
                                viewModel: FeatureTwoViewModel,
                                items : ArrayList<String>) {

    val _layoutManager = LinearLayoutManager(view.context)
    with(view) {
        layoutManager = _layoutManager
        itemAnimator = DefaultItemAnimator()
        setHasFixedSize(true)

        viewModel.childAdapter.characters = items

        adapter = viewModel.childAdapter
    }
}





