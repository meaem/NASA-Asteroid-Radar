package udacity.fwd.project2solution.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import udacity.fwd.project2solution.R
import udacity.fwd.project2solution.database.AsteroidDatabase
import udacity.fwd.project2solution.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    val viewModel by viewModels<MainViewModel> {
        MainViewModelFactory(
            AsteroidDatabase.getInstance(
                requireNotNull(this.activity).application
            ).asteroidDao, requireNotNull(this.activity).application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.asteroidRecycler.adapter =
            AsteroidListAdapter(AsteroidListAdapter.AsteroidClickListener {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailFragment(it)
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
        viewModel.updateData(
            when (item.itemId) {
                R.id.show_today_menu -> DataChoices.TODAY
                R.id.show_locally_menu -> DataChoices.LOCALLY
                else -> DataChoices.CURRENT_WEEK

            }
        )
        return true
    }
}