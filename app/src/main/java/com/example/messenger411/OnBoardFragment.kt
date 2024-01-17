package com.example.messenger411

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
// import com.example.messenger411.Services.Notifications.NotificationService
import com.example.messenger411.databinding.FragmentOnBoardBinding


class OnBoardFragment : Fragment(R.layout.fragment_on_board) {

    private var _binding: FragmentOnBoardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener {
            val action = OnBoardFragmentDirections.actionOnBoardFragmentToSignUpFragment()
            this.findNavController().navigate(action)
        }
        binding.signInButton.setOnClickListener {

            val action = OnBoardFragmentDirections.actionOnBoardFragmentToSignInFragment()
            this.findNavController().navigate(action)
        }
        binding.datastoreButton.setOnClickListener {
            val action = OnBoardFragmentDirections.actionOnBoardFragmentToDatastoreFragment()
            this.findNavController().navigate(action)
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d("OnBoardFragment", "Start")
    }

    override fun onStop() {
        super.onStop()
        Log.d("OnBoardFragment", "Stop")
    }

//    override fun onDestroy() {
//        super.onDestroy(
//        Log.d("OnBoardFragment", "Destroy")
//    }

    override fun onDetach() {
        super.onDetach()
        Log.d("OnBoardFragment", "Detach")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("OnBoardFragment", "DestroyView")
    }
    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    override fun onPause() {
        super.onPause()
        Log.d("OnBoardFragment", "Pause")


//        val serviceIntent = Intent(requireContext(), NotificationService::class.java)
//        requireContext().startService(serviceIntent)
    }

    override fun onResume() {
        super.onResume()
        Log.d("OnBoardFragment", "Resume")
//        val serviceIntent = Intent(requireContext(), NotificationService::class.java)
//        requireContext().stopService(serviceIntent)
    }
}
