package aalto.kotlin.experiment.applicationtemplate.util

import aalto.kotlin.experiment.applicationtemplate.MainViewModel
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText

/**
 *
 */
class PhoneNumberFormatter(val mEditText: AppCompatEditText,
                            val mViewModel: MainViewModel
): TextWatcher {

    // indices: 0 1 2 - 4 5 6 - 8 9 10 11
    private var len = 0

    // dashes removed
    private var cleanString = ""

    // formatted string
    private var newString = ""
    private var middle = ""
    private var rest = ""

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(s: CharSequence?, star: Int, before: Int, count: Int) { }

    override fun afterTextChanged(p0: Editable?) {
        //Log.d("=MB=","afterTextChange - ${p0.toString()}  | start = $start")

        newString = p0.toString()
        middle = ""
        rest = ""

        // clear all dashes
        cleanString = newString.replace("-","",true)
        //Log.d("=MB=","clean string $cleanString")
        len = cleanString.length

        // insert first dash
        if( len > 3) {
            newString = cleanString.substring(0,3)
        }

        if( len==3 )
            newString = cleanString

        //----------------------

        // insert middle section 3 digits
        if( len>3 && len < 7) {
            middle = "-" + cleanString.substring(3,len)
        }

        if( len>5 )
            middle = "-" + cleanString.substring(3,6)

        newString+=middle

        //-----------------------

        // and the last 4 digits
        if(len>9)
            rest = "-" + cleanString.substring(6,10)

        if( len>6 && len<10 )
            rest = "-" + cleanString.substring(6,len)

        newString+=rest

        // Notify viewModel when we got a valid phone number -> banner will be
        // dismissed if it's visible
        if( newString.length == VALID_LENGTH )
            mViewModel.onValidPhoneNumberEntered()

        mEditText.removeTextChangedListener(this)

        mEditText.setText(newString)
        mEditText.setSelection(newString.length)

        mEditText.addTextChangedListener(this)
    }

    companion object {
        private val VALID_LENGTH = 12
    }
}