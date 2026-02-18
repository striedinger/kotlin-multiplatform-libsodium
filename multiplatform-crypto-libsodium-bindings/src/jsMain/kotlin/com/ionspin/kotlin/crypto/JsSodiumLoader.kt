package ext.libsodium.com.ionspin.kotlin.crypto

import com.ionspin.kotlin.crypto.getSodiumLoaded
import com.ionspin.kotlin.crypto.sodiumLoaded
import ext.libsodium._libsodiumPromise
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Ugljesa Jovanovic
 * ugljesa.jovanovic@ionspin.com
 * on 27-May-2020
 */
object JsSodiumLoader {

    suspend fun load() = suspendCoroutine { continuation ->
        if (!getSodiumLoaded()) {
            _libsodiumPromise.then<dynamic> {
                sodiumLoaded = true
                continuation.resumeWith(Result.success(Unit))
            }.catch { e ->
                continuation.resumeWith(Result.failure(e))
            }
        } else {
            continuation.resumeWith(Result.success(Unit))
        }
    }

    fun loadWithCallback(doneCallback: () -> (Unit)) {
        if (!getSodiumLoaded()) {
            _libsodiumPromise.then<dynamic> {
                sodiumLoaded = true
                doneCallback.invoke()
            }
        } else {
            doneCallback.invoke()
        }
    }
}
