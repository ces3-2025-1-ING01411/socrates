create database socrates;

use socrates;

create table socrates.users
(
    id         int auto_increment
        primary key,
    names      varchar(150) charset utf8mb4            not null,
    last_name  varchar(180) charset utf8mb4            null,
    birthdate  date                                    not null,
    email      varchar(100) charset utf8mb4            null,
    is_active  tinyint(1) default 0                    not null,
    phone      varchar(15) charset utf8mb4             null,
    gender     enum ('male', 'female') charset utf8mb4 null,
    password   varchar(40)                             not null,
    created_at timestamp  default CURRENT_TIMESTAMP    not null,
    updated_at timestamp  default CURRENT_TIMESTAMP    not null on update CURRENT_TIMESTAMP,
    deleted_at timestamp                               null
)
    charset = utf8mb3;

/*
SELECT *
FROM users;

UPDATE socrates.users
SET password = SHA1('taejunk13')
where id=1;
 */

INSERT INTO socrates.users
(names, last_name, birthdate, email, is_active, phone, gender, password)
VALUES
    ('Ana',      'Martínez',    '1987-11-10', 'ana.martinez@example.com',      1, '3123456789', 'female', SHA1('anaPass!23')),
    ('Carlos',   'Ramírez',     '1990-02-20', 'carlos.ramirez@example.com',   1, '3132345678', 'male',   SHA1('carlos123')),
    ('Laura',    'Gómez',       '1995-06-05', 'laura.gomez@example.com',      0, '3143456789', 'female', SHA1('lauraPwd789')),
    ('José',     'Fernández',   '1982-12-15', 'jose.fernandez@example.com',   1, '3154567890', 'male',   SHA1('josePass456')),
    ('Lucía',    'Rodríguez',   '1993-04-25', 'lucia.rodriguez@example.com',  1, '3165678901', 'female', SHA1('luciaSecret987')),
    ('Andrés',   'Hernández',   '1988-09-30', 'andres.hernandez@example.com', 0, '3176789012', 'male',   SHA1('andresKey321')),
    ('Sara',     'López',       '1991-08-18', 'sara.lopez@example.com',       1, '3187890123', 'female', SHA1('saraLock654')),
    ('Diego',    'Díaz',        '1984-01-22', 'diego.diaz@example.com',       1, '3198901234', 'male',   SHA1('diegoPass111'));

COMMIT;

select * from socrates.users;
select * from socrates.subjects;
select * from socrates.enrollment;


-- 2. Poblar SUBJECTS
INSERT INTO socrates.subjects (code, name, description, credits, faculty)
VALUES
    ('ENG101', 'Cálculo I',                 'Funciones, límites y derivadas básicas',         4, 'engineering'),
    ('ENG102', 'Física I',                  'Mecánica clásica y vectores',                    4, 'engineering'),
    ('COM101', 'Redacción I',               'Introducción a la redacción periodística',       3, 'communication'),
    ('COM201', 'TeoríasComun',             'Principales corrientes de la comunicación',       3, 'communication'),
    ('SPO101', 'Anatomía Deportiva',        'Sistemas muscular y óseo aplicados al deporte',  3, 'schoolOfSports'),
    ('SPO201', 'EntrenamientoFunc',         'Metodologías de entrenamiento funcional',        3, 'schoolOfSports'),
    ('SCI101', 'Química General',           'Fundamentos de química inorgánica',              4, 'basicSciences'),
    ('SCI201', 'Biología Celular',          'Estructura y función de la célula',              4, 'basicSciences');

-- 3. Poblar ENROLLMENT para el usuario id = 1
--    Asumimos que los ids de las asignaturas insertadas van del 1 al 8.
INSERT INTO socrates.enrollment (id_user, id_subject, date_enrollment, status, term)
VALUES
    (1, 1, '2025-01-15 10:00:00', 'finished',  '2024-2'),
    (1, 2, '2025-01-15 10:05:00', 'finished',  '2024-2'),
    (1, 3, '2025-02-01 08:00:00', 'active',    '2025-1'),
    (1, 4, '2025-02-01 08:05:00', 'active',    '2025-1'),
    (1, 5, '2025-02-01 08:10:00', 'scheduled', '2025-2'),
    (1, 6, '2025-02-01 08:15:00', 'scheduled', '2025-2'),
    (1, 7, '2024-08-20 09:00:00', 'canceled',  '2024-2'),
    (1, 8, '2024-08-20 09:05:00', 'inactive',  '2024-2');

COMMIT;