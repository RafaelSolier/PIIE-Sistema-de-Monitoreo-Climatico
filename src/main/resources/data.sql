-- src/main/resources/data.sql

-- 1) INSERT en la tabla persona
INSERT INTO persona (
    dni, nombre, apellidos, celular, sexo
) VALUES
      ( '71351874', 'Yulinio',       'Zavala Mariño', '966570219', 'M'),
      ( '12345678', 'Miguel',        'Lozano',        '923456789', 'M'),
      ( '12345679', 'Rafael',        'Solier Soto',   '987654321', 'M');

-- 2) INSERT en la tabla usuario (usando el mismo id_persona)
INSERT INTO usuario (
    id_persona,
    rol,
    email,
    password,
    expired,
    locked,
    credentials_expired,
    enable
) VALUES
      (
          1,
          'ADMIN',
          'yulinio@gmail.com',
          -- bcrypt de 'yulinio'
          '$2b$12$9bL3VfOLjNNUGv/A/iaFdO.fQvIqW6C59mEnPKkDu17x9sGH.O9Gi',
          false,  -- expired
          false,  -- locked
          false,  -- credentials_expired
          true    -- enable
      ),
      (
          2,
          'ADMIN',
          'miguel@gmail.com',
          -- bcrypt de 'miguel'
          '$2b$12$6ZOw60yl9y2qIUCmPw7vz.H2Gbc/P3PxvaJfDlac2cH5clSc18avS',
          false,
          false,
          false,
          true
      ),
      (
          3,
          'ADMIN',
          'rafael@gmail.com',
          -- bcrypt de 'rafael'
          '$2b$12$CI1TRAWIgYrKf9xED0zKEeM0mstzQ3gLhgAN.pYQyHITmyDwBg3x.',
          false,
          false,
          false,
          true
      );
