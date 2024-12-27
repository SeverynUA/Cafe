CREATE TABLE IF NOT EXISTS Languages (
     Code VARCHAR(10) PRIMARY KEY,
    Name VARCHAR(50) NOT NULL
    );

INSERT INTO Languages (Code, Name) VALUES
    ('en', 'English'),
    ('fr', 'French'),
    ('es', 'Spanish'),
    ('ua', 'Ukrainian')
    ON CONFLICT (Code) DO NOTHING;


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

INSERT INTO Drinks (Price, Type) VALUES
    (2.99, 'Hot drink'),
    (1.49, 'Soft drink'),
    (3.99, 'Alcohol drink')
    ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS DrinkTranslations (
     Id SERIAL PRIMARY KEY,
     DrinkId INT NOT NULL,
    LanguageCode VARCHAR(10) NOT NULL,
    Name VARCHAR(100) NOT NULL,
    FOREIGN KEY (DrinkId) REFERENCES Drinks(Id) ON DELETE CASCADE,
    FOREIGN KEY (LanguageCode) REFERENCES Languages(Code) ON DELETE CASCADE
    );

INSERT INTO DrinkTranslations (DrinkId, LanguageCode, Name) VALUES
    (1, 'en', 'Coffee'),
    (2, 'en', 'Cola'),
    (3, 'en', 'Beer'),
    (1, 'ua', 'Кава'),
    (2, 'ua', 'Кола'),
    (3, 'ua', 'Пиво')
    ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS Desserts (
    Id SERIAL PRIMARY KEY,
    Price NUMERIC(10, 2) NOT NULL
    );

INSERT INTO Desserts (Price) VALUES
    (3.99),
    (5.49),
    (4.99)
    ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS Desserts_Translations (
    Id SERIAL PRIMARY KEY,
    DessertId INT NOT NULL,
    LanguageCode VARCHAR(10) NOT NULL,
    Name VARCHAR(100) NOT NULL,
    FOREIGN KEY (DessertId) REFERENCES Desserts(Id) ON DELETE CASCADE,
    FOREIGN KEY (LanguageCode) REFERENCES Languages(Code) ON DELETE CASCADE
    );

INSERT INTO Desserts_Translations (DessertId, LanguageCode, Name) VALUES
    (1, 'en', 'Chocolate Cake'),
    (2, 'en', 'Ice Cream'),
    (3, 'en', 'Cheesecake'),
    (1, 'ua', 'Шоколадний торт'),
    (2, 'ua', 'Морозиво'),
    (3, 'ua', 'Сирник')
    ON CONFLICT DO NOTHING;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'jobposition') THEN
CREATE TYPE JobPosition AS ENUM ('Barista', 'Waiter', 'Confectioner');
END IF;
END $$;


CREATE TABLE IF NOT EXISTS Employees
(
    Id SERIAL PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    MiddleName VARCHAR(50),
    Type JobPosition NOT NULL,
    HireDate DATE NOT NULL DEFAULT CURRENT_DATE
);

INSERT INTO Employees (FirstName, LastName, MiddleName, Type, HireDate) VALUES
    ('John', 'Doe', 'A', 'Barista', '2023-01-15'),
    ('Jane', 'Smith', 'B', 'Waiter', '2023-02-20'),
    ('Emily', 'Brown', 'C', 'Confectioner', '2023-03-10')
    ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS EmployeeContact (
    Id SERIAL PRIMARY KEY,
    EmployeeId INT NOT NULL,
    PhoneNumber VARCHAR(15) NOT NULL,
    Email VARCHAR(100),
    FOREIGN KEY (EmployeeId) REFERENCES Employees(Id) ON DELETE CASCADE
    );

INSERT INTO EmployeeContact (EmployeeId, PhoneNumber, Email) VALUES
    (1, '+123456789', 'john.doe@example.com'),
    (2, '+987654321', 'jane.smith@example.com'),
    (3, '+564738291', 'emily.brown@example.com')
    ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS EmployeeAddresses
(
    Id SERIAL PRIMARY KEY,
    EmployeeId INT NOT NULL,
    AddressLine1 VARCHAR(255) NOT NULL,
    AddressLine2 VARCHAR(255),
    City VARCHAR(100) NOT NULL,
    PostalCode VARCHAR(20) NOT NULL,
    Country VARCHAR(100) NOT NULL,
    FOREIGN KEY (EmployeeId) REFERENCES Employees(Id) ON DELETE CASCADE
    );

INSERT INTO EmployeeAddresses (EmployeeId, AddressLine1, AddressLine2, City, PostalCode, Country) VALUES
    (1, '123 Main St', '', 'New York', '10001', 'USA'),
    (2, '456 Elm St', 'Apt 2B', 'Los Angeles', '90001', 'USA'),
    (3, '789 Pine St', '', 'Chicago', '60601', 'USA')
    ON CONFLICT DO NOTHING;

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

INSERT INTO Customers (FirstName, LastName, MiddleName, BirthDate, Email, PhoneNumber, Sale) VALUES
    ('Alice', 'Johnson', 'D', '1985-05-10', 'alice.johnson@example.com', '+123456789', 10.0),
    ('Bob', 'Williams', 'E', '1990-07-20', 'bob.williams@example.com', '+987654321', 15.0),
    ('Charlie', 'Davis', 'F', '1975-12-30', 'charlie.davis@example.com', '+564738291', 20.0)
    ON CONFLICT DO NOTHING;
