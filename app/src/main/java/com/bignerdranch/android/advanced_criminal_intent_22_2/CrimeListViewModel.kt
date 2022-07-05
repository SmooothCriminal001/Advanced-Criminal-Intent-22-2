import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.advanced_criminal_intent_22_2.Crime
import com.bignerdranch.android.advanced_criminal_intent_22_2.CrimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class CrimeListViewModel : ViewModel() {

    val crimeRepository = CrimeRepository.get()

    private val _crimes = MutableStateFlow<List<Crime>>(emptyList())

    val crimes : StateFlow<List<Crime>>
    	get() = _crimes.asStateFlow()

    init {
		viewModelScope.launch{
            crimeRepository.getCrimes().collect {
                _crimes.value = it
            }
		}
    }
}