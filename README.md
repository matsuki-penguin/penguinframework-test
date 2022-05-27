# Penguin Test

Penguin Test consists of an Extension Model for JUnit 5, which supports the implementation of test code with JUnit 5.

Penguin Test can manage test data and expected values in Excel and CSV files, which can then be used to perform the following functions.

* Annotation-based database initialization
* Annotation-based Java Bean Initialization (unimplement)
* Assertions for values stored in the database (unimplement)
* Assertion of the value stored in the Java Bean (unimplement)

## Enable Penguin Test

To enable Penguin Test in a JUnit 5 test class, specify PenguinExtension.class in the ExtendWith annotation of the test class.

```java
@ExtendWith({ SpringExtension.class, PenguinExtension.class })    ➊
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProfileDaoInitExcelTest {

    @Test
    void testFindAllClassInit() {
        ・
        ・
        ・
```

➊ Add PenguinExtension.class to the ExtendWith annotation.  

## Database initialization

### Prepare initialization file

Initialization files are created in either Excel or CSV file format.  
The table name to be initialized is specified by sheet name for Excel files or file name for CSV files.  
In both file formats, the first line is a header line that lists the column names to be initialized, and the second and subsequent lines specify the contents to be initialized.

### Database initialization by annotation

The database can be initialized by specifying the TableValueSource annotation in the JUnit 5 test class.  
The value attribute or path attribute of the TableValueSource annotation should contain the path to the file containing the contents to initialize the database.

```java
@ExtendWith({ SpringExtension.class, PenguinExtension.class })
@SpringBootTest(classes = Application.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TableValueSource("prepare_for_class.xlsx")                       ➊
class ProfileDaoInitExcelTest {

    @Test
    void testFindAllClassInit() {
        ・
        ・
        ・
    }

    @Test
    @TableValueSource(path = "prepare_for_method.xlsx")           ➋
    void testFindAllMethodInit() {
        ・
        ・
        ・
    }
```

➊ If you wish to initialize the database on a class-by-class basis, specify a TableValueSource annotation in the test class that specifies the path to a file containing the contents of the database to be initialized.  
➋ If you wish to initialize the database on a method-by-method basis, specify a TableValueSource annotation in the test method that specifies the path to a file containing the contents of the database to be initialized.
