import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Schedule {
  private List<ScheduledClass> classes = new ArrayList<>();

  public Schedule add(ScheduledClass newClass) {
    for (ScheduledClass c : this.classes) {
      if (c.clashWith(newClass)) {
        return this;
      }
    }
    this.classes.add(newClass);
    return this;
  }

 @Override
 public String toString() {
   Collections.sort(this.classes);
   String str = "";
   for (ScheduledClass c : classes) 
    str += c.toString() + "\n";
   return str;
 }
}
