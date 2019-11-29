package com.example.restkotlinized.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.restkotlinized.R
import com.example.restkotlinized.view.mainAdapter.NewzAdapter
import com.example.restkotlinized.view.fragments.ChosenFragment
import com.example.restkotlinized.view.view_interface_impl.StoriesFragment
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {
    private var disposable: Disposable? = null
    private lateinit var myFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            myFragment = supportFragmentManager.getFragment(savedInstanceState, "frag")!!
            println("get from save")
            return
        } else {
            myFragment = StoriesFragment(applicationContext)
        }

        val transaction = supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, myFragment)
            addToBackStack(null)
        }
        transaction.commit()

        disposable = NewzAdapter.switchObservable.subscribe({
            setTrans()
            println("onNext event")
        }, {
            println("Error -> $it")
        })
    }

    fun setTrans() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.apply {
            replace(R.id.fragment_container, ChosenFragment(applicationContext))
            addToBackStack(null)
        }
        transaction.commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("save")
        supportFragmentManager.putFragment(outState, "frag", myFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}