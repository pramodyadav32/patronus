package com.patronus.ui.main.users

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
import com.example.demo.UserAdapter
import com.patronus.ImageClickPressed
import com.patronus.R
import com.patronus.databinding.FragmentUserBinding
import com.patronus.util.Constant
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Pramod on 3/31/23.
 */
@AndroidEntryPoint
class UserFragment : Fragment(),ImageClickPressed {

    private var userAdapter = UserAdapter(this)

    var recyclerView: RecyclerView? = null
    private var mLayoutManager: LinearLayoutManager? = null

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentUserBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView_holders)
        recyclerView!!.adapter = userAdapter
        mLayoutManager = LinearLayoutManager(activity)
        recyclerView!!.adapter = userAdapter
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = GridLayoutManager(activity, 1)

        viewModel.list.observe(viewLifecycleOwner){
            if (it?.data!!.size <= 0)
                recyclerView!!.visibility = View.GONE
            else
                recyclerView!!.visibility = View.VISIBLE

            userAdapter.items = it.data

        }
    }

    override fun onEditClickEvent(postion: Int) {
        Constant.DEVICE_HOLDER_ID = viewModel.list.value?.data?.get(postion)?.id!!
        val action = UserFragmentDirections.actionUserFragmentToUserDetailsFragment(viewModel.list.value?.data?.get(postion)?.id!!)
        findNavController().navigate(action)
    }

}