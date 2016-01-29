import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;

public class StoreTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Store.all().size(), 0);
  }

  @Test
  public void storeInitiatesCorrectly() {
    Store store = new Store("Nike Outlet");
    store.save();
    Store savedStore = Store.find(store.getId());
    assertEquals("Nike Outlet", store.getName());
    assert(store.equals(savedStore));
  }


}
