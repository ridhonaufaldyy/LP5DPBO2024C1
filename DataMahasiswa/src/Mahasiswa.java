public class Mahasiswa {
    private String nim;
    private String nama;
    private String jenisKelamin;
    private int tahunAngkatan;

    public Mahasiswa(String nim, String nama, String jenisKelamin, int tahunAngkatan) {
        this.nim = nim;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.tahunAngkatan = tahunAngkatan;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setTahunAngkatan(int tahunAngkatan) {this.tahunAngkatan = tahunAngkatan;}


    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNim() {
        return this.nim;
    }

    public String getNama() {
        return this.nama;
    }
    public int getTahunAngkatan(){return this.tahunAngkatan;}

    public String getJenisKelamin() {
        return this.jenisKelamin;
    }
}
