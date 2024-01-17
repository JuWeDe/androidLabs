package com.example.messenger411

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.chatapp.Models.getDatabase
import com.example.chatapp.Repository.PersonRepo
import com.example.messenger411.Adapters.ResponseAdapter
import com.example.messenger411.Data.Hero
import com.example.messenger411.Ktor.KtorRepository
import com.example.messenger411.Models.Person
import com.example.messenger411.Services.Storage.StorageService
import com.example.messenger411.databinding.FragmentChatBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChatFragment : Fragment(R.layout.fragment_chat) {
    private val fileName: String = "wowwowweewa.txt"
    private var _binding: FragmentChatBinding? = null
    private var number: Int = 11;
    private val binding get() = _binding!!
    private var _ktorApi: KtorRepository? = null
    private val ktorApi get() = _ktorApi!!
    private lateinit var characters: List<Hero>
    private lateinit var personsRepository: PersonRepo
    private var job: Job? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        _ktorApi = KtorRepository()
        personsRepository = PersonRepo(getDatabase(requireContext()))
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            binding.textView.setText(requireArguments().getString("name"))
        }
        if (StorageService.isFileInDownloads(fileName) ||
            StorageService.isFileInInternalStorage(requireContext(), "characterList_11.txt")
        ) {
            binding.load.visibility = View.INVISIBLE
        }

        binding.loadButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                StorageService.saveToDownloads(
                    requireContext(),
                    fileName,
                    characters.joinToString("; ")
                )
            } else {
                StorageService.saveToDownloads(fileName, characters.joinToString("; "))
            }
            binding.load.visibility = View.INVISIBLE
        }

        binding.settingsButton.setOnClickListener {
            val action = ChatFragmentDirections.actionChatFragmentToSettings()
            this.findNavController().navigate(action)
        }

        binding.sendButton.setOnClickListener {
            number = binding.numberText.text.toString().toIntOrNull() ?: 11
            loadData(number)
        }

        loadData(number)

        Log.d("ChatFragment", "onViewCreated")
    }

    private fun loadData(number: Int) {
        job?.cancel()
        lifecycleScope.launch {
            if (!personsRepository.checkPersonInDatabase(number)) {
                characters = ktorApi.getHeroes(number)
                binding.characterList.adapter = ResponseAdapter(characters)

                personsRepository.insertPersons(characters.map { dto ->
                    Person(
                        number = number,
                        name = dto.name,
                        culture = dto.culture,
                        born = dto.born,
                        titles = dto.titles?.joinToString(", "),
                        aliases = dto.aliases?.joinToString(", "),
                        playedBy = dto.playedBy?.joinToString(", ")
                    )
                })
            } else {
                job = lifecycleScope.launch {
                    personsRepository.getPersonsByNumber(number).collect { persons ->
                        characters = persons.map { person ->
                            Hero(
                                name = person.name,
                                culture = person.culture,
                                born = person.born,
                                titles = person.titles?.split(", "),
                                aliases = person.aliases?.split(", "),
                                playedBy = person.playedBy?.split(", ")
                            )
                        }
                        binding.characterList.adapter = ResponseAdapter(characters)
                    }
                }
            }
        }
    }

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeFragment", "onCreate")
    }

    override fun onStop() {
        super.onStop()
        Log.d("HomeFragment", "onStop")
    }

    override fun onStart() {
        super.onStart()
        Log.d("HomeFragment", "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("HomeFragment", "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d("HomeFragment", "onResume")
    }
}
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.messenger411.Adapters.ChatAdapter
//import com.example.messenger411.Data.Chat
//
//class ChatFragment : Fragment(R.layout.fragment_chat) {
//
//    private lateinit var chatList: List<Chat>
//    private lateinit var rvChatList: RecyclerView
//    private lateinit var chatAdapter: ChatAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_chat, container, false)
//
//        chatList = listOf(
//            Chat("John", "10:30 AM", "Hello!", R.drawable.ic_launcher_foreground),
//            Chat("Sarah", "11:00 AM", "Hi, how are you?", R.drawable.logo),
//            Chat(
//                "Alex",
//                "11:30 AM",
//                "What's up?",
//                com.google.android.material.R.drawable.abc_btn_check_material
//            )
//        )
//
////        rvChatList = view.findViewById(R.id.rv_chat_list)
//        chatAdapter = ChatAdapter(chatList)
//        rvChatList.layoutManager = LinearLayoutManager(requireContext())
//        rvChatList.adapter = chatAdapter
//
//        return view
//    }
//
//    override fun onStart() {
//        super.onStart()
//        Log.d("ChatFragment", "onStart")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.d("ChatFragment", "onResume")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.d("ChatFragment", "onPause")
//    }
//
//
//    override fun onStop() {
//        super.onStop()
//        Log.d("ChatFragment", "onStop")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d("ChatFragment", "onDestroy")
//    }
//}
