# Penguin Test

Penguin TestはJUnit 5のExtension Modelで構成されており、JUnit 5によるテストコードの実装をサポートするものです。

Penguin Testではテストデータや期待値をExcelファイルやCSVファイルにて管理することができ、そのデータを用いて、次の機能を実行することができます。

* アノテーションベースによるデータベースの初期化
* アノテーションベースによるJava Beanの初期化
* データベースに格納されている値のアサーション
* Java Beanに格納されている値のアサーション

## Penguin Testの有効化

JUnit 5のテストクラスで、Penguin Testを有効にする場合、テストクラスのExtendWithアノテーションにPenguinExtension.classを指定します。

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

➊ ExtendWithアノテーションにPenguinExtension.classを追加します。  

## データベースの初期化

### 初期化ファイルの用意

初期化ファイルはExcelファイル、CSVファイルのいずれかの形式で作成します。  
初期化するテーブル名は、Excelファイルの場合はシート名、CSVファイルの場合はファイル名で指定します。  
どちらのファイル形式も1行目はヘッダ行で、初期化する列名を羅列し、2行目以降に初期化する内容を指定します。

### アノテーションによるデータベースの初期化

JUnit 5のテストクラスにTableValueSourceアノテーションを指定することで、データベースを初期化することができます。  
TableValueSourceアノテーションのvalue属性、もしくはpath属性にデータベースを初期化する内容を記載したファイルのパスを記載します。

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

➊ データベースをクラス単位で初期化したい場合、データベースを初期化する内容を記載したファイルのパスを指定したTableValueSourceアノテーションをテストクラスに指定します。  
➋ データベースをメソッド単位で初期化したい場合、データベースを初期化する内容を記載したファイルのパスを指定したTableValueSourceアノテーションをテストメソッドに指定します。

## Java Beanの初期化

### 初期化ファイルの用意

初期化ファイルはExcelファイル、CSVファイルのいずれかの形式で作成します。  
Excelファイルの場合、初期化するJava Beanの単純クラス名と一致するシートが使用されます。  
どちらのファイル形式も1行目はヘッダ行で、初期化するJava Beanのフィールド名を羅列し、2行目以降に初期化する内容を指定します。  
単体のJava Beanの場合は初期化ファイルの2行目の内容で初期化されます。Java BeanのList、もしくは配列の場合は初期化ファイルの2行目以降の行数を要素数として初期化されます。

### アノテーションによるJava Beanの初期化

JUnit 5のテストクラスのクラス変数やメソッド引数のJava BeanにTableValueSourceアノテーションを指定することで、クラス変数やメソッド引数のJava Beanを初期化することができます。  
TableValueSourceアノテーションのvalue属性、もしくはpath属性にクラス変数やメソッド引数のJava Beanを初期化する内容を記載したファイルのパスを記載します。

```java
@ExtendWith(PenguinExtension.class)
class ProfileDaoInitExcelTest {

    @TableValueSource(path = "prepare_profile.xlsx")                   ➊
    private Profile profile;

    void testFindAllMethodInit(
            @TableValueSource(path = "param.xlsx") Profile param) {    ➋
        ・
        ・
        ・
    }
```

➊ Java Beanを初期化する内容を記載したファイルのパスを指定したTableValueSourceアノテーションをクラス変数に指定します。  
➋ Java Beanを初期化する内容を記載したファイルのパスを指定したTableValueSourceアノテーションをテストメソッドの引数に指定します。

## データベースのアサーション

### 期待値ファイルの用意

アサーションの期待値ファイルはExcelファイル、CSVファイルのいずれかの形式で作成します。  
Excelファイルの場合はアサーションするテーブル名と同一のシート名に期待値を記載します。  
どちらのファイル形式も1行目はヘッダ行で、アサーションする列名を羅列し、2行目以降にアサーションする期待値を指定します。

### データベースのアサーション方法

