# SC2001-CAMS

updated 19/11/23 <br>

TBD: <br>
1. add javadoc<br>
<br>

some design considerations: <br>

<h5>CAMSApp:</h5>
This is where the entire program starts. The most important thing here is the 3 arraylist: staffs, camps, students. They are set to protected so only other classes in application package can access them. This means that outside classes such as staff and students cant directly access them. Youll see later how they can be accessed. Anyway at the start of the program CAMSApp calls FileProcessing.readDataFromFile() to populate the above 3 arraylist with objects, and end the end of the program these objects will be recorded back to the csv via FileProcessing.writeDataToFile().<br>

<h5>FileProcessing:</h5>
It has only 2 public methods, readDataFromFile() and writeDataToFile(), which is called once each only in CAMSApp as written above. So this means that throughout the program, any changes are made to the 3 arrayList (staffs, camps, students) only and not directly to the csv. Only at the end of program they will be written back to csv. I did this so that only this class will have to handle all the csv writing, all other classes does not have to care anything about csv parsing and stuff, so single responsibility theroem i guess. Now if you see how writeDataToFile() is implemented you can see that its a lazy approach. It simply overwrite the old data in the csv file and write everything from the 3 arraylist to csv. It does not do any insertion or deletion. Pretty slow and ineffficient, but less error prone, and much easier to implemenet.<br>

<h5>CampManager, EnquiryManager, SuggestionManager:</h5>
lets say for example student want to register for a camp, so he has the camp name and call registerForCamp(). What was done previously that there is a findCampByName() that return a camp object. Then within the registerForCamp() in student, it will do something like camp.addParticpant(this). This works of course, but what this mean is that a student can potentially access every single camp object avaliable. Maybe they cant use the methods in the camp object if the methods require a staff object as parameter, but still not very safe design if you ask me. Another example is student want to view all open camps. What was done previously was the student directly access the camps arraylist (which was previously set to public, v bad oop), and loop through each camp in camps arraylist and print out the details of that camp if it is open to the student. So yes in the both of the cases above (and in many other cases), the problem here is that the students or staff objects can direclty access the 3 big arraylist (staffs, camps, students) within their methods. Not very oop. So instead there is the CampManager class, which contain all static methods, that serve as a proxy/medium between the student/staff object and the 3 big arraylist. So the student/staff object has to go through the CampManager class to do stuff. The same thing applies for EnquiryManager and Suggestionmanager. These manager classes are within the same application package as CAMSApp so they can access the protected arraylist in CAMSAPP normally<br>

Of course not every method has to go though the manager classes. For example if a staff want to view his created camps, he dont have to go though the manager class as he has a arraylist of created camps as attribute, so he can just access that instead. But if he want to view all camps for example, he has to go though the manager class instead of you know like direclty accessing the big camps arraylist.<br>

<h5>UserInterface, StudentInterface, StaffInterface:</h5>
They handle the input and output. They ask user for input, call the methods by providing the input as arguments, then print to screen based on the output/exception thrown by the methods. The methods themselves dont do the input and output. The only exception are methods start with "view~". Then these methods will do printing and return nothing, as you can see in student class or staff class etc.<br>

<h5>Utility:</h5>
Bunch of nice to have static methods that dont belong anywhere else (for now).<br>

<h5>Camp, Student, Staff:</h5>
Not much to say about them. Standard classes. Methods within them are self explainatory.<br>

<h5>Exception:</h5>
Just created them on the fly. Maybe we do not need so many but it is wat it is.<br>

<h5>Current UML?</h5>
Staff and student inherit from User.<br>
CampCommittee and CampAttendee inherit from CampRole.<br>
CampCommittee and CampAttendee are part of Student (Composition) (Observe how in the Student.addCampCommittee() and addCampAttendee(), the attendee and committee are instanaited (via new) within the methods themselves and passed in to the methods instead.<br>
Enquiry and suggestion are part of camp (Composition) (okay this one the code is slightly erronous cos in Camp.addEnquiry() and addSuggestion() they are passed in and not instaniated; might fix that later i hope)<br>
CampInformation is also part of camp (Composition) (yes this one the code is also wrong but might be fixed later as well)<br>

There is absoultey no interface here which means very bad SOLID design, but I cant see any place that can fit a interface in can someone enlighten me pls.<br>



