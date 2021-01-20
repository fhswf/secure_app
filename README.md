# SecureApp
Demo-Applikation für Android für Pentester, Entwickler und jeden, der Interesse an Application-Security hat!

***

Die SecureApp ist auf Basis meiner Bachelorarbeit entstanden. In dieser wurden die wichtigsten Techniken zur Vermeidung von Sicherheitslücken in modernen Android-Apps untersucht und voneinander abgegrenzt.

Die Applikation beinhaltet technische Lösungen zu bestimmten Sicherheitslücken, bezogen auf die [OWASP Mobile Top10 Risks](https://owasp.org/www-project-mobile-top-10/).

-----

## Download der APK
https://github.com/MaHo1194/secure_app/releases/tag/v1.0

## Backend
Der Quellcode des Backends kann hier eingesehen werden:

https://github.com/MaHo1194/secure_app_backend

-----

## Installation

Am besten lässt sich die SecureApp auf einem Android-Emulator ausführen, beispielsweise Genymotion:
https://www.genymotion.com/

-----

## Anleitung zum Testen der einzelnen Schwachstellen

### Lokale Datenspeicherung
Die SecureApp impelementiert die lokale Datenspeicherung, in Form der EncryptedSharedPreferences und einer SQLite-Datenbank, welche mit Hilfe von SQL-Cipher verschlüsselt wird.

#### EncryptedSharedPreferences
Die Speicherung in den EncryptedSharedPreferences lässt sich testen, indem im Eingabefenster ein beliebiger String übergeben wird. Dieser wird hier gespeichert: /data/data/shared_prefs/encrypted_shared_prefs.xml. Der String ist nicht im Klartext vorhanden, sondern wurde mit dem AES256-Algorithmus verschlüsselt. Zur Kontrolle wird der String im unteren Fenster ausgegeben. 

<img src="https://user-images.githubusercontent.com/71799051/105432574-24404000-5c58-11eb-9d7d-f3b265d0e6db.png" width="200" height="400"/><img src="https://user-images.githubusercontent.com/71799051/105426537-ab3aeb80-5c4b-11eb-8fa1-72497ec89999.png" width="900" height="100"/>

Um die Datei zu öffnen, muss in der ADB-Shell folgender Befehl abgesetzt werden:

```
adb pull /data/data/shared_prefs/encrypted_shared_prefs.xml <Zielpfad>
```

Anschließend kann die XML-Datei beispielsweise mit Notepad++ geöffnet werden.

***

#### Verschlüsselte SQLite-Datenbank
Bei der Erstbenutzung muss ein Passwort ausgewählt werden. Dieses wird lokal in den EncryptedSharedPreferneces gespeichert. Anschließend kann die Datenbank geöffnet werden. Diese Datenbank ermöglicht das Speichern von E-Mail-Adressen. Nachdem ein Passwort gesetzt und die Datenbank verlassen wurde, kann mit dem Passwort die Datenbank erneut geöffnet werden.

<img src="https://user-images.githubusercontent.com/71799051/105432693-62d5fa80-5c58-11eb-9068-9436cc487781.png" width="200" height="400"/> <img src="https://user-images.githubusercontent.com/71799051/105432817-b34d5800-5c58-11eb-8b51-91889f05c79b.png" width="200" height="400"/> <img src="https://user-images.githubusercontent.com/71799051/105432854-c3fdce00-5c58-11eb-95fd-98dd87b62ffc.png" width="200" height="400"/>


Um die Verschlüsselung der Datenbank zu testen, muss in der ADB-Shell folgender Befehl abgesetzt werden:

```
adb pull /data/data/databases/encrypted_database.db <Zielpfad>
```

Anschließend kann die Datenbank mit dem DB-Browser und dem Passwort geöffnet werden.

***

### Verschlüsselte Backend-Kommunikation
Das Backend stellt einen öffentlichen API-Endpunkt zur Verfügung, welcher die aktuelle Serverzeit übermittelt. Die Verschlüsselung geschieht hier über TLSv1.3. Einsehbar ist das ganze zum einen über die Burp-Suite oder auch Wireshark. Die Daten werden verschlüsselt übertragen.

Zum Testen mit Wireshark, den Filter auf 
```
ip.addr == 193.174.71.211
```
setzen. Dies ist die IP-Adresse des Backends. Anschließend eine Anfrage an das Backend senden.

<img src="https://user-images.githubusercontent.com/71799051/105432507-07a40800-5c58-11eb-95e5-c25567cd046b.png" width="200" height="400"/>

Danach ist in Wireshark eine Verschlüsselte Verbindung sichtbar.

<img src="https://user-images.githubusercontent.com/71799051/105429671-2901f580-5c52-11eb-9167-db22ac7214a5.png" width="900" height="300"/>

***

### Authentifizierung mit JWT
Neben dem öffentlichen API-Endpunkt, stellt das Backend auch einen privaten API-Endpunkt zur Verfügung. Dieser wird mit Hilfe von JSON Web Tokens abgesichert. Um das ganze zu testen, kann sich in der SecureApp registriert werden. Nach erfolgreicher Registrierung und erfolgreichem Login, kann nachfolgend auf den privaten API-Endpunkt zugegriffen werden und eine Hello World-Message sollte erscheinen. 

<img src="https://user-images.githubusercontent.com/71799051/105430341-8fd3de80-5c53-11eb-9c5b-dae2702a2081.png" width="200" height="400"/>

Wird kein AccessToken im Header mitgeliefert, erscheint folgender Error.
<img src="https://user-images.githubusercontent.com/71799051/105430642-42a43c80-5c54-11eb-8305-86dad227748e.png" width="900" height="400"/>

Die SecureApp erhält seinen AccessToken von Auth0.com. Bevor die API aufgerufen werden kann, muss in der Applikation die Übergabe des Authorization Headers gewährleistet sein. Genutzt wird Volley. Dies geschieht wie folgt:
```java
public Map<String, String> getHeaders() throws AuthFailureError {
  Map<String, String> headerMap = new HashMap<String, String>();
  headerMap.put("Content-Type", "application/json");
  headerMap.put("Authorization", "Bearer " + ACCESS_TOKEN);
  return headerMap;
 }
```

***

### Client Side Injection / SQLite-Injection
Neben der verschlüsselten Datenbank, besitzt die SecureApp eine weitere Datenbank, welche speziell zum Testen von SQLite-Injections implementiert wurde. Diese ist unverschlüsselt, damit trotz SQLi-Gegenmaßnahmen die Datenbank eingesehen werden kann. Als Payloads können beispielsweise Folgende verwendet werden:
     
        - admin' or '1'='1
        - a'or'1'='1

<img src="https://user-images.githubusercontent.com/71799051/105432427-ed6a2a00-5c57-11eb-885c-466941f8b8c7.png" width="200" height="400"/>

Der Insert-Into-Befehl wurde wie folgt implementiert:
```java
String SQL = "INSERT INTO sqliuser(user, password, phone_number) VALUES (?, ?, ?)";
mDB = openOrCreateDatabase("sqli", MODE_PRIVATE, null);
mDB.execSQL("DROP TABLE IF EXISTS sqliuser;");
mDB.execSQL("CREATE TABLE IF NOT EXISTS sqliuser(user VARCHAR, password VARCHAR, phone_number VARCHAR);");

SQLiteStatement statement = mDB.compileStatement(SQL);
statement.bindString(1, "admin");
statement.bindString(2, "passwd123");
statement.bindString(3, "0123456789");
statement.executeInsert();
```

Der Select-Befehl so:
```java
String SQL = "select * from sqliuser where user = ?";

Cursor cursor = mDB.rawQuery(SQL, new String[]{srchtxt.getText().toString()});
StringBuilder strb = new StringBuilder("");
```

Dadurch werden Eingaben in das Suchfeld direkt als Parameter geparst.
