-- Create users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    profile_info JSONB,
    kyc_status VARCHAR(50),
    mfa_enabled BOOLEAN DEFAULT false,
    mfa_secret VARCHAR(255),
    mfa_backup_codes JSONB,
    oauth_provider VARCHAR(50),
    oauth_id VARCHAR(255)
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_kyc_status ON users(kyc_status);

-- Create roles table
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

CREATE INDEX idx_roles_name ON roles(name);

-- Create user_roles table
CREATE TABLE user_roles (
    user_id INTEGER REFERENCES users(id),
    role_id INTEGER REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);

-- Create properties table
CREATE TABLE properties (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    address TEXT,
    status VARCHAR(50)
);

CREATE INDEX idx_properties_status ON properties(status);

-- Create dao_llc table
CREATE TABLE dao_llc (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    registration_number VARCHAR(255) UNIQUE NOT NULL,
    creation_date DATE NOT NULL
);

CREATE INDEX idx_dao_llc_name ON dao_llc(name);
CREATE INDEX idx_dao_llc_registration_number ON dao_llc(registration_number);
CREATE INDEX idx_dao_llc_creation_date ON dao_llc(creation_date);

-- Create user_dao_llc table
CREATE TABLE user_dao_llc (
    user_id INTEGER REFERENCES users(id),
    dao_llc_id INTEGER REFERENCES dao_llc(id),
    PRIMARY KEY (user_id, dao_llc_id)
);

-- Create tags table
CREATE TABLE tags (
    property_id INTEGER REFERENCES properties(id),
    name VARCHAR(255) NOT NULL,
    value VARCHAR(255),
    CONSTRAINT unique_property_tag UNIQUE (property_id, name)
);

CREATE INDEX idx_tags_property_id ON tags(property_id);

-- Create launchpads table
CREATE TABLE launchpads (
    id SERIAL PRIMARY KEY,
    property_id INTEGER REFERENCES properties(id),
    creation_datetime TIMESTAMP NOT NULL,
    expiration_datetime TIMESTAMP NOT NULL,
    token_emission INTEGER NOT NULL,
    token_price NUMERIC(19, 4) NOT NULL,
    ticker VARCHAR(10) UNIQUE NOT NULL,
    contract VARCHAR(255) UNIQUE NOT NULL,
    min_buy NUMERIC(19, 4) NOT NULL,
    max_buy NUMERIC(19, 4) NOT NULL,
    is_pool_reached BOOLEAN DEFAULT false,
    dao_llc_id INTEGER REFERENCES dao_llc(id)
);

CREATE INDEX idx_launchpads_expiration_datetime ON launchpads(expiration_datetime);
CREATE INDEX idx_launchpads_ticker ON launchpads(ticker);
CREATE INDEX idx_launchpads_is_pool_reached ON launchpads(is_pool_reached);

-- Create images table
CREATE TABLE images (
    id SERIAL PRIMARY KEY,
    property_id INTEGER REFERENCES properties(id),
    image_url VARCHAR(255) NOT NULL
);

CREATE INDEX idx_images_property_id ON images(property_id);

-- Create documents table
CREATE TABLE documents (
    id SERIAL PRIMARY KEY,
    document_type VARCHAR(50) NOT NULL,
    url VARCHAR(255) NOT NULL,
    upload_date DATE NOT NULL
);

-- Create kyc_documents table
CREATE TABLE kyc_documents (
    id INTEGER PRIMARY KEY REFERENCES documents(id),
    user_id INTEGER REFERENCES users(id),
    verification_status VARCHAR(50),
    verification_date DATE
);

CREATE INDEX idx_kyc_documents_user_id ON kyc_documents(user_id);
CREATE INDEX idx_kyc_documents_verification_status ON kyc_documents(verification_status);
CREATE INDEX idx_kyc_documents_verification_date ON kyc_documents(verification_date);

-- Create property_documents table
CREATE TABLE property_documents (
    id INTEGER PRIMARY KEY REFERENCES documents(id),
    property_id INTEGER REFERENCES properties(id)
);

CREATE INDEX idx_property_documents_property_id ON property_documents(property_id);

-- Create llc_documents table
CREATE TABLE llc_documents (
    id INTEGER PRIMARY KEY REFERENCES documents(id),
    dao_llc_id INTEGER REFERENCES dao_llc(id)
);

CREATE INDEX idx_llc_documents_dao_llc_id ON llc_documents(dao_llc_id);
