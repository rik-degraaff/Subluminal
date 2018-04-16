package tech.subluminal.shared.messages;

import java.util.List;
import java.util.function.Supplier;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONList;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a request to move a movable object.
 */
public abstract class MoveReq implements SONRepresentable {

  private static final String CLASS_NAME = MoveReq.class.getSimpleName();
  private static final String STARLIST_KEY = "starList";
  private List<String> stars;

  /**
   * Creates a new move request and initializes the list symbolizing the jumping path with an new
   * empty list.
   */
  public MoveReq(List<String> stars) {
    this.stars = stars;
  }
  


  /**
   * Creates ans returns a new MoveReq-extending object from its SON representation.
   *
   * @param son the SON representation of a move request.
   * @param reqSupplier the function enabling the input of any child of MoveReq.
   * @param <E> the type of move request to be converted.
   * @return the SON representation of a move request.
   * @throws SONConversionError if the conversion fails.
   */
  static <E extends MoveReq> E fromSON(SON son, Supplier<E> reqSupplier) throws SONConversionError {
    E moveReq = reqSupplier.get();
    SONList starList = son.getList(STARLIST_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, STARLIST_KEY));

    starList.strings().forEach(moveReq::addStar);

    return moveReq;
  }

  /**
   * @param star the id of a star to add to the target list.
   */
  public void addStar(String star) {
    stars.add(star);
  }

  /**
   * @return the list of targets the movable wants to fly by.
   */
  public List<String> getTargets() {
    return stars;
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    SONList starList = new SONList();
    stars.forEach(starList::add);
    return new SON().put(starList, STARLIST_KEY);
  }
}
