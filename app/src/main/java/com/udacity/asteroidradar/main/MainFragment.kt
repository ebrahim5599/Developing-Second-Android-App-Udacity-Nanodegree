package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

private const val TAG = "MainFragment"

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            Factory(app = requireActivity().application)
        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        binding.viewModel = viewModel
        val adapter = AsteroidsAdapter(OnClickListener { viewModel.navigateToDetailFragment(it) })
        binding.asteroidRecycler.adapter = adapter
        viewModel.asteroids.observe(viewLifecycleOwner, Observer {
            if (it != null && it.isNotEmpty()) {
                Log.d(TAG, "onCreateView: ${it[0].closeApproachDate}")
                adapter.submitList(it)
            }
        })

        viewModel.imageOfDay.observe(viewLifecycleOwner, Observer { pic ->
            if (pic != null && pic.mediaType == "image") {
                Picasso.with(context).load(pic.url).into(binding.activityMainImageOfTheDay)
                binding.activityMainImageOfTheDay.contentDescription = pic.title
            }
        })

        viewModel.asteroidNav.observe(viewLifecycleOwner, Observer { ast ->
            if (ast != null){
                findNavController().navigate(MainFragmentDirections.actionShowDetail(ast))
                viewModel.onCompleteNavigation()
            }
        })
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
