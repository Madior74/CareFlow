-- =========================
-- ROLES
-- =========================
-- V1__initial_schema.sql
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE 
        CHECK (name IN ('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_SECRETARY', 'ROLE_STAFF')),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);



-- =========================
-- USERS
-- =========================
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_valid_email CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

-- =========================
-- USER_ROLES
-- =========================
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- =========================
-- PATIENTS
-- =========================
CREATE TABLE patient (
    id BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(255),
    date_of_birth DATE,
    gender VARCHAR(10),
    address VARCHAR(255) NOT NULL,
    blood_type VARCHAR(5),
    allergies TEXT,
    emergency_contact_name VARCHAR(100),
    emergency_contact_phone VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_valid_phone CHECK (phone ~ '^[0-9]{9}$'),
    CONSTRAINT chk_valid_birthdate CHECK (date_of_birth <= CURRENT_DATE)
);

-- =========================
-- DOCTORS
-- =========================
CREATE TABLE doctor (
    id BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL UNIQUE,
    specialty VARCHAR(100),
    date_of_birth DATE,
    hire_date DATE NOT NULL DEFAULT CURRENT_DATE,
    gender VARCHAR(10),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_doctor_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- =========================
-- STAFF
-- =========================
CREATE TABLE staff (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL,
    position VARCHAR(100),
    department VARCHAR(100),
    hire_date DATE NOT NULL DEFAULT CURRENT_DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_staff_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- =========================
-- AVAILABILITIES
-- =========================
CREATE TABLE availability (
    id BIGSERIAL PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_availability_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE,
    CONSTRAINT chk_availability_time CHECK (start_time < end_time)
);

-- =========================
-- APPOINTMENTS
-- =========================
CREATE TABLE appointments (
    id BIGSERIAL PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    appointment_time TIMESTAMP NOT NULL,
    duration_minutes INTEGER NOT NULL DEFAULT 30,
    type VARCHAR(20) NOT NULL DEFAULT 'consultation',
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
    reason TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE,
    CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE,
    CONSTRAINT chk_appointment_type CHECK (type IN ('premiere', 'suivi', 'urgence', 'consultation')),
    CONSTRAINT chk_appointment_status CHECK (status IN ('SCHEDULED', 'COMPLETED', 'CANCELLED', 'NO_SHOW')),
    CONSTRAINT chk_future_appointment CHECK (appointment_time > created_at),
    CONSTRAINT chk_positive_duration CHECK (duration_minutes > 0)
);

-- =========================
-- CONSULTATIONS
-- =========================
CREATE TABLE consultations (
    id BIGSERIAL PRIMARY KEY,
    appointment_id BIGINT NOT NULL UNIQUE,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    symptoms TEXT,
    observations TEXT,
    height DECIMAL(5,2),
    weight DECIMAL(5,2),
    temperature DECIMAL(4,1),
    blood_pressure VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_consultation_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE,
    CONSTRAINT fk_consultation_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE,
    CONSTRAINT fk_consultation_patient FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE
);

-- =========================
-- MEDICATIONS
-- =========================
CREATE TABLE medications (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    dosage_form VARCHAR(50),
    active_ingredient TEXT,
    strength VARCHAR(50),
    manufacturer VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- DIAGNOSTICS
-- =========================
CREATE TABLE diagnostics (
    id BIGSERIAL PRIMARY KEY,
    consultation_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    diagnosis_code VARCHAR(20),
    diagnosis_text TEXT NOT NULL,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_diagnostic_consultation FOREIGN KEY (consultation_id) REFERENCES consultations(id) ON DELETE CASCADE,
    CONSTRAINT fk_diagnostic_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE
);

-- =========================
-- PRESCRIPTIONS
-- =========================
CREATE TABLE prescriptions (
    id BIGSERIAL PRIMARY KEY,
    diagnostic_id BIGINT NOT NULL,
    medication_id BIGINT NOT NULL,
    dosage TEXT NOT NULL,
    duration_days INTEGER NOT NULL,
    quantity INTEGER,
    instructions TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_prescription_diagnostic FOREIGN KEY (diagnostic_id) REFERENCES diagnostics(id) ON DELETE CASCADE,
    CONSTRAINT fk_prescription_medication FOREIGN KEY (medication_id) REFERENCES medications(id) ON DELETE CASCADE,
    CONSTRAINT chk_prescription_status CHECK (status IN ('ACTIVE', 'COMPLETED', 'CANCELLED')),
    CONSTRAINT chk_positive_duration_days CHECK (duration_days > 0),
    CONSTRAINT chk_prescription_dates CHECK (end_date IS NULL OR start_date IS NULL OR end_date > start_date)
);

-- =========================
-- MEDICAL HISTORY
-- =========================
CREATE TABLE medical_history (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    condition_name VARCHAR(200) NOT NULL,
    condition_type VARCHAR(50) NOT NULL,
    diagnosis_date DATE,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_medical_history_patient FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE,
    CONSTRAINT chk_condition_type CHECK (condition_type IN ('CHRONIC', 'PAST', 'ALLERGY', 'SURGERY', 'FAMILY'))
);

-- =========================
-- Activity Log
-- =========================
CREATE TABLE activity_log (
    id BIGSERIAL PRIMARY KEY,
    type TEXT NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- INDEXES
-- =========================
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_is_active ON users(is_active);
CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX idx_user_roles_role_id ON user_roles(role_id);
CREATE INDEX idx_patient_name ON patient(lastname, firstname);
CREATE INDEX idx_patient_phone ON patient(phone);
CREATE INDEX idx_patient_email ON patient(email);
CREATE INDEX idx_doctor_user_id ON doctor(user_id);
CREATE INDEX idx_doctor_name ON doctor(lastname, firstname);
CREATE INDEX idx_doctor_specialty ON doctor(specialty);
CREATE INDEX idx_staff_user_id ON staff(user_id);
CREATE INDEX idx_staff_position ON staff(position);
CREATE INDEX idx_availability_doctor ON availability(doctor_id);
CREATE INDEX idx_availability_time ON availability(start_time, end_time);
CREATE INDEX idx_availability_range ON availability(doctor_id, start_time, end_time);
CREATE INDEX idx_appointment_doctor ON appointments(doctor_id);
CREATE INDEX idx_appointment_patient ON appointments(patient_id);
CREATE INDEX idx_appointment_time ON appointments(appointment_time);
CREATE INDEX idx_appointment_status ON appointments(status);
CREATE INDEX idx_appointment_doctor_time ON appointments(doctor_id, appointment_time);
CREATE INDEX idx_consultation_appointment ON consultations(appointment_id);
CREATE INDEX idx_consultation_patient ON consultations(patient_id);
CREATE INDEX idx_consultation_doctor ON consultations(doctor_id);
CREATE INDEX idx_medication_name ON medications(name);
CREATE INDEX idx_medication_active_ingredient ON medications(active_ingredient);
CREATE INDEX idx_diagnostic_consultation ON diagnostics(consultation_id);
CREATE INDEX idx_diagnostic_doctor ON diagnostics(doctor_id);
CREATE INDEX idx_prescription_diagnostic ON prescriptions(diagnostic_id);
CREATE INDEX idx_prescription_medication ON prescriptions(medication_id);
CREATE INDEX idx_prescription_status ON prescriptions(status);
CREATE INDEX idx_prescription_end_date ON prescriptions(end_date);
CREATE INDEX idx_medical_history_patient ON medical_history(patient_id);
CREATE INDEX idx_medical_history_type ON medical_history(condition_type);

-- =========================
-- DONNÉES INITIALES
-- =========================





-- =========================
-- FONCTIONS et TRIGGERS
-- =========================

-- Fonction pour updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Fonction pour vérifier les chevauchements
CREATE OR REPLACE FUNCTION check_appointment_overlap()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM appointments a
        WHERE a.doctor_id = NEW.doctor_id
        AND a.id != NEW.id
        AND a.status = 'SCHEDULED'
        AND NEW.appointment_time < (a.appointment_time + (a.duration_minutes * interval '1 minute'))
        AND (NEW.appointment_time + (NEW.duration_minutes * interval '1 minute')) > a.appointment_time
    ) THEN
        RAISE EXCEPTION 'Le rendez-vous chevauche un autre rendez-vous existant pour ce médecin';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers pour toutes les tables
CREATE TRIGGER trigger_update_roles_updated_at BEFORE UPDATE ON roles FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER trigger_update_users_updated_at BEFORE UPDATE ON users FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER trigger_update_patient_updated_at BEFORE UPDATE ON patient FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER trigger_update_doctor_updated_at BEFORE UPDATE ON doctor FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER trigger_update_staff_updated_at BEFORE UPDATE ON staff FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER trigger_update_availability_updated_at BEFORE UPDATE ON availability FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER trigger_update_appointments_updated_at BEFORE UPDATE ON appointments FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER trigger_update_consultations_updated_at BEFORE UPDATE ON consultations FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER trigger_update_medications_updated_at BEFORE UPDATE ON medications FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER trigger_update_diagnostics_updated_at BEFORE UPDATE ON diagnostics FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER trigger_update_prescriptions_updated_at BEFORE UPDATE ON prescriptions FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER trigger_update_medical_history_updated_at BEFORE UPDATE ON medical_history FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Trigger pour chevauchement rendez-vous
CREATE TRIGGER trigger_check_appointment_overlap BEFORE INSERT OR UPDATE ON appointments FOR EACH ROW EXECUTE FUNCTION check_appointment_overlap();

-- =========================
-- VUES UTILES
-- =========================

CREATE OR REPLACE VIEW v_doctor_statistics AS
SELECT 
    d.id as doctor_id,
    d.firstname || ' ' || d.lastname as doctor_name,
    d.specialty,
    COUNT(DISTINCT a.id) as total_appointments,
    COUNT(DISTINCT CASE WHEN a.status = 'COMPLETED' THEN a.id END) as completed_appointments,
    COUNT(DISTINCT CASE WHEN a.status = 'CANCELLED' THEN a.id END) as cancelled_appointments,
    COUNT(DISTINCT a.patient_id) as unique_patients
FROM doctor d
LEFT JOIN appointments a ON d.id = a.doctor_id
GROUP BY d.id, d.firstname, d.lastname, d.specialty;

CREATE OR REPLACE VIEW v_patient_full_history AS
SELECT 
    p.id as patient_id,
    p.firstname || ' ' || p.lastname as patient_name,
    p.phone,
    p.email,
    EXTRACT(YEAR FROM AGE(p.date_of_birth)) as age,
    p.blood_type,
    COUNT(DISTINCT a.id) as total_appointments,
    COUNT(DISTINCT c.id) as total_consultations,
    COUNT(DISTINCT di.id) as total_diagnostics,
    MAX(a.appointment_time) as last_appointment
FROM patient p
LEFT JOIN appointments a ON p.id = a.patient_id
LEFT JOIN consultations c ON a.id = c.appointment_id
LEFT JOIN diagnostics di ON c.id = di.consultation_id
GROUP BY p.id, p.firstname, p.lastname, p.phone, p.email, p.date_of_birth, p.blood_type;