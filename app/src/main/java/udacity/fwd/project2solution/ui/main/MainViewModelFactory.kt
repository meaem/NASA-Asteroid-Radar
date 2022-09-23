package udacity.fwd.project2solution.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import udacity.fwd.project2solution.database.AsteroidDao

class MainViewModelFactory(private val dataSource: AsteroidDao) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
