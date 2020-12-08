# Checkflix - movie&series details search engine

This is a FrontEnd part of the Checkflix web app, created for the purpose of:
* Finding movies and series by their name & year of release
* Displaying details about their runtime, cast, etc.
* Showing user reviews available on TMDB website
* Showing basic analytics of user reviews available on IMDB website
* Searching for digital streaming/rent/buy providers
* Enrolling onto a watchlist, which will notify you when your selected movie/series is available via marked digital provider type in a given country

To run this web app:
1. Have both FrontEnd and BackEnd downloaded locally.
  - FrontEnd: https://github.com/Greem666/CheckFlix-FrontEnd
  - BackEnd: https://github.com/Greem666/CheckFlix-BackEnd
    - Side note: BackEnd uses Spring Cloud, and requires external configuration files to be supplied. It is currently configured to grab these files from GitHub uri https://github.com/Greem666/CheckFlix-cloud-config. You can instead elect to clone that repo, and have the app run from a local git repo holding these files. See application.properties in cloud-config-server for details.  
2. First, run BackEnd, by:
  - First, running ConfigApplication.java in cloud-config-server module
  - Second, running DiscoveryApplication.java in cloud-discovery-server module
  - Third, running GatewayApplication.java in cloud-gateway-server module (This application is currently glitchy, and might need a few attempts to start before it correctly detects Configuration Server! Just stop it if it bugs out, make sure Config Server is already running, wait a moment and try to start it again.)
  - Fourth, run in any order:
    - OmdbServiceApplication.java (watchflix-omdb-service), 
    - TmdbServiceApplication.java (watchflix-tmdb-service), 
    - WatchlistApplication.java (watchflix-watchlist), 
    - WebscraperApplication.java (watchflix-webscraper), 
    - AnalyticsApplication.java(watchflix-analytics)
3. Lastly, run FrontEnd by starting CheckflixFrontendApplication.java (src).
4. You can then access the frontend website on http://localhost:8080/.