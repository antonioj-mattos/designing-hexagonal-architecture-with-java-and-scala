package dev.amattos

/**
  * An alias for `Either[Throwable, A]`.
  */
type Task[A] = Either[Throwable, A]