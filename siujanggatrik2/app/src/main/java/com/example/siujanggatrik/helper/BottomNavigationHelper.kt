package com.example.siujanggatrik.helper

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import com.example.siujanggatrik.DashboardActivity
import com.example.siujanggatrik.HistoryActivity
import com.example.siujanggatrik.ProfileActivity
import com.example.siujanggatrik.PermohonanNidiActivity
import com.example.siujanggatrik.R

object BottomNavigationHelper {

    fun setup(activity: Activity) {

        val home =
            activity.findViewById<LinearLayout>(R.id.navHome)


        val history =
            activity.findViewById<LinearLayout>(R.id.navHistory)

        val profile =
            activity.findViewById<LinearLayout>(R.id.navProfile)

        home?.setOnClickListener {

            if (activity !is DashboardActivity) {

                activity.startActivity(
                    Intent(activity, DashboardActivity::class.java)
                )

                activity.finish()
            }
        }

        history?.setOnClickListener {

            if (activity !is HistoryActivity) {

                activity.startActivity(
                    Intent(activity, HistoryActivity::class.java)
                )

                activity.finish()
            }
        }

        profile?.setOnClickListener {

            if (activity !is ProfileActivity) {

                activity.startActivity(
                    Intent(activity, ProfileActivity::class.java)
                )

                activity.finish()
            }
        }
    }
}