package com.example.pexelsretrofitapi.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.pexelsretrofitapi.model.pexels.Photo
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.custom_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_view_photo.*
import com.example.pexelsretrofitapi.model.ktxs.Helper
import com.example.pexelsretrofitapi.ui.MainActivity.Companion.dbHelper
import com.google.android.material.snackbar.Snackbar


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

class ViewPhotoFragment : DialogFragment() {

    private var photo: Photo? = null
    private var isFavorite: Boolean? = null

    private lateinit var header: ImageView
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<*>
    private lateinit var mCustomBottomSheet: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            DialogFragment.STYLE_NORMAL,
            com.example.pexelsretrofitapi.R.style.DialogFragmentPhoto
        );

        arguments?.let {
            photo = it.getSerializable(ARG_PARAM1) as Photo?
            isFavorite = it.getBoolean(ARG_PARAM2, false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            com.example.pexelsretrofitapi.R.layout.fragment_view_photo,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isFavorite == false) tv_favorite.visibility = View.VISIBLE
        else tv_favorite.visibility = View.GONE

        tv_photo_by.text = "Photographer: ${photo?.photographer}"
        tv_photo_size.text = "Original resolution: ${photo?.width} x ${photo?.height}"
        Glide.with(requireActivity()).load(photo?.src?.medium).into(image_preview)

        initBottomSheet(view)

        iv_back.setOnClickListener { dialog?.dismiss() }

        tv_download.setOnClickListener {
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            requestStoragePermissionWithAction {
                Helper.downloadPhoto(photo!!, requireActivity())
            }
        }

        tv_wallpaper.setOnClickListener {
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            Helper.setAsWallpaper(photo!!, requireActivity(), mCustomBottomSheet)
        }

        tv_favorite.setOnClickListener {
            dbHelper?.addFavorite(photo!!)
            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            Snackbar.make(mCustomBottomSheet, "Added to favorites", Snackbar.LENGTH_SHORT).show()
        }

    }

    private var onPermissionGrantedAction: (() -> Unit)? = null
    private val PERMISSION_CODE = 99
    fun requestStoragePermissionWithAction(permissionNeededAction: () -> Unit) {
        onPermissionGrantedAction = permissionNeededAction
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_CODE)
        } else {
            onPermissionGrantedAction?.invoke()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CODE &&
            grantResults.first() == PackageManager.PERMISSION_GRANTED
        ) {
            onPermissionGrantedAction?.invoke()
            onPermissionGrantedAction = null
        }
    }


    private fun initBottomSheet(view: View) {
        mCustomBottomSheet =
            view.findViewById<LinearLayout>(com.example.pexelsretrofitapi.R.id.custom_bottom_sheet)
        mBottomSheetBehavior = BottomSheetBehavior.from<LinearLayout>(mCustomBottomSheet)

        header = view.findViewById<ImageView>(com.example.pexelsretrofitapi.R.id.header_arrow)

        header.setOnClickListener(View.OnClickListener {
            if (mBottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            } else {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
        })

        mBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                header.setRotation(slideOffset * 180)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.getWindow()?.getAttributes()?.windowAnimations =
            com.example.pexelsretrofitapi.R.style.DialogFragmentAnimation
    }

    companion object {
        @JvmStatic
        fun newInstance(photo: Photo, isFavorite: Boolean) =
            ViewPhotoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, photo)
                    putBoolean(ARG_PARAM2, isFavorite)
                }
            }
    }

}
