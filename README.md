# Dağıtık Abonelik Sistemi (Distributed Subscriber Service)

Sistem progamlama proje ödevi


# Server1.java Özellikleri
+ **Müşteri Verilerini Saklama:** İstemcilerden gelen abone verilerini kendi belleğinde tutar ve yönetir.
+ **Server2 ve Server3 için Yedekleme:** İstemcilerden gelen verilerin bir kopyasını diğer sunuculara aktarır.
+ **Admin İstemci ile İletişim:** Admin.rb istemcisinden gelen başlatma (STRT) komutlarını işler.
+ **Sunucular Arası İletişim:** Diğer sunucularla soketler üzerinden veri alışverişi yapar.
+ **Python Plotter Server ile İletişim:**  Kapasite bilgilerini plotter sunucusuna göndererek grafikte gösterir.

# Server2.java Özellikleri
+ **Müşteri Verilerini Saklama:** İstemcilerden gelen abone verilerini kendi belleğinde tutar ve yönetir.
+ **Server3 için Yedekleme:** Verilerin bir kopyasını Server3'e gönderir.
+ **Server1'e Veri Gönderimi:** Yedekleme amacıyla verileri Server1'e gönderir.
+ **Admin İstemci ile İletişim:** Admin.rb istemcisinden gelen başlatma (STRT) komutlarını işler.
+ **Sunucular Arası İletişim:** Diğer sunucularla soketler üzerinden veri alışverişi yapar.
+ **Python Plotter Server ile İletişim:** Kapasite bilgilerini plotter sunucusuna gönderir.

# Server3.java Özellikleri
+ **Müşteri Verilerini Saklama:** İstemcilerden gelen abone verilerini kendi belleğinde tutar ve yönetir.
+ **Server1 ve Server2'ye Veri Gönderimi**: Yedekleme amacıyla verileri diğer sunuculara gönderir.
+ **Admin İstemci ile İletişim:** Admin.rb istemcisinden gelen başlatma (STRT) komutlarını işler.
+ **Sunucular Arası İletişim:** Diğer sunucularla soketler üzerinden veri alışverişi yapar.
+ **Python Plotter Server ile İletişim:** Kapasite bilgilerini plotter sunucusuna gönderir.


# Admin.rb Özellikleri
+ **Sunucuları Başlatma:** dist_subs.conf dosyasından alınan yapılandırma bilgisiyle sunucuları başlatır.
+ **Sunucularla İletişim:** Her sunucuya başlatma (STRT) ve kapasite sorgulama (CPCTY) komutlarını gönderir.
+ **Sunucu Yanıtlarını Yönetme:** Sunuculardan gelen Message ve Capacity nesnelerini işler.
+ **Plotter Server ile Veri Paylaşımı:** Plotter sunucusuna sunucuların kapasite durumlarını gönderir.

# Python Plotter Server Özellikleri
+ **Sunucu Kapasitesini Görselleştirme:** Sunuculardan alınan kapasite bilgilerini çubuk grafik olarak anlık olarak görselleştirir.
+ **Sunucu Bilgisi Gösterimi:** Her sunucunun kapasitesini farklı renklerle ayırt eder ve grafikte belirtir.


### Ekip üyeleri

- 22060695, Mustafa Batur
- 20060385, Oğuzhan Sezgin


