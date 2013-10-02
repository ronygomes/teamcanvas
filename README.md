#Team Canvas
*A project management application*
This project is hosted on [GoogleCode](https://code.google.com/p/teamcanvas)

_By_
Manuel Rony Gomes, Trainee Software Engineer, Therap Services LLC

###Introduction
Project management is the discipline of planning, organizing and controlling resources to achieve specific goals. It is an integral part to the business world. The primary challenge of project management is to achieve all of the project goals and objectives while honoring the preconceived constraints. Projects often fail due to the lack of proper organization and team collaboration. By definition, projects change, and we need to respond to changes. Large projects, especially those in the information technology sectors, fail because of having poor record. Multiple studies show that a significant share of projects overrun their original time lines or are never completed. IT failure rates is between 5% and 15%, which represents a loss of $50 billion to $150 billion per year in the United States.

Team Canvas is an is an online project management solution that helps managers and staff to work together more easily and productively. This system allows users to create, manage and collaborate with team within a managed and organized environment. 

###Scope
The scope of this project will be limited to those people who are looking for an online solution for project management. Team leaders and project managers are the main user of this application. After registering to the application Team leader can create a team by inviting other registered members. A team leader can create project and define its various phase. He can also create task and assign tasks to his group members. Every member can comment on various aspect of the project and can view their own and group progress as Gantt chart. 


###Users and their role
*Anonymous user:*
  * Anonymous user can register to the application.

*Registered user:*
Anonymous user becomes registered user by registering in the application. They can access all the features of the application.
  * They can create team
  * They can invite other people to join in team
  * They can create, edit, delete project.
  * They can create, edit, delete task
  * They can assign task to their group member
  * Can comment of their project
  * Can view Gantt chart
  * Can view archived projects  


###Use case of the system 

  # *Account creation:* An anonymous user can become registered user by registering in the application. Then they can access all the features of the application. When an anonymous user tries to register in this site they are provided with a simple form, which will ask for simple information like name, email, password. After providing these information and confirming email address they can log in to their dashboard.
  # *Team creation:* An registered user can create a team and invite other people to join the team. He will be treated as the administrator of the team. He can invite, add and delete members. He can also make other member administrator.
  # *Create project:* A administrator can create project. He can edit, delete and mark the project as close project. He can also add, delete member to the project.
  # *Create project phase:* At the beginning of the project a administrator has to define phase for the project. A simple software project may have planning, implementation, review, merge etc. Phase is define at the beginning and it can not be modified later.
  # *Create task:* A member or administrator can create task. The creator of the task can edit, delete the task. A member can not delete task of other members but can view them if he has privileges. An administrator can not also see the personal task of a member. 
  # *Assign task:* A administrator can assign task to the member of the group. He can then observe the progress of those tasks.
  # *Comment:* A member can provide, edit, delete comment on any phase of the project. His comment can view other member. 
  # *Inspect Gantt chart:* An administrator can inspect the progress of the project in a Gantt chart. A member can also view his own progress in Gantt chart. 
  # *View deadline calendar:* Member can view deadlines in a calendar. When they login to their dashboard the critical deadlines will be shown in the dashboard.  
  # *View archives:* Administrator can view his previous projects and inspect their Gantt chart and project summery. Projects needed to be close to send in archive section.
  # *Chatting:*  Members can chat among themselves. When a user login to his dashboard he can view the online user of that particular project. And by clicking on their name he can chat with other member.
  # *Download reports*: An administrator can download the project report as PDF. A member can also download his progress as PDF.

Details of used technologies, site mock ups, ER diagram can be found on project [Wiki](https://code.google.com/p/teamcanvas/w/list) page.
