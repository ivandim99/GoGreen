package groupxii.database;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import java.io.IOException;

public class DatabaseTest {
	@Test
	public void testDbAddr() {
		Database db = new Database();
		db.setDbAddr("test");
		assertEquals(db.getDbAddr(), "test");
	}

	@Test
	public void testDbPort() {
		Database db = new Database();
		db.setDbPort(1234);
		assertEquals(db.getDbPort(), 1234);
	}

	@Test
	public void testDbName() {
		Database db = new Database();
		db.setDbName("testDB");
		assertEquals(db.getDbName(), "testDB");
	}

	/*
	@Test
	public void testConstructor() {
		Database db = new Database();
		assertEquals(db.getDbAddr(), "localhost");
		assertEquals(db.getDbPort(), 27017);
		assertEquals(db.getDbName(), "GoGreen");
	}
	*/

	@Test
	public void testNotRunning() {
		Database db = new Database();
		assertFalse(db.isRunning());
	}

	@Test
	public void testRunning() throws IOException {
		Database db = new Database();
		db.startDb();
		assertTrue(db.isRunning());
	}
	/*
	@Test
	public void testWrongHost() {
		Database db = new Database();
		db.setDbAddr("badhost");
		db.startDb();
		assertFalse(db.isRunning());
	}
	*/

	@Test
	public void testSave() throws IOException {
		Database.instance.setDbName("test");
		Database.instance.startDb();
		VehicleEntry entry = new VehicleEntry(1337, "car");
		Database.instance.save(entry);
		assertEquals(entry.toDbObject(), Database.instance.findVehicleEntry(entry));
		//TODO Drop the test DB
	}

//	@Test
//	public void testSaveNonBlocking() {
//		Database.instance.setDbName("test");
//		Database.instance.startDb();
//		VehicleEntry entry = new VehicleEntry(1337, "car");
//		Database.instance.saveNonBlocking(entry);
//
//		assertEquals(entry.toDbObject(), Database.instance.findVehicleEntry(entry));
//		//TODO Drop the test DB
//	}

	/*
	@Test
	public void testSaveMeal() {
		Database.instance.setDbName("test");
		Database.instance.startDb();
		MealEntry entry = new MealEntry(1, "apple", "pizza", 100, 100, 200);
		Database.instance.save(entry);
		assertEquals(entry.toDbObject(), Database.instance.findMealEntry(entry));
		//TODO Drop the test DB
	}

	@Test
	public void testSaveMealNonBlocking() {
		Database.instance.setDbName("test");
		Database.instance.startDb();
		MealEntry entry = new MealEntry(1, "apple", "pizza", 100, 100, 200);
		Database.instance.saveNonBlocking(entry);

		assertEquals(entry.toDbObject(), Database.instance.findMealEntry(entry));
		//TODO Drop the test DB
	}
	*/

	/*
	@Test
	public void testSaveUser() {
		Database.instance.setDbName("test");
		Database.instance.startDb();
<<<<<<< HEAD
		MealEntry entry = new MealEntry("GRAPEFRUIT", 100, "GRAPEFRUIT", 100);
=======
		List<Integer> list = new ArrayList<>();
<<<<<<< HEAD
		UserEntry entry = new UserEntry(1, "Ivan","pass",100,1,6,list);
=======
		UserEntry entry = new UserEntry(1, "Ivan",100,1,6,list);
>>>>>>> master
>>>>>>> origin
		Database.instance.save(entry);
		assertEquals(entry.toDbObject(), Database.instance.findUserEntry(entry));
		//TODO Drop the test DB
	}
	*/

	/*
	@Test
	public void testSaveUserNonBlocking() {
		Database.instance.setDbName("test");
		Database.instance.startDb();
		List<Integer> list = new ArrayList<>();
		UserEntry entry = new UserEntry(1, "Ivan",100,1,6,list);
		Database.instance.saveNonBlocking(entry);

		assertEquals(entry.toDbObject(), Database.instance.findUserEntry(entry));
		//TODO Drop the test DB
	}
	*/
	/*
	@Test
	public void testUserById() {
		Database.instance.setDbName("test");
		Database.instance.startDb();
		List<Integer> list = new ArrayList<>();
		UserEntry entry = new UserEntry(1, "Ivan","pass",100,1,6,list);
		Database.instance.saveNonBlocking(entry);
		assertNotEquals(entry.toDbObject(),Database.instance.findUserById(2));
	}

	@Test
	public void testSortUsersByPoints() {
		Database.instance.setDbName("test");
		Database.instance.startDb();
		List<Integer> list = new ArrayList<>();
		UserEntry entry = new UserEntry(1, "Ivan","pass",100,1,6,list);
		Database.instance.saveNonBlocking(entry);
		assertNotEquals(entry.toDbObject(),Database.instance.sortUsersByReducedCo2());
	}

	@Test
	public void testAddFriend() {
		Database.instance.setDbName("test");
		Database.instance.startDb();
		List<Integer> list = new ArrayList<>();
		UserEntry entry = new UserEntry(1, "Ivan","pass",100,1,6,list);
		Database.instance.saveNonBlocking(entry);
		Database.instance.addFriend("Ivan",2);
		assertEquals(entry.getFriendsId(),list);
	}
	*/
//	@Test
//	public void testUserById() throws IOException {
//		Database.instance.setDbName("test");
//		Database.instance.startDb();
//		List<Integer> list = new ArrayList<>();
//		UserEntry entry = new UserEntry(1, "Ivan",100,1,6,list);
//		Database.instance.saveNonBlocking(entry);
//		assertNotEquals(entry.toDbObject(),Database.instance.findUserById(2));
//	}
//
//	@Test
//	public void testSortUsersByPoints() throws IOException {
//		Database.instance.setDbName("test");
//		Database.instance.startDb();
//		List<Integer> list = new ArrayList<>();
//		UserEntry entry = new UserEntry(1, "Ivan",100,1,6,list);
//		Database.instance.saveNonBlocking(entry);
//		assertNotEquals(entry.toDbObject(),Database.instance.sortUsersByReducedCo2());
//	}
//
//	@Test
//	public void testAddFriend() throws IOException {
//		Database.instance.setDbName("test");
//		Database.instance.startDb();
//		List<Integer> list = new ArrayList<>();
//		UserEntry entry = new UserEntry(1, "Ivan",100,1,6,list);
//		Database.instance.saveNonBlocking(entry);
//		Database.instance.addFriend("Ivan",2);
//		assertEquals(entry.getFriendsId(),list);
//	}
}
