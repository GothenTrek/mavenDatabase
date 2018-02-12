DROP TABLE IF EXISTS `registered`, `teaches`, `student`, `staff`, `module`;

CREATE TABLE `student` (
	`student_Id` CHAR(6),
    `student_name` VARCHAR(100) NOT NULL,
    `degree_scheme` VARCHAR(100) NOT NULL,
PRIMARY KEY (`student_Id`)
)ENGINE = InnoDB;
	
CREATE TABLE `staff` (
	`staff_Id` CHAR(6),
    `staff_name` VARCHAR(100) NOT NULL,
    `staff_grade` VARCHAR(100) NOT NULL,
PRIMARY KEY (`staff_Id`)
)ENGINE = InnoDB;

CREATE TABLE `module` (
    `module_Id` CHAR(6),
    `module_name` VARCHAR(100) NOT NULL,
    `credits` TINYINT NOT NULL,
PRIMARY KEY (`module_Id`)
)ENGINE = InnoDB;

CREATE TABLE `registered` (
    `student_Id` CHAR(6),
    `module_Id` CHAR(6),
PRIMARY KEY (`student_Id`, `module_Id`),
INDEX `fk_registered_student_idx` (`student_Id` ASC),
CONSTRAINT `fk_registered_student`
    FOREIGN KEY (`student_Id`)
    REFERENCES `student` (`student_Id`)
    ON DELETE CASCADE,
INDEX `fk_registered_module_idx` (`module_Id` ASC),
CONSTRAINT `fk_registered_module`
    FOREIGN KEY (`module_Id`)
    REFERENCES `module` (`module_Id`)
    ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE `teaches` (
    `staff_Id` CHAR(6),
    `module_Id` CHAR(6),
PRIMARY KEY (`staff_Id`, `module_Id`),
INDEX `fk_teaches_staff_idx` (`staff_Id` ASC),
CONSTRAINT `fk_teaches_staff`
    FOREIGN KEY (`staff_Id`)
    REFERENCES `staff` (`staff_Id`)
    ON DELETE CASCADE,
INDEX `fk_teaches_module_idx` (`module_Id` ASC),
CONSTRAINT `fk_teaches_module`
    FOREIGN KEY (`module_Id`)
    REFERENCES `module` (`module_Id`)
    ON DELETE CASCADE
) ENGINE = InnoDB;

INSERT INTO `student` (`student_Id`, `student_name`, `degree_scheme`)
VALUES ('S10345', 'John Smith', 'BSc Computer Science'),
('S10346', 'Sian Evans', 'BSc Computer Science'),
('S10347', 'Sean Crossan', 'BSc Electronic Engineering'),
('S10348', 'Jamie McDonal', 'BSc Mathematics');

INSERT INTO `module` (`module_Id`, `module_name`, `credits`)
VALUES ('CS101', 'Introduction to Computing', 10),
('CS203', 'Data Structures and Algorithms', 10),
('CS204', 'Computer Architecture', 10),
('M101', 'Foundation Mathematics', 20);

INSERT INTO `staff` (`staff_Id`, `staff_name`, `staff_grade`)
VALUES ('E10010', 'Alan Turing', 'Senior Lecturer'),
('E10011', 'Tony Hoare', 'Reader'),
('E10012', 'Seymour Cray', 'Lecturer');

INSERT INTO `registered` (`student_Id`, `module_Id`)
VALUES ('S10345', 'CS101'),
('S10346', 'CS203'),
('S10346', 'CS204'),
('S10347', 'CS204'),
('S10348', 'M101'),
('S10348', 'CS101');

INSERT INTO `teaches` (`staff_id`, `module_Id`)
VALUES ('E10010', 'CS101'),
('E10011', 'CS203'),
('E10012', 'CS204'),
('E10010', 'CS204'),
('E10011', 'M101'),
('E10011', 'CS101');

SELECT `module`.`module_Id`, `module_name`
FROM `teaches`
JOIN `staff` ON `staff`.`staff_Id` = `teaches`.`staff_Id`
JOIN `module` ON `module`.`module_Id` = `teaches`.`module_Id`
WHERE `staff`.`staff_name` = 'Alan Turing';

SELECT `student`.`student_Id`, `student_name`
FROM `registered`
JOIN `student` ON `student`.`student_Id` = `registered`.`student_Id`
JOIN `module` ON `module`.`module_Id` = `registered`.`module_Id`
WHERE `module`.`module_name` = 'Computer Architecture';

SELECT `staff`.`staff_Id`, `staff_name`, `teaches`.`module_Id`
FROM `teaches`
JOIN `staff` ON `staff`.`staff_Id` = `teaches`.`staff_Id`
JOIN `registered` ON `registered`.`module_Id` = `teaches`.`module_Id`
JOIN `student` ON `student`.`student_Id` = `registered`.`student_Id`
WHERE `student_name` = 'John Smith';

SELECT `staff`.`staff_Id`, `staff_name`
FROM `teaches`
JOIN `staff` ON `staff`.`staff_Id` = `teaches`.`staff_Id`
GROUP BY `staff_name`
HAVING COUNT(*) > 1