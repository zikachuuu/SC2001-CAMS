# Camp Application and Management System (CAMs)

CAMs is an application for staff and students at Nanyang Technological University (NTU) to manage, view, and register for camps. It serves as a centralized hub for all camp-related activities.

## General Features
- **Login Requirement**: All users must log in with their NTU network user ID (the part before `@` in the email address) using a default password, which can be changed within the system.
- **Faculty Information**: Each user has associated faculty information, e.g., EEE, SCSE.
- **Initialization**: User lists for students and staff are initiated through file uploads.

## User Roles and Permissions

### Staff
- **Camp Management**: Create, edit, and delete camps.
- **Visibility Toggle**: Toggle camp visibility to "on" or "off."
- **Viewing**: View all camps and a list of camps they created.
- **Enquiries and Suggestions**: Respond to student enquiries and approve suggestions for camp changes.
- **Reports**:
  - Generate attendance reports for camps they manage, including participant roles, in txt or csv format.
  - Produce performance reports for camp committee members.
- **Camp Details**: Include name, dates, registration deadlines, user group restrictions, location, total and committee slots, description, and auto-assigned staff in charge.

### Students
- **Camp Viewing**: View camps open to their user group with "on" visibility.
- **Registration and Withdrawal**: Register for camps as an attendee or committee member, with constraints on date clashes and vacancies. Withdrawal updates remaining slots.
- **Enquiries**: Submit, view, edit, and delete enquiries about camps.
- **Status and History**: View registered camps and roles, and reflect committee status in the profile.

### Camp Committee Members
- **Role Management**: Cannot directly edit camp details but can suggest changes.
- **Reporting**: Generate detailed reports of camp attendees and their roles.
- **Points System**: Earn points for replying to enquiries and for accepted suggestions.
- **Enquiry Interaction**: View, reply, edit, and delete enquiries and suggestions.

## Assumptions
- **View Filters**: Users can filter camp lists by date, location, etc., with alphabetical order as the default.
- **Registration Mechanism**: Registration for camps and committees is automatic upon vacancy.
- **Slot Accounting**: Committee member slots are included in total camp slots.
