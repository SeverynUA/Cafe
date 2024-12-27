CREATE TABLE IF NOT EXISTS Languages (
    Code VARCHAR(10) PRIMARY KEY,
    Name VARCHAR(50) NOT NULL
    );

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'drinktype') THEN
CREATE TYPE DrinkType AS ENUM ('Hot drink', 'Soft drink', 'Alcohol drink');
END IF;
END $$;

CREATE TABLE IF NOT EXISTS Drinks (
    Id SERIAL PRIMARY KEY,
    Price NUMERIC(10, 2),
    Type DrinkType NOT NULL
    );

CREATE TABLE IF NOT EXISTS DrinkTranslations (
    Id SERIAL PRIMARY KEY,
    DrinkId INT NOT NULL,
    LanguageCode VARCHAR(10) NOT NULL,
    Name VARCHAR(100) NOT NULL,
    FOREIGN KEY (DrinkId) REFERENCES Drinks(Id) ON DELETE CASCADE,
    FOREIGN KEY (LanguageCode) REFERENCES Languages(Code) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Desserts(
     Id SERIAL PRIMARY KEY,
     Price NUMERIC(10, 2) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Desserts_Translations (
    Id SERIAL PRIMARY KEY,
    DessertId INT NOT NULL,
    LanguageCode VARCHAR(10) NOT NULL,
    Name VARCHAR(100) NOT NULL,
    FOREIGN KEY (DessertId) REFERENCES Desserts(Id) ON DELETE CASCADE,
    FOREIGN KEY (LanguageCode) REFERENCES Languages(Code) ON DELETE CASCADE
    );

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'jobposition') THEN
CREATE TYPE JobPosition AS ENUM ('Barista', 'Waiter', 'Confectioner');
END IF;
END $$;

CREATE TABLE IF NOT EXISTS Employees (
    Id SERIAL PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    MiddleName VARCHAR(50),
    Type JobPosition NOT NULL,
    HireDate DATE NOT NULL DEFAULT CURRENT_DATE
    );

CREATE TABLE IF NOT EXISTS EmployeeContact (
    Id SERIAL PRIMARY KEY,
    EmployeeId INT NOT NULL,
    PhoneNumber VARCHAR(15) NOT NULL,
    Email VARCHAR(100),
    FOREIGN KEY (EmployeeId) REFERENCES Employees(Id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS EmployeeAddresses (
    Id SERIAL PRIMARY KEY,
    EmployeeId INT NOT NULL,
    AddressLine1 VARCHAR(255) NOT NULL,
    AddressLine2 VARCHAR(255),
    City VARCHAR(100) NOT NULL,
    PostalCode VARCHAR(20) NOT NULL,
    Country VARCHAR(100) NOT NULL,
    FOREIGN KEY (EmployeeId) REFERENCES Employees(Id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Customers (
    Id SERIAL PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    MiddleName VARCHAR(50),
    BirthDate DATE NOT NULL,
    Email VARCHAR(100),
    PhoneNumber VARCHAR(15),
    Sale DOUBLE PRECISION
    );
