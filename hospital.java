package hospital;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

class Patient {
    private static int idCounter = 1000;
    private int patientId;
    private String name;
    private String medicalHistory;

    public Patient(String name, String medicalHistory) {
        this.patientId = idCounter++;
        this.name = name;
        this.medicalHistory = medicalHistory;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    @Override
    public String toString() {
        return "Patient ID: " + patientId + "\nName: " + name + "\nMedical History: " + medicalHistory;
    }
}

class PatientService {
    private Map<Integer, Patient> patientMap;

    public PatientService() {
        patientMap = new HashMap<>();
    }

    public void registerPatient(Patient patient) {
        patientMap.put(patient.getPatientId(), patient);
    }

    public Patient getPatient(int patientId) {
        return patientMap.get(patientId);
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }
}

class Doctor {
    private String name;
    private List<Prescription> prescriptions;

    public Doctor(String name) {
        this.name = name;
        this.prescriptions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void writePrescription(Patient patient, String medication, String dosage) {
        Prescription prescription = new Prescription(patient.getPatientId(), medication, dosage);
        prescriptions.add(prescription);
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    @Override
    public String toString() {
        return "Doctor Name: " + name;
    }
}

class DoctorService {
    private List<Doctor> doctors;

    public DoctorService() {
        doctors = new ArrayList<>();
    }

    public void registerDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public Doctor getDoctor(String name) {
        for (Doctor doctor : doctors) {
            if (doctor.getName().equals(name)) {
                return doctor;
            }
        }
        return null;
    }

    public List<Doctor> getAllDoctors() {
        return doctors;
    }
}

class Pharmacy {
    private Map<Integer, List<Prescription>> prescriptionMap;

    public Pharmacy() {
        prescriptionMap = new HashMap<>();
    }

    public void addPrescription(Prescription prescription) {
        if (!prescriptionMap.containsKey(prescription.getPatientId())) {
            prescriptionMap.put(prescription.getPatientId(), new ArrayList<>());
        }
        prescriptionMap.get(prescription.getPatientId()).add(prescription);
    }

    public List<Prescription> getPrescriptions(int patientId) {
        return prescriptionMap.getOrDefault(patientId, new ArrayList<>());
    }
}


class PharmacyService {
    private Pharmacy pharmacy;

    public PharmacyService() {
        pharmacy = new Pharmacy();
    }

    public void addPrescription(Prescription prescription) {
        pharmacy.addPrescription(prescription);
    }

    public List<Prescription> getPrescriptions(int patientId) {
        return pharmacy.getPrescriptions(patientId);
    }
}


class Prescription {
    private int patientId;
    private String medication;
    private String dosage;

    public Prescription(int patientId, String medication, String dosage) {
        this.patientId = patientId;
        this.medication = medication;
        this.dosage = dosage;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getMedication() {
        return medication;
    }

    public String getDosage() {
        return dosage;
    }
    @Override
    public String toString() {
        return "Patient ID: " + patientId + "\nMedication: " + medication + "\nDosage: " + dosage;
    }
}


class Medicine {
    private String name;
    private String manufacturer;
    private double price;

    public Medicine(String name, String manufacturer, double price) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Medicine Name: " + name + "\nManufacturer: " + manufacturer + "\nPrice: $" + price;
    }
}

class MedicineService {
    private List<Medicine> medicines;

    public MedicineService() {
        medicines = new ArrayList<>();
    }

    public void addMedicine(Medicine medicine) {
        medicines.add(medicine);
    }

    public List<Medicine> getAllMedicines() {
        return medicines;
    }
}

class HospitalLoginPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public HospitalLoginPage() {
        setTitle("Hospital Management System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/LOGO2.png"));
        JLabel logoLabel = new JLabel(logoIcon);
        add(logoLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        inputPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        inputPanel.add(passwordField, gbc);

        add(inputPanel, BorderLayout.CENTER);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.equals("Luna") && password.equals("5413")) {
                    dispose();
                    hospital window = new hospital();
                    window.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                }
            }
        });

        setVisible(true);
    }
}

public class hospital extends JFrame implements Serializable {
    private static final long serialVersionUID = 1L;

    private JButton registerPatientButton;
    private JButton registerDoctorButton;
    private JButton viewPatientRecordButton;
    private JButton addMedicineButton;
    private JButton viewAllPatientsButton;
    private JButton viewAllDoctorsButton;
    private JButton viewAllMedicinesButton;
    private JTextArea outputTextArea;

