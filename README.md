![ic_launcher-playstore](https://user-images.githubusercontent.com/71799051/104077814-78403300-521b-11eb-9bbc-059cbb2c8d12.png)

***

Die SecureApp ist auf Basis meiner Bachelorarbeit entstanden. In dieser wurden die wichtigsten Techniken zur Vermeidung von Sicherheitslücken in modernen Android-Apps untersucht und voneinander abgegrenzt.

Die Applikation beinhaltet technische Lösungen zu bestimmten Sicherheitslücken, bezogen auf die [OWASP Mobile Top10 Risks](https://owasp.org/www-project-mobile-top-10/).

-----

Download: Die Applikation kann als apk-Datei vom Jupiter-Server der FH Südwestfalen heruntergeladen werden.
Backend: Der Quellcode des Backends kann hier eingesehen werden:

-----

Installation:

Am besten lässt sich die SecureApp auf einem Android-Emulator ausführen, beispielsweise Genymotion.

-----

Anleitung zum Testen der einzelnen Schwachstellen:

Lokale Datenspeicherung:
Die SecureApp impelementiert die lokale Datenspeicherung, in Form der EncryptedSharedPreferences und einer SQLite-Datenbank, welche mit Hilfe von SQL-Cipher verschlüsselt wird.
Die Speicherung in den EncryptedSharedPreferences lassen sich testen, indem im Eingabefenster ein beliebiger String übergeben wird. Dieser wird im Ordner /data/data/shared_prefs gespeichert. Der String ist nicht im Klartext vorhanden, sondern wurde mit dem AES256-Algorithmus verschlüsselt. Zur Kontrolle wird der String im unteren Fenster ausgegeben.
Die SQLite-Datenbank TODO

Verschlüsselte Backend-Kommunikation:
Das Backend stellt einen öffentlichen API-Endpunkt zur Verfügung, welcher die aktuelle Serverzeit übermittelt. Die Verschlüsselung geschieht hier über TLSv1.3. Einsehbar ist das ganze zum einen über die Burp-Suite oder auch Wireshark. Die Daten werden verschlüsselt übertragen.

Authentifizierung mit JWT:
Neben dem öffentlichen API-Endpunkt, stellt das Backend auch einen privaten API-Endpunkt zur Verfügung. Dieser wird mit Hilfe von JSON Web Tokens abgesichert. Um das ganze zu testen, kann sich in der SecureApp registriert werden. Nach erfolgreicher Registrierung und erfolgreichem Login, kann nachfolgend auf den privaten API-Endpunkt zugegriffen werden und eine Hello World-Message sollte erscheinen.

Client Side Injection:
TODO
