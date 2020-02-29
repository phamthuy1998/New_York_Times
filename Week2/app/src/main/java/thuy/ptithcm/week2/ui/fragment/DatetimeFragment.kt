package thuy.ptithcm.week2.ui.fragment


import android.R
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.text.DateFormat
import java.util.*


class DatetimeFragment(val edt: EditText) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,
            this,
            year,
            month,
            day
        )
        calendar.add(Calendar.DATE, -1);
        datePickerDialog.datePicker.maxDate = calendar.getTimeInMillis()
        calendar.add(Calendar.YEAR, -8);
        datePickerDialog.datePicker.minDate = calendar.getTimeInMillis()
        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        var date1 = day.toString()
        var month1 = (month + 1).toString()
        if (day.toString().length == 1) date1 = "0$day"
        if (month.toString().length == 1) month1 = "0$month1"
        val date = year.toString() + month1 + date1
        edt.run {
            setText(date)
        }
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        calendar.set(year, month, day, 0, 0, 0)
        val chosenDate = calendar.time
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM)
        return df.format(chosenDate).toString()
    }
}
