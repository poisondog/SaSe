package start.android.kotlin.utils

import start.android.kotlin.ui.MainViewModelFactory

object InjectUtils {
	fun getMainViewModelFactory(): MainViewModelFactory {
		return MainViewModelFactory()
	}
}
