package com.hf.starwars.details

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hf.presentation.details.PersonDetailsViewModel
import com.hf.presentation.model.PersonListItemView
import com.hf.starwars.Constants.PERSON_EXTRA_KEY
import com.hf.starwars.R
import com.hf.starwars.ViewModelFactory
import dagger.android.AndroidInjection

import kotlinx.android.synthetic.main.activity_person_details.*
import kotlinx.android.synthetic.main.content_person_details.*
import javax.inject.Inject

class PersonDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel : PersonDetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)
        setSupportActionBar(toolbar)

        supportActionBar?.title = ""
        AndroidInjection.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(PersonDetailsViewModel::class.java)

        setUpObservers()

        (intent.getParcelableExtra(PERSON_EXTRA_KEY) as PersonListItemView?)?.let {
            viewModel.onPersonReceived(it)
        }

    }

    private fun setUpObservers() {

        viewModel.personDetailsLiveData.observe(this, Observer {
            it?.data?.let {
                supportActionBar?.title = it.name
                nameTv.text = it.name
                birthYearTv.text = it.birth_year
                heightTv.text = it.height
            }
        })

        viewModel.specieLiveData.observe(this, Observer {
            it?.data?.let {
                languageTv.text = it.language
                specieNameTv.text = it.name
            }
        })

        viewModel.planetLiveData.observe(this, Observer {
            it.data?.let {
                homeworldTv.text = it.name
                populationTv.text = it.population
            }
        })

        viewModel.getFilmLiveData.observe(this, Observer {
            it?.data?.let {

            }
        })

        viewModel.filmLiveData.observe(this, Observer {
            it?.data?.let {
                var text = ""

                for (f in it) {
                    text += f.title + ", "
                }

                if (it.size > 0) text = text.substring(0, text.length - 2)

                filmsTv.text = text

            }
        })
    }

}
