package com.aeryz.seimbanginapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aeryz.seimbanginapp.databinding.FragmentHomeBinding
import com.aeryz.seimbanginapp.ui.transaction.createTransaction.CreateTransactionActivity
import com.aeryz.seimbanginapp.ui.transaction.transactionHistory.TransactionHistoryActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCreateTransaction.setOnClickListener {
            navigateToCreateTransaction()
        }
        binding.btnTransactionHistory.setOnClickListener {
            navigateToTransactionHistory()
        }
    }

    private fun navigateToCreateTransaction() {
        val intent = Intent(requireContext(), CreateTransactionActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToTransactionHistory() {
        val intent = Intent(requireContext(), TransactionHistoryActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}