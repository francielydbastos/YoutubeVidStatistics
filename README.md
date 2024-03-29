# API to monitor Youtube Videos Stats

This API allows the user to monitor Youtube videos (via their url id) and automatically updates its statistics (views, likes and comments count) every 5 minutes.

# Available endpoints:
- POST /video/{id} : inserts (via the Youtube url id) new videos to be monitored
- GET /video/monitor : returns all the videos currently being monitored
- GET /video/monitor/{id} : returns information about the requested video id
- GET /videoStatistics/updateStatistics : forces the update of the statistics data of all the videos currently saved in the database 
- GET /videoStatistics/{id}/all : returns all the view stats currently saved in the database for the requested video id
- GET /videoStatistics/{id}/top20 : returns the top 20 most recent view stats currently saved in the database for the requested video id

