package com.ionspin.kotlin.crypto

/**
 * Created by Ugljesa Jovanovic (jovanovic.ugljesa@gmail.com) on 02/Aug/2020
 */
expect object LibsodiumInitializer {
    fun isInitialized() : Boolean

    suspend fun initialize()

    fun initializeWithCallback(done: () -> (Unit))
}

/**
 * Ensures a successful response from the Libsodium init call.
 *
 * The init call has a slightly different return pattern, documented as:
 *
 *     sodium_init() returns 0 on success, -1 on failure, and 1 if the library had already been initialized.
 *
 * This function will throw if anything other than 0 or 1 is returned.
 */
internal fun Int.ensureInitSuccess() {
    when (this) {
        0, 1 -> {
            // no-op: both successful responses
        }
        else -> throw GeneralLibsodiumException("Libsodium initialization failed with code: $this")
    }
}
