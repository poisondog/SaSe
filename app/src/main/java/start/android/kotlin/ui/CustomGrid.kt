package start.android.kotlin.ui

import java.util.logging.Logger
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import android.os.AsyncTask;
import android.widget.BaseAdapter
import android.content.Context
import android.view.View
import android.view.ViewGroup
//import kotlinx.coroutines.*
import start.android.kotlin.data.MyPic
import start.android.kotlin.app.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.Disposable
import io.reactivex.android.schedulers.AndroidSchedulers

class CustomGrid(val context:Context, val content:List<MyPic>): BaseAdapter() {

	override fun getView(position: Int, convertView:View?, parent:ViewGroup): View {
		val inflater = LayoutInflater.from(context)
		var grid = inflater.inflate(R.layout.grid_item, null)
//		val textView: TextView = grid.findViewById(R.id.grid_text) as TextView
//		textView.setText(content[position].thumbnail)
		val imageView: ImageView = grid.findViewById(R.id.grid_image) as ImageView
//		imageView.setImageResource(content[position].thumbnail);

		Observable.fromCallable<Bitmap> {
			println("fromCallable: " + Thread.currentThread().name)
			val urlImage:URL = URL(content[position].url)
			return@fromCallable urlImage.toBitmap()
		}
		.subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())
		.subscribe { item ->
			println("subscribe: " + Thread.currentThread().name)
			imageView.setImageBitmap(item)
		}

//		val task = LoadingTask(imageView)
//		task.execute(content[position].url)

		return grid;
	}

	override fun getItem(position: Int): String {
		return content[position].url
	}

	override fun getItemId(position: Int): Long {
		return position.toLong()
	}

	override fun getCount(): Int {
		return content.size;
	}
}

// extension function to get bitmap from url
fun URL.toBitmap(): Bitmap?{
	return try {
		BitmapFactory.decodeStream(openStream())
	}catch (e:IOException){
		e.printStackTrace()
		null
	}
}

class LoadingTask(val view:ImageView): AsyncTask<String, String, Bitmap>() {
	override fun doInBackground(vararg url: String): Bitmap? {
		val urlImage:URL = URL(url[0])
		return urlImage.toBitmap()
	}
	override fun onPostExecute(result: Bitmap) {
		view.setImageBitmap(result)
	}
}

