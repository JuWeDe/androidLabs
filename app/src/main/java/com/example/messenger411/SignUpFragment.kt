package com.example.messenger411

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.messenger411.Data.User
import com.example.messenger411.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val args: SignUpFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.emailEditText.setText(args.email)
        binding.passwordEditText.setText(args.password)
        binding.nameEditText.setText(args.name)
        Log.d("SignUpFragment", "onViewCreated")

        binding.createAccountButton.setOnClickListener {
            if (!isValidEmail(binding.emailEditText.text.toString())) {
                Toast.makeText(this.activity, "Введите корректный Email", Toast.LENGTH_SHORT)
                    .show()
            } else if (!isValidPassword(binding.emailEditText.text.toString())) {
                Toast.makeText(this.activity, "Введите корректный пароль", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val user = User(
                    binding.nameEditText.text.toString(),
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )

                val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                this.findNavController().navigate(action)
            }
        }
    }

    private fun isValidPassword(password: CharSequence): Boolean {
        return password.length >= 8
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
