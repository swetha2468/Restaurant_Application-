
# SWEN_732_Group_4 - Getting Started

## Making and Submitting Changes

<ol>
  <li>Branch from the "main" branch to create a new feature branch [git branch branch_name].</li>
  <li>Checkout the new branch locally to switch to it for development [git checkout branch_name].</li>
  <li>Push new branch to main [git push]</li>
  <li>Do all development, committing and pushing changes as needed.</li>
  <li>---Finish Changes---</li>
  <li>Push final changes to the remote repository [git push]</li>
  <li>In GitHub, open pull request for changes. Navigate to "Pull Requests" and select "New Pull Request".</li>
  <li>In the pull request form select the "base" as "main" and the "compare" as "branch_name" and select "Create Pull Request"</li>
  <li>Add a comment documenting the reason for the pull request.</li>
  <li>In the upper right of the pull request, select Reviewers for the review (add everyone).</li>
  <li>Get (minimum) one other person code review and approval for changes.</li>
  <li>When changes have been approved click "Merge Pull Request" to merge.</li>
</ol>
  


## Configuring paths:

Search windows for "edit environment variables for your account"<br>
Hit "New..."<br>
Use Variable Name "JAVA_HOME"<br>
Use Variable value <em>\<directory where java is installed on your machine\></em>. NOT the bin, just root. NOTE: Need the JDK version and >JDK11.<br>
(ex. "C:\Program Files\Java\jdk-14.0.2")<br>


## Installing Extensions:

Open Visual Studio Code.<br>
<br>
Executes the following steps in the order (refer to number callouts in image below for reference <br>
<ol>
  <li>Find "Extensions" on the left toolbar</li>
  <li>Search for "Extension Pack for Java"</li>
  <li>Click "Install"</li>
</ol>

![Installing Extension](/_references/Installing%20Extension.png)

## Import project:
Clone this repository if not already done
Open Visual Studio Code<br>
Select "File / Open Folder"<br>
Point VS Code to the project folder ("group4") under this repository.<br>


## Run the JavaFX Application:
Open project in VS Code through "File / Open Recent" (If it has opened previously, otherwise see Import Project Section of this document)<br>
<br>
Executes the following steps in the order (refer to number callouts in image below for reference<br>
<ol>
  <li>Find the "Explorer" on the left toolbar</li>
  <li>Expand "Java Projects"</li>
  <li>Right Mouse Click on the Java Project "group4"</li>
  <li>click "Run" to run.</li>
</ol>

![Running Project](/_references/RunningJava.png)


# Backup:


## Making the project:
To create a new project from scratch (using a built-in maven template):<br>
<ol>
  <li>In Visual Studio Code, open the Command Palette (Ctrl+Shift+P)</li>
  <li>Select the command "Java: Create Java Project".</li>
  <li>Select the option "JavaFX" in the list</li>
  <li>Follow the prompts, which will help you scaffold a new JavaFX project via Maven Archetype</li>
</ol>
