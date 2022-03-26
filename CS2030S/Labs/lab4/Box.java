/**
 * A generic box storing an item.
 * CS2030S Lab 4
 * AY21/22 Semester 2
 *
 * @Brandon (Group 12A)
 */
class Box<T> {
  private final T content;
  private static final Box<?> EMPTY_BOX = new Box<>(null);

  private Box(T content) {
    this.content = content;
  }

  public static <T> Box<T> of(T content) {
    if (content == null) {
      return null;
    } else {
      return new Box<T>(content);
    }
  }

  public static <T> Box<T> ofNullable(T content) {
    if (content == null) {
      return Box.empty();
    } else {
      return new Box<T>(content);
    }
  }

  public static <T> Box<T> empty() {
    @SuppressWarnings("unchecked")
    Box<T> emptyBox = (Box<T>) EMPTY_BOX;
    return emptyBox;
  }

  public boolean isPresent() {
    return this != EMPTY_BOX;
  }

  public Box<T> filter(BooleanCondition<? super T> cond) {
    if (this == EMPTY_BOX || cond.test(this.content)) {
      return this;
    } else {
      return Box.empty();
    }
  }

  public <U> Box<U> map(Transformer<? super T, U> transformer) {
    if (this == EMPTY_BOX) {
      return Box.<U>empty();
    } else {
      U newContent = transformer.transform(this.content);
      return Box.ofNullable(newContent);
    }
  }

  @Override
  public String toString() {
    return String.format("[%s]", (this == EMPTY_BOX ? "" : this.content));
  }
  
  @Override 
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } 
    if (obj instanceof Box<?>) {
      Box<?> otherBox = (Box<?>) obj;
      if (this.content == otherBox.content) {
        return true;
      }
      if (this.content == null || otherBox.content == null) {
        return false;
      }
      return this.content.equals(otherBox.content);
    }
    return false;
  }
}

