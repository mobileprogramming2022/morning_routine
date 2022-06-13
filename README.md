# Morning Routine
## Application name
### **Morning Routine**
## Application Vision
> "One day One routine"


> "One thing a day that goes to me"


Through this app, achieving what user really want even for a small amount of time allows user to meet the truely who they are.

In a tired daily life, it can help improve self-care and self-esteem.
## Brief Description
### It's an application that allows you to practice Miracle Morning.

It helps self-development through exercise, study and custom functions.

* Why we choose "Miracle Morning" as achieving our vision?
     
        Miracle Morning is mainly trending in 2030 Koreans.
               
     * Evidence 1 : "Miracle Morning" has more than 320,000 hashtags on Instagram.
        
     * Evidence 2 : 20/30s answered Miracle Morning positively in a [survey](https://www.hankookilbo.com/News/Read/A2021033011030003661)
         

## Project Contents
There are 4 tabs in Applications.

Planning | Reward | Statistics | Options |
---|---|---|---|

## Contents In Details
* TAB1 : Planning

<img src = https://user-images.githubusercontent.com/36127653/173236401-d7ab652e-f029-4765-970d-dd860f2218b7.png width = "150" height = "350" /> <img src = https://user-images.githubusercontent.com/36127653/173236683-796f8ed4-8154-4e0c-9c67-ea098aad94dc.png width = "150" height = "350" /> <img src = https://user-images.githubusercontent.com/36127653/173236686-54c04ae9-f55b-495c-aae7-7784e966aa93.png width = "150" height = "350" />

* Alarm Screen(Night Alarm & Morning Alarm) : User can go to doing Acitivity

<img src = https://user-images.githubusercontent.com/36127653/173236813-f7a89d06-b7bd-4745-9bd0-4c68ee1fbd0f.png width = "150" height = "350" /> <img src = https://user-images.githubusercontent.com/36127653/173236818-6377d095-d87b-4f4f-aa4a-ef421e46b190.png width = "150" height = "350" />

* Doing Acitivity : Screen captures are the case for setting Goal as "Exercise", "10 steps". If user achieves the goal, then user can get **random** rewards.

<img src = https://user-images.githubusercontent.com/36127653/173237362-9de130d5-23d8-402b-82b7-3625982c92fc.png width = "150" height = "350" /> <img src = https://user-images.githubusercontent.com/36127653/173237464-9cb46f56-1b32-4de6-850b-b3b0df02413b.png width = "150" height = "350" />

* TAB2 : Reward : Users can place 


<img src = https://user-images.githubusercontent.com/36127653/173237520-51d9234a-9ba9-47e4-9e77-64e9d81061d8.png width = "150" height = "350" /> <img src = https://user-images.githubusercontent.com/36127653/173237521-35079c1f-8790-45c0-ac10-d329485bb2d9.png width = "150" height = "350" />  <img src = https://user-images.githubusercontent.com/36127653/173237522-6a61e7c8-d654-44e0-8b12-5c1eb3bfdd00.png width = "150" height = "350" />

* TAB3 : Statistics


<img src = https://user-images.githubusercontent.com/36127653/173237810-a6eff3a9-37af-425a-8032-3efae12fa5e6.png width = "150" height = "350" /> <img src = https://user-images.githubusercontent.com/36127653/173237815-5eb76c3f-39d1-410f-857f-80d9651d0279.png width = "150" height = "350" />  <img src = https://user-images.githubusercontent.com/36127653/173237816-f53d8d5f-1dee-4390-b0cf-0fcab61ac760.png width = "150" height = "350" />

* TAB4 : Options


<img src = https://user-images.githubusercontent.com/36127653/173237848-4ec47d6f-1152-473a-b8b0-c5a4b8469aa6.png width = "150" height = "350" /> <img src = https://user-images.githubusercontent.com/36127653/173237849-07fa481a-b664-4d21-9068-ec436cc17954.png width = "150" height = "350" />  <img src = https://user-images.githubusercontent.com/36127653/173237851-c8220541-1208-4ada-b4da-b78efa7bdb42.png width = "150" height = "350" />


## Used Technologies
1. 2 exact alarms
2. Using Firebase

## Code Simple Explanation
* java file

  Acitivty name | Explanation
  --|--|
  **Planning**|
  MainAcitivty|
  getTodayPlan|
  **Planning - Exercise**|
  AlarmSetAcitivity|
  WalkFlowAcitivity|
  **Planning - Study**|
  StudyInflationAcitivty|
  StudyTimerActivity|
  **Planning - Custom**|
  TodoAcitivity|
  TodoInflationAcitivity|
  **Alarm**|
  Alarm| broadcastreceiver
  MorningAlarmAcitivity|
  NightAlarmAcitivty|
  **Do Acitivty**|
  TimerAcitivty|
  **Reward**|
  getVillage|
  RewardActivity|
  getInventory|
  **Statistics**|
  getStats|
  GraphActivity|
  **Option**|
  OptionAcitivty|
  CopyrightAcitivity|
  DevelopersInfoActivity|
  getLastTimeVisited|
  
* resource(res) file


  * drawable
   
  name | Explanation
  --|--|
  book_icon.png, check_list.png, foot.png, graph.png, option_button_click.xml, options.png, plan.png |image for tab image
  dawm.png, dawnic_lancher_background.xml, dawnmx.png, gradation.xml, ic_launcher_background, |image for total background UI
  plus.png|image for add planning
  fail.png, success.png |image for statistics
  tile01 ~ tile 15 | assets for reward
  devscreen.png, img.png |image for option
  border.xml | 
  line.png | image for intent menu (not used in new version)
    
  * font


  name | Explanation
  --|--|
  fontstyle.xml | apply font to app
  pb.otf, pl.otf, pm.otf | font-weight per font
  
  * layout


  **Planning**
    
  name | Explanation
  --|--|
  activity_main.xml|
  activity_alarm_set.xml|
  activity_study_inflation.xml|
  activity_study_timer.xml|
  acitivity_timer.xml|
  activity_todo.xml|
  activity_todo_inflation.xml|
  activity_walk_flow.xml|
  
  **Alarm**
   
  name | Explanation
  --|--|
  acitivity_morning_alarm.xml| morning alarm activity
  activity_night_alarm.xml| night alarm acitivity
    
    **Reward**
  
  name | Explanation
  --|--|
  activity_reward.xml|
    
  **Statistics**
    
  name | Explanation
  --|--|
  activity_graph.xml|
  
  **Options**
    
  name | Explanation
  --|--|
  acitivty_option.xml|
  activity_option_service_info.xml|
  activity_developers_info.xml|
  activity_copyright.xml|


  * mipmap
  
  
  name | Explanation
  --|--|
  dawnic_launcher|
  dawnic_launcher_background|
  dawnic_launcher_foreground|
 
  * raw
 
 
  name | Explanation
  --|--|
  alarm_music_ex.mp3| morning alarm music
  alarm_music_ex2.mp3| night alarm music
  white_noise.mp3| music for study
 

  * values


  name | Explanation
  --|--|
  colors.xml| set of color that we used in app
  strings.xml| set of text that we used in app
  text_outline.xml|


* user-permission in android manifest file


  name | Explanation
  --|--|
  "com.android.alarm.permission.SET_ALARM"| user-permission for set alarm
  "android.permission.SCHEDULE_EXACT_ALARM"| user-permission for exact alarm
  "android.permission.INTERNET"| user-permission for using Firebase
  "android.permission.ACTIVITY_RECOGNITION"|
  "android.permission.ACCESS_NOTIFICATION_POLICY"|
  
## License That We Used
 
All License are written in App's option tab's **저작권 정보**.

## If you wants to use this program
* You need to make Login for sepeare Firebase
* Change Firebase Link for your own apps.
