# REST API

# 📘 REST API ve İlgili Kavramlar

## 1) API Nedir?

- **API (Application Programming Interface)**: Farklı yazılımların **birbiriyle iletişim kurmasını** sağlayan arayüzdür.
- Örnek:
    - Telefonundaki hava durumu uygulaması → Meteoroloji servisinden veri alır.
    - Banka mobil uygulaması → Banka sunucusundaki API ile haberleşir.

---

## 2) REST Nedir?

- **REST (Representational State Transfer)**: Web üzerinde API yazmak için kullanılan **mimari bir stil**dir.
- REST API’ler **HTTP protokolü** üzerinden çalışır.
- Temel prensip:
    - Her şey bir **kaynak (resource)** olarak görülür (ör. `users`, `products`, `orders`).
    - Kaynaklara **URL** ile ulaşılır (ör. `/api/users/1`).
    - İşlemler için HTTP metodları kullanılır.

---

## 3) HTTP Metotları

REST API’de en çok kullanılan 4 temel HTTP metodu:

- **GET** → Veri çekmek için.
    - Örn: `GET /api/users` → Tüm kullanıcıları getir.
- **POST** → Yeni veri eklemek için.
    - Örn: `POST /api/users` → Yeni kullanıcı oluştur.
- **PUT** → Var olan veriyi güncellemek için.
    - Örn: `PUT /api/users/1` → ID’si 1 olan kullanıcıyı güncelle.
- **DELETE** → Veri silmek için.
    - Örn: `DELETE /api/users/1` → ID’si 1 olan kullanıcıyı sil.

---

## 4) HTTP Durum Kodları (Status Codes)

Her isteğe karşılık sunucu bir **durum kodu** döner.

En önemlileri:

- **2xx (Başarılı işlemler)**
    - `200 OK` → İstek başarılı, cevap var.
    - `201 Created` → Yeni veri oluşturuldu.
    - `204 No Content` → İşlem başarılı ama içerik yok (ör. DELETE sonrası).
- **4xx (İstemci hataları)**
    - `400 Bad Request` → İstek hatalı (eksik parametre vs).
    - `401 Unauthorized` → Yetkilendirme yok.
    - `403 Forbidden` → Yetki var ama erişim yasak.
    - `404 Not Found` → Kaynak bulunamadı.
- **5xx (Sunucu hataları)**
    - `500 Internal Server Error` → Sunucu tarafında genel hata.
    - `503 Service Unavailable` → Sunucu geçici olarak hizmet veremiyor.

---

## 5) JSON Nedir?

- **JSON (JavaScript Object Notation)**: Veri paylaşımı için kullanılan hafif bir formattır.
- REST API’lerin en sık döndürdüğü veri formatı **JSON**’dur.
- Avantajları:
    - İnsan tarafından okunabilir.
    - Makine tarafından kolayca işlenebilir.
    - Programlama dillerinden bağımsızdır.

---

## 6) JSON Yapısı

JSON, **anahtar-değer** çiftlerinden oluşur.

- Anahtar (key) → her zaman **çift tırnak içinde yazılır**.
- Değer (value) → string, number, boolean, array, object olabilir.

### Basit Örnek:

```json
{
  "id": 1,
  "name": "Ali",
  "age": 25,
  "isActive": true}

```

### İç içe (nested) JSON:

```json
{
  "id": 1,
  "name": "Ali",
  "address": {
    "city": "Konya",
    "zip": 42000
  },
  "hobbies": ["yüzme", "kitap", "yazılım"]
}

```

Burada:

- `"address"` → başka bir JSON nesnesi (object).
- `"hobbies"` → bir dizi (array).

---

## 7) REST API Örnek Akış

### İstek:

```
GET /api/users/1

```

### Sunucunun Döndürdüğü JSON:

```json
{
  "id": 1,
  "name": "Ali Veli",
  "email": "ali@example.com"
}

```

### Durum Kodu:

```
200 OK

```

Başka bir örnek:

### İstek:

```
POST /api/users
Body:
{
  "name": "Ayşe",
  "email": "ayse@example.com"
}

```

### Sunucunun Cevabı:

```json
{
  "id": 2,
  "name": "Ayşe",
  "email": "ayse@example.com"
}

```

### Durum Kodu:

```
201 Created

```

---

# 📌 Özet

- **API:** Yazılımlar arası köprü.
- **REST API:** HTTP üzerinden çalışan API standardı.
- **HTTP metodları:** GET, POST, PUT, DELETE → CRUD işlemleri.
- **HTTP durum kodları:** 2xx (başarı), 4xx (istemci hatası), 5xx (sunucu hatası).
- **JSON:** Veri taşıma formatı (anahtar-değer çiftleri).

---