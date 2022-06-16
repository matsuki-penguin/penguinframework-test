# Penguin Test

Penguin TestはJUnit 5のExtension Modelで構成されており、JUnit 5によるテストコードの実装をサポートするものです。

Penguin Testではテストデータや期待値をExcelファイルやCSVファイルにて管理することができ、そのデータを用いて、次の機能を実行することができます。

* アノテーションベースによるデータベースの初期化
* アノテーションベースによるJava Beanの初期化
* データベースに格納されている値のアサーション (未実装)
* Java Beanに格納されている値のアサーション (未実装)

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
Java Beanの場合は初期化ファイルの2行目の内容で初期化されます。Java BeanのList、もしくは配列の場合は初期化ファイルの2行目以降の行数を要素数として初期化されます。

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
