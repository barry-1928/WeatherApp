# WeatherApp

The following are the general information and guidelines regarding the "WeatherApp".

1. The Weather App is an unofficial, free app which lets you search weather details for any city you desire.
2. The app first opens with a splash screen for 4 seconds.
3. The details of the app has been given in the "App Details" menu item of the Action Bar.
4. The app uses the Google PlaceAutocomplete Widget in which you may select the name of a city using the drop down and the information is shown in a fragment box showing the name of the city.
5. The app uses an API "http://api.openweathermap.org/data/2.5/weather?Query" to retrieve the information for the weather of the selected city.
6. The app uses"http://openweathermap.org/img/w/icon_id.png" for getting the image of the weather description. The image is loaded through the "Picasso" image-caching library in the activity. The icon_id is extracted from the API mentioned in point no 5.
7. The app contains a floating button at the bottom right end of the screen. Clicking on it redirects you to another page which contains your recent search history.
8. The recent search history page is implemented using a "List View" and a class which extends "BaseAdapter". This list contains the list of cities along with the dates and times at which the searches were made respectively.
9. Clicking on any of the items in the recent search history page shows a dialog box showing the weather details of that city, when it was searched.
10. I have used an SQLite database to store the list of the recently searched cities names and the time and date on which the search was made, and all the corresponding weather details.
11. I have made use AsyncTask to do the background thread operations of connecting the client(your mobile device) to the server(providing the API).

Hope you enjoy the app.

Thank You.

Developed by Rajat Bhatta.

106116066
