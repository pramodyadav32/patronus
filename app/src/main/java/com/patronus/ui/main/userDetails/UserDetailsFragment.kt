package com.patronus.ui.main.userDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.patronus.MainActivity
import com.patronus.R
import com.patronus.databinding.FragmentUserDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Pramod on 3/31/23.
 */
@AndroidEntryPoint
class UserDetailsFragment : Fragment(), OnMapReadyCallback {

    var backArrowDetail: ImageView? = null
    var profile_image: ImageView? = null
    var stickersDetail: TextView? = null
    var name: TextView? = null
    var gender: TextView? = null
    var number: TextView? = null
    var address_name: TextView? = null
    private var mMap: GoogleMap? = null

    private val viewModel: UserDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentUserDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false)
        binding.lifecycleOwner = this
        setUpMap()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name = view.findViewById(R.id.name)
        gender = view.findViewById(R.id.gender)
        number = view.findViewById(R.id.number)
        address_name = view.findViewById(R.id.address_name)
        stickersDetail = view.findViewById(R.id.stickersDetail)
        profile_image = view.findViewById(R.id.profile_image)
        backArrowDetail = view.findViewById(R.id.ivBack)
        backArrowDetail!!.setOnClickListener(View.OnClickListener {
            findNavController().popBackStack()
        })

        viewModel.data.observe(viewLifecycleOwner) {
            if(viewModel.data.value?.data?.imageUrl != null) {
                val options: com.bumptech.glide.request.RequestOptions =
                    com.bumptech.glide.request.RequestOptions()
                        .centerCrop()
                Glide.with(activity as MainActivity).load(viewModel.data.value?.data?.imageUrl).apply(options).into(profile_image!!)
            }
            name?.text = viewModel.data.value?.data?.firstName + " " + viewModel.data.value?.data?.lastName
            gender?.text = viewModel.data.value?.data?.gender
            number?.text = viewModel.data.value?.data?.phoneNumber
            address_name?.text = viewModel.data.value?.data?.address?.street + " " + viewModel.data.value?.data?.address?.city + " " + viewModel.data.value?.data?.address?.state + " " + viewModel.data.value?.data?.address?.zip + " \n" + viewModel.data.value?.data?.address?.country
            var stickers = "";
            for(temp in viewModel.data.value?.data?.stickers!!){
                stickers = stickers + " " + temp
            }
            stickersDetail?.text = stickers
            if (mMap != null) {
                val lati: Double? = viewModel.data.value?.data?.currentLatitude?.toDouble()
                val longi: Double? = viewModel.data.value?.data?.currentLongitude?.toDouble()
                mMap!!.addMarker(
                    MarkerOptions()
                        .position(LatLng(lati!!, longi!!))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                )
                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lati!!, longi!!)));
                mMap!!.animateCamera(CameraUpdateFactory.zoomTo(12F));
            }
        }

    }

    private fun setUpMap() {
        if (mMap == null) {
            (childFragmentManager.findFragmentById(R.id.location_map) as SupportMapFragment?)!!.getMapAsync(
                this
            )
        }
    }

    override fun onMapReady(map: GoogleMap) {
        if (mMap != null) {
            return
        }
        mMap = map
        mMap!!.setMapType(GoogleMap.MAP_TYPE_NORMAL)
    }

}