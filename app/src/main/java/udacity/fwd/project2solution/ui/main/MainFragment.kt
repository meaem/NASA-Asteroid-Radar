package udacity.fwd.project2solution.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import udacity.fwd.project2solution.R
import udacity.fwd.project2solution.database.AsteroidDatabase
import udacity.fwd.project2solution.databinding.FragmentMainBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
//    private val viewModel: MainViewModel by lazy {
//        ViewModelProvider(this).get(MainViewModel::class.java)
//    }

//    private lateinit var viewModel :MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        val dataSource = AsteroidDatabase.getInstance(application).asteroidDao
//       viewModel  by viewModels<MainViewModel>()
        val viewModelFactory = MainViewModelFactory(dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)


        binding.viewModel = viewModel
        binding.asteroidRecycler.adapter =
            AsteroidListAdapter(AsteroidListAdapter.onAsteroidClickListener {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailFragment(
                        it, it.codename
                    )
                )
            })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}