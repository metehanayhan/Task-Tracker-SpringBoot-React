# Tomcat Nedir?

# 📘 Tomcat Nedir?

## 1. Tanım

- **Apache Tomcat**, **Java tabanlı web uygulamalarını çalıştıran açık kaynaklı bir uygulama sunucusudur (application server).**
- Apache Software Foundation tarafından geliştirilmiştir.
- **Java Servlet**, **JavaServer Pages (JSP)** ve **Java Expression Language (EL)** gibi teknolojileri destekler.
- Dünyada en yaygın kullanılan **Java Web Server**’dır.

---

## 2. Web Sunucusu vs Uygulama Sunucusu

- **Web Sunucusu (Web Server):** Statik içerikleri (HTML, CSS, JS, resim) sunar. (Örn: Apache HTTP Server, Nginx)
- **Uygulama Sunucusu (Application Server):** Dinamik içerikleri (Java kodu, Spring uygulaması) çalıştırır.

👉 Tomcat, hem **web sunucusu** hem de **uygulama sunucusu** işlevi görebilir ama daha çok **Servlet Container** (servlet çalıştırıcısı) olarak bilinir.

---

## 3. Tomcat Ne İşe Yarar?

- Java ile yazdığın bir **web uygulamasını çalıştırır**.
- HTTP isteklerini alır ve **Servlet** veya **Spring Controller** gibi bileşenlere yönlendirir.
- Verilen cevabı (HTML, JSON, XML) istemciye geri gönderir.

---

## 4. Çalışma Mantığı

1. **Kullanıcı** tarayıcıdan `http://localhost:8080/hello` adresine istek atar.
2. **Tomcat**, bu isteği alır (varsayılan port: `8080`).
3. Tomcat, isteği uygun **Servlet** veya Spring Boot Controller’a gönderir.
4. Uygulama cevabı üretir (`Merhaba Dünya` gibi).
5. Tomcat, cevabı HTTP Response olarak kullanıcıya iletir.

---

## 5. Tomcat ve Spring Boot İlişkisi

- Eskiden Java web projelerinde Tomcat’i **ayrı kurman** ve projenin `.war` dosyasını Tomcat’in `webapps/` klasörüne koyman gerekirdi.
- Spring Boot ise işleri kolaylaştırdı:
    - **Embedded Tomcat (Gömülü Tomcat)** ile birlikte gelir.
    - Yani ekstra Tomcat kurulumu yapmana gerek yok.
    - `java -jar uygulama.jar` dediğinde Spring Boot otomatik olarak içindeki Tomcat’i çalıştırır.

---

## 6. Tomcat’in Özellikleri

- Açık kaynak ve ücretsizdir.
- Hafif ve hızlıdır.
- Java Servlet 5.0, JSP 3.0 gibi standartları destekler.
- Yüksek trafikli sistemlerde kullanılabilir (bankacılık, e-devlet, kurumsal sistemler).
- Varsayılan port: **8080**

---

## 7. Tomcat vs Diğer Sunucular

- **Tomcat:** Hafif, Servlet Container, genelde Spring Boot ile gömülü gelir.
- **WildFly (eski JBoss):** Daha ağır, kurumsal özellikleri fazla.
- **GlassFish:** Java EE (Jakarta EE) için tam destekli.
- **Jetty / Undertow:** Alternatif gömülü sunucular (Spring Boot bunları da destekler).

---

## 8. Tomcat’in Dosya Yapısı (Klasik Kurulumda)

Eğer Tomcat’i ayrı kurarsan, şu klasör yapısını görürsün:

```
apache-tomcat/
 ├── bin/          # Çalıştırma dosyaları (startup.sh / startup.bat)
 ├── conf/         # Konfigürasyon dosyaları (server.xml)
 ├── lib/          # Tomcat kütüphaneleri
 ├── logs/         # Log dosyaları
 ├── webapps/      # Web uygulamaları (war dosyaları buraya atılır)
 └── work/         # Çalışma dosyaları

```

Spring Boot’ta ise bu adımları sen yapmazsın çünkü her şey **otomatik gömülü** gelir.

---

## 9. Örnek

Spring Boot uygulamasını çalıştırıp şu adrese gidersen:

👉 `http://localhost:8080/`

Aslında perde arkasında **Tomcat çalışıyordur** ve gelen HTTP isteğini Controller’a yönlendiriyordur.

---

✅ **Özet:**

- Tomcat, **Java web uygulamalarını çalıştıran en popüler uygulama sunucusudur.**
- Spring Boot, Tomcat’i **embedded** (gömülü) şekilde getirir, ayrıca kurmaya gerek yoktur.
- HTTP isteklerini alır, uygulama koduna yönlendirir ve cevabı döner.

---