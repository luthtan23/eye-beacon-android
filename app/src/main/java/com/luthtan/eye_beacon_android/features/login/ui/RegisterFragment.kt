package com.luthtan.eye_beacon_android.features.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.luthtan.eye_beacon_android.databinding.FragmentRegisterBinding
import com.luthtan.eye_beacon_android.features.login.util.PRIVACY_AGREEMENT_TO_REGISTER_REQUEST_KEY
import com.luthtan.eye_beacon_android.features.login.util.PRIVACY_AGREEMENT_TO_REGISTER_STATE_KEY
import com.luthtan.eye_beacon_android.features.login.util.REGISTER_TO_LOGIN_LINK
import com.luthtan.eye_beacon_android.features.login.util.REGISTER_TO_PRIVACY_AGREEMENT_LINK
import com.luthtan.simplebleproject.common.makeLinks
import com.luthtan.simplebleproject.data.repository.PreferencesRepository

class RegisterFragment : Fragment(), View.OnClickListener {

//    private val preference: PreferencesRepository by inject()

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(PRIVACY_AGREEMENT_TO_REGISTER_REQUEST_KEY) { requestKey, bundle ->
            val result = bundle.getBoolean(PRIVACY_AGREEMENT_TO_REGISTER_STATE_KEY)
            binding.checkboxRegisterPrivacyAgreement.isChecked = result
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvLoginRegister.makeLinks(pairMakeLinkRegister)

        binding.tvRegisterPrivacyAgreement.makeLinks(pairMakeLinkPrivacyAgreement)
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()


    }

    override fun onStop() {
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    override fun onClick(v: View) {
        when(v.id) {

        }
    }

    private val acceptPrivacyAgreement = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult ->
    }


    private val pairMakeLinkRegister = Pair(REGISTER_TO_LOGIN_LINK, View.OnClickListener {
        requireActivity().supportFragmentManager.popBackStack()
    })

    private val pairMakeLinkPrivacyAgreement = Pair(REGISTER_TO_PRIVACY_AGREEMENT_LINK, View.OnClickListener {
    })




}