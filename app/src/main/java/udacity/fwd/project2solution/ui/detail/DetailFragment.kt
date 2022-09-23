package udacity.fwd.project2solution.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import udacity.fwd.project2solution.R
import udacity.fwd.project2solution.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid
        val factory = DetailViewModelFactory(asteroid)
        viewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)


        viewModel.shoeAlert.observe(viewLifecycleOwner) {
            if (it) {
                displayAstronomicalUnitExplanationDialog()
                viewModel.displayAlertComplete()
            }
        }

        binding.viewModel = viewModel


        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}