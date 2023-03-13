package app.com.patricksdeliveryboy.utility

import android.content.Context
import android.text.format.DateUtils
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import app.com.patricksdeliveryboy.home.HomeActivity
import app.com.patricksdeliveryboy.models.JwtData
import app.com.patricksdeliveryboy.sideMenu.SideMenuPagesActivity
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okio.ByteString.Companion.decodeBase64
import java.math.RoundingMode
import java.nio.charset.StandardCharsets
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayDeque

private val shiftTimeFormat = SimpleDateFormat("hh:mm aa", Locale.ROOT)
private val shiftDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ROOT)
private val timeStampFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'", Locale.ROOT)
private val shiftDateFromServer = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
private val dateWeekDayFormat = SimpleDateFormat("EEE", Locale.ROOT)
private val dateDayFormat = SimpleDateFormat("dd", Locale.ROOT)
private val earningDateFormat = SimpleDateFormat("MMM yyyy", Locale.ROOT)
private val joinDateFormat = SimpleDateFormat("MMM dd yyyy", Locale.ROOT)
private val decimalFormat = DecimalFormat("#.##")

fun String.toShiftTimeFormat(): String = shiftTimeFormat.format(timeStampFormat.parse(this)!!)
fun String.toShiftDateFormat(): String = shiftDateFormat.format(shiftDateFromServer.parse(this)!!)
fun String.toWeekDayFormat(): String = dateWeekDayFormat.format(shiftDateFromServer.parse(this)!!)
fun String.toDayFormat(): String = dateDayFormat.format(shiftDateFromServer.parse(this)!!)
fun String.toEarningDateFormat(): String = earningDateFormat.format(shiftDateFromServer.parse(this)!!)
fun String.toMilliseconds(): Date = shiftDateFromServer.parse(this)
fun Date.toJoinDateFormat(): String = joinDateFormat.format(this)

fun getRoundOffValue(value : Double) : String{
    decimalFormat.roundingMode = RoundingMode.CEILING
    return decimalFormat.format(value)
}

fun getRelativeDay(mCalender: Calendar, today: Calendar): String {
    return DateUtils.getRelativeTimeSpanString(
            mCalender.timeInMillis,
            today.timeInMillis,
            DateUtils.DAY_IN_MILLIS,
            DateUtils.FORMAT_SHOW_WEEKDAY
    ).toString()
}

fun decodeJwt(): JwtData? {
    if (Validator.TOKEN != null) {
        return ObjectMapper().readValue(
                Validator.TOKEN!!.split("\\.".toRegex())[1].decodeBase64()!!.string(StandardCharsets.UTF_8),
                JwtData::class.java
        )
    }
    return null
}

class JsonUtils {
    companion object {
        @JvmStatic
        inline fun <reified T> getAsObject(jsonString : String): T {
            return ObjectMapper().readValue(
                    jsonString,
                    T::class.java
            )
        }
    }
}

fun Fragment.showLoading(showLoading: Boolean) {
    if (requireActivity() is SideMenuPagesActivity) {
        (requireActivity() as SideMenuPagesActivity).showLoading(showLoading)
    } else if (requireActivity() is HomeActivity) {
        (requireActivity() as HomeActivity).showLoading(showLoading)
    }
}

fun FragmentActivity.hideKeyboard() {
    if (currentFocus == null)
        return
    val inputManager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(
            currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
    )
}

val cancelableJobs = ArrayDeque<Job>()

fun addCancellable(job: Job) = cancelableJobs.add(job)

inline fun Fragment.addCancellable(crossinline job: suspend () -> Unit) = cancelableJobs.add(
        lifecycleScope.launch { job() }
)

inline fun FragmentActivity.addCancellable(crossinline job: suspend () -> Unit) = cancelableJobs.add(
        lifecycleScope.launch { job() }
)

fun cancelAllJobs() {
    while (cancelableJobs.isNotEmpty()) {
        cancelableJobs.removeFirstOrNull()?.cancel()
    }
}