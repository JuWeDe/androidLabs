package com.example.messenger411

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.messenger411.Data.User
import com.example.messenger411.databinding.FragmentSignInBinding


class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val args: SignInFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.emailEditText.setText(args.email)
        binding.passwordEditText.setText(args.password)
        Log.d("SignInFragment", "onViewCreated")
        binding.logInButton.setOnClickListener {
            if (isValidEmail(binding.emailEditText.text.toString()) && isValidPassword(binding.emailEditText.text.toString())) {
                val action = SignInFragmentDirections.actionSignInFragmentToChatFragment()
                this.findNavController().navigate(action)
            } else if (!isValidPassword(binding.emailEditText.text.toString())) {
                Toast.makeText(this.activity, "Введите корректный пароль", Toast.LENGTH_SHORT)
                    .show();
            } else if (!isValidEmail(binding.emailEditText.text.toString())){
                Toast.makeText(this.activity, "Введите корректный Email", Toast.LENGTH_SHORT)
                .show();
            }
        }
    }
    private fun isValidEmail(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun isValidPassword(password: CharSequence): Boolean {
        return password.length >= 8
    }
}
