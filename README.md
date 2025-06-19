# 📊 Sistem Pendukung Keputusan (SPK) Kepuasan Pelanggan - Metode SAW

Aplikasi desktop GUI berbasis Java untuk menghitung **Skor Kepuasan Pelanggan** menggunakan metode **Simple Additive Weighting (SAW)**. Cocok digunakan untuk skripsi, studi kasus sistem pengambil keputusan, dan evaluasi layanan pelanggan berdasarkan kriteria.

---

## 🎯 Tujuan Aplikasi

Aplikasi ini dirancang untuk membantu proses pengambilan keputusan terhadap tingkat **kepuasan pelanggan** berdasarkan empat kriteria utama:

1. **Kualitas Produk**
2. **Kecepatan Layanan**
3. **Harga**
4. **Customer Service (CS)**

Melalui proses normalisasi dan pembobotan menggunakan metode SAW, aplikasi ini dapat memberikan hasil **ranking pelanggan** berdasarkan skor akhir yang dihitung secara objektif.

---

## 🛠 Teknologi yang Digunakan

| Komponen     | Teknologi        |
|--------------|------------------|
| Bahasa       | Java 17          |
| GUI          | Java Swing       |
| Build Tool   | Maven            |
| Visualisasi  | JFreeChart 1.5.4 |
| Ekspor Data  | CSV              |

---

## ⚙️ Cara Menjalankan

### 1. Clone Repository
```bash
git clone https://github.com/username/spk-saw.git
cd spk-saw
mvn clean compile
mvn exec:java -Dexec.mainClass="Main"
```

Atau buka di IntelliJ / NetBeans dan jalankan file ```Main.java```

# 📈 Metodologi SAW (Simple Additive Weighting)
Metode SAW digunakan untuk melakukan perhitungan keputusan multi-kriteria. Berikut tahapan utamanya:

➤ Normalisasi Matriks Keputusan
Semua nilai dari masing-masing kriteria akan dinormalisasi menggunakan rumus:
```R_ij = x_ij / x_j_max```

Contoh:

| Nama | Kualitas | Normalisasi |
| ---- | -------- | ----------- |
| A    | 4        | 4 / 5 = 0.8 |


➤ Bobot Kriteria

| Kriteria          | Bobot |
| ----------------- | ----- |
| Kualitas Produk   | 30%   |
| Kecepatan Layanan | 25%   |
| Harga             | 20%   |
| Customer Service  | 25%   |

➤ Perhitungan Skor

Skor akhir dihitung dengan menjumlahkan hasil dari normalisasi * bobot:

```Skor = Σ (R_ij * W_j)```

---

# 💡 Fitur Aplikasi
#### ✅ Input data pelanggan & nilai 1–5 untuk tiap kriteria
#### ✅ Perhitungan skor otomatis menggunakan metode SAW
#### ✅ Tabel ranking pelanggan
#### ✅ Preview matriks normalisasi (dalam dialog)
#### ✅ Grafik skor SAW dalam bentuk bar chart
#### ✅ Export hasil ranking ke file CSV
#### ✅ Hapus data (klik pada baris tabel)
#### ✅ Desain user-friendly (GUI desktop)

---

# 📂 Struktur Proyek

```
spk-saw/
├── src/
│   ├── model/               # Class CustomerFeedback
│   ├── service/             # DecisionService, ExportService
│   └── ui/                  # GUI utama SPKFrame.java
├── Main.java                # Entry point aplikasi
├── pom.xml                  # Konfigurasi Maven + Dependency JFreeChart
└── README.md                # Dokumentasi proyek
```

---

# 📤 Contoh Output CSV
```
Rank,   Nama,   Skor
1,      Budi,   0.8650
2,      Andi,   0.8350
3,      Cici,   0.7925
```

# 📚 Contoh Dataset
| Nama | Kualitas | Kecepatan | Harga | CS |
| ---- | -------- | --------- | ----- | -- |
| Andi | 4        | 5         | 4     | 3  |
| Budi | 5        | 4         | 3     | 5  |
| Cici | 3        | 3         | 5     | 2  |

# 🧾 Lisensi
Project ini dibuat untuk kebutuhan skripsi / tugas akhir / pembelajaran akademik dan bebas digunakan, dikembangkan, serta dimodifikasi oleh siapa saja.

)

# 📩 Kontak
Jika kamu tertarik dengan proyek ini atau ingin kolaborasi dalam proyek yang lain, silakan hubungi melalui:

https://egichandrap.vercel.app/

https://dashboard-egichandrap.vercel.app/

---

### 📌 Petunjuk Lanjutan

- Ganti `https://github.com/username/spk-saw.git` dengan URL repo kamu sendiri.
- Silahkan lakukan perubahan sesuai dengan kreasi kamu sendiri di repo kamu sendiri.




