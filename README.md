FootBall Fantasy Leaque

Description : Football Fantasy League is a spring boot application,containing a microservice to find standings of a team playing league football match using country name, league name and team name.

URL : http://localhost:8080/api/service/v1/team/position/
Request :  {"teamName":"Watford","countryName":"England","leagueName":"Championship"}
Response : {"country":"(100) - England","league":"(1000) - Championship","team":"(0) - Watford","positions":0}

Installation Steps:

1) git clone git@github.com:Hemant5171/fantasy-league.git
2) cd installation_directory 
3) excute: mvn clean install
4) execute java -jar -Dserver.port=8090 target/fantasy-league-0.0.1-SNAPSHOT.jar

Docker Steps:

1) Build 
   docker build --build-arg JAR_FILE=target/*.jar -t fantasy-league/1.0.0 .
2) Run
   docker run -p 8080:8080 fantasy-league/1.0.0
