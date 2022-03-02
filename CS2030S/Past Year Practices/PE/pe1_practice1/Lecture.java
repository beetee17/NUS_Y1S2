public class Lecture extends ScheduledClass {
  public Lecture(String modCode, int classID, String venueID, Instructor instructor, int startTime) {
    super("L", modCode, classID, venueID, 2, instructor, startTime);

  }

  @Override
  public boolean clashWith(ScheduledClass otherClass) {
    if (otherClass instanceof Lecture) {
      // no two lectures can have overlapping time slots
      return this.hasSameModule(otherClass) && this.timeSlotOverlapsWith(otherClass);
    }

    if (otherClass instanceof Tutorial) {
      // cannot clash with a tutorial of the same module
      return this.timeSlotOverlapsWith(otherClass) && this.hasSameModule(otherClass);
    }

    return false;
  }

}
