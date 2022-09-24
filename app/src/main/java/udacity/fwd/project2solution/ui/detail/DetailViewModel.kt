package udacity.fwd.project2solution.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import udacity.fwd.project2solution.model.Asteroid

class DetailViewModel(asteroid: Asteroid) : ViewModel() {

    private val _asteroid = MutableLiveData<Asteroid>()
    val asteroid: LiveData<Asteroid>
        get() = _asteroid


    private val _showAlert = MutableLiveData<Boolean>()
    val showAlert: LiveData<Boolean>
        get() = _showAlert


    init {
        _asteroid.value = asteroid
    }

    fun displayAlert() {
        _showAlert.value = true
    }

    fun displayAlertComplete() {
        _showAlert.value = false
    }


}