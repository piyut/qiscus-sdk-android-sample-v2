# Qiscus Android Sample App

![login](https://i.imgur.com/wWZ75U1.png?1)
![list room](https://i.imgur.com/KB0kzRz.png?1)
![new chat](https://i.imgur.com/BXcL5i8.png?1)
![room chat](https://i.imgur.com/uMa7016.png?1)

## Sample Android App Features
- [x] Splash/Launch Screen
- [x] Login
- [x] Random user avatar
- [x] Room List
- [x] Contact List
- [x] Create New Chat
  - [x] Chat with Stranger
  - [x] Create Group
  - [x] Select Participant


## Try Sample App

To meet your expectations, we suggest you try out our sample app. The sample app is built with full functionalities so that you can figure out the flow and main activities of common chat apps.  We provide you with two options to start with the sample app: 
1) Try Sample App only or
2) Try Sample App with Sample Dashboard

### Try Sample App Only

If you simply want to try the android sample app, you can direct to our [github repository](https://github.com/qiscus/qiscus-sdk-android-sample-v2) to clone our sample app. You can explore features of Qiscus Chat SDK.

If you want your sample app running with your own APP ID, you can change it inside SampleApp.java.

```java
public class SampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Qiscus.init(this,"your APP ID");
    }
}
```

### Try Sample App With Sample Dashboard

If you have your own chat app, you may be wondering how you can manage your users. In this case, we provide a sample dashboard for user management. This sample dashboard can help you to figure out how to work with Qiscus Server Api for more advanced functionalities. You can go to https://www.qiscus.com/documentation/rest/list-api to know more about Server API.

> Note: We assume that you already downloaded the sample app. The sample app will be needed to work together with the sample dashboard.

You can explore the sample dashboard http://dashboard-sample.herokuapp.com/ to try it online, or you also can download the source code to deploy it locally or to your own server.

To start trying the sample dashboard on your end, you should carry out the following steps:
Clone sample dashboard in our github (https://github.com/qiscus/dashboard-sample), or just copy the following code.

```
git clone https://github.com/qiscus/dashboard-sample.git
cd dashboard-sample
```

Before running the sample app on your local, first, you need to install composer. 

```
composer install
php -S localhost:8000
```

>The sample dashboard provided Client API to enable your sample app get list of users. This API is based on PHP and used Composer as its dependency manager. Thus, you need to have PHP and composer installed to use the API.

Now you would have successfully run the sample dashboard. However, do note that the sample app is running using our App ID. If you want the sample dashboard to be connected to your app with your own App ID, you need to change the App ID and Secret Key in the sample dashboard login page. You can find your own App ID and Secret Key in your own [Qiscus SDK dashboard](https://www.qiscus.com/dashboard).

If you are wondering how our sample app with dashboard worked, here some ilustration :
<p align="center"><br/><img src="https://raw.githubusercontent.com/qiscus/qiscus-sdk-android/develop/screenshot/how_sample_work.png" width="80%" /><br/></p>

There are 2 Server API that are used inside Qiscus Sample Dashboard:

1. ```.qiscus.com/api/v2.1/rest/get_user_list``` to get list of users from Qiscus SDK database, and
2. ```.qiscus.com/api/v2/rest/login_or_register``` to enable user login or register via Sample Dashboard.

The Sample Dashboard called these APIs inside main.js file. To use these APIs, you need to pass your APP ID and  set method and request parameter.

To pass the APP ID, If you login to Sample Dashboard with your own APP ID and Secret Key, the APP ID and Secret Key has been saved, so that you need nothing to setup APP ID inside main.js.  

To set method and request parameter, you can refer to [Get User List](https://www.qiscus.com/documentation/rest/list-api#get-user-list) and [Login and Register](https://www.qiscus.com/documentation/rest/list-api#login-or-register) on Qiscus Server API Documentation.

The Sample Dashboard also provided API for client app to get list of users from the Sample Dasboard. 
To enable your client app to get list of users, you need to set your APP ID and Secret Key inside .env file. Then, you need to pass your domain name when calling the API.

```
//your-domain.com/api/contacts
Example: //dashboard-sample.herokuapp.com/api/contacts
```
You will get the response as follow:
```JSON
{
   "results":{
      "meta":{
         "total_data":123,
         "total_page":6
      },
      "users":[
         {
            "avatar_url":"https:\/\/d1edrlpyc25xu0.cloudfront.net\/kiwari-prod\/image\/upload\/75r6s_jOHa\/1507541871-avatar-mine.png",
            "created_at":"2017-12-05T08:07:58.405896Z",
            "email":"tesweqeq",
            "id":452773,
            "name":"tesweqeq",
            "updated_at":"2017-12-05T08:07:58.405896Z",
            "username":"tesweqeq"
         }
      ]
   },
   "status":200
}
```
