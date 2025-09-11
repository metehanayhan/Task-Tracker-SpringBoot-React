# Axios ile POST İsteği

### **Forma Girilen Veriyi Sunucuya Gönderme**

### **Adım 1: Formu Oluşturalım**

Önce sadece iki input ve bir butondan oluşan basit bir HTML formu oluşturalım. Burada dikkat etmen gereken tek şey, her `input` etiketine bir `name` özelliği vermemiz. Bu `name` özelliği, daha sonra veriyi kolayca almamızı sağlayacak.

JavaScript

```java
// src/components/AddUserFormSimple.jsx
import axios from 'axios';

function AddUserFormSimple() {
  
  const handleSubmit = (event) => {
    // Bu fonksiyon form gönderildiğinde çalışacak
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Yeni Kullanıcı Ekle (Basit)</h2>
      <div>
        <label>İsim:</label>
        {/* Sadece "name" özelliğini ekliyoruz */}
        <input type="text" name="userName" required />
      </div>
      <div>
        <label>Email:</label>
        {/* Sadece "name" özelliğini ekliyoruz */}
        <input type="email" name="userEmail" required />
      </div>
      <button type="submit">Gönder</button>
    </form>
  );
}

export default AddUserFormSimple;
```

Bu formda `useState` falan yok. Sadece saf HTML gibi düşünebilirsin.

### **Adım 2: Form Gönderildiğinde Veriyi Alalım**

Şimdi `handleSubmit` fonksiyonunun içini dolduralım. Bu fonksiyon, form gönderildiğinde otomatik olarak bir `event` parametresi alır. Biz bu `event`'i kullanarak formun içindeki inputların değerlerine ulaşacağız.

JavaScript

```java
const handleSubmit = (event) => {
  // 1. Sayfanın yenilenmesini engelle. Bu her zaman ilk adımdır.
  event.preventDefault();

  // 2. Formun içindeki inputların değerlerini "name" özelliklerini kullanarak al.
  const form = event.target;
  const name = form.elements.userName.value;
  const email = form.elements.userEmail.value;

  console.log('Formdan gelen veri:', { name, email });
  // Şimdilik sadece veriyi doğru alıp alamadığımızı kontrol edelim.
};
```

Formu doldurup butona bastığında tarayıcının konsolunda girdiğin isim ve e-posta adresini bir obje olarak görmelisin.

### **Adım 3: Veriyi Axios ile Sunucuya Gönderelim**

Veriyi başarıyla alabildiğimize göre, son adımda bu veriyi `axios.post` ile sunucuya göndereceğiz.

JavaScript

```java
const handleSubmit = (event) => {
  event.preventDefault();

  const form = event.target;
  const name = form.elements.userName.value;
  const email = form.elements.userEmail.value;

  // 3. Sunucuya göndereceğimiz objeyi oluşturalım.
  const newUser = {
    name: name,
    email: email,
  };

  // 4. Axios ile POST isteği atalım.
  axios.post('https://jsonplaceholder.typicode.com/users', newUser)
    .then(response => {
      // Başarılı olursa:
      console.log('Sunucudan gelen cevap:', response.data);
      alert(`'${response.data.name}' adlı kullanıcı başarıyla eklendi!`);
      // Formu temizleyelim
      form.reset();
    })
    .catch(error => {
      // Hata olursa:
      console.error('Bir hata oluştu!', error);
      alert('Kullanıcı eklenirken bir hata oluştu.');
    });
};
```

---

### **Tam ve Basit Kod (`AddUserFormSimple.jsx`)**

İşte her şeyin bir arada olduğu, olabildiğince basitleştirilmiş hali:

JavaScript

```java
import axios from 'axios';

function AddUserFormSimple() {

  const handleSubmit = (event) => {
    // 1. Sayfanın yenilenmesini engelle
    event.preventDefault();

    // 2. Formdaki verilere "name" attribute'ları üzerinden eriş
    const form = event.target;
    const name = form.elements.userName.value;
    const email = form.elements.userEmail.value;

    // 3. Veriyi bir objede topla
    const newUser = { name, email };

    // 4. Axios ile POST isteği gönder
    axios.post('https://jsonplaceholder.typicode.com/users', newUser)
      .then(response => {
        // BAŞARILI DURUMU
        console.log('Sunucudan gelen cevap:', response.data);
        alert(`Kullanıcı başarıyla eklendi! ID: ${response.data.id}`);
        form.reset(); // Formun içini temizler
      })
      .catch(error => {
        // HATA DURUMU
        console.error('Bir hata oluştu!', error);
        alert('İstek gönderilirken bir problem yaşandı.');
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Yeni Kullanıcı Ekle (Basit)</h2>
      <div>
        <label>İsim:</label>
        <input type="text" name="userName" required />
      </div>
      <div>
        <label>Email:</label>
        <input type="email" name="userEmail" required />
      </div>
      <button type="submit">Gönder</button>
    </form>
  );
}

export default AddUserFormSimple;
```

### **Özetle Ne Yaptık?**

1. Input'lara `useState` bağlamak yerine, onlara sadece `name` özelliği verdik.
2. Form gönderildiğinde `event.target.elements` kullanarak bu `name`'ler üzerinden input'ların değerini okuduk.
3. "Yükleniyor", "Başarılı" gibi durumlar için ekstra state'ler oluşturmak yerine, `alert` ve `console.log` ile anlık geri bildirim verdik.

---