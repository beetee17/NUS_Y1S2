public class Tutorial extends ScheduledClass {

  public Tutorial(String modCode, int classID, String venueID, Instructor instructor, int startTime) {
    super("T", modCode, classID, venueID, 1, instructor, startTime);
  }

  @Override
  public boolean clashWith(ScheduledClass otherClass) {
    if (otherClass instanceof Tutorial) {
      // tutorials of the same mod can be scheduled in parallel if the instructors and venues are different
      if (this.hasSameModule(otherClass) && this.timeSlotOverlapsWith(otherClass)) {
        return this.hasSameInstructor(otherClass) || this.hasSameVenue(otherClass);

      } else {
        return this.timeSlotOverlapsWith(otherClass);
      }
    }

    if (otherClass instanceof Lecture) {
      // cannot clash with a tutorial of the same module
      return this.timeSlotOverlapsWith(otherClass) && this.hasSameModule(otherClass);
    }

    return false;
  }
}
