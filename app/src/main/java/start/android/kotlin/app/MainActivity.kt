package start.android.kotlin.app

import java.util.logging.Logger
import java.util.Timer
import java.util.TimerTask
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import start.android.kotlin.utils.InjectUtils
import start.android.kotlin.ui.MainViewModel
import start.android.kotlin.ui.CustomGrid
import start.android.kotlin.data.MyPic
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
	companion object {
		val log = Logger.getLogger(MainActivity::class.java.name)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		log.info("OnCreate Called")

		val factory = InjectUtils.getMainViewModelFactory()
		val viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

		viewModel.getPics().observe(this, Observer { quotes ->
			val adapter = CustomGrid(this, quotes)
//			val grid = findViewById(R.id.grid) as GridView
			grid.setAdapter(adapter);
		})
	}

	override fun onStart() {
		super.onStart()
		log.info("onStart Called")
	}

	override fun onRestart() {
		super.onRestart()
		log.info("OnRestart Called")
	}

	override fun onResume() {
		super.onResume()
		log.info("OnResume Called")
	}


	override fun onStop() {
		super.onStop()
		log.info("OnStop Called")
	}

	override fun onPause() {
		super.onPause()
		log.info("OnPause Called")
	}

	override fun onDestroy() {
		super.onDestroy()
		log.info("OnDestroy Called")
	}


}
