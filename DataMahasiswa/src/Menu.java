import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Menu extends JFrame{
    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JTextField tahunAngkatanField;
    private JLabel tahunAngkatanLabel;

    // constructor
    public Menu() {
        // atur ukuran window
        setSize(480, 560);
        // letakkan window di tengah layar
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(null); // atur layout menjadi null untuk penggunaan absolute positioning

        titleLabel = new JLabel("Menu Mahasiswa");
        titleLabel.setBounds(180, 10, 200, 30);
        mainPanel.add(titleLabel);

        nimLabel = new JLabel("NIM:");
        nimLabel.setBounds(50, 50, 120, 25);
        mainPanel.add(nimLabel);

        nimField = new JTextField();
        nimField.setBounds(180, 50, 200, 25);
        mainPanel.add(nimField);

        namaLabel = new JLabel("Nama:");
        namaLabel.setBounds(50, 90, 120, 25);
        mainPanel.add(namaLabel);

        namaField = new JTextField();
        namaField.setBounds(180, 90, 200, 25);
        mainPanel.add(namaField);

        jenisKelaminLabel = new JLabel("Jenis Kelamin:");
        jenisKelaminLabel.setBounds(50, 130, 120, 25);
        mainPanel.add(jenisKelaminLabel);

        jenisKelaminComboBox = new JComboBox();
        jenisKelaminComboBox.setBounds(180, 130, 200, 25);
        mainPanel.add(jenisKelaminComboBox);

        tahunAngkatanLabel = new JLabel("Tahun Angkatan:");
        tahunAngkatanLabel.setBounds(50, 170, 120, 25);
        mainPanel.add(tahunAngkatanLabel);

        tahunAngkatanField = new JTextField();
        tahunAngkatanField.setBounds(180, 170, 200, 25);
        mainPanel.add(tahunAngkatanField);

        mahasiswaTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(mahasiswaTable);
        scrollPane.setBounds(50, 210, 350, 200);
        mainPanel.add(scrollPane);

        addUpdateButton = new JButton("Add");
        addUpdateButton.setBounds(50, 450, 100, 25);
        mainPanel.add(addUpdateButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(180, 450, 100, 25);
        mainPanel.add(cancelButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(300, 450, 100, 25);
        mainPanel.add(deleteButton);

        // inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();

        // isi listMahasiswa
        populateList();

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD,20f));

        // atur isi combo box
        String[] jenisKelaminData ={"","laki-laki","perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel(jenisKelaminData));

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                }else {
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0){
                    deleteData();
                }
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // simpan value textfield dan combo box
                String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex,1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex,2).toString();
                String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex,3).toString();

                // ubah isi textfield dan combo box
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });

        // setContentPane setelah mainPanel diinisialisasi
        setContentPane(mainPanel);

        // ubah warna background
        getContentPane().setBackground(Color.white);

        // agar program ikut berhenti saat window diclose
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Membuat frame menjadi fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public final DefaultTableModel setTable() {
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Tahun Angkatan"};
        DefaultTableModel temp = new DefaultTableModel(null, column);

        for (int i = 0; i < listMahasiswa.size(); i++) {
            Object[] row = new Object[5]; // Ubah ukuran array menjadi 5 karena ada 5 kolom

            row[0] = i + 1;
            row[1] = listMahasiswa.get(i).getNim(); // Perbaikan indeks menjadi 'i'
            row[2] = listMahasiswa.get(i).getNama(); // Perbaikan indeks menjadi 'i'
            row[3] = listMahasiswa.get(i).getJenisKelamin(); // Perbaikan indeks menjadi 'i'
            row[4] = listMahasiswa.get(i).getTahunAngkatan(); // Menambahkan tahun angkatan

            temp.addRow(row);
        }

        return temp;
    }

    public void insertData() {
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        int tahunAngkatan = Integer.parseInt(tahunAngkatanField.getText());

        listMahasiswa.add(new Mahasiswa(nim, nama, jenisKelamin, tahunAngkatan));
        mahasiswaTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
    }

    public void updateData() {
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        int tahunAngkatan = Integer.parseInt(tahunAngkatanField.getText());

        listMahasiswa.get(selectedIndex).setNim(nim);
        listMahasiswa.get(selectedIndex).setNama(nama);
        listMahasiswa.get(selectedIndex).setJenisKelamin(jenisKelamin);
        listMahasiswa.get(selectedIndex).setTahunAngkatan(tahunAngkatan);

        mahasiswaTable.setModel(setTable());
        clearForm();
        JOptionPane.showMessageDialog(null, "Data berhasil diubah");
    }

    public void deleteData() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            listMahasiswa.remove(selectedIndex);
            mahasiswaTable.setModel(setTable());
            clearForm();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        }
    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    private void populateList() {
        listMahasiswa.add(new Mahasiswa("2203999", "Amelia Zalfa Julianti", "Perempuan", 2022));
        listMahasiswa.add(new Mahasiswa("2202292", "Muhammad Iqbal Fadhilah", "Laki-laki", 2021));
        listMahasiswa.add(new Mahasiswa("2202346", "Muhammad Rifky Afandi", "Laki-laki", 2023));
        listMahasiswa.add(new Mahasiswa("2210239", "Muhammad Hanif Abdillah", "Laki-laki", 2020));
        listMahasiswa.add(new Mahasiswa("2202046", "Nurainun", "Perempuan", 2022));
        listMahasiswa.add(new Mahasiswa("2205101", "Kelvin Julian Putra", "Laki-laki", 2021));
        listMahasiswa.add(new Mahasiswa("2200163", "Rifanny Lysara Annastasya", "Perempuan", 2023));
        listMahasiswa.add(new Mahasiswa("2202869", "Revana Faliha Salma", "Perempuan", 2020));
        listMahasiswa.add(new Mahasiswa("2209489", "Rakha Dhifiargo Hariadi", "Laki-laki", 2022));
        listMahasiswa.add(new Mahasiswa("2203142", "Roshan Syalwan Nurilham", "Laki-laki", 2021));
        listMahasiswa.add(new Mahasiswa("2200311", "Raden Rahman Ismail", "Laki-laki", 2023));
        listMahasiswa.add(new Mahasiswa("2200978", "Ratu Syahirah Khairunnisa", "Perempuan", 2020));
        listMahasiswa.add(new Mahasiswa("2204509", "Muhammad Fahreza Fauzan", "Laki-laki", 2022));
        listMahasiswa.add(new Mahasiswa("2205027", "Muhammad Rizki Revandi", "Laki-laki", 2021));
        listMahasiswa.add(new Mahasiswa("2203484", "Arya Aydin Margono", "Laki-laki", 2023));
        listMahasiswa.add(new Mahasiswa("2200481", "Marvel Ravindra Dioputra", "Laki-laki", 2020));
        listMahasiswa.add(new Mahasiswa("2209889", "Muhammad Fadlul Hafiizh", "Laki-laki", 2022));
        listMahasiswa.add(new Mahasiswa("2206697", "Rifa Sania", "Perempuan", 2021));
        listMahasiswa.add(new Mahasiswa("2207260", "Imam Chalish Rafidhul Haque", "Laki-laki", 2020));
        listMahasiswa.add(new Mahasiswa("2204343", "Meiva Labibah Putri", "Perempuan", 2023));
    }

    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();
        // tampilkan window
        window.setVisible(true);
    }
}
