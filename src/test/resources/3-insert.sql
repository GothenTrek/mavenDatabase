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
