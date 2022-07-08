INSERT INTO tag (id, description, name) VALUES (1, 'Java developer position', 'ReactJS');
INSERT INTO tag (id, description, name) VALUES (2, 'Java developer position', 'Java');
INSERT INTO tag (id, description, name) VALUES (3, 'Java developer position', 'Spring');

INSERT INTO department (id, description, name) VALUES (1, 'Students who need permenant US visa', 'Compro');

INSERT INTO address (id, city, state, zip) VALUES (1, 'Fairfield', 'Iowa', '52557');
INSERT INTO address (id, city, state, zip) VALUES (2, 'Fairfield', 'Iowa', '52557');

INSERT INTO faculty (id, active, email, first_name, last_logged_in, last_name, password, address_id, department_id)
VALUES (2, true, 'Lead@gmail.com', 'Johnfaculty', null, 'Doefaculty ', 'Prsard1997', 2, 1);

INSERT INTO student (id, active, email, first_name, last_logged_in, last_name, password, address_id, gpa, major_id)
VALUES (1, true, 'Lead@gmail.com', 'John', null, 'Doe', 'Prsard1997', 1, 3.85, 1);

INSERT INTO student_comment (id, comment, faculty_id, student_id) VALUES (1, 'This guy is eligible', 2, 1);

-- INSERT INTO job_ad (id, benefits, description, creator_id)
-- VALUES (1, '$50k sign in bonus', 'Microsoft Job Position', 1);
--
-- INSERT INTO job_ad (id, benefits, description, creator_id)
-- VALUES (2, '$40k sign in bonus', 'Google Job Position', 1);
--
-- INSERT INTO job_ad_tags (job_ad_id, tags_id)
-- VALUES (1,  1);

-- INSERT INTO job_ad_tags (job_ad_id, tags_id)
-- VALUES (1,  2);




