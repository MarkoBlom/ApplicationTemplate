package aalto.kotlin.experiment.featuretwo.adapter

import aalto.kotlin.experiment.featuretwo.FeatureTwoViewModel
import aalto.kotlin.experiment.featuretwo.R
import aalto.kotlin.experiment.featuretwo.databinding.ChildRecyclerViewBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

class ChildAdapter(val viewModel: FeatureTwoViewModel) :
    RecyclerView.Adapter<ChildAdapter.ViewHolder?>() {

    var characters : ArrayList<String> = arrayListOf("")

    var url = ""

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
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        // Binding class: name generated from layout res file name i.e. layout/child_recycler_view.xml
        var binder: ChildRecyclerViewBinding? = DataBindingUtil.bind(v!!)
    }

    /**
     * Create new views (invoked by the layout manager). ViewHolder pattern
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // In onCreateViewHolder(), the Views are created and the ViewHolder contains
        // references to them so that the data can be set quickly.
        // Then in onBindView(), the specific data is assigned to each Views.

        // create new view:
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.child_recycler_view, parent, false)

        // set the view's size, margins, paddings, click listeners and layout parameters
        // ...
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binder?.characterUrl = characters.get(position)

        url = characters.get(position)

        holder.binder?.characterItemOnClickListener = View.OnClickListener {
            viewModel.onCharacterClicked( url) }

        holder.binder?.executePendingBindings()
    }

    override fun getItemCount() = characters.size

}