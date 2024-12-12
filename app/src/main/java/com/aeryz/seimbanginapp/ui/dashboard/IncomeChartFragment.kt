package com.aeryz.seimbanginapp.ui.dashboard

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.data.local.database.entity.TransactionEntity
import com.aeryz.seimbanginapp.data.local.database.entity.toTransactionItemList
import com.aeryz.seimbanginapp.databinding.FragmentIncomeChartBinding
import com.aeryz.seimbanginapp.model.TransactionItem
import com.aeryz.seimbanginapp.ui.transaction.transactionDetail.TransactionDetailActivity
import com.aeryz.seimbanginapp.ui.transaction.transactionHistory.TransactionHistoryActivity
import com.aeryz.seimbanginapp.ui.transaction.transactionHistory.TransactionListAdapter
import com.aeryz.seimbanginapp.utils.formatAmount
import com.aeryz.seimbanginapp.utils.proceedWhen
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import org.koin.androidx.viewmodel.ext.android.viewModel

class IncomeChartFragment : Fragment() {

    private var _binding: FragmentIncomeChartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIncomeChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeTransaction()
        binding.tvSeeAll.setOnClickListener { navigateToTransactionHistory() }
    }

    private fun observeTransaction() {
        viewModel.getTransactionByType(0).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.rvTransactionList.isVisible = true
                    binding.tvTransactionListError.isVisible = false
                    val data = it.payload
                    setupPieChart(data)
                    data?.toTransactionItemList()?.let { it1 -> setupRecyclerView(it1) }
                },
                doOnError = {
                    binding.tvIncomeAmount.text = formatAmount("0.0", 0)
                    binding.rvTransactionList.isVisible = false
                    binding.tvTransactionListError.isVisible = true
                    binding.tvTransactionListError.text = it.exception?.localizedMessage.orEmpty()
                },
                doOnEmpty = {
                    binding.tvIncomeAmount.text = formatAmount("0.0", 0)
                    binding.rvTransactionList.isVisible = false
                    binding.tvTransactionListError.isVisible = true
                    binding.tvTransactionListError.text =
                        getString(R.string.text_transaction_still_empty)
                }
            )
        }
    }

    private fun setupPieChart(listEntity: List<TransactionEntity>?) {
        val entries = ArrayList<PieEntry>()

        val filteredData = listEntity?.filter { it.type == 0 } ?: emptyList()
        val totalAmount = filteredData.sumOf { it.amount?.toDoubleOrNull() ?: 0.0 }
        val categoryAmountMap = filteredData.groupBy { it.category }.mapValues { entry ->
            entry.value.sumOf { it.amount?.toDoubleOrNull() ?: 0.0 }
        }

        for ((category, amount) in categoryAmountMap) {
            if (totalAmount > 0) {
                val percentage = (amount / totalAmount * 100).toFloat()
                entries.add(PieEntry(percentage, category))
            }
        }

        Log.d("PieChart", "Entries: $entries")

        if (entries.isEmpty()) {
            Log.e("PieChart", "No data to display!")
            return
        }

        val dataSet = PieDataSet(entries, "Category").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            valueTextColor = Color.WHITE
            valueTextSize = 12f
        }

        val pieData = PieData(dataSet).apply {
            setValueFormatter(PercentFormatter())
        }

        binding.pieChart.apply {
            data = pieData
            invalidate()
            description.isEnabled = false
            setUsePercentValues(true)
            setExtraOffsets(5f, 10f, 10f, 10f)
            setDragDecelerationFrictionCoef(0.95f)
            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)
            holeRadius = 36f
            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)
            transparentCircleRadius = 40f
            setDrawCenterText(true)
            centerText = "Income Chart"
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(12f)
            animateY(1400, Easing.EaseInOutQuad)
            legend.isEnabled = false
        }
        binding.tvIncomeAmount.text = formatAmount(totalAmount.toString(), 0)
    }

    private fun setupRecyclerView(listItem: List<TransactionItem>) {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvTransactionList.layoutManager = layoutManager
        val adapter = TransactionListAdapter {
            TransactionDetailActivity.startActivity(requireActivity(), it)
        }
        binding.rvTransactionList.adapter = adapter
        adapter.submitData(listItem.take(5))
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
