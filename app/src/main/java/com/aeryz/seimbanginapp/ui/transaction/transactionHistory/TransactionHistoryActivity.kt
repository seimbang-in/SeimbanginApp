package com.aeryz.seimbanginapp.ui.transaction.transactionHistory

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.data.network.model.transactionHistory.TransactionMeta
import com.aeryz.seimbanginapp.data.network.model.transactionHistory.toTransactionItemList
import com.aeryz.seimbanginapp.databinding.ActivityTransactionHistoryBinding
import com.aeryz.seimbanginapp.model.TransactionItem
import com.aeryz.seimbanginapp.ui.transaction.transactionDetail.TransactionDetailActivity
import com.aeryz.seimbanginapp.utils.exception.ApiException
import com.aeryz.seimbanginapp.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionHistoryBinding

    private val viewModel: TransactionHistoryViewModel by viewModel()

    private var isShowFilter = false

    private var editMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar()
        getData()
        observeTransactionHistory()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.transaction_history_menu, menu)
        editMenuItem = menu?.findItem(R.id.action_filter)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                isShowFilter = !isShowFilter
                updateEditMode()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateEditMode() {
        binding.clFilter.isVisible = isShowFilter
    }

    private fun setupRecyclerView(data: List<TransactionItem>) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvTransactionList.layoutManager = layoutManager
        val adapter = TransactionListAdapter {
            TransactionDetailActivity.startActivity(this, it)
        }
        binding.rvTransactionList.adapter = adapter
        adapter.submitData(data)
    }

    private fun setupPaginationControls(transactionMeta: TransactionMeta) {
        binding.btnPrevious.isVisible = transactionMeta.hasPreviousPage == true
        binding.btnNext.isVisible = transactionMeta.hasNextPage == true
        binding.tvPage.text = getString(
            R.string.format_pagination,
            transactionMeta.currentPage,
            transactionMeta.totalPages
        )

        binding.btnPrevious.setOnClickListener {
            if (viewModel.currentPage > 1) {
                viewModel.currentPage--
                getData()
            }
        }
        binding.btnNext.setOnClickListener {
            if (transactionMeta.hasNextPage == true) {
                viewModel.currentPage++
                getData()
            }
        }
        binding.etLimit.setText(viewModel.currentLimit.toString())
        binding.btnSearch.setOnClickListener {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            val currentFocusView = currentFocus
            if (currentFocusView != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocusView.windowToken, 0)
            }

            val newLimit = binding.etLimit.text.toString().toIntOrNull()
            if (newLimit != null) {
                viewModel.currentLimit = newLimit
                getData()
            }
        }
    }

    private fun observeTransactionHistory() {
        viewModel.transactionHistory.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.contentState.root.isVisible = false
                    binding.contentState.pbLoading.isVisible = false
                    binding.contentState.tvError.isVisible = false
                    binding.rvTransactionList.isVisible = true
                    binding.clPagination.isVisible = true
                    it.payload?.let { response ->
                        response.data?.let { data ->
                            setupRecyclerView(
                                data.toTransactionItemList()
                                    .sortedByDescending { item -> item.createdAt })
                        }
                        response.meta?.let { meta -> setupPaginationControls(meta) }
                    }
                },
                doOnLoading = {
                    binding.contentState.root.isVisible = true
                    binding.contentState.pbLoading.isVisible = true
                    binding.contentState.tvError.isVisible = false
                    binding.rvTransactionList.isVisible = false
                    binding.clPagination.isVisible = false
                },
                doOnError = {
                    binding.contentState.root.isVisible = true
                    binding.contentState.pbLoading.isVisible = false
                    binding.contentState.tvError.isVisible = true
                    binding.rvTransactionList.isVisible = false
                    binding.clPagination.isVisible = false
                    if (it.exception is ApiException) {
                        binding.contentState.tvError.text = it.exception.getParsedError()?.message
                    }
                },
                doOnEmpty = {
                    binding.contentState.root.isVisible = true
                    binding.contentState.pbLoading.isVisible = false
                    binding.contentState.tvError.isVisible = true
                    binding.rvTransactionList.isVisible = false
                    binding.clPagination.isVisible = false
                    binding.contentState.tvError.text =
                        getString(R.string.text_transaction_still_empty)
                }
            )
        }
    }

    private fun getData() {
        viewModel.getTransactionHistory(viewModel.currentLimit, viewModel.currentPage)
    }

    private fun setupToolBar() {
        val toolbar = binding.toolBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}