# SI UJANG GATRIK

SI UJANG GATRIK adalah aplikasi Android yang dibuat untuk membantu proses pengajuan sertifikasi instalasi listrik. Aplikasi ini mendukung dua jenis layanan, yaitu permohonan NIDI dan permohonan SLO. Seluruh data disimpan secara lokal menggunakan file JSON sehingga aplikasi dapat dijalankan tanpa koneksi internet.

## Fitur

### Pengguna

* Login dan registrasi akun.
* Session login menggunakan SharedPreferences.
* Dashboard pengguna.
* Pengajuan permohonan NIDI.
* Pengajuan permohonan SLO.
* Upload dokumen pendukung.
* Riwayat seluruh permohonan.
* Detail permohonan.
* Profil pengguna.
* Logout.

### Administrator

* Login sebagai administrator.
* Dashboard admin.
* Statistik jumlah permohonan.
* Filter berdasarkan status permohonan.
* Melihat seluruh permohonan NIDI dan SLO.
* Melihat detail permohonan.
* Mengubah status permohonan.
* Logout.

## Teknologi

* Kotlin
* Android Studio
* Material Design 3
* RecyclerView
* Gson
* SharedPreferences
* JSON Local Storage

## Struktur Data

Aplikasi menggunakan penyimpanan lokal berupa file JSON.

Data yang disimpan meliputi:

* Data pengguna
* Data permohonan NIDI
* Data permohonan SLO
* Session login

## Role Akun

### Administrator

Email

```
admin@gmail.com
```

Password

```
admin123
```

### Pengguna

Pengguna dapat membuat akun baru melalui halaman registrasi.

## Alur Aplikasi

1. Pengguna melakukan registrasi.
2. Pengguna login.
3. Pengguna mengajukan permohonan NIDI atau SLO.
4. Data disimpan ke file JSON.
5. Administrator melihat daftar permohonan.
6. Administrator memverifikasi permohonan.
7. Administrator mengubah status permohonan.
8. Pengguna dapat melihat perubahan status pada menu riwayat.

## Struktur Project

```
app
├── adapter
├── helper
├── model
├── repository
├── activity
├── res
│   ├── drawable
│   ├── layout
│   ├── values
│   └── mipmap
└── AndroidManifest.xml
```

## Cara Menjalankan

1. Clone repository.

```
git clone https://github.com/username/siujanggatrik.git
```

2. Buka project menggunakan Android Studio.

3. Sync Gradle.

4. Jalankan aplikasi menggunakan Emulator atau perangkat Android.

## Screenshot

Tambahkan screenshot berikut pada folder `screenshots`.

* Login
* Register
* Dashboard User
* Permohonan NIDI
* Permohonan SLO
* History
* Detail Permohonan
* Dashboard Admin
* Detail Admin

Kemudian tambahkan pada README menggunakan Markdown.

## Pengembang

Dikembangkan sebagai proyek aplikasi pelayanan sertifikasi instalasi listrik berbasis Android menggunakan Kotlin.

## Lisensi

Proyek ini dibuat untuk kebutuhan pembelajaran dan pengembangan akademik.

## VIDIO
https://drive.google.com/file/d/1FlvOGp-Y40mebWKyjMlGuo7cLMHj2bRe/view?usp=sharing

## LAPORAN OOAD
https://drive.google.com/file/d/11Lwx4Jn8nPeVc-5YSFMkHjPbXsm1KCMw/view?usp=sharing

## UI DESIGN
https://www.figma.com/design/Lh53yV2G17mNhfFio7r3Yb/Untitled?node-id=0-1&t=3NICru8zRgYf69NC-1

## PEMBUAT
Gilang hudatama ramadhan - 24552011246
dan beberapa kawan yang membantu 
