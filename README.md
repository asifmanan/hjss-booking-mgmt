# HJSS Booking Management System

## 1. Introduction
This document specifies the requirements for a booking management system for Hatfield Junior Swimming School (HJSS). The system will manage lesson bookings, user profiles, and provide reporting features to streamline operations and improve user experience for learners and coaches.

## 2. Functional Requirements

2.1. Lesson Management
Lessons are categorized into Grade 1, 2, 3, 4, and 5. Each lesson lasts one hour and accommodates up to 4 learners.
Lessons occur on Monday, Wednesday, Friday, and Saturday. Monday, Wednesday, and Friday have time slots at 4-5 pm, 5-6 pm, and 6-7 pm. Saturday has time slots at 2-3 pm and 3-4 pm.
Administrators can assign coaches to lessons, ensuring they are qualified to teach the respective grade.

### 2.2. Booking System
Learners can view the timetable by selecting a day, grade, or coach's name. The system displays available lessons based on the selection.
Booking constraints ensure learners only book lessons at their grade level or one level higher. After attending a higher-grade lesson, the learner's grade level in the system is updated accordingly.
Learners can change or cancel bookings if spots are available. Duplicate bookings are prohibited.

### 2.3. Learner Management
Learner profiles contain name, gender, age (between 4 and 11 years), emergency contact, and current grade level (0 to 5, with 0 indicating new to swimming).
The system supports adding new learners and managing existing profiles, ensuring data accuracy and compliance with constraints.

### 2.4. Reviews and Ratings
After attending a lesson, learners can submit a review and a numerical rating (1 to 5, with 1 being Very Dissatisfied and 5 being Very Satisfied).
The system records these reviews and ratings, associating them with the respective lesson and coach.

### 2.5. Reporting
Biweekly reports detail each learner's activities, 
- Number of lessons booked 
- Cancelled 
- Attended

Coach reports aggregate the: - 
- Ratings received, (presenting an average rating per coach alongside their name)
