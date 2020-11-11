package start.android.kotlin.utils

import start.android.kotlin.ui.MainViewModelFactory
import org.jsoup.Jsoup;

object InjectUtils {
	fun getMainViewModelFactory(): MainViewModelFactory {
		return MainViewModelFactory()
	}

	fun loadAllPic() :List<String> {
//		val url = "https://www.google.com/search?q=java+sparrow&sclient=img"
		val url = "https://www.google.com/search?q=java+sparrow&tbm=isch&ved=2ahUKEwjnsoje2vfsAhUNWpQKHd0TANMQ2-cCegQIABAA&oq=java+sparrow&gs_lcp=CgNpbWcQAzIECCMQJzIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjIECAAQHjoFCAAQsQM6AggAUN8iWMU3YIE5aABwAHgAgAGWAYgBtQySAQQwLjEymAEAoAEBqgELZ3dzLXdpei1pbWfAAQE&sclient=img&ei=b2OqX6e_Fo200QTdp4CYDQ&bih=675&biw=1280&client=firefox-b-d"
		val document = Jsoup.connect(url).get()
		val result = mutableListOf<String>()
		val media = document.select("img")
		for(e in media) {
			result.add(e.attr("data-src"))
		}
		return result.filterNotNull().filter { e -> e.length > 10 }
	}
}
