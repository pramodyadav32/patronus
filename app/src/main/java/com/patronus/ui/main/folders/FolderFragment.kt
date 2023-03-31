package com.patronus.ui.main.folders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.FolderAdapter
import com.patronus.ImageClickPressed
import com.patronus.R
import com.patronus.databinding.FragmentFolderBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Pramod on 3/19/23.
 */
@AndroidEntryPoint
class FolderFragment : Fragment(),ImageClickPressed {

    private var folderAdapter = FolderAdapter(this)

    var recyclerView: RecyclerView? = null
    private var mLayoutManager: LinearLayoutManager? = null

    private val viewModel: FolderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentFolderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_folder, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView_folder)
        mLayoutManager = LinearLayoutManager(activity)
        recyclerView!!.adapter = folderAdapter
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = GridLayoutManager(activity, 2)
        viewModel.list.observe(viewLifecycleOwner){
            if (it?.data!!.size <= 0) {
                recyclerView!!.visibility = View.GONE
            }else{
                recyclerView!!.visibility = View.VISIBLE
            }
            folderAdapter.items = it.data

        }
    }

    override fun onEditClickEvent(postion: Int) {
        val action = FolderFragmentDirections.actionFolderFragmentToFilesFragment(viewModel.list.value?.data?.get(postion)?.id!!)
        findNavController().navigate(action)
    }

}