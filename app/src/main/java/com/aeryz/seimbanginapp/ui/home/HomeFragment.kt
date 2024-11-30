package com.aeryz.seimbanginapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.data.network.model.transactionHistory.toTransactionItemList
import com.aeryz.seimbanginapp.databinding.FragmentHomeBinding
import com.aeryz.seimbanginapp.model.TransactionItem
import com.aeryz.seimbanginapp.ui.transaction.createTransaction.CreateTransactionActivity
import com.aeryz.seimbanginapp.ui.transaction.transactionDetail.TransactionDetailActivity
import com.aeryz.seimbanginapp.ui.transaction.transactionHistory.TransactionHistoryActivity
import com.aeryz.seimbanginapp.ui.transaction.transactionHistory.TransactionListAdapter
import com.aeryz.seimbanginapp.utils.exception.ApiException
import com.aeryz.seimbanginapp.utils.proceedWhen
import com.aeryz.seimbanginapp.utils.withCurrencyFormat
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProfileData()
        observeProfileData()
        getTransactionHistory()
        observeTransactionHistory()
        setOnClickListener()
        observeInsertTransactionToDatabase()
    }

    override fun onResume() {
        super.onResume()
        getProfileData()
        getTransactionHistory()
    }

    private fun getProfileData() {
        viewModel.getProfileData()
    }

    private fun observeProfileData() {
        viewModel.profileData.observe(viewLifecycleOwner) { resultWrapper ->
            resultWrapper.proceedWhen(
                doOnSuccess = {
                    binding.content.isVisible = true
                    binding.contentState.root.isVisible = false
                    it.payload.let { response ->
                        binding.profileImage.load(response?.profileData?.profilePicture) {
                            crossfade(true)
                            placeholder(R.drawable.profile_image)
                            error(R.drawable.profile_image)
                            transformations(CircleCropTransformation())
                        }
                        binding.tvHelloProfile.text =
                            getString(R.string.text_hi_user, response?.profileData?.fullName)
                        binding.tvBalance.text = withCurrencyFormat(response?.profileData?.balance)
                    }
                },
                doOnLoading = {
                    binding.content.isVisible = false
                    binding.contentState.root.isVisible = true
                    binding.contentState.tvError.isVisible = false
                    binding.contentState.pbLoading.isVisible = true
                },
                doOnError = {
                    binding.content.isVisible = false
                    binding.contentState.root.isVisible = true
                    binding.contentState.tvError.isVisible = true
                    binding.contentState.pbLoading.isVisible = false
                    if (it.exception is ApiException) {
                        binding.contentState.tvError.text = it.exception.getParsedError()?.message
                    }
                }
            )
        }
    }

    private fun setupRecyclerView(data: List<TransactionItem>) {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvTransactionList.layoutManager = layoutManager
        val adapter = TransactionListAdapter {
            TransactionDetailActivity.startActivity(requireActivity(), it)
        }
        binding.rvTransactionList.adapter = adapter
        adapter.submitData(data)
    }

    private fun getTransactionHistory() {
        viewModel.getTransactionHistory(null, null)
    }

    private fun observeTransactionHistory() {
        viewModel.transactionHistory.observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.contentState.root.isVisible = false
                    binding.contentState.pbLoading.isVisible = false
                    binding.contentState.tvError.isVisible = false
                    binding.rvTransactionList.isVisible = true
                    it.payload?.let { response ->
                        response.data?.let { data ->
                            val sortedData = data.toTransactionItemList()
                                .sortedByDescending { item -> item.createdAt }
                            setupRecyclerView(sortedData.take(5))
                            viewModel.insertListToDatabase(sortedData)
                        }
                    }
                },
                doOnLoading = {
                    binding.contentState.root.isVisible = true
                    binding.contentState.pbLoading.isVisible = true
                    binding.contentState.tvError.isVisible = false
                    binding.rvTransactionList.isVisible = false
                },
                doOnError = {
                    binding.contentState.root.isVisible = true
                    binding.contentState.pbLoading.isVisible = false
                    binding.contentState.tvError.isVisible = true
                    binding.rvTransactionList.isVisible = false
                    if (it.exception is ApiException) {
                        binding.contentState.tvError.text = it.exception.getParsedError()?.message
                    }
                },
                doOnEmpty = {
                    binding.contentState.root.isVisible = true
                    binding.contentState.pbLoading.isVisible = false
                    binding.contentState.tvError.isVisible = true
                    binding.rvTransactionList.isVisible = false
                    binding.contentState.tvError.text =
                        getString(R.string.text_transaction_still_empty)
                }
            )
        }
    }

    private fun observeInsertTransactionToDatabase() {
        viewModel.insertListToDatabaseResult.observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        requireContext(),
                        "Update Local Transaction Success",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun setOnClickListener() {
        binding.btnAdd.setOnClickListener {
            navigateToCreateTransaction()
        }
        binding.tvSeeAll.setOnClickListener {
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