    private PatientService patientService;
    private DoctorService doctorService;
    private PharmacyService pharmacyService;
    private MedicineService medicineService;
    private JButton writePrescriptionButton;
    private JButton viewPrescriptionsButton;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;

    public hospital() {
    	getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 13));
    	getContentPane().setBackground(new Color(176, 176, 176));
        setTitle("Hospital Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        patientService = new PatientService();
        doctorService = new DoctorService();
        pharmacyService = new PharmacyService();
        medicineService = new MedicineService();

        initializeComponents();
        setupListeners();

        setVisible(true);
    }

    private void initializeComponents() {
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(20, 0, 550, 50);
        titlePanel.setBackground(new Color(168, 153, 167));
        
       
        ImageIcon logoIcon = new ImageIcon("images/logo.png");
        getContentPane().setLayout(null);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(122, 16, -1, -1);
        
       
        JLabel titleLabel = new JLabel("HOSPITAL MANAGEMENT SYSTEM");
        titleLabel.setBounds(126, 5, 307, 22);
        titleLabel.setForeground(new Color(0, 4, 60));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.setLayout(null);
        
        
        titlePanel.add(logoLabel);
        titlePanel.add(titleLabel);
        
        
        getContentPane().add(titlePanel);
        
        lblNewLabel = new JLabel("New label");
        Image img=new ImageIcon(this.getClass().getResource("/file3.png")).getImage();
        lblNewLabel.setIcon(new ImageIcon(img));
        lblNewLabel.setBounds(49, 0, 63, 50);
        titlePanel.add(lblNewLabel);

        registerPatientButton = new JButton("Register Patient");
        registerPatientButton.setBounds(20, 60, 150, 30);
        registerPatientButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        registerPatientButton.setBackground(new Color(148, 163, 149));
        registerPatientButton.setForeground(new Color(0, 0, 0));
        getContentPane().add(registerPatientButton);

        registerDoctorButton = new JButton("Register Doctor");
        registerDoctorButton.setBounds(200, 60, 150, 30);
        registerDoctorButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        registerDoctorButton.setBackground(new Color(148, 163, 149));
        registerDoctorButton.setForeground(new Color(0, 0, 0));
        getContentPane().add(registerDoctorButton);

        viewPatientRecordButton = new JButton("View Patient Record");
        viewPatientRecordButton.setBounds(380, 60, 150, 30);
        viewPatientRecordButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        viewPatientRecordButton.setBackground(new Color(148, 163, 149));
        viewPatientRecordButton.setForeground(new Color(0, 0, 0));
        getContentPane().add(viewPatientRecordButton);

        addMedicineButton = new JButton("Add Medicine");
        addMedicineButton.setBounds(20, 110, 150, 30);
        addMedicineButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        addMedicineButton.setBackground(new Color(148, 163, 149));
        addMedicineButton.setForeground(new Color(0, 0, 0));
        getContentPane().add(addMedicineButton);

        viewAllPatientsButton = new JButton("View All Patients");
        viewAllPatientsButton.setBounds(200, 110, 150, 30);
        viewAllPatientsButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        viewAllPatientsButton.setBackground(new Color(148, 163, 149));
        viewAllPatientsButton.setForeground(new Color(0, 0, 0));
        getContentPane().add(viewAllPatientsButton);

        viewAllDoctorsButton = new JButton("View All Doctors");
        viewAllDoctorsButton.setBounds(380, 110, 150, 30);
        viewAllDoctorsButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        viewAllDoctorsButton.setBackground(new Color(148, 163, 149));
        viewAllDoctorsButton.setForeground(new Color(0, 0, 0));
        getContentPane().add(viewAllDoctorsButton);

        viewAllMedicinesButton = new JButton("View All Medicines");
        viewAllMedicinesButton.setBounds(20, 160, 150, 30);
        viewAllMedicinesButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        viewAllMedicinesButton.setBackground(new Color(148, 163, 149));
        viewAllMedicinesButton.setForeground(new Color(0, 0, 0));
        getContentPane().add(viewAllMedicinesButton);

        outputTextArea = new JTextArea();
        outputTextArea.setBounds(20, 210, 550, 150);
        outputTextArea.setBackground(new Color(200, 183, 199));
        outputTextArea.setEditable(false);
        getContentPane().add(outputTextArea);
        
        writePrescriptionButton = new JButton("Write Prescription");
        writePrescriptionButton.setBounds(200, 160, 150, 30);
        writePrescriptionButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        writePrescriptionButton.setBackground(new Color(148, 163, 149));
        writePrescriptionButton.setForeground(new Color(0, 0, 0));
        getContentPane().add(writePrescriptionButton);

        viewPrescriptionsButton = new JButton("View Prescriptions");
        viewPrescriptionsButton.setBounds(380, 160, 150, 30);
        viewPrescriptionsButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
        viewPrescriptionsButton.setBackground(new Color(148, 163, 149));
        viewPrescriptionsButton.setForeground(new Color(0, 0, 0));
        getContentPane().add(viewPrescriptionsButton);
        
        lblNewLabel_1 = new JLabel("New label");
        lblNewLabel_1.setBounds(371, 231, 46, 14);
        getContentPane().add(lblNewLabel_1);
        
        lblNewLabel_2 = new JLabel("New label");
        lblNewLabel_2.setBounds(380, 266, 46, 14);
        getContentPane().add(lblNewLabel_2);
    }

    private void setupListeners() {
        registerPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter patient name:");
                String medicalHistory = JOptionPane.showInputDialog("Enter patient medical history:");
                Patient patient = new Patient(name, medicalHistory);
                patientService.registerPatient(patient);
                outputTextArea.setText("Patient Registered:\n" + patient.toString());
            }
        });

        registerDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter doctor name:");
                Doctor doctor = new Doctor(name);
                doctorService.registerDoctor(doctor);
                outputTextArea.setText("Doctor Registered:\n" + doctor.toString());
            }
        });

        viewPatientRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int patientId = Integer.parseInt(JOptionPane.showInputDialog("Enter patient ID:"));
                Patient patient = patientService.getPatient(patientId);
                if (patient != null) {
                    outputTextArea.setText("Patient Record:\n" + patient.toString());
                } else {
                    outputTextArea.setText("Patient not found.");
                }
            }
        });

        addMedicineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter medicine name:");
                String manufacturer = JOptionPane.showInputDialog("Enter manufacturer:");
                double price = Double.parseDouble(JOptionPane.showInputDialog("Enter price:"));
                Medicine medicine = new Medicine(name, manufacturer, price);
                medicineService.addMedicine(medicine);
                outputTextArea.setText("Medicine Added:\n" + medicine.toString());
            }
        });

        viewAllPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Patient> patients = patientService.getAllPatients();
                StringBuilder sb = new StringBuilder();
                for (Patient patient : patients) {
                    sb.append(patient.toString()).append("\n\n");
                }
                outputTextArea.setText("All Patients:\n" + sb.toString());
            }
        });

        viewAllDoctorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Doctor> doctors = doctorService.getAllDoctors();
                StringBuilder sb = new StringBuilder();
                for (Doctor doctor : doctors) {
                    sb.append(doctor.toString()).append("\n\n");
                }
                outputTextArea.setText("All Doctors:\n" + sb.toString());
            }
        });

        viewAllMedicinesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Medicine> medicines = medicineService.getAllMedicines();
                StringBuilder sb = new StringBuilder();
                for (Medicine medicine : medicines) {
                    sb.append(medicine.toString()).append("\n\n");
                }
                outputTextArea.setText("All Medicines:\n" + sb.toString());
            }
        });
        
        writePrescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int patientId = Integer.parseInt(JOptionPane.showInputDialog("Enter patient ID:"));
                String medication = JOptionPane.showInputDialog("Enter medication:");
                String dosage = JOptionPane.showInputDialog("Enter dosage:");
                Doctor doctor = doctorService.getAllDoctors().get(0); 
                doctor.writePrescription(patientService.getPatient(patientId), medication, dosage);
                Prescription prescription = new Prescription(patientId, medication, dosage);
                pharmacyService.addPrescription(prescription);
                outputTextArea.setText("Prescription Written:\n" + prescription.toString());
            }
        });

        viewPrescriptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int patientId = Integer.parseInt(JOptionPane.showInputDialog("Enter patient ID:"));
                List<Prescription> prescriptions = pharmacyService.getPrescriptions(patientId);
                StringBuilder sb = new StringBuilder();
                for (Prescription prescription : prescriptions) {
                    sb.append(prescription.toString()).append("\n\n");
                }
                if (sb.length() > 0) {
                    outputTextArea.setText("Prescriptions for Patient ID " + patientId + ":\n" + sb.toString());
                } else {
                    outputTextArea.setText("No prescriptions found for Patient ID " + patientId);
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
            	  
                try {
                    new HospitalLoginPage();
                } catch (Exception e) {
                    e.printStackTrace();
               }
            }
        });
    }
};