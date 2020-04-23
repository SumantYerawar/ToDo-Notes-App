package com.sumant.todonotesapp.onBoarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.sumant.todonotesapp.R


class OnBoardingTwoFragment : Fragment() {

    lateinit var nextTextView: TextView
    lateinit var backTextView: TextView

    lateinit var onOptionsClick: OnOptionsClick

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionsClick = context as OnOptionsClick
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
    }

    private fun bindViews(view: View) {
        nextTextView = view.findViewById(R.id.doneTextView)
        backTextView = view.findViewById(R.id.backTextView)
        clickListeners()
    }

    private fun clickListeners() {
        nextTextView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                onOptionsClick.onOptionDone()
            }
        })
        backTextView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                onOptionsClick.onOptionBack()
            }

        })
    }

    interface OnOptionsClick{
        fun onOptionDone()
        fun onOptionBack()
    }
}
