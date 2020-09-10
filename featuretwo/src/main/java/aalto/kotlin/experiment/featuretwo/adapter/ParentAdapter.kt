package aalto.kotlin.experiment.featuretwo.adapter

import aalto.kotlin.experiment.featuretwo.FeatureTwoViewModel
import aalto.kotlin.experiment.featuretwo.R
import aalto.kotlin.experiment.featuretwo.databinding.CardViewBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView


class ParentAdapter(val viewModel: FeatureTwoViewModel) :
            RecyclerView.Adapter<ParentAdapter.CardViewHolder?>() {

    /**
     * Our own ViewHolder by extending RecyclerView.ViewHolder
     *
     * The view is inflated from the card_view.xml
     * which we had defined in the layout directory.
     *
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     */
    class CardViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        // Binding class: name generated from layout res file name i.e. layout/card_view.xml
        var binder: CardViewBinding? = DataBindingUtil.bind(v!!)
    }

    /**
     * Create new views (invoked by the layout manager). ViewHolder pattern
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

        // In onCreateViewHolder(), the Views are created and the ViewHolder contains
        // references to them so that the data can be set quickly.
        // Then in onBindView(), the specific data is assigned to each Views.

        // create new view:
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view, parent, false)

        // set the view's size, margins, paddings, click listeners and layout parameters
        // ...
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        holder.binder?.dataItem = viewModel.data[position]

        holder.binder?.viewModel = viewModel

        // binding data (characters) to a child RecyclerView
        holder.binder?.characters = viewModel.data[position].characters

        holder.binder?.executePendingBindings()

        // Notifies viewModel about the currentPosition in RecyclerView to load more if needed
        viewModel.latestPositionInRecyclerView(position)
    }

    override fun getItemCount() = viewModel.data.size
}