TableAssertionクラスにより、データベースをアサーションします。  
アサーションにはTableAssertionクラスのインスタンスを使用するので、TableAssertionクラスの変数を宣言する必要があります。  
TableAssertionクラスは明示的にインスタンス化する必要はなく、インスタンス変数、もしくはテストメソッドの引数を宣言し、@Loadアノテーションを指定すると、自動的にインスタンスが注入されます。  
TableAssertionクラスのアサーションメソッドにより、期待値ファイルとテーブルの値を比較することができます。

```java
@ExtendWith(PenguinExtension.class)
class ProfileDaoInitExcelTest {

    @Autowired
    private ProfileDao profileDao;

    @Load
    private TableAssertion tableAssertion;                                      ➊

    @Test
    void update() {
        ProfileEntity profile = new ProfileEntity();
        profile.setName("update");
        profile.setBirthday(LocalDate.of(2012, 12, 31));
        this.profileDao.updateById(1L, profile);

        this.tableAssertion.assertEquals("expected_update.xlsx", "PROFILE");    ➋
    }

    @Test
    void insert(@Load TableAssertion assertion) {                               ➌
        ・
        ・
        ・

        assertion.assertEquals("expected_insert.xlsx", "PROFILE");              ➍
    }
```

➊ TableAssertion型のフィールドを宣言して@Loadアノテーションを指定します。  
➋ 宣言したTableAssertion型のフィールドのassertEqualsメソッドを使用して期待値ファイルとテーブルを比較します。  
➌ TableAssertion型の引数を宣言して@Loadアノテーションを指定します。  
➍ 宣言したTableAssertion型の引数のassertEqualsメソッドを使用して期待値ファイルとテーブルを比較します。

## Java Beanのアサーション

### 期待値ファイルの用意

アサーションの期待値ファイルはExcelファイル、CSVファイルのいずれかの形式で作成します。  
Excelファイルの場合、アサーションするJava Beanの単純クラス名と一致するシートが使用されます。  
どちらのファイル形式も1行目はヘッダ行で、アサーションするJava Beanのフィールド名を羅列し、2行目以降にアサーションする期待値を指定します。  
単体のJava Beanの場合は期待値ファイルの2行目の内容でアサーションされます。Java BeanのList、もしくは配列の場合は期待値ファイルの2行目以降の行数を要素数としてアサーションされます。

### Java Beanのアサーション方法

BeanAssertionクラスにより、Java Beanをアサーションします。  
アサーションにはBeanAssertionクラスのインスタンスを使用するので、BeanAssertionクラスの変数を宣言する必要があります。  
BeanAssertionクラスは明示的にインスタンス化する必要はなく、インスタンス変数、もしくはテストメソッドの引数を宣言し、@Loadアノテーションを指定すると、自動的にインスタンスが注入されます。  
BeanAssertionクラスのアサーションメソッドにより、期待値ファイルとJava Beanのフィールドの値を比較することができます。

```java
@ExtendWith(PenguinExtension.class)
class ProfileDaoInitExcelTest {

    @Autowired
    private ProfileDao profileDao;

    @Load
    private BeanAssertion beanAssertion;                                                       ➊

    @Test
    void find() {
        ProfileEntity profile = this.profileDao.findById(1L);

        this.beanAssertion.assertEquals("expected_find.xlsx", profile);                        ➋
    }

    @Test
    void findAll(@Load BeanAssertion assertion) {                                              ➌
        List<ProfileEntity> profileList = this.profileDao.findAll();

        assertion.assertEquals("expected_find_all.xlsx", profileList, ProfileEntity.class);    ➍
    }
```

➊ BeanAssertion型のフィールドを宣言して@Loadアノテーションを指定します。  
➋ 宣言したBeanAssertion型のフィールドのassertEqualsメソッドを使用して期待値ファイルとJava Beanを比較します。  
➌ BeanAssertion型の引数を宣言して@Loadアノテーションを指定します。  
➍ 宣言したBeanAssertion型の引数のassertEqualsメソッドを使用して期待値ファイルとJava Beanを比較します。比較するJava BeanがListの場合、assertEqualsメソッドの第3引数にListの型となるクラスを指定する必要があります。
