class Log {
  static Trace<Long> log2(Long n) {
    return (n == 1) ? Trace.of(1L) : Trace.of(n).map(x->x).flatMap(y -> log2(y/2));
  }

  static long log(long n) {
    System.out.println(n);
    if (n == 1) {
      return 0;
    } else {
      return 1 + log(n/2);
    }
  }
}
