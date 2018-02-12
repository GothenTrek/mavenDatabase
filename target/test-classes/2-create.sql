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