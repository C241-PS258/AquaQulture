# Aquaculture Mate

Aquaculture Mate adalah aplikasi yang membantu pengguna mengidentifikasi jenis ikan melalui scan gambar ikan. Aplikasi ini menyediakan fitur registrasi, login, scan ikan, dan melihat histori scan.

## Fitur Utama

1. **Registrasi dan Login**: Pengguna baru dapat mendaftar dan login untuk menggunakan aplikasi.
2. **Scan Ikan**: Menggunakan teknologi pengenalan gambar untuk memprediksi jenis ikan.
3. **Histori Scan**: Melihat riwayat scan yang telah dilakukan sebelumnya.

## Prasyarat

Pastikan Anda telah menginstal dependensi berikut sebelum memulai:

- Python 3.8+
- Library Python: `flask`, `tensorflow`, `opencv-python`, `numpy`
- Database: SQLite

## Instalasi

1. Clone repository ini ke lokal Anda:

    ```bash
    git clone https://github.com/username/aquaculture-mate.git
    cd aquaculture-mate
    ```

2. Buat virtual environment dan aktifkan:

    ```bash
    python -m venv venv
    source venv/bin/activate  # Untuk pengguna Unix/macOS
    venv\Scripts\activate     # Untuk pengguna Windows
    ```

3. Instal dependensi yang diperlukan:

    ```bash
    pip install -r requirements.txt
    ```

4. Buat database SQLite dan migrasi tabel:

    ```bash
    flask db init
    flask db migrate -m "Initial migration."
    flask db upgrade
    ```

## Penggunaan

1. **Jalankan Server**

    Untuk memulai server, gunakan perintah berikut:

    ```bash
    flask run
    ```

    Server akan berjalan di `http://127.0.0.1:5000/`.

2. **Registrasi Pengguna Baru**

    Buka browser Anda dan akses `http://127.0.0.1:5000/register` untuk membuat akun baru.

3. **Login**

    Setelah berhasil registrasi, login di `http://127.0.0.1:5000/login`.

4. **Scan Ikan**

    Setelah login, Anda dapat mulai scan ikan dengan mengunggah gambar ikan di `http://127.0.0.1:5000/scan`.

5. **Histori Scan**

    Anda dapat melihat histori scan di `http://127.0.0.1:5000/history`.

## Struktur Proyek

```markdown
aquaculture-mate/
│
├── app/
│   ├── __init__.py
│   ├── routes.py
│   ├── models.py
│   ├── forms.py
│   ├── static/
│   └── templates/
│
├── migrations/
├── venv/
├── requirements.txt
└── run.py
