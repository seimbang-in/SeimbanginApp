package com.aeryz.seimbanginapp.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.aeryz.seimbanginapp.R
import com.aeryz.seimbanginapp.ui.ocr.OcrActivity
import com.aeryz.seimbanginapp.ui.splash.SplashActivity
import com.aeryz.seimbanginapp.ui.transaction.createTransaction.CreateTransactionActivity
import com.aeryz.seimbanginapp.utils.withCurrencyFormat

/**
 * Implementation of App Widget functionality.
 */
class CardBalanceWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, R.layout.card_balance_widget)

    // Setup tombol scan
    val mainIntent = Intent(context, SplashActivity::class.java)
    val mainPendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE)
    views.setOnClickPendingIntent(R.id.card_balance, mainPendingIntent)

    // Setup tombol scan
    val scanIntent = Intent(context, OcrActivity::class.java)
    val scanPendingIntent = PendingIntent.getActivity(context, 0, scanIntent, PendingIntent.FLAG_IMMUTABLE)
    views.setOnClickPendingIntent(R.id.btn_scan, scanPendingIntent)

    // Setup tombol add
    val addIntent = Intent(context, CreateTransactionActivity::class.java)
    val addPendingIntent = PendingIntent.getActivity(context, 1, addIntent, PendingIntent.FLAG_IMMUTABLE)
    views.setOnClickPendingIntent(R.id.btn_add, addPendingIntent)

    views.setTextViewText(R.id.tv_balance, withCurrencyFormat("0"))

    appWidgetManager.updateAppWidget(appWidgetId, views)
}
