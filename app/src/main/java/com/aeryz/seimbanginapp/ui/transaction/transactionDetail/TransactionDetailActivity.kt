package com.aeryz.seimbanginapp.ui.transaction.transactionDetail

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.databinding.ActivityTransactionDetailBinding
import com.aeryz.seimbanginapp.model.TransactionItem
import com.aeryz.seimbanginapp.ui.transaction.transactionHistory.TransactionHistoryActivity
import com.aeryz.seimbanginapp.utils.exception.ApiException
import com.aeryz.seimbanginapp.utils.proceedWhen
import com.aeryz.seimbanginapp.utils.withCurrencyFormat
import com.aeryz.seimbanginapp.utils.withDateFormat
import com.shashank.sony.fancytoastlib.FancyToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionDetailBinding

    private val viewModel: TransactionDetailViewModel by viewModel()

    private var deleteMenuItem: MenuItem? = null

    private var transactionId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.getParcelableExtra<TransactionItem>(EXTRA_TRANSACTION)
        bindData(extras)
        setupToolBar()
        observeDeleteTransaction()
    }

    private fun observeDeleteTransaction() {
        viewModel.deleteTransactionResult.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.contentState.root.isVisible = false
                    binding.contentState.tvError.isVisible = false
                    binding.contentState.pbLoading.isVisible = false
                    showToast(
                        getString(R.string.text_delete_transaction_success),
                        FancyToast.SUCCESS
                    )
                    navigateToTransactionHistory()
                },
                doOnLoading = {
                    binding.contentState.root.isVisible = true
                    binding.contentState.tvError.isVisible = false
                    binding.contentState.pbLoading.isVisible = true
                },
                doOnError = {
                    binding.contentState.root.isVisible = true
                    binding.contentState.tvError.isVisible = false
                    binding.contentState.pbLoading.isVisible = false
                    if (it.exception is ApiException) {
                        showToast(it.exception.getParsedError()?.message, FancyToast.ERROR)
                    }
                }
            )
        }
    }

    private fun navigateToTransactionHistory() {
        val intent = Intent(this, TransactionHistoryActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
        finish()
    }

    private fun setupToolBar() {
        val toolbar = binding.toolBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.transaction_detail_menu, menu)
        deleteMenuItem = menu?.findItem(R.id.action_delete)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                transactionId?.let { deleteTransaction(it) }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun bindData(item: TransactionItem?) {
        transactionId = item?.id
        binding.tvType.text =
            (if (item?.type == 0) getString(R.string.text_income) else getString(R.string.text_outcome))
        binding.tvType.backgroundTintList = ColorStateList.valueOf(
            if (item?.type == 0) {
                ContextCompat.getColor(this, R.color.success_500)
            } else {
                ContextCompat.getColor(this, R.color.error_500)
            }
        )
        binding.tvCreateAt.text = item?.createdAt?.let { withDateFormat(it) }
        binding.tvDescription.text = item?.description
        binding.tvAmount.text = withCurrencyFormat(item?.amount)
        val productListAdapter = ProductListAdapter()
        item?.items?.let { items ->
            productListAdapter.submitData(items)
            binding.tvItemsCount.text = items.size.toString()
        }
        binding.rvProductList.apply {
            layoutManager = LinearLayoutManager(this@TransactionDetailActivity)
            adapter = productListAdapter
        }
    }

    private fun deleteTransaction(id: Int) {
        val dialog = AlertDialog.Builder(this)
            .setMessage(getString(R.string.text_delete_transaction_confirmation))
            .setPositiveButton(
                getString(R.string.text_yes)
            ) { _, _ ->
                viewModel.deleteTransaction(id)
                viewModel.deleteTransactionFromDb(id)
            }
            .setNegativeButton(
                getString(R.string.text_no)
            ) { _, _ ->
                // no-op , do nothing
            }.create()
        dialog.show()
    }

    private fun showToast(message: String?, type: Int) {
        FancyToast.makeText(this, message, FancyToast.LENGTH_LONG, type, false)
            .show()
    }

    companion object {
        const val EXTRA_TRANSACTION = "EXTRA_TRANSACTION"

        fun startActivity(context: Context, transactionItem: TransactionItem) {
            val intent = Intent(context, TransactionDetailActivity::class.java)
            intent.putExtra(EXTRA_TRANSACTION, transactionItem)
            context.startActivity(intent)
        }
    }
}
