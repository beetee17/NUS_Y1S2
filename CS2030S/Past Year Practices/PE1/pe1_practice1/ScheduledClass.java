abstract class ScheduledClass implements Comparable<ScheduledClass> {
  private String type;
  private String modCode;
  private int classID;
  private String venueID;
  private int startTime;
  private int duration;
  private Instructor instructor;

  public ScheduledClass(String type, String modCode, int classID, String venueID, int duration, Instructor instructor, int startTime) {
    this.type = type;
    this.modCode = modCode;
    this.classID = classID;
    this.venueID = venueID;
    this.duration = duration;
    this.instructor = instructor;
    this.startTime = startTime;
  }

  public boolean hasSameModule(ScheduledClass otherClass) {
    return this.modCode.equals(otherClass.modCode);
  }

  public boolean hasSameInstructor(ScheduledClass otherClass) {
    return this.instructor.equals(otherClass.instructor);
  }

  public boolean hasSameVenue(ScheduledClass otherClass) {
    return this.venueID.equals(otherClass.venueID);
  }

  public boolean timeSlotOverlapsWith(ScheduledClass otherClass) {
    int myEndTime = this.startTime + this.duration;
    int otherEndTime = otherClass.startTime + otherClass.duration;
    
    if (myEndTime > otherClass.startTime && myEndTime <= otherEndTime) return true;
    
    if (otherEndTime > this.startTime && otherEndTime <= myEndTime)
      return true;

    return false;
  }

  abstract boolean clashWith(ScheduledClass otherClass);

  @Override
  public String toString() {
    return String.format("%s %s%d @ %s [%s] %d--%d", this.modCode, this.type, this.classID, this.venueID, this.instructor, this.startTime, this.startTime + this.duration);
  }

  @Override
  public int compareTo(ScheduledClass other) {
   Integer myTime = this.startTime;
   Integer otherTime = other.startTime;
   String myMod = this.modCode;
   String otherMod = other.modCode;
   Integer myClass = this.classID;
   Integer otherClass = other.classID;
   if (myTime.compareTo(otherTime) == 0) {
     if (myMod.compareTo(otherMod) == 0) {
       return myClass.compareTo(otherClass);
     }
     return myMod.compareTo(otherMod);
   }
   return myTime.compareTo(otherTime);
  }
}
