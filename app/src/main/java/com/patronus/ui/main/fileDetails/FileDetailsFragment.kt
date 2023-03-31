package com.patronus.ui.main.fileDetails

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.patronus.GetFilePathFromDevice
import com.patronus.MainActivity
import com.patronus.R
import com.patronus.databinding.FragmentFileDetailsBinding
import com.patronus.ui.main.files.FilesViewModel
import com.patronus.util.Constant
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Pramod on 3/19/23.
 */
@AndroidEntryPoint
class FileDetailsFragment : Fragment() {

    var imageViewDetails: ImageView? = null
    var backArrowDetail: ImageView? = null

    private val viewModel: FileDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentFileDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_file_details, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFileByID("e4a255cf-d408-437f-859e-81c695779e19")

        imageViewDetails = view.findViewById(R.id.imageViewDetails)
        backArrowDetail = view.findViewById(R.id.backArrowDetail)
        backArrowDetail!!.setOnClickListener(View.OnClickListener {
            findNavController().popBackStack()
        })
        viewModel.data.observe(viewLifecycleOwner) {

            if (it?.data!!.contentType.equals("image/jpeg")) {
                imageViewDetails?.visibility = (View.VISIBLE)
                try {
                    val options: com.bumptech.glide.request.RequestOptions =
                        com.bumptech.glide.request.RequestOptions()
                            .centerCrop()

                    Glide.with(activity as MainActivity).load(it?.data!!.previewUrl)
                        .apply(options).into(imageViewDetails!!)
                    Log.d("PATH", it?.data!!.previewUrl)
                } catch (e: Exception) {
                    Log.d("PATH", e.toString())
                }
            }
        }

    }

}