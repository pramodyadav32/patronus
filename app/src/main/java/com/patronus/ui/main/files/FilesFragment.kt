package com.patronus.ui.main.files

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.patronus.GetFilePathFromDevice
import com.patronus.ImageClickPressed
import com.patronus.MainActivity
import com.patronus.R
import com.patronus.databinding.FragmentFilesBinding
import com.patronus.network.interceptors.ApiFactory
import com.patronus.network.interceptors.ApiService
import com.patronus.network.model.ImageUploadRaisecomplainResponse
import com.patronus.util.Constant.PICK_FILE
import com.patronus.util.Constant.PICK_IMAGE
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

/**
 * Created by Pramod on 3/19/23.
 */
@AndroidEntryPoint
class FilesFragment : Fragment(), ImageClickPressed {

//    private val args: FilesFragmentArgs by navArgs()

    var isOpen = false
    var fabOpen: Animation? = null
    var fabClose: Animation? = null
    var rotateForward: Animation? = null
    var rotateBackward: Animation? = null
    private var fileAdapter = FileAdapter(this)
    var backarrow: ImageView? = null
    private var fab: FloatingActionButton? = null
    private var fab1: FloatingActionButton? = null
    private var fab2: FloatingActionButton? = null
    var image: LinearLayout? = null
    var file: LinearLayout? = null
    var recyclerView: RecyclerView? = null
    private var mLayoutManager: LinearLayoutManager? = null

    private val viewModel: FilesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentFilesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_files, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabOpen = AnimationUtils.loadAnimation(activity, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(activity, R.anim.fab_close)
        rotateForward = AnimationUtils.loadAnimation(activity, R.anim.rotate_forward)
        rotateBackward = AnimationUtils.loadAnimation(activity, R.anim.rotate_backward)

        backarrow = view.findViewById(R.id.backarrow)
        fab = view.findViewById(R.id.fab)
        fab1 = view.findViewById(R.id.fab1)
        fab2 = view.findViewById(R.id.fab2)
        file = view.findViewById(R.id.file)
        image = view.findViewById(R.id.image)
        backarrow!!.setOnClickListener(View.OnClickListener {
            findNavController().popBackStack()
        })
        fab!!.setOnClickListener(View.OnClickListener {
            animateFab()
        })

        fab1!!.setOnClickListener(View.OnClickListener {
            var chooseFile = Intent(Intent.ACTION_GET_CONTENT)
            chooseFile.type = "*/*"
            chooseFile = Intent.createChooser(chooseFile, "Choose a file")
            startActivityForResult(chooseFile, PICK_FILE)
        })

        fab2!!.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        })
        recyclerView = view.findViewById(R.id.recyclerView_files)
        mLayoutManager = LinearLayoutManager(activity)
        recyclerView!!.adapter = fileAdapter
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = GridLayoutManager(activity, 2)
        viewModel.list.observe(viewLifecycleOwner){
            if (it?.data!!.size <= 0) {
                recyclerView!!.visibility = View.GONE
            }else{
                recyclerView!!.visibility = View.VISIBLE
            }
            fileAdapter.items = it.data

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)


        when (requestCode) {
            PICK_FILE -> if (resultCode === AppCompatActivity.RESULT_OK) {
                var path: String = resultData!!.getData()!!.getPath()!!
                Log.d("File Location", path)
                var pathqq = GetFilePathFromDevice.getPath((activity as MainActivity),resultData!!.getData()!!)
                if(path != null){
                    uploadPicture(path)
                }

            }
        }
    }

    fun uploadPicture(filePath: String) {
        val photo1 = File(filePath)
        val requestFile1 = RequestBody.create(MultipartBody.FORM, photo1)
        val fileBody1: MultipartBody.Part = createFormData("file1", photo1.name, requestFile1)

        val CreationSource = RequestBody.create(MultipartBody.FORM, "ANdroid")
        val StorageContainerId = RequestBody.create(MultipartBody.FORM, "complaintId")
        val CreatedBy = RequestBody.create(MultipartBody.FORM, "Pramod")
        val apiService = ApiFactory.application?.create(
            ApiService::class.java
        )
        val uploadFile =
            apiService?.uploadProfile(CreationSource, StorageContainerId, CreatedBy, fileBody1)
        uploadFile?.enqueue(
            object : Callback<ImageUploadRaisecomplainResponse?> {
                override fun onResponse(
                    call: Call<ImageUploadRaisecomplainResponse?>,
                    response: Response<ImageUploadRaisecomplainResponse?>
                ) {
                    Log.i(ContentValues.TAG, "onResponse: $response")

                    var responseCode = response.code()

                    Toast.makeText(
                        activity, "File is uploaded successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    if (!response.isSuccessful) return
                    if (response.body() == null) return


                }

                override fun onFailure(
                    call: Call<ImageUploadRaisecomplainResponse?>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        activity, "Some thing went wrong",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun animateFab() {
        isOpen = if (isOpen) {
            fab!!.startAnimation(rotateForward)
            file!!.startAnimation(fabClose);
            image!!.startAnimation(fabClose);
            file!!.setClickable(false);
            image!!.setClickable(false);
            false
        } else {
            fab!!.startAnimation(rotateBackward)
            file!!.startAnimation(fabOpen);
            image!!.startAnimation(fabOpen);
            file!!.setClickable(true);
            image!!.setClickable(true);
            true
        }
    }

    override fun onEditClickEvent(postion: Int) {
        val action = FilesFragmentDirections.actionFileFragmentToFileDetailsFragment(viewModel.list.value?.data?.get(postion)?.id!!)
        findNavController().navigate(action)
    }


}