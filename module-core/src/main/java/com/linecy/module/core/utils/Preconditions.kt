package com.linecy.module.core.utils

/**
 * @author linecy.
 */
object Preconditions {

  fun <T> checkNotNull(reference: T?): T {
    if (reference == null) {
      throw NullPointerException()
    }

    return reference
  }

  fun <T> checkNotNull(reference: T?, message: String): T {
    if (reference == null) {
      throw NullPointerException(message)
    }

    return reference
  }
}
