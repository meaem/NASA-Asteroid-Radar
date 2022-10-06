package udacity.fwd.project2solution.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import udacity.fwd.project2solution.R
import udacity.fwd.project2solution.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    val viewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory(
            DetailFragmentArgs.fromBundle(
                requireArguments()
            ).selectedAsteroid
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel.showAlert.observe(viewLifecycleOwner) {
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
            .setMessage(getString(R.string.astronomical_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}