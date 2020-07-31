package aalto.kotlin.experiment.featuretwo.dagger

import aalto.kotlin.experiment.featuretwo.FeatureTwoViewModel
import aalto.kotlin.experiment.featuretwo.R
import aalto.kotlin.experiment.featuretwo.databinding.CardViewBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView


class TestAdapter :  RecyclerView.Adapter<TestAdapter.CardViewHolder> {

    lateinit var viewModel : FeatureTwoViewModel

    constructor( vm : FeatureTwoViewModel ) { viewModel = vm }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Binding class: name generated from layout res file name i.e. card_view
        var binder : CardViewBinding = DataBindingUtil.bind(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        // In onCreateViewHolder(), the Views are created and the ViewHolder contains
        // references to them so that the data can be set quickly.
        // Then in onBindView(), the specific data is assigned to the Views.

        // create new view:
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view,
                                                                parent,
                                                                false)
        return view as CardViewHolder
    }

    override fun getItemCount() = viewModel.data.results.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        // bind: item
        holder.binder.dataItem(viewModel.data.results[position])
        holder.binder.executePendingBindings()
    }
}