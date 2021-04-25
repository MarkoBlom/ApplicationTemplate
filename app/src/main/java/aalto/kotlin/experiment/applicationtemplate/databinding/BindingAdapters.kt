package aalto.kotlin.experiment.applicationtemplate.databinding

import aalto.kotlin.experiment.applicationtemplate.MainViewModel
import aalto.kotlin.experiment.applicationtemplate.util.PhoneNumberFormatter
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter

@BindingAdapter("phoneNumberFormatter", "viewModelForPhoneNumberFormatter")
fun bindPhoneNumberFormatterForEdit(view: AppCompatEditText, edit: AppCompatEditText, viewModel: MainViewModel) {

    edit.addTextChangedListener( PhoneNumberFormatter(edit, viewModel) )

}

