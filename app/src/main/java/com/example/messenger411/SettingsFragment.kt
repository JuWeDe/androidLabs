package com.example.messenger411


import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.messenger411.Services.Storage.StorageService
import com.example.messenger411.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private val fileName: String = "characterList_11.txt"
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences(
            "CHAT_APPLICATION",
            Context.MODE_PRIVATE
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (StorageService.isFileInDownloads(fileName)) {
            binding.deleteFile.visibility = View.VISIBLE
        } else if (StorageService.isFileInInternalStorage(requireContext(), fileName)) {
            binding.restoreFile.visibility = View.VISIBLE
        }

        binding.deleteFile.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                StorageService.moveFileToInternalStorage(requireContext(), fileName)
                StorageService.deleteFileFromDownloads(requireContext(), fileName)

                binding.deleteFile.visibility = View.INVISIBLE
                binding.restoreFile.visibility = View.VISIBLE
            } else {
                StorageService.moveFileToInternalStorage(requireContext(), fileName)
                StorageService.deleteFileFromDownloads(fileName)

                binding.deleteFile.visibility = View.INVISIBLE
                binding.restoreFile.visibility = View.VISIBLE
            }
        }
        binding.restoreFile.setOnClickListener {
            StorageService.moveFileToDownloads(requireContext(), fileName)
            if (StorageService.isFileInDownloads(fileName)) {
                binding.restoreFile.visibility = View.INVISIBLE
                binding.deleteFile.visibility = View.VISIBLE
            }
        }
        binding.saveInDataStore.setOnClickListener {
            lifecycleScope.launch {
                save("secondEmail", binding.emailText.text.toString())
                save("darkTheme", binding.darkSwitch.isChecked.toString())
            }
        }
        binding.saveInSharedPreferences.setOnClickListener {
            lifecycleScope.launch {
                saveSharedPreferences("address", binding.addressText.text.toString())
                saveSharedPreferences("push", binding.pushSwitch.isChecked.toString())
            }
        }
        lifecycleScope.launch {
            binding.emailText.setText(read("secondEmail"))
            binding.addressText.setText(readSharedPreferences("address"))
            binding.darkSwitch.isChecked = read("darkTheme").toBoolean()
            binding.pushSwitch.isChecked = readSharedPreferences("push").toBoolean()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
        Log.d("SettingsFragment", "onStop")
    }

    override fun onStart() {
        super.onStart()
        Log.d("SettingsFragment", "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("SettingsFragment", "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d("SettingsFragment", "onResume")
    }

    private suspend fun save(key: String, data: String) {
        Log.d("save", "key: $key, value: $data")
        val dataStoreKey = stringPreferencesKey(key)
        requireActivity().dataStore.edit { settings ->
            settings[dataStoreKey] = data
        }
    }

    private suspend fun read(key: String): String? {
        Log.d("read", "key: $key")
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = requireActivity().dataStore.data.first()
        return preferences[dataStoreKey]
    }

    private fun saveSharedPreferences(key: String, data: String) {
        with(sharedPref.edit()) {
            putString(key, data)
            apply()
        }
    }

    private fun readSharedPreferences(key: String): String? {
        return sharedPref.getString(key, null)
    }